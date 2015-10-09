package com.app;

import java.math.BigDecimal;

/**
 * 
 * @param   
 * @description 用于处理double和float的浮点数参与计算出现精度不准的问题
 * @author pzheng
 * @E_mail pzheng@hengzhi.sh.cn
 * @update 2015-7-27 上午9:49:29
 * @version V1.0 
 * @copy 上海恒志信息技术有限公司
 */
public class ArithUtil {
	private static final int DEF_DIV_SCALE=10;  
    
    private ArithUtil(){}  
      
    public static double add(double d1,double d2){  
        BigDecimal b1=new BigDecimal(Double.toString(d1));  
        BigDecimal b2=new BigDecimal(Double.toString(d2));  
        return b1.add(b2).doubleValue();  
    }  
      
    public static double sub(double d1,double d2){  
        BigDecimal b1=new BigDecimal(Double.toString(d1));  
        BigDecimal b2=new BigDecimal(Double.toString(d2));  
        return b1.subtract(b2).doubleValue();  
    }  
      
    public static double mul(double d1,double d2){  
        BigDecimal b1=new BigDecimal(Double.toString(d1));  
        BigDecimal b2=new BigDecimal(Double.toString(d2));  
        return b1.multiply(b2).doubleValue();  
    }  
      
    public static double div(double d1,double d2){  
        return div(d1,d2,DEF_DIV_SCALE);  
    }  
      
    public static double div(double d1,double d2,int scale){  
        if(scale<0){  
            throw new IllegalArgumentException("The scale must be a positive integer or zero");  
        }  
        BigDecimal b1=new BigDecimal(Double.toString(d1));  
        BigDecimal b2=new BigDecimal(Double.toString(d2));  
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();  
    }  
}
