package com.tnw.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tnw.R;
import com.tnw.controller.UserLoginController;
import com.tnw.entities.UserBaseInfo;
import com.tnw.mvp.UserLoginView;
import com.tnw.utils.NetUtils;
import com.tnw.utils.Utils;
import com.app.views.button.CircularProgressButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CSH on 2015/7/14 0014.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener
        ,UserLoginView<UserBaseInfo.UserInfo> {

    private static final int REGISTCODE = 5456;

    @Bind(R.id.toolbar)       Toolbar mToolbar;
    @Bind(R.id.txtRegist)     TextView txtRegist;
    @Bind(R.id.txtForgetPwd)  TextView txtForgetPwd;
    @Bind(R.id.btnLogin)      CircularProgressButton btnLogin;

    @Bind(R.id.etxtUserName)  MaterialEditText etxtUserName;
    @Bind(R.id.etxtUserPwd)   MaterialEditText etxtUserPwd;

    private UserLoginController controller;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if(controller!=null)controller.stop();
    }

    public static void laucher(Activity activity){
        Intent intent = new Intent(activity,LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        ButterKnife.bind(this);

       /* etxtUserName.setText("18547813540");
        etxtUserPwd.setText("111111");*/

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtRegist.setOnClickListener(this);
        btnLogin.setIndeterminateProgressMode(true);
        btnLogin.setOnClickListener(this);
        txtForgetPwd.setOnClickListener(this);

        controller = new UserLoginController(false);
        controller.atteView(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if (requestCode == REGISTCODE && data!=null) {
                String userName = data.getStringExtra("userName");
                if(!TextUtils.isEmpty(userName))etxtUserName.setText(userName);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtRegist:
                Intent intent = new Intent(this,RegistActivity.class);
                startActivityForResult(intent, REGISTCODE);
                break;
            case R.id.txtForgetPwd:
                Intent it = new Intent(this,ForgotPWDActivity.class);
                startActivityForResult(it, REGISTCODE);
                break;
            case R.id.btnLogin:
                if(NetUtils.isNetworkAvailable(this) && emptyValidate()){
                    controller.excute(etxtUserName.getText().toString().trim()
                            ,etxtUserPwd.getText().toString().trim());
                }else{
                    showTost(R.string.netNotAvailable);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showUser(UserBaseInfo.UserInfo userInfo) {
        finish();
    }

    @Override
    public void showMsg(String msg) {
//        btnLogin.setProgress(-1);
//        btnLogin.setText(msg);
        showTost(msg);
        btnLogin.setProgress(0);
    }

    @Override
    public void showLoading() {
        btnLogin.setProgress(50);
    }

    @Override
    public void hideLoading() {
        btnLogin.setProgress(100);
    }

    private boolean emptyValidate() {
        String phoneStr = etxtUserName.getText().toString();
        String pwdStr = etxtUserPwd.getText().toString();
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
        } else {
            return true;
        }
    }

}
