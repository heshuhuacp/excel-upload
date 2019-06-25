package com.example.demo.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;

public class ExcelUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * 
	 * 通用的读取上传的Excel封装成List对象，但是要求必须Excel第一列就是跟Model属性对应上
	 * 
	 * @author heshuhua343
	 * @date 20180124
	 * @param file
	 *            上传的文件类
	 * @param list
	 *            返回结果
	 * @param clazz
	 *            最终封装的对象
	 * 
	 * @return MAP----key:errorMsg,key:data
	 */
	@SuppressWarnings("rawtypes")
	public static <T> Map<String, Object> readExcel(MultipartFile file, List<T> list, Class<T> clazz) {

		InputStream input = null;
		Workbook workBook = null;
		Map<String, Object> returnMap = new HashMap<>();
		// 错误信息接收器
		String errorMsg = "";
		try {

			// 获得这个类的所有方法
			input = file.getInputStream();
			workBook = new XSSFWorkbook(input);
			// 得到第一个sheet
			Sheet sheet = workBook.getSheetAt(0);
			// 得到Excel的行数
			int totalRows = sheet.getPhysicalNumberOfRows();
			// 总列数
			int totalCells = 0;
			// 第一行标题
			Row rowFilst = sheet.getRow(0);
			// 封装所有属性名字，类型
			Map<String, String> clazzTypeMap = getClazzType(clazz);
			// 得到Excel的列数(前提是有行数)，从第二行算起
			if (totalRows >= 2 && sheet.getRow(1) != null) {
				totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
			}
			T obj = null;
			String br = "<br/>";

			// 循环Excel行数,从第二行开始。标题不入库
			for (int r = 1; r < totalRows; r++) {
				String rowMessage = "";
				Row row = sheet.getRow(r);
				if (row == null) {
					errorMsg += br + "第" + (r + 1) + "行数据有问题，请仔细检查！";
					continue;
				}
				obj = clazz.newInstance();

				// 循环Excel的列
				for (int c = 0; c < totalCells; c++) {

					Cell cellName = rowFilst.getCell(c);
					Cell cellValue = row.getCell(c);

					if (null != cellValue) {
						// 属性
						String cellNameV = cellName.getStringCellValue().trim();
						String type = clazzTypeMap.get(cellNameV);
						Class attributeClazz = ClazzEnum.getClazzByType(type);
						// 属性类型
						cellNameV = cellNameV.substring(0, 1).toUpperCase() + cellNameV.substring(1); // 将属性的首字符大写，方便构造get，set方法
						Method m = clazz.getMethod("set" + cellNameV, attributeClazz);
						//String value = cellValue.getStringCellValue();

						m.invoke(obj, ClazzEnum.getOriginTypeValueByType(cellValue, attributeClazz));
					} else {
						rowMessage += "第" + (c + 1) + "列数据有问题，请仔细检查；";
					}
				}
				list.add(obj);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
				if (workBook != null) {
					workBook.cloneSheet(0);
				}
			} catch (IOException e) {
				logger.error("关闭流出现异常,{}", e);
			}
		}
		returnMap.put("errorMsg", errorMsg);
		returnMap.put("data", list);
		return returnMap;
	}

	/**
	 * 
	 * 功能描述: 获取一个类的所有属性，属性类型封装成Map<属性名字,属性类型>
	 * 
	 * @author heshuhua343
	 * @date
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getClazzType(Class clazz) {

		Field[] fields = clazz.getDeclaredFields();
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < fields.length; i++) {

			Field field = fields[i];
			String name = field.getName(); // 获取属性的名字
			String type = field.getGenericType().toString(); // 获取属性的类型
			map.put(name, type);

		}
		return map;
	}

}
