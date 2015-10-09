package com.tnw.api.apifig;

/**
 * 接口配置文件
 * @author SHChen
 */
public interface ApiConfig {
	
	/**是否测试接口  默认 true(测试) */
	boolean IS_DEBUG = true;
	
	String API ="http://interface-m.tenongwang.com/";
	String API_DEBUG = API ;//"http://interface-m.17dalie.com:8000";
	
	/**接口api地址*/
	String API_DB_HOST = IS_DEBUG?API_DEBUG:API;

    /**
     * 每页显示的条数
     */
	int PAGESIZE = 20;

    /**
     * 接口错误信息
     */
	String ERROR_INFO = "error_info";

    /**
     * 接口参数key
     */
	String PARAM_KEY = "data";
	
}
