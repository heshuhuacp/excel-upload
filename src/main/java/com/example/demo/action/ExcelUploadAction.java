package com.example.demo.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.demo.handler.UploadThread;
import com.example.demo.model.DataCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.BlackListSN;
import com.example.demo.service.BlackListSNService;
import com.example.demo.util.ExcelUtil;

/**
 * @author heshuhua343
 * @date
 */
@Controller
public class ExcelUploadAction {

	private static final Logger logger = LoggerFactory.getLogger(ExcelUploadAction.class);

	private static final int THREAD_SIZE = 5;

	@Autowired
	private BlackListSNService blackListSNService;

	@RequestMapping("/uploadExcel")
	public String uploadExcel() {

		return "uploadExcel";

	}

	@PostMapping("/excel/uploadExcelData")
	@ResponseBody
	public Map<String, String> uploadExcelData(@RequestParam("file") MultipartFile file) {

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("code", "success");
		resultMap.put("message", "上传文件成功");
		if (file == null) {
			resultMap.put("code", "fail");
			resultMap.put("message", "上传文件不能为空");
			return resultMap;
		}
		String fileName = file.getOriginalFilename();
		if (!fileName.endsWith(".xlsx")) {
			resultMap.put("code", "fail");
			resultMap.put("message", "必须上传Excel 2007及以上版本的Excel文件");
			return resultMap;
		}

		List<BlackListSN> list = new ArrayList<>();
		logger.info("从Excel读取数据封装成List...");
		Map<String, Object> dataMap = ExcelUtil.readExcel(file, list, BlackListSN.class);
		@SuppressWarnings("unchecked")
		List<BlackListSN> dataList = (List<BlackListSN>) dataMap.get("data");
		logger.info("开始插入数据,每次1000条数据...");
		batchInsertUser(dataList);
		String errorMsg = (String) dataMap.get("errorMsg");
		return resultMap;
	}

	public void batchInsertUser(List<BlackListSN> blackListSNList){

		DataCache dataCache = new DataCache(blackListSNList);

		ExecutorService executor = Executors.newFixedThreadPool(THREAD_SIZE);
		for (int i = 0; i < THREAD_SIZE; i++) {
			logger.info("开始插入数据,调用线程池处理");
			executor.execute(new UploadThread(dataCache, blackListSNService));
		}
		((ExecutorService) executor).shutdown();

	}

	
}
