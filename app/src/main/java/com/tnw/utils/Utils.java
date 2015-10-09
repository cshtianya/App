package com.tnw.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.TypedValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 开发人员可根据实际需求，逐步完善改优化工具类 。 <br>
 * 添加或更新内容请注明创建时间以及人员，规范方法的使用说明
 * 
 * @author ChenSh
 * @E-mail csh_tianya@163.COM
 * @data 2012-11-14
 * @version 1.0
 * @category Utility class.
 */
public class Utils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, double dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Convert Dp to Pixel
     */
    public static int dpToPx(float dp, Resources resources){
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    /**
     * 判断SDCard是否可用
     *
     * @return true if the SDCard can use , false otherwise
     * @category 静态公用方法
     */
    public static boolean IsCanUseSdCard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * Get the memory class of this device (approx. per-app memory limit)
     *
     * @param context
     *            The context to use
     * @return Memory size
     * @category 实例公用方法
     */
    public int getMemoryClass(Context context) {
        return ((ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
    }

    /**
     * Check if OS version has built-in external cache dir method.
     *
     * @return Return true if the External Cache dir exist,otherwise false;
     * @category 实例公用方法
     */
    public boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static String getFormatDate(String strTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String result = "";
        try {
            if (strTime == null) {
                result = "";
            } else {
                date = sdf.parse(strTime);
                result = sdf.format(date);
            }
        } catch (Exception e) {
            result = strTime;
        }
        return (result.equalsIgnoreCase("null") || result == null) ? ""
                : result;
    }

    public static String getFormatTimeMM(String strTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date date = null;
        String result = "";
        try {
            if (strTime == null) {
                result = "";
            } else {
                date = sdf.parse(strTime);
                result = sdf.format(date);
            }
        } catch (Exception e) {
            result = strTime;
        }
        return (result.equalsIgnoreCase("null") || result == null) ? ""
                : result;
    }

    public static String getFormatTime(String strTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        String result = "";
        try {
            if (strTime == null) {
                result = "";
            } else {
                date = sdf.parse(strTime);
                result = sdf.format(date);
            }
        } catch (Exception e) {
            result = strTime;
        }
        return (result.equalsIgnoreCase("null") || result == null) ? ""
                : result;
    }

    /**
     * 对邮箱格式的验证
     *
     * @return
     */
    public static boolean checkEmail(String email) {
        String emailPattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    /**
     * 对手机号码的验证
     *
     * @param mobile
     * @return true if it is a phone number
     */
    public static boolean checkMobile(String mobile) {
        String mobilePattern = "^1[3,4,5,8]\\d{9}$";
        return mobile.matches(mobilePattern);
    }

    /**
     * 是否联通号码(130 131 132 155 156 185 186 145,147)
     *
     * @param num
     * @return
     */
    public static boolean isUnicomMobile(String num) {
        String phone = num.substring(num.length() - 11, num.length());
        String regex = "^(130|131|132|155|156|185|186|145|147)[0-9]{8}$";
        if (phone.matches(regex)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkUserName(String userName) {
        String userNamePattern = "^/w+$";
        Pattern pattern = Pattern.compile(userNamePattern);
        Matcher matcher = pattern.matcher(userName);
        return matcher.find();
    }

    /**
     * "|"分割字符数组
     * @param str
     * @return
     */
    public static String[] strToArray(String str) {
        StringTokenizer st = new StringTokenizer(str, "/");
        String[] strArray = new String[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
            strArray[i++] = st.nextToken();
        }
        return strArray;
    }

    /**
     * 直接拨打电话
     *
     * @param phone
     */
    public static void callPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    /**
     * 验证邮政编码
     *
     * @param post
     * @return
     */
    public static boolean checkZipCode(String post) {
        return post.matches("[1-9]\\d{5}(?!\\d)");
    }

}
