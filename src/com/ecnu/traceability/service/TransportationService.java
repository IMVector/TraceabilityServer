package com.ecnu.traceability.service;

import java.util.ArrayList;
import java.util.List;

import com.ecnu.traceability.mapper.UserMapper;
import com.ecnu.traceability.one_net.OneNETDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecnu.traceability.entity.TransportationInfo;
import com.ecnu.traceability.mapper.PushInfoMapper;
import com.ecnu.traceability.mapper.TransportationInfoMapper;

@Transactional
@Service
public class TransportationService extends JudgeIsPushed {
    @Autowired
    private TransportationInfoMapper transportationDao;

    @Autowired
    private UserMapper userDao;

    //上传病人的乘车信息
    public boolean addTranpostationInfo(List<TransportationInfo> tranportationInfoList) {
        if (null != tranportationInfoList && tranportationInfoList.size() > 0) {
            String deviceId = userDao.getDeviceIdOfUser(tranportationInfoList.get(0).getMacaddress());
            if (null != deviceId) {
                OneNETDevice.pushTransportInfo(deviceId, tranportationInfoList);
            }
        }


        try {
            for (TransportationInfo info : tranportationInfoList) {
                transportationDao.addTranpostationInfo(info);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //推送病人的乘车信息
    public List<TransportationInfo> getTranpostationIndoByMacAddress(String patientMacAddress, String userMacAddress) {
        if (!isPushed(patientMacAddress, userMacAddress)) {
            return transportationDao.getTranpostationIndoByMacAddress(patientMacAddress);
        } else {
            return new ArrayList<TransportationInfo>();
        }

    }

}
