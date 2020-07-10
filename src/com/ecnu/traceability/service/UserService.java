package com.ecnu.traceability.service;

import java.util.Iterator;
import java.util.List;

import com.ecnu.traceability.entity.PushInfo;
import com.ecnu.traceability.one_net.OneNETDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecnu.traceability.entity.User;
import com.ecnu.traceability.mapper.UserMapper;

@Transactional
@Service
public class UserService extends JudgeIsPushed {
    @Autowired
    private UserMapper userDao;

    public String getDeviceIdByMacAddress(String macAddress) {
        String deviceId = userDao.getDeviceIdOfUser(macAddress);
        if (null != deviceId) {
            return deviceId;
        } else {
            System.err.println("用户的deviceId为空");
            return null;
        }

    }

    public String addUser(User user) {
        String deviceId;

        try {//如果注册成功
            deviceId = OneNETDevice.addDevice(user.getMacaddress());
            if (deviceId != null) {
                user.setDeviceid(deviceId);
                try {//插入数据库
                    userDao.addUser(user);
                    return deviceId;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return userDao.getDeviceIdOfUser(user.getMacaddress());
            }

        } catch (Exception e) {//查看数据库中是否已经存在，如果已经存在则返回
            return userDao.getDeviceIdOfUser(user.getMacaddress());
        }

        return null;
    }

    public boolean updateUser(User user) {
        try {
            userDao.updateUser(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public User getUserByMacAddress(String macAddress) {

        return userDao.getUserByMacAddress(macAddress);
    }

    public List<User> getPatientUser() {
        return userDao.getPatientUser();
    }

    public List<String> getPatientMacAddress(String userMACAddress) {
        List<String> list = userDao.getPatientMacAddress();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String patientMac = (String) it.next();
            boolean flag = isPushed(patientMac, userMACAddress);
            if (flag) {
                it.remove();
            }
        }
        return list;
    }
}
