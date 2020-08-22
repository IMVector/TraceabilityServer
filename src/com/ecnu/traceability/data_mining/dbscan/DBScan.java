package com.ecnu.traceability.data_mining.dbscan;

import com.ecnu.traceability.entity.LocationDataMiningResult;
import com.ecnu.traceability.mapper.LocationDMResultMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class DBScan {
    private static final String fileName = "src/value.txt";
    private static final String resultFileName = "src/result.txt";
    private static final double r = 0.005; // 半径
    private static final int minPoints = 4;// 密度阈值
    private static List<List<Point_DBSCAN>> microCluster = new CopyOnWriteArrayList<>();

    public static class Point_DBSCAN {
        private int id; // 点的序列号
        private List<Double> location;// 点的坐标
        private int flag; // 0表示未处理，1表示处理了

        public Point_DBSCAN(int id, List<Double> location, int flag) {
            this.id = id;
            this.location = location;
            this.flag = flag;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<Double> getLocation() {
            return location;
        }

        public void setLocation(List<Double> location) {
            this.location = location;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + flag;
            result = prime * result + id;
            result = prime * result
                    + ((location == null) ? 0 : location.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Point_DBSCAN other = (Point_DBSCAN) obj;
            if (flag != other.flag)
                return false;
            if (id != other.id)
                return false;
            if (location == null) {
                if (other.location != null)
                    return false;
            } else if (!location.equals(other.location))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Point_DBSCAN [id=" + id + ", location=" + location + ", flag="
                    + flag + "]";
        }
    }

    public static List<Point_DBSCAN> readFile() throws Exception {
        BufferedReader file = new BufferedReader(new FileReader(fileName));
        List<Point_DBSCAN> points = new ArrayList<Point_DBSCAN>();
        String line = file.readLine();
        int i = 0;
        while (line != null) {
            String p[] = line.split("\t");
            List<Double> location = new ArrayList<Double>();
            for (int j = 0; j < p.length; j++) {
                location.add(Double.parseDouble(p[j]));
            }
            points.add(new Point_DBSCAN(i++, location, 0));
            line = file.readLine();
        }
        file.close();
        return points;
    }

    public static int saveResult(LocationDMResultMapper resultDao,int batch) throws Exception {
        List<LocationDataMiningResult> resultsList = new ArrayList<>();

//        BufferedWriter bw = new BufferedWriter(new FileWriter(resultFileName));
        int i = 1;
        for(List<Point_DBSCAN> mic : microCluster){
            for(Point_DBSCAN p  : mic){
                p.location.get(0);
                p.location.get(1);
                resultsList.add(new LocationDataMiningResult(p.location.get(0)+"",p.location.get(1)+""
                        ,new Date(),batch,"result"));

//                StringBuffer sb = new StringBuffer();
//                for(int j = 0;j<p.location.size();j++){
//                    sb.append(p.location.get(j)+",");
//                }
//                bw.write(sb.append(i).toString());
//                bw.newLine();
            }
            i++;
        }
//        bw.flush();
//        bw.close();
        for(LocationDataMiningResult result:resultsList){
            resultDao.addData(result);
        }
        return i;
    }

    public static double getDistance(Point_DBSCAN point1, Point_DBSCAN point2) {
        int wide = point1.location.size(); // 共多少维
        double sum = 0;
        for (int i = 0; i < wide; i++) {
            sum += Math.pow((point1.location.get(i) - point2.location.get(i)), 2);
        }
        return Math.sqrt(sum);
    }

    public static List<Double> getDistances(Point_DBSCAN point1, List<Point_DBSCAN> points) {
        List<Double> diss = new ArrayList<>();
        int size = points.size();
        for (int i = 0; i < size; i++)
            diss.add(getDistance(point1, points.get(i)));
        return diss;
    }

    public static boolean canCombine(List<Point_DBSCAN> clu1, List<Point_DBSCAN> clu2) {
        Set<Point_DBSCAN> result = new HashSet<Point_DBSCAN>();
        Set<Point_DBSCAN> s1 = new HashSet<Point_DBSCAN>(clu1);
        Set<Point_DBSCAN> s2 = new HashSet<Point_DBSCAN>(clu2);
        result.clear();
        result.addAll(s1);
        result.retainAll(s2);
        if (result.size() > 0) {
            return true;
        }
        return false;
    }

    public static void combine(List<Point_DBSCAN> clu){
        List<Integer> combine = new ArrayList<>();
        for(int i = 0;i< microCluster.size();i++){
            if(canCombine(clu,microCluster.get(i))) combine.add(i);
        }
        Set<Point_DBSCAN> com = new HashSet<>();
        List<List<Point_DBSCAN>> remove = new ArrayList<>();
        for(int i = 0;i<combine.size();i++){
            List<Point_DBSCAN> p = microCluster.get(combine.get(i));
            com.addAll(p);
            remove.add(p);
        }
        microCluster.removeAll(remove);
        microCluster.add(new ArrayList<>(com));
    }

    public static void cluster(List<Point_DBSCAN> points) {
        int size = points.size();
        for (int i = 0; i < size; i++) {            // 遍历所有的点
            Point_DBSCAN p = points.get(i);
            if (p.flag != 0) continue;
            p.flag = 1;
            List<Double> diss = getDistances(p, points);
            List<Point_DBSCAN> clu = new ArrayList<Point_DBSCAN>();
            for (int j = 0; j < size; j++) {
                if (diss.get(j) < r)   clu.add(points.get(j)); //若距离大于r
            }
            if(clu.size() < minPoints) continue;
            else {
                microCluster.add(clu);
                combine(clu);
            }
        }
    }

//    public static void main(String[] args) throws Exception {
//        long start = System.nanoTime();
//        List<Point_DBSCAN> points = readFile();
//        cluster(points);
//        saveResult();
//        System.out.println("use time:" + (System.nanoTime() - start));
//    }
}
