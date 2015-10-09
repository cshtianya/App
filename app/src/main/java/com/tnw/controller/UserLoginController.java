package com.tnw.controller;

import android.util.Log;

import com.tnw.MApplication;
import com.tnw.api.APIDataListener;
import com.tnw.api.Base64_Encode_PHP;
import com.tnw.api.action.ActionEnum;
import com.tnw.api.action.UserAction;
import com.tnw.api.apifig.ApiConfig;
import com.tnw.api.apifig.ApiParma;
import com.tnw.entities.UserBaseInfo;
import com.tnw.mvp.UserLoginView;
import com.tnw.utils.IShareUtils;
import com.tnw.utils.SecretAES;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by CSH on 2015/7/10 0010.
 */
public class UserLoginController {

    private final UserAction action;
    private final boolean sBackground;

    private UserLoginView<UserBaseInfo.UserInfo> mView;

    /**
     * @param isBackground 是否后台登录
     */
    public UserLoginController(boolean isBackground) {
        this.action = (UserAction) ActionEnum.getUserAction.getInstance();
        this.sBackground = isBackground;
    }

    public void atteView(UserLoginView<UserBaseInfo.UserInfo> view){
        mView = view;
    }


    public void excute(String userName,String userPwd) {

        mView.showLoading();

        HashMap<String, String> map = new HashMap(3);
        map.put(ApiParma.username.getKey(), userName);
        map.put(ApiParma.password.getKey(), userPwd);
        map.put(ApiParma.type.getKey(), "4");

        String param = new Gson().toJson(map);
        if(ApiConfig.IS_DEBUG)
            Log.v("csh", "UserLoginController=======>" + param);
        param = Base64_Encode_PHP.encode(param);

        action.userLogin(param, listener);

    }

    private APIDataListener<UserBaseInfo> listener = new APIDataListener<UserBaseInfo>() {
        @Override
        public void success(UserBaseInfo result) {
            if(sBackground){
                try {
                    String strUser = new Gson().toJson(result);
                    MApplication.getInstance().getShareUtils()
                            .setStringValues(IShareUtils.USER_CACHE, SecretAES.encrypt(strUser));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{

                if(isStop)return;

                mView.hideLoading();
                if(result.getData()!=null){
                    mView.showUser(result.getData());
                }else{
                    mView.showMsg(result.getErrorMessage());
                }

            }


            if(result.getData()!=null){
                try {
                    MApplication.getInstance().setUserId(result.getData().getUserId());
                    MApplication.getInstance().getShareUtils()
                            .setStringValues(IShareUtils.USERID, result.getData().getUserId());
                    MApplication.getInstance().getShareUtils()
                            .setStringValues(IShareUtils.USERNAME, result.getData().getUsername());
                    MApplication.getInstance().getShareUtils()
                            .setStringValues(IShareUtils.USERPWD
                                    , SecretAES.encrypt(result.getData().getPassword()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void failure(String errorMsg) {
            if(sBackground){
                MApplication.getInstance().getShareUtils().clearAll(null);
            }else {

                if(isStop)return;
                mView.showMsg(errorMsg);
                mView.hideLoading();
            }
        }
    };


    private boolean isStop = false;

    public void stop(){
        isStop = true;
    }

}
