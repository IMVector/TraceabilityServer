//package com.ecnu.traceability.data_mining.dbscan;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//public class Main {
//
//    public static ArrayList<double[]> readFile(String fileName) throws Exception {
//        BufferedReader file = new BufferedReader(new FileReader(fileName));
//        ArrayList<double[]> dataSet = new ArrayList<double[]>();
//
////        dataSet.add(new double[] { 1, 2});
//
//        String line = file.readLine();
//        int i = 0;
//        while (line != null) {
//            String p[] = line.split("\t");
//            double[] location = new double[p.length];
//            for (int j = 0; j < p.length; j++) {
//                location[j] = Double.parseDouble(p[j]);;
//            }
//            dataSet.add(location);
//            line = file.readLine();
//        }
//        file.close();
////        System.out.println(dataSet.size());
//        return dataSet;
//    }
//
//    public static void saveResult(StringBuffer buffer, String resultFileName) throws Exception {
//        BufferedWriter bw = new BufferedWriter(new FileWriter(resultFileName));
//        bw.write(String.valueOf(buffer));
//        bw.flush();
//        bw.close();
//    }
//
//
//    public static void main(String[] args) throws Exception {
//        List<DBScan.Point_DBSCAN> points = DBScan.readFile();
//        DBScan.cluster(points);
//        int key_point = DBScan.saveResult();
//
//        ArrayList<double[]> dataSet = readFile("src/value.txt");
//
//        KMeansRun kRun =new KMeansRun(key_point, dataSet);
//
//        Set<Cluster> clusterSet = kRun.run();
////        System.out.println("单次迭代运行次数："+kRun.getIterTimes());
//
//        StringBuffer buffer = new StringBuffer();              // 用来存放获得中心点坐标
//        for (Cluster cluster : clusterSet) {
//            Point cent = cluster.getCenter();
//            String cen = cent.toString();
//            String[] temp = cen.split(" ");
//
//            String lot = temp[2].substring(1);
//            String lat = temp[3].substring(0, temp[3].length() - 1);
//
//            buffer.append(lot +" "+lat + "\n");
//
//        }
//        saveResult(buffer, "src/center.txt");
//    }
//}