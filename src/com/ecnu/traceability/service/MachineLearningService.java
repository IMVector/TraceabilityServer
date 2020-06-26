package com.ecnu.traceability.service;

import com.ecnu.traceability.entity.PatientDetail;
import com.ecnu.traceability.utils.Email;
import junit.framework.TestListener;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.datavec.api.writable.Writable;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.api.Model;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.api.BaseTrainingListener;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MachineLearningService {
    private boolean initFlag = false;

    private ServletContext context;
    private String basePath;

    private static String originModelSuffix = "origin_model";
    private static String clientsModelSuffix = "clients_model";
    private static String updateModelSuffix = "updated_model";

    private static String originModel;
    private static String clientsModelPath;
    private static String updatedModel;

    public void init() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
        this.context = request.getSession().getServletContext();
        basePath = context.getRealPath(File.separator);

        clientsModelPath = basePath + File.separator + clientsModelSuffix + File.separator;
        originModel = basePath + File.separator + originModelSuffix + File.separator + "originModel.zip";
        updatedModel = basePath + File.separator + updateModelSuffix + File.separator + "updatedModel.zip";

        fileIsExist(clientsModelPath);
        fileIsExist(basePath + File.separator + originModelSuffix + File.separator);
        fileIsExist(basePath + File.separator + updateModelSuffix + File.separator);

        initFlag = true;
        System.out.println("=======================");
        System.out.println(basePath);

        //try {
        //    trainModel();
        //    federatedAverage();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
    }

    //判断文件夹是否存在，不存在创建
    public void fileIsExist(String path) {
        System.out.println(path + "-----------=============");
        File folder = new File(path);
        if (!folder.exists() && !folder.isDirectory()) {//文件夹不存在
            folder.mkdirs();//创建文件夹
        }
    }

    //执行联邦平均
    public void federatedAverage() {

        if (!initFlag) {
            init();
        }

        File dir = new File(clientsModelPath);//加载用户的模型
        File[] listOfFiles = dir.listFiles();
        List<File> models = new ArrayList<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                models.add(listOfFiles[i]);
            }
        }
        File originalModel = new File(originModel);
        AverageWeights(models, originalModel, 2, 0.0);
    }

    //执行参数平均
    public static void AverageWeights(List<File> files, File originModel, int layer, double alpha) {
        /*
            files indicates locations that mobile device uploaded model
            originModel is the model maintained by the server
            layerName is the layer to be averaged
            alpha is a coefficient indicates the weight of original model for the updated model
            currently, we just do transfer learning on the devices and we assume that it happens only at
            the last layer (i.e., the output layer) and keep other layers friezed. Therefore, we just need
            to average weights over the last layer.
         */
        // load original model
        MultiLayerNetwork model = null;
        try {
            model = ModelSerializer.restoreMultiLayerNetwork(originModel, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, INDArray> paramTable = model.paramTable();
        INDArray weight = paramTable.get(String.format("%d_W", layer));
        INDArray bias = paramTable.get(String.format("%d_b", layer));
        INDArray avgWeights = weight.mul(alpha);
        INDArray avgBias = bias.mul(alpha);

        // average weights over mobile devices' models
        int len = files.size();
        for (int i = 0; i < len; i++) {
            try {
                model = ModelSerializer.restoreMultiLayerNetwork(files.get(i), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            paramTable = model.paramTable();
            weight = paramTable.get(String.format("%d_W", layer));
            avgWeights = avgWeights.add(weight.mul(1.0 - alpha).div(len));
            bias = paramTable.get(String.format("%d_b", layer));
            avgBias = avgBias.add(bias.mul(1.0 - alpha).div(len));
        }
        model.setParam(String.format("%d_W", layer), avgWeights);
        model.setParam(String.format("%d_b", layer), avgBias);
        try {
            ModelSerializer.writeModel(model, updatedModel, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("联邦平均过程已完成");
    }

    public void trainModel() throws IOException, InterruptedException {

        if (!initFlag) {
            init();
        }

        int seed = 100;
        double learningRate = 0.001;
        int batchSize = 10;
        int nEpochs = 1000;

        int numInputs = 6;
        int numOutputs = 3;
        int numHiddenNodes = 1000;

        final String filenameTrain = basePath + File.separator + "resources" + File.separator + "res" +
                File.separator + "dataset" + File.separator + "COVID19.csv";
        System.out.println(filenameTrain);
        //final String modelPath = basePath + File.separator + "resources" + File.separator + "res" + File.separator + "model" + File.separator + "test.zip";


        //Load the training data:
        RecordReader rr = new CSVRecordReader();
        rr.initialize(new FileSplit(new File(filenameTrain)));
        List<Writable> text = rr.next();

        //Load the test/evaluation data:
        RecordReader rrTest = new CSVRecordReader();
        rrTest.initialize(new FileSplit(new File(filenameTrain)));
        DataSetIterator testIter = new RecordReaderDataSetIterator(rrTest,batchSize,0,3);
        for(Writable w:text){
            System.out.println(w.toDouble());
        }

        DataSetIterator trainIter = new RecordReaderDataSetIterator(rr, batchSize, 0, 3);

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .weightInit(WeightInit.XAVIER)
                .updater(new Nesterovs(learningRate, 0.9))
                .list()
                .layer(new DenseLayer.Builder().nIn(numInputs).nOut(numHiddenNodes)
                        .activation(Activation.RELU)
                        .build())
                .layer(new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
                        .activation(Activation.RELU)
                        .build())
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .activation(Activation.SOFTMAX)
                        .nIn(numHiddenNodes).nOut(numOutputs).build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new TestListener(10));  //Print score every 10 parameter updates

        model.fit(trainIter, nEpochs);

        boolean saveUpdate = true;

        File locationToSave = new File(originModel);

        ModelSerializer.writeModel(model, locationToSave, saveUpdate);
        System.out.println("机器学习过程已完成");
        System.out.println("machine learning process complete!");

        Evaluation eval = new Evaluation(numOutputs);
        while(testIter.hasNext()){
            DataSet t = testIter.next();
            INDArray features = t.getFeatures();
            INDArray labels = t.getLabels();
            INDArray predicted = model.output(features,false);

            eval.eval(labels, predicted);

        }

//        Print the evaluation statistics
        System.out.println(eval.stats());
    }

    public InputStream shareModel() {

        if (!initFlag) {
            init();
        }

        try {
            InputStream is = new FileInputStream(updatedModel);
            return is;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class TestListener  extends BaseTrainingListener implements Serializable {
        private final Logger log = LoggerFactory.getLogger(ScoreIterationListener.class);
        private int printIterations = 10;

        public TestListener(int printIterations) {
            this.printIterations = printIterations;
        }

        public TestListener() {
        }

        public void iterationDone(Model model, int iteration, int epoch) {
            if (this.printIterations <= 0) {
                this.printIterations = 1;
            }

            if (iteration % this.printIterations == 0) {
                double score = model.score();
                Map<String, INDArray> paramTable = model.paramTable();
                INDArray weight = paramTable.get(String.format("%d_W", 0));
                INDArray bias = paramTable.get(String.format("%d_b", 0));
                log.info("Score at iteration {} is {}", iteration, score);
            }

        }

        public String toString() {
            return "ScoreIterationListener(" + this.printIterations + ")";
        }
    }


    // 每24小时执行一次
    @Scheduled(cron = "0 0 0 1/1 * ?")
    public void federatedCombine() {

        if (!initFlag) {
            init();
        }

        File dir = new File(clientsModelPath);//加载用户的模型
        File[] listOfFiles = dir.listFiles();

        if(listOfFiles.length>2){
            federatedAverage();
        }

    }
}
