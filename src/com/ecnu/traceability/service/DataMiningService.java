package com.ecnu.traceability.service;


import com.ecnu.traceability.data_mining.dbscan.Cluster;
import com.ecnu.traceability.data_mining.dbscan.DBScan;
import com.ecnu.traceability.data_mining.dbscan.KMeansRun;
import com.ecnu.traceability.data_mining.dbscan.Point;
import com.ecnu.traceability.entity.LocationDataMiningResult;
import com.ecnu.traceability.entity.LocationInfo;
import com.ecnu.traceability.mapper.GPSLocationMapper;
import com.ecnu.traceability.mapper.LocationDMResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class DataMiningService {

    @Autowired
    private GPSLocationMapper GPSLocationDao;

    @Autowired
    private LocationDMResultMapper locationDMDao;

    public void showOnMap() {

        int batch = locationDMDao.getMaxBatch();
        try {
            excutePythonScript(batch);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void excutePythonScript(int batch) throws IOException, InterruptedException {
        String exe = "python";
        String command = "/Users/vector/desktop/show_on_map.py";
        String[] cmdArr = new String[]{exe, command, String.valueOf(batch)};
        Process process = Runtime.getRuntime().exec(cmdArr);
        InputStream is = process.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        String str = dis.readLine();
        System.out.println("=========执行结果========");
        System.out.println(str);
        System.out.println("=========执行结果========");

        process.waitFor();
    }

    public void cluster() throws Exception {
        ArrayList<double[]> dataSet = new ArrayList<double[]>();
        List<DBScan.Point_DBSCAN> points = new ArrayList<DBScan.Point_DBSCAN>();
        List<LocationInfo> locationList = GPSLocationDao.getGPSLocationList();
        int maxBatch = 0;
        if (null != locationDMDao.getMaxBatch()) {//数据库为空时会报错
            maxBatch = locationDMDao.getMaxBatch();
        }
        maxBatch++;
        //数据读取
        int i = 0;
        for (LocationInfo locationInfo : locationList) {
            if (!locationInfo.getLongitude().equals(0)) {
                List<Double> location = new ArrayList<Double>();

                double[] data = new double[2];
                data[0] = Double.parseDouble(locationInfo.getLatitude());
                data[1] = Double.parseDouble(locationInfo.getLongitude());
                dataSet.add(data);

                location.add(data[0]);
                location.add(data[1]);

                points.add(new DBScan.Point_DBSCAN(i++, location, 0));
            }

        }
        //数据处理

        DBScan.cluster(points);
        int key_point = DBScan.saveResult(locationDMDao, maxBatch);


        KMeansRun kRun = new KMeansRun(key_point, dataSet);
        Set<Cluster> clusterSet = kRun.run();
        //        System.out.println("单次迭代运行次数："+kRun.getIterTimes());

        List<LocationDataMiningResult> resultsList = new ArrayList<>();
        //        StringBuffer buffer = new StringBuffer();              // 用来存放获得中心点坐标
        for (Cluster cluster : clusterSet) {
            Point cent = cluster.getCenter();
            String cen = cent.toString();
            String[] temp = cen.split(" ");

            String lat = temp[2].substring(1);
            String lon = temp[3].substring(0, temp[3].length() - 1);

            resultsList.add(new LocationDataMiningResult(lat, lon, new Date(), maxBatch, "center"));
            //            buffer.append(lot + " " + lat + "\n");

        }


        System.out.println(resultsList.size());
        for (LocationDataMiningResult result : resultsList) {
            locationDMDao.addData(result);
        }

        //        saveResult(buffer, "src/center.txt");

        showOnMap();
    }


}
