package com.ecnu.traceability.service;

import java.util.ArrayList;
import java.util.List;

import com.ecnu.traceability.entity.PatientDetail;
import com.ecnu.traceability.mapper.PatientDetailMapper;
import com.ecnu.traceability.one_net.OneNETDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecnu.traceability.entity.LocationInfo;
import com.ecnu.traceability.entity.PushInfo;
import com.ecnu.traceability.entity.User;
import com.ecnu.traceability.mapper.GPSLocationMapper;
import com.ecnu.traceability.mapper.PushInfoMapper;
import com.ecnu.traceability.mapper.UserMapper;

@Transactional
@Service
public class LocationService extends JudgeIsPushed {

    @Autowired
    private PushInfoMapper PushInfoDao;
    @Autowired
    private GPSLocationMapper gpsLocationDao;
    @Autowired
    private UserMapper userDao;

    @Autowired
    private PatientDetailMapper patientDetailDao;

    public boolean addGPSLocation(List<LocationInfo> gpsLocationInfoList) {
        if (null != gpsLocationInfoList && gpsLocationInfoList.size() > 0) {
            String macAddress = gpsLocationInfoList.get(0).getMacaddress();
//            String deviceId = userDao.getDeviceIdOfUser(macAddress);
            List<PatientDetail> patientList = patientDetailDao.getAllPatientDetail();
            if (null != patientList && patientList.size() > 0) {
                System.out.println("==========上传感染病例数量=======");
                OneNETDevice.pushRiskPeople("", patientList);
            }
            List<String> deviceIdList = new ArrayList<>();
            for (PatientDetail detail : patientList) {
                deviceIdList.add(userDao.getUserByMacAddress(detail.getMacAddress()).getDeviceid());
            }
            if(deviceIdList.size()>0){
                OneNETDevice.pushDeviceIdList("",deviceIdList);

            }
//            OneNETDevice.pushMapTraceData(deviceId, gpsLocationInfoList);
            User user = userDao.getUserByMacAddress(macAddress);
            if (null != user) {
                user.setFlag(false);
                userDao.updateUser(user);
            }
        }
        try {
            for (LocationInfo locinfo : gpsLocationInfoList) {
                gpsLocationDao.addGPSLocation(locinfo);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<LocationInfo> getGPSLocationListByMacAddress(String patientMacAddress, String userMacAddress) {
        if (!isPushed(patientMacAddress, userMacAddress)) {
            return gpsLocationDao.getGPSLocationListByMacAddress(patientMacAddress);
        } else {
            System.out.println("===========================error===================");
            return new ArrayList<LocationInfo>();
        }
    }


}
