package com.example.demo.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.util.ExcelUtil;

/**
 * @author heshuhua343
 * @date
 */
@Controller
public class ExcelUploadAction {

	private Logger logger = LoggerFactory.getLogger(ExcelUploadAction.class);

	@Autowired
	private UserService userService;

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

		List<User> list = new ArrayList<>();
		logger.info("从Excel读取数据封装成List...");
		Map<String, Object> dataMap = ExcelUtil.readExcel(file, list, User.class);
		@SuppressWarnings("unchecked")
		List<User> dataList = (List<User>) dataMap.get("data");
		logger.info("开始插入数据,每次1000条数据...");
		userService.batchInsertUser(dataList);
		String errorMsg = (String) dataMap.get("errorMsg");
		return resultMap;
	}

	
}
