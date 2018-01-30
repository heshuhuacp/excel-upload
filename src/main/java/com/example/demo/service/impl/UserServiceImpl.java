package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor = Exception.class)
	public void batchInsertUser(List<User> userList){
		
		List<User> subList = new ArrayList<User>();
		while(userList!=null && userList.size()>1000){
			subList = userList.subList(0, 1000);
			userList = userList.subList(1000, userList.size());
			userMapper.batchInsertUser(userList);
		}
		userMapper.batchInsertUser(userList);
	}

}
