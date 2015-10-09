package com.tnw;

import android.app.Application;
import android.text.TextUtils;

import com.tnw.api.DatabaseAPI;
import com.tnw.api.apifig.ApiConfig;
import com.tnw.utils.IShareUtils;
import com.tnw.utils.ImagePipelineConfigFactory;
import com.tnw.utils.SecretAES;
import com.tnw.utils.ShareUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

import retrofit.RestAdapter;

/**
 * Created by CSH on 2015/6/29 0029.
 */
public class MApplication extends Application {

    private static MApplication mApplication;
    public static MApplication getInstance(){return mApplication;};

    private static DatabaseAPI dataApi;

    private ShareUtils mShareUtils;
    private String userId;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Fresco.initialize(this, ImagePipelineConfigFactory.getImagePipelineConfig(this));
        initialize();

        mShareUtils = getShareUtils();
        try {
            userId = mShareUtils.getStringValues(IShareUtils.USERID);
            if (TextUtils.isEmpty(userId)) {
                userId = "";
            } else {
                userId = SecretAES.decrypt(userId);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }


    /**
     * 网络数据接口初始化
     * @return
     */
    public static DatabaseAPI initialize(){
        if(dataApi==null){
            RestAdapter movieAPIRest = new RestAdapter.Builder()
                    .setEndpoint(ApiConfig.API_DB_HOST)
                    .setLogLevel(ApiConfig.IS_DEBUG
                            ?RestAdapter.LogLevel.FULL
                            :RestAdapter.LogLevel.NONE)
                    .build();

            dataApi = movieAPIRest.create(DatabaseAPI.class);
        }
        return dataApi;
    }


    public ShareUtils getShareUtils() {
        return mShareUtils == null ? ShareUtils.getInstance(this
                .getApplicationContext()) : mShareUtils;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

}
