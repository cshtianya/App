package com.tnw.utils;

import java.util.List;
import java.util.Map;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @说明：Json解析
 */
public class GsonTools {

	public static <T> T getObject(String jsonData, Class<T> cls) {
		if (TextUtils.isEmpty(jsonData))
			return null;
		try {
			return JSON.parseObject(jsonData, cls);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	/**
	 * 使用Gson进行解析List
	 * 
	 * @param jsonData
	 * @param cls
	 * @return
	 */
	public static <T> List<T> getList(String jsonData, Class<T> cls) {
		try {
			return JSON.parseArray(jsonData, cls);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	/**
	 * 解析List里面有String
	 * 
	 * @param jsonData
	 * @return
	 */
	public static List<String> getList(String jsonData) {
		try {
			return JSON.parseArray(jsonData, String.class);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	/**
	 * 功能描述：把java对象转换成JSON数据
	 * 
	 * @param object
	 *            java对象
	 * @return
	 */
	public static String getJsonString(Object object) {
		try {
			return JSON.toJSONString(object);

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static List<Map<String, Object>> getlistMap(String jsonData) {
		try {
			return JSON.parseObject(jsonData,
					new TypeReference<List<Map<String, Object>>>() {
					});
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

}
