package com.tnw.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tnw.MApplication;
import com.tnw.R;
import com.tnw.entities.CollectNode;
import com.tnw.utils.IShareUtils;
import com.tnw.utils.ShareUtils;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by CSH on 2015/9/18 0018.
 * <br> 用户中心
 */
public class UserActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.toolbar)        Toolbar mToobar;
    @Bind(R.id.btn_logout)    Button btn_logout;
    @Bind(R.id.tv_userName)  TextView tv_userName;
    private ShareUtils mShareUtils;
    private MApplication application;
    private Context context;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        context = this.getApplicationContext();

        application = MApplication.getInstance();
        mShareUtils =  application.getShareUtils();

        tv_userName.setText(mShareUtils.getStringValues(IShareUtils.USERNAME));

        mToobar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        onBackPressed();

    }

    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("你确认要退出当前账户吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作，清除账户信息
                        application.setUserId("");
                        mShareUtils.removeShareValues(IShareUtils.USERNAME);
                        mShareUtils.clearAll(context);

                        UserActivity.this.finish();
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();
    }
}
