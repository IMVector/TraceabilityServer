package com.ecnu.traceability.controller;

import java.security.KeyStore.Entry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecnu.traceability.entity.LocationInfo;
import com.ecnu.traceability.entity.PatientDetail;
import com.ecnu.traceability.entity.PushInfo;
import com.ecnu.traceability.entity.ReportInfo;
import com.ecnu.traceability.entity.TransportationInfo;
import com.ecnu.traceability.entity.User;
import com.ecnu.traceability.service.LocationService;
import com.ecnu.traceability.service.PatientDetailService;
import com.ecnu.traceability.service.PushInfoService;
import com.ecnu.traceability.service.ReportInfoService;
import com.ecnu.traceability.service.TransportationService;
import com.ecnu.traceability.service.UserService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

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

	private boolean pushFlag = false;

	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ModelMap test() {
		System.out.println("------------------------------------");
		ModelMap map = new ModelMap();
		map.put("abc", "���Գɹ�");
		return map;
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	@ResponseBody
	public boolean addUser(@RequestBody Map<String, Object> models) {
		Boolean flag = models.get("flag").equals("true") ? true : false;
		return userService
				.addUser(new User(models.get("macAddress").toString(), models.get("deviceId").toString(), flag));
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

	// ��������Ϣ������Ѿ����͵ļ�¼
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

	// ����
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

	@RequestMapping(value = "/getPatientData", method = RequestMethod.GET)
	@ResponseBody
	public List<String> queryPatient() {
		return userService.getPatientMacAddress();
	}

	// ����
	@RequestMapping(value = "/getLocationInfo/{userMACAddress}/{patientMACAddress}", method = RequestMethod.GET)
	@ResponseBody
	public List<LocationInfo> queryPatientLocationInfo(@PathVariable String userMACAddress,
			@PathVariable String patientMACAddress) {
		return locationService.getGPSLocationListByMacAddress(patientMACAddress, userMACAddress);
	}

	// ����
	@RequestMapping(value = "/getReportInfo/{userMACAddress}/{patientMACAddress}", method = RequestMethod.GET)
	@ResponseBody
	public List<ReportInfo> queryPatientReportInfo(@PathVariable String userMACAddress,
			@PathVariable String patientMACAddress) {
		return reportInfoService.getReportInfoByMacAddress(patientMACAddress);
	}

	// ����
	@RequestMapping(value = "/getTransportationinfo/{userMACAddress}/{patientMACAddress}", method = RequestMethod.GET)
	@ResponseBody
	public List<TransportationInfo> queryPatientTransportationinfo(@PathVariable String userMACAddress,
			@PathVariable String patientMACAddress) {
		return transportationService.getTranpostationIndoByMacAddress(patientMACAddress, userMACAddress);
	}

	// ����
	@RequestMapping(value = "/isPushed/{userMACAddress}/{patientMACAddress}", method = RequestMethod.GET)
	@ResponseBody
	public boolean queryPushInfo(@PathVariable String userMACAddress, @PathVariable String patientMACAddress) {
		System.out.println(userMACAddress + "\t" + patientMACAddress);
		return pushInfoService.isPushed(userMACAddress, patientMACAddress);
	}

}
