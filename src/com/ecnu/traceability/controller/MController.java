package com.ecnu.traceability.controller;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore.Entry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ecnu.traceability.entity.*;
import com.ecnu.traceability.one_net.OneNETDevice;
import com.ecnu.traceability.service.*;
import com.ecnu.traceability.utils.Email;
import com.ecnu.traceability.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MController {

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ReportInfoService reportInfoService;

    @Autowired
    private PushInfoService pushInfoService;

    @Autowired
    private TransportationService transportationService;

    @Autowired
    private PatientDetailService patientDetailService;

    @Autowired
    private MachineLearningService machineLearningService;

    @Autowired
    private RelationshipService relationshipService;

    @Autowired OneNETService oneNETService;

    private boolean pushFlag = false;

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelMap test() throws IOException, InterruptedException {
        System.out.println("------------------------------------");
        ModelMap map = new ModelMap();
        map.put("abc", "测试成功");
        // machineLearningService.federatedAverage();
        machineLearningService.trainModel();
        //machineLearningService.pullModelFormClient();
        return map;
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    @ResponseBody
    public String addUser(@RequestBody Map<String, Object> models) {
        Boolean flag = models.get("flag").equals("true") ? true : false;
        return userService
                .addUser(new User(models.get("macAddress").toString(), models.get("deviceId").toString(), models.get("tel").toString(), flag));
    }

    @RequestMapping(value = "/tel/add", method = RequestMethod.POST)
    @ResponseBody
    public boolean addPatientTel(@RequestBody Map<String, Object> models) throws ParseException {
        String telphone = (String) models.get("telephone");
        String macAddress = (String) models.get("macAddress");
        Boolean flag = models.get("flag").equals("true") ? true : false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = sdf.parse((String) models.get("date"));

        return patientDetailService.addPatientDetail(new PatientDetail(telphone, macAddress, flag, date));
    }

    // 向推送信息表添加已经推送的记录
    @RequestMapping(value = "/pushedinfo/add", method = RequestMethod.POST)
    @ResponseBody
    public boolean addPushedInfo(@RequestBody Map<String, Object> models) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String patientMacAddress = (String) models.get("patientMacAddress");
        String userMacAddress = (String) models.get("userMacAddress");
        String date_ = (String) models.get("date");
        String disease = (String) models.get("disease");
        Date date = sdf.parse(date_);
        PushInfo pushInfo = new PushInfo(userMacAddress, patientMacAddress, date, disease);
        return pushInfoService.addPushInfo(pushInfo);
    }


    @RequestMapping(value = "/relationshipList/add", method = RequestMethod.POST)
    @ResponseBody
    public boolean addRelationship(@RequestBody JSONObject json) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        JSONArray array = json.getJSONArray("data");
        List<Relationship> relationshipList = new ArrayList<Relationship>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String originMac = obj.getString("originMac");
            String targetMac = obj.getString("targetMac");
            String date_ = obj.getString("date");
            int flag = (int) obj.get("flag");
            Date date = sdf.parse(date_);
            Relationship relationship = new Relationship(originMac, targetMac, date, flag);
            relationshipList.add(relationship);
        }

        return relationshipService.addRelationshipList(relationshipList);
    }

    // 测试
    @RequestMapping(value = "/getRelationship", method = RequestMethod.GET)
    @ResponseBody
    public List<Relationship> getRelationship() {
        return relationshipService.getRelationship();
    }

    // 测试
    @RequestMapping(value = "/getRelationshipByMac", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getRelationship(HttpSession session) {
        String mac = (String) session.getAttribute("mac");
        ModelMap map = new ModelMap();
        map.addAttribute("list", relationshipService.getRelationship());
        map.addAttribute("mac", mac);
        return map;
    }

    @RequestMapping("/gotoRelationshipDetail/{mac}")
    public String goToPatientIndex(@PathVariable String mac, HttpSession session) {
        session.setAttribute("mac", mac);
        return "homePage";
    }

    @RequestMapping(value = "/addPieChartData", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public boolean addPieChartData(@RequestBody JSONObject json) {
        String deviceId = userService.getDeviceIdByMacAddress(json.getString("macAddress"));
        if (null != deviceId) {
            try {
                OneNETDevice.pushPieChartData(deviceId, json.getJSONArray("datastreams"));
                return true;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @RequestMapping(value = "/addBarChartData", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public boolean addBarChartData(@RequestBody JSONObject json) {
        String deviceId = userService.getDeviceIdByMacAddress(json.getString("macAddress"));
        if (null != deviceId) {
            try {
                OneNETDevice.pushBarChartData(deviceId, json.getJSONArray("datastreams"));
                return true;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @RequestMapping(value = "/addRealtimeLocation", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public boolean addRealtimeLocation(@RequestBody JSONObject json) {
        String deviceId = userService.getDeviceIdByMacAddress(json.getString("macAddress"));
        if (null != deviceId) {
           JSONObject obj= json.getJSONObject("datastreams");
            oneNETService.addLocation(deviceId,obj);
            //  OneNETDevice.pushRealTimeLocation(deviceId, json.getJSONArray("datastreams"));
//            realTimeLocationMap.put(deviceId, json.getJSONObject("datastreams"));
            return true;
        }
        return false;
    }



    // 测试
    @RequestMapping(value = "/addLocationInfo", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public boolean addLocationInfo(@RequestBody JSONObject json) {
        JSONArray array = json.getJSONArray("data");
        List<LocationInfo> locationList = new ArrayList<LocationInfo>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                LocationInfo locInfo = new LocationInfo(obj.getString("macAddress"), obj.getString("latitude"),
                        obj.getString("longitude"), sdf.parse(obj.getString("date")));
                locationList.add(locInfo);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return locationService.addGPSLocation(locationList);
    }

    @RequestMapping(value = "/addReportInfo", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public boolean addReportInfo(@RequestBody JSONObject json) {
        JSONArray array = json.getJSONArray("data");
        List<ReportInfo> reportList = new ArrayList<ReportInfo>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                ReportInfo reportInfo = new ReportInfo(obj.getString("macAddress"), obj.getString("description"),
                        sdf.parse(obj.getString("date")));
                reportList.add(reportInfo);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return reportInfoService.addReportInfo(reportList);
    }

    @RequestMapping(value = "/addTransportationinfo", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public boolean addTransportationinfo(@RequestBody JSONObject json) {
        JSONArray array = json.getJSONArray("data");
        List<TransportationInfo> transportationInfoList = new ArrayList<TransportationInfo>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                TransportationInfo transportationInfo = new TransportationInfo(obj.getString("macAddress"),
                        obj.getString("type"), obj.getString("No"), obj.getString("seat"),
                        sdf.parse(obj.getString("date")));
                transportationInfoList.add(transportationInfo);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return transportationService.addTranpostationInfo(transportationInfoList);
    }

    @RequestMapping(value = "/getPatientData/{userMACAddress}", method = RequestMethod.GET)
    @ResponseBody
    public List<String> queryPatient(@PathVariable String userMACAddress) {
        return userService.getPatientMacAddress(userMACAddress);
    }

    // 测试
    @RequestMapping(value = "/getLocationInfo/{userMACAddress}/{patientMACAddress}", method = RequestMethod.GET)
    @ResponseBody
    public List<LocationInfo> queryPatientLocationInfo(@PathVariable String userMACAddress,
                                                       @PathVariable String patientMACAddress) {
        return locationService.getGPSLocationListByMacAddress(patientMACAddress, userMACAddress);
    }

    // 测试
    @RequestMapping(value = "/getReportInfo/{userMACAddress}/{patientMACAddress}", method = RequestMethod.GET)
    @ResponseBody
    public List<ReportInfo> queryPatientReportInfo(@PathVariable String userMACAddress,
                                                   @PathVariable String patientMACAddress) {
        return reportInfoService.getReportInfoByMacAddress(patientMACAddress);
    }

    // 测试
    @RequestMapping(value = "/getTransportationinfo/{userMACAddress}/{patientMACAddress}", method = RequestMethod.GET)
    @ResponseBody
    public List<TransportationInfo> queryPatientTransportationinfo(@PathVariable String userMACAddress,
                                                                   @PathVariable String patientMACAddress) {
        return transportationService.getTranpostationIndoByMacAddress(patientMACAddress, userMACAddress);
    }

    // 测试
    @RequestMapping(value = "/isPushed/{userMACAddress}/{patientMACAddress}", method = RequestMethod.GET)
    @ResponseBody
    public boolean queryPushInfo(@PathVariable String userMACAddress, @PathVariable String patientMACAddress) {
        System.out.println(userMACAddress + "\t" + patientMACAddress);
        return pushInfoService.isPushed(userMACAddress, patientMACAddress);
    }

    /**
     * 客户端模型上传
     *
     * @param macAddress
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload/model/{macAddress}", method = RequestMethod.POST)
    @ResponseBody
    public boolean uploadCheckRecordResultFile(@PathVariable String macAddress, HttpServletRequest request) {
        System.out.println("==================模型已经上传==================");
        String showPath = "";
        try {
            showPath = UploadUtil.uploadFile(request, macAddress.replaceAll(":", "_"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 客户端下载模型
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/download/model", method = RequestMethod.GET)
    @ResponseBody
    public void getFile(HttpServletResponse response) throws IOException {
        System.out.println("=====================模型已经共享到一个客户端========================");
        org.apache.commons.io.IOUtils.copy(machineLearningService.shareModel(), response.getOutputStream());
        response.flushBuffer();
    }
}
