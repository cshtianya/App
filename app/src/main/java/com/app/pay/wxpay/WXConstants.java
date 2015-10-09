package com.app.pay.wxpay;

public class WXConstants {

	/** 官方网站申请到的合法appId  wxd6de2851ca85192d */
    public static final String APP_ID = "wxd6de2851ca85192d";
    /** 
     * 官方网站申请到的合法app_secret V3版本支付不需要这个参数  登录授权需要
    */
    public static final String APP_SECRET  = "55239294a7d479055bf1ea5adb9abd40";
    /** 
     * 商家向财付通申请的商家id V3版本这个参数（PARTNER_ID）名改为：MCH_ID
     */
    public static final String MCH_ID  = "1260215401";
    /** 
     * 商家向财付通申请的商家Key 操蛋的这个key是在商户平台自己随便写的32位大小写字母跟数字的组合
     */ 									  
    public static final String PARTNER_KEY = "7d5ynJgVBaDPSqKYUfXt7QEg6PFh0ocj";
    
    /**获取与支付订单ID*/
    public static final String URL_PREPAY_ID   = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    
    
}
