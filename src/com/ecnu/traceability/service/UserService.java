package com.ecnu.traceability.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecnu.traceability.entity.User;
import com.ecnu.traceability.mapper.UserMapper;
@Transactional
@Service
public class UserService {
	@Autowired
	private UserMapper userDao;

	public boolean addUser(User user) {
		try {
			userDao.addUser(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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

	public List<String> getPatientMacAddress() {
		return userDao.getPatientMacAddress();
	}
}
