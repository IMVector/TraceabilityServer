package com.ecnu.traceability.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecnu.traceability.entity.User;

@Repository
public interface UserMapper {

    public void addUser(User user);

    public void updateUser(User user);

    public User getUserByMacAddress(String macAddress);

    public List<String> getPatientMacAddress();

    public List<User> getPatientUser();

    public String getDeviceIdOfUser(String macAddress);

}