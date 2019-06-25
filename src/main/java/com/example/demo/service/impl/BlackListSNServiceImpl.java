package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.BlackListSNMapper;
import com.example.demo.model.BlackListSN;
import com.example.demo.service.BlackListSNService;

@Service
public class BlackListSNServiceImpl implements BlackListSNService {
	
	@Autowired
	private BlackListSNMapper blackListSNMapper;
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor = Exception.class)
	public void batchInsertUser(List<BlackListSN> blackListSNList){

		blackListSNMapper.batchInsertBlackListSN(blackListSNList);
	}

}
