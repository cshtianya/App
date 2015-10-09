package com.tnw.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.app.views.button.CircularProgressButton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tnw.R;
import com.tnw.controller.UserRegistAuthController;
import com.tnw.mvp.UserRegistAuthView;
import com.tnw.utils.NetUtils;
import com.tnw.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CSH on 2015/9/21 0014.
 */
public class ForgotPWDActivity extends BaseActivity implements View.OnClickListener
    ,UserRegistAuthView {

    @Bind(R.id.toolbar)         Toolbar mToolbar;
    @Bind(R.id.etxtUserName)    MaterialEditText etxtUserName;
    @Bind(R.id.etxtUserPwd)     MaterialEditText etxtUserPwd;
    @Bind(R.id.etxtAuthCode)    MaterialEditText etxtAuthCode;

    @Bind(R.id.btnAuthCode)   CircularProgressButton btnAuthCode;
    @Bind(R.id.btnRegist)   CircularProgressButton btnRegist;

    private UserRegistAuthController controller;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if(controller!=null)controller.stop();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forgot_pwd);
        ButterKnife.bind(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAuthCode.setIndeterminateProgressMode(true);
        btnAuthCode.setOnClickListener(this);

        btnRegist.setIndeterminateProgressMode(true);
        btnRegist.setOnClickListener(this);

        controller = new UserRegistAuthController(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAuthCode:
                if(NetUtils.isNetworkAvailable(this) && checkMobile()){
                    controller.excuteSendCode(etxtUserName.getText().toString(),"");
                }
                break;
            case R.id.btnRegist:
                if(NetUtils.isNetworkAvailable(this) && emptyValidate()){
                    controller.excuteRegist(etxtUserName.getText().toString().trim()
                            , etxtUserPwd.getText().toString().trim()
                            , etxtAuthCode.getText().toString().trim());
                }
                break;
        }
    }

    @Override
    public void isSendSuccessed(boolean isSuccessed, String msg) {
        showTost(msg);
    }

    @Override
    public void isRigistSuccessed(boolean isSuccessed, String msg) {

    }

    @Override
    public void isResetSuccessed(boolean isSuccessed, String msg) {
        showTost(msg);
        if(isSuccessed){
            finish();
            Intent intent = new Intent();
            intent.putExtra("userName",etxtUserName.getText().toString().trim());
            setResult(RESULT_OK, intent);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMsg(String msg) {

    }

    private boolean checkMobile(){
        String phoneStr = etxtUserName.getText().toString().trim();
        if (TextUtils.isEmpty(phoneStr)) {
            showTost(R.string.hint_l_phone);
            return false;
        } else if (!Utils.checkMobile(phoneStr)) {
            showTost(R.string.p_l_phone_error);
            return false;
        } else {
            return true;
        }
    }

    private boolean emptyValidate() {
        String phoneStr = etxtUserName.getText().toString().trim();
        String pwdStr = etxtUserPwd.getText().toString().trim();
        String authCode = etxtAuthCode.getText().toString().trim();
        if (TextUtils.isEmpty(phoneStr)) {
            showTost(R.string.hint_l_phone);
            return false;
        } else if (TextUtils.isEmpty(pwdStr)) {
            showTost(R.string.hint_l_pwd);
            return false;
        } else if (!Utils.checkMobile(phoneStr)) {
            showTost(R.string.p_l_phone_error);
            return false;
        } else if (pwdStr.length() < 6) {
            showTost(R.string.p_l_pwd_short);
            return false;
        } else if (TextUtils.isEmpty(authCode)) {
            showTost(R.string.lrg_regist_autncode_empty);
            return false;
        }else {
            return true;
        }
    }

}
