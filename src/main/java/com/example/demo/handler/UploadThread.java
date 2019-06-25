package com.example.demo.handler;

import com.example.demo.action.ExcelUploadAction;
import com.example.demo.model.BlackListSN;
import com.example.demo.model.DataCache;
import com.example.demo.service.BlackListSNService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UploadThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(UploadThread.class);

    private DataCache dataCache;

    private BlackListSNService blackListSNService;

    public UploadThread(DataCache dataCache, BlackListSNService blackListSNService){
        this.dataCache = dataCache;
        this.blackListSNService = blackListSNService;
    }

    @Override
    public void run() {
        logger.info("开始插入数据,进入线程内部...");
        while(true){
            List<BlackListSN> dataList = dataCache.getInstance();
            logger.info("开始插入数据,当前需要处理数据条数:size={}", dataList.size());
            if(dataList != null && dataList.size()>0){
                logger.info("开始插入数据,批量插入数据条数:size={}", dataList.size());
                blackListSNService.batchInsertUser(dataList);
            }else{
                break;
            }
        }
    }
}
