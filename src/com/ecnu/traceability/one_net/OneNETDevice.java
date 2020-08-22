package com.ecnu.traceability.one_net;

import cmcc.iot.onenet.javasdk.api.datapoints.AddDatapointsApi;
import cmcc.iot.onenet.javasdk.api.device.AddDevicesApi;
import cmcc.iot.onenet.javasdk.model.Data;
import cmcc.iot.onenet.javasdk.model.Datapoints;
import cmcc.iot.onenet.javasdk.model.Location;
import cmcc.iot.onenet.javasdk.response.BasicResponse;
import cmcc.iot.onenet.javasdk.response.device.NewDeviceResponse;

import com.alibaba.fastjson.JSONObject;
import com.ecnu.traceability.service.OneNETService;

import java.text.SimpleDateFormat;
import java.util.*;

public class OneNETDevice {
    public static final String key = "qsCeEuesQROBsUFzN4TcFaq2WzE=";

    public static void main(String[] args) {
        addDevice("E2:22:AB:EE:12:0A");
        addData("608614166");
    }

    public static String addDevice(String macAddress) {
        String title = macAddress;
        String desc = macAddress;
        String protocol = "HTTP";
        Location location = new Location(121.406, 31.227, 100);//设备位置{"纬度", "经度", "高度"}（可选）
        List<String> tags = new ArrayList<String>();
        tags.add("traceability");
        String auth_info = macAddress;//设备详细信息中的信息可以随便填写
        try {
            AddDevicesApi api = new AddDevicesApi(title, protocol, desc, tags, location, null, auth_info, null, null, key);
            BasicResponse<NewDeviceResponse> response = api.executeApi();
            System.out.println("errno:" + response.errno + " error:" + response.error);

            String s = response.getJson();

            JSONObject jsonObject = JSONObject.parseObject(s);
            JSONObject json = jsonObject.getJSONObject("data");
            String device_id = json.getString("device_id");
            return device_id;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        AddDevicesApi api = new AddDevicesApi(title, protocol, desc, tags, location, null, auth_info, null, null, key);
//        BasicResponse<NewDeviceResponse> response = api.executeApi();
//        System.out.println("errno:" + response.errno + " error:" + response.error);
//
//        String s=response.getJson();
//
//        JSONObject jsonObject = JSONObject.parseObject(s);
//        JSONObject json=jsonObject.getJSONObject("data");
//        String device_id=json.getString("device_id");
//        System.out.println(response.getJson());
//        System.out.println(s);

    }

    public static void addData(String deviceId) {
        List<Datapoints> list = new ArrayList<Datapoints>();
        List<Data> dataList = new ArrayList<Data>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataList.add(new Data(sdf.format(new Date()), dataList));
        list.add(new Datapoints("data_flow_4", dataList));
        Map<String, List<Datapoints>> map = new HashMap<String, List<Datapoints>>();
        map.put("datastreams", list);
        AddDatapointsApi api = new AddDatapointsApi(map, null, null, deviceId, key);
        BasicResponse<Void> response = api.executeApi();
        System.out.println("errno:" + response.errno + " error:" + response.error);
    }

    public static void pushMapTraceData(String deviceId, Object object) {
        List<Datapoints> list = new ArrayList<Datapoints>();
        List<Data> dataList = new ArrayList<Data>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataList.add(new Data("", object));
        list.add(new Datapoints("data_flow_1", dataList));
        Map<String, List<Datapoints>> map = new HashMap<String, List<Datapoints>>();
        map.put("datastreams", list);
        AddDatapointsApi api = new AddDatapointsApi(map, null, null, deviceId, key);
        BasicResponse<Void> response = api.executeApi();
        System.out.println("errno:" + response.errno + " error:" + response.error);
    }

    public static void pushReportInfo(String deviceId, Object object) {
        List<Datapoints> list = new ArrayList<Datapoints>();
        List<Data> dataList = new ArrayList<Data>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataList.add(new Data("", object));
        list.add(new Datapoints("data_flow_2", dataList));
        Map<String, List<Datapoints>> map = new HashMap<String, List<Datapoints>>();
        map.put("datastreams", list);
        AddDatapointsApi api = new AddDatapointsApi(map, null, null, deviceId, key);
        BasicResponse<Void> response = api.executeApi();
        System.out.println("errno:" + response.errno + " error:" + response.error);
    }
    public static void pushTransportInfo(String deviceId, Object object) {
        List<Datapoints> list = new ArrayList<Datapoints>();
        List<Data> dataList = new ArrayList<Data>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataList.add(new Data("", object));
        list.add(new Datapoints("data_flow_6", dataList));
        Map<String, List<Datapoints>> map = new HashMap<String, List<Datapoints>>();
        map.put("datastreams", list);
        AddDatapointsApi api = new AddDatapointsApi(map, null, null, deviceId, key);
        BasicResponse<Void> response = api.executeApi();
        System.out.println("errno:" + response.errno + " error:" + response.error);
    }
    public static void pushBarChartData(String deviceId, Object object) {
        List<Datapoints> list = new ArrayList<Datapoints>();
        List<Data> dataList = new ArrayList<Data>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataList.add(new Data("", object));
        list.add(new Datapoints("data_flow_4", dataList));
        Map<String, List<Datapoints>> map = new HashMap<String, List<Datapoints>>();
        map.put("datastreams", list);
        AddDatapointsApi api = new AddDatapointsApi(map, null, null, deviceId, key);
        BasicResponse<Void> response = api.executeApi();
        System.out.println("errno:" + response.errno + " error:" + response.error);
    }

    public static void pushPieChartData(String deviceId, Object json) {
        List<Datapoints> list = new ArrayList<Datapoints>();
        List<Data> dataList = new ArrayList<Data>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataList.add(new Data("", json));
        list.add(new Datapoints("data_flow_3", dataList));
        Map<String, List<Datapoints>> map = new HashMap<String, List<Datapoints>>();
        map.put("datastreams", list);
        AddDatapointsApi api = new AddDatapointsApi(map, null, null, deviceId, key);
        BasicResponse<Void> response = api.executeApi();
        System.out.println("errno:" + response.errno + " error:" + response.error);
    }

    public static void pushRealTimeLocation(String deviceId, Object json) {
        List<Datapoints> list = new ArrayList<Datapoints>();
        List<Data> dataList = new ArrayList<Data>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataList.add(new Data("", json));
        list.add(new Datapoints("data_flow_5", dataList));
        Map<String, List<Datapoints>> map = new HashMap<String, List<Datapoints>>();
        map.put("datastreams", list);
        AddDatapointsApi api = new AddDatapointsApi(map, null, null, deviceId, key);
        BasicResponse<Void> response = api.executeApi();
        System.out.println("errno:" + response.errno + " error:" + response.error);
    }

    public static void pushOnlineDeviceNum(String deviceId,Object json) {
        List<Datapoints> list = new ArrayList<Datapoints>();
        List<Data> dataList = new ArrayList<Data>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataList.add(new Data("", json));
        list.add(new Datapoints("data_flow_7", dataList));
        Map<String, List<Datapoints>> map = new HashMap<String, List<Datapoints>>();
        map.put("datastreams", list);
        try{
            AddDatapointsApi api = new AddDatapointsApi(map, null, null, deviceId, key);
            BasicResponse<Void> response = api.executeApi();
            System.out.println("errno:" + response.errno + " error:" + response.error);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void pushRiskPeople(String deviceId,Object json) {
        List<Datapoints> list = new ArrayList<Datapoints>();
        List<Data> dataList = new ArrayList<Data>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataList.add(new Data("", json));
        list.add(new Datapoints("infection_count", dataList));
        Map<String, List<Datapoints>> map = new HashMap<String, List<Datapoints>>();

        map.put("datastreams", list);
        try{
            AddDatapointsApi api = new AddDatapointsApi(map, null, null, "619900399", "dWkW7tJtWTeM0xOaR4Dh5x8mn8U=");
            BasicResponse<Void> response = api.executeApi();
            System.out.println("errno:" + response.errno + " error:" + response.error);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void pushDeviceIdList(String deviceId,Object json) {
        List<Datapoints> list = new ArrayList<Datapoints>();
        List<Data> dataList = new ArrayList<Data>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataList.add(new Data("", json));
        list.add(new Datapoints("patient_device_ids", dataList));
        Map<String, List<Datapoints>> map = new HashMap<String, List<Datapoints>>();

        map.put("datastreams", list);
        try{
            AddDatapointsApi api = new AddDatapointsApi(map, null, null, "619900399", "dWkW7tJtWTeM0xOaR4Dh5x8mn8U=");
            BasicResponse<Void> response = api.executeApi();
            System.out.println("errno:" + response.errno + " error:" + response.error);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void pushIsolateNum(Object json){
        List<Datapoints> list = new ArrayList<Datapoints>();
        List<Data> dataList = new ArrayList<Data>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataList.add(new Data("", json));
        list.add(new Datapoints("isolate_num", dataList));
        Map<String, List<Datapoints>> map = new HashMap<String, List<Datapoints>>();

        map.put("datastreams", list);
        try{
            AddDatapointsApi api = new AddDatapointsApi(map, null, null, "619900399", "dWkW7tJtWTeM0xOaR4Dh5x8mn8U=");
            BasicResponse<Void> response = api.executeApi();
            System.out.println("errno:" + response.errno + " error:" + response.error);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void pushNoIsolateNum(Object json){
        List<Datapoints> list = new ArrayList<Datapoints>();
        List<Data> dataList = new ArrayList<Data>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataList.add(new Data("", json));
        list.add(new Datapoints("non_isolate_num", dataList));
        Map<String, List<Datapoints>> map = new HashMap<String, List<Datapoints>>();

        map.put("datastreams", list);
        try{
            AddDatapointsApi api = new AddDatapointsApi(map, null, null, "619900399", "dWkW7tJtWTeM0xOaR4Dh5x8mn8U=");
            BasicResponse<Void> response = api.executeApi();
            System.out.println("errno:" + response.errno + " error:" + response.error);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void pushIsolateMonthData(Object json){
        List<Datapoints> list = new ArrayList<Datapoints>();
        List<Data> dataList = new ArrayList<Data>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataList.add(new Data("", json));
        list.add(new Datapoints("isolate_month_data", dataList));
        Map<String, List<Datapoints>> map = new HashMap<String, List<Datapoints>>();

        map.put("datastreams", list);
        try{
            AddDatapointsApi api = new AddDatapointsApi(map, null, null, "619900399", "dWkW7tJtWTeM0xOaR4Dh5x8mn8U=");
            BasicResponse<Void> response = api.executeApi();
            System.out.println("errno:" + response.errno + " error:" + response.error);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
