package com.tnw.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2015/9/18 0018.
 */
public class AssetUtils {

    public static String readFile(Context context,String fileName){
        StringBuffer strBuffer = new StringBuffer();
        AssetManager am = context.getAssets();
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    am.open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)strBuffer.append(line);
        } catch (Exception e) {
        }
        return strBuffer.toString();

    }

    public static Bitmap readImage(Context context,String fileName){
        Bitmap image = null;
        try{
            AssetManager am = context.getAssets();
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

}
