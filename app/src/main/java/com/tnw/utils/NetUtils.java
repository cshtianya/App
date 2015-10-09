package com.tnw.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * {@code NetUtils}网络工具类<br>
 * <li></li>Purpose：判断网络状态与模式<br>
 * <li></li>Rules:该类多为静默方法,可直接通过{@code NetUtils}对象直接调用<br>
 * <li></li>对外方法：
 * {@link #isNetworkAvailable},
 *
 * <p>该类未提供实例对象，目前该类中对外方法都是静态方法，开发人员可根据实际需求，逐步完善改优化工具类 。
 * <br>添加或更新内容请注明创建时间以及人员，规范方法的使用说明
 * 
 * @author ChenSh
 * @E-mail csh_tianya@163.COM
 * @data 2012-11-14
 * @version 1.0
 * @category Utility class.
 */
public class NetUtils {
	
	/**
	 * Judge the network is available;
	 * @param context The context to use.  Usually your {@link android.app.Application}
     *        or {@link android.app.Activity} object.
	 * @return true if the network is available, false otherwise
	 * @category 静态公用方法 
	 */
	public static boolean isNetworkAvailable(Context context){
		ConnectivityManager manager = (ConnectivityManager)
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if(networkinfo==null)
			return false;
		return networkinfo.isAvailable() && networkinfo.isConnected();
	}
	

}
