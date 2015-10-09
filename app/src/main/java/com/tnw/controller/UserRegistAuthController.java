package com.tnw.controller;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.tnw.api.APIDataListener;
import com.tnw.api.Base64_Encode_PHP;
import com.tnw.api.action.ActionEnum;
import com.tnw.api.action.UserAction;
import com.tnw.api.apifig.ApiConfig;
import com.tnw.api.apifig.ApiParma;
import com.tnw.entities.ResultMsg;
import com.tnw.mvp.UserRegistAuthView;

import java.util.HashMap;

/**
 * Created by CSH on 2015/7/10 0010.
 */
public class UserRegistAuthController {

    private final UserAction action;
    private final UserRegistAuthView mView;

    public UserRegistAuthController(UserRegistAuthView view) {
        this.action = (UserAction) ActionEnum.getUserAction.getInstance();
        this.mView = view;
    }

    /**
     * 验证码
     * @param mobile 手机号码
     * @param userId 用户ID
     * <br> 用户ID主要用于修改密码 注册时为空""
     */
    public void excuteSendCode(String mobile,String userId) {

        mView.showLoading();

        HashMap<String, String> map = new HashMap(2);
        map.put(ApiParma.mobile.getKey(), mobile);
        map.put(ApiParma.userId.getKey(), userId);

        String param = new Gson().toJson(map);
        if(ApiConfig.IS_DEBUG)
            Log.v("csh", "UserLoginController=======>" + param);
        param = Base64_Encode_PHP.encode(param);

        action.getAuthCode(param, authListener);

    }

    /**
     * 注册
     * @param mobile 手机号码
     * @param password 密码
     * @param authCode 验证码
     */
    public void excuteRegist(String mobile,String password,String authCode) {

        mView.showLoading();

        HashMap<String, String> map = new HashMap(3);
        map.put(ApiParma.username.getKey(), mobile);
        map.put(ApiParma.password.getKey(), password);
        map.put(ApiParma.code.getKey(), authCode);

        String param = new Gson().toJson(map);
        if(ApiConfig.IS_DEBUG)
            Log.v("csh", "UserLoginController=======>" + param);
        param = Base64_Encode_PHP.encode(param);

        action.userRegister(param, reigistListener);

    }

    /**
     * 重置密码
     * @param mobile 手机号码
     * @param password 密码
     * @param authCode 验证码
     */
    public void excuteReset(String mobile,String password,String authCode) {

        mView.showLoading();

        HashMap<String, String> map = new HashMap(3);
        map.put(ApiParma.username.getKey(), mobile);
        map.put(ApiParma.password.getKey(), password);
        map.put(ApiParma.code.getKey(), authCode);

        String param = new Gson().toJson(map);
        if(ApiConfig.IS_DEBUG)
            Log.v("csh", "UserLoginController=======>" + param);
        param = Base64_Encode_PHP.encode(param);

        action.userResetPwd(param, resetListener);

    }

    private APIDataListener<ResultMsg> authListener = new APIDataListener<ResultMsg>() {
        @Override
        public void success(ResultMsg result) {

            if (isStop)return;
            if(TextUtils.equals("0",result.getError())){
                mView.isSendSuccessed(true, result.getErrorMessage());
            }else{
                mView.isSendSuccessed(false, result.getErrorMessage());
            }
            mView.hideLoading();

        }

        @Override
        public void failure(String errorMsg) {
            if (!isStop)
            mView.isSendSuccessed(false, errorMsg);
            mView.hideLoading();
        }
    };

    private APIDataListener<ResultMsg> reigistListener = new APIDataListener<ResultMsg>() {
        @Override
        public void success(ResultMsg result) {

            if (isStop)return;
            if(TextUtils.equals("0",result.getError())){
                mView.isRigistSuccessed(true, result.getErrorMessage());
            }else{
                mView.isRigistSuccessed(false, result.getErrorMessage());
            }
            mView.hideLoading();

        }

        @Override
        public void failure(String errorMsg) {
            if (isStop) return;
            mView.isRigistSuccessed(false, errorMsg);
            mView.hideLoading();
        }
    };


    private APIDataListener<ResultMsg> resetListener = new APIDataListener<ResultMsg>() {
        @Override
        public void success(ResultMsg result) {

            if (isStop)return;
            if(TextUtils.equals("0",result.getError())){
                mView.isResetSuccessed(true, result.getErrorMessage());
            }else{
                mView.isResetSuccessed(false, result.getErrorMessage());
            }
            mView.hideLoading();

        }

        @Override
        public void failure(String errorMsg) {
            if (isStop) return;
            mView.isResetSuccessed(false, errorMsg);
            mView.hideLoading();
        }
    };

    private boolean isStop = false;

    public void stop(){
        isStop = true;
    }

}
