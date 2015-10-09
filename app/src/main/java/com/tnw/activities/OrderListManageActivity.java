package com.tnw.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.app.views.custom_views.UIOrderTabView;
import com.tnw.R;
import com.tnw.fragments.OrderAllFragment;
import com.tnw.fragments.OrderCompletedFragment;
import com.tnw.fragments.OrderNoDeliveryFragment;
import com.tnw.fragments.OrderNoPaymentFragment;
import com.tnw.fragments.OrderNoReceivedFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的订单界面
 */
public class OrderListManageActivity extends BaseActivity implements UIOrderTabView.OnChangedListener {

    @Bind(R.id.toolbar)    Toolbar mToolbar;
    @Bind(R.id.tabOrderView) UIOrderTabView orderTabView;

    private FragmentManager fManager;
//    private OrderAllFragment allFragment;
    private OrderNoPaymentFragment noPaymentFragment;
    private OrderNoDeliveryFragment  noDeliveryFragment;
    private OrderNoReceivedFragment  noReceivedFragment;
    private OrderCompletedFragment completedFragment;

    private int selecId;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * @param activity 跳转类
     * @param viewId 目标类 控制显示Fragment的控件Id
     */
    public static void launcher(Activity activity,int viewId){
        Intent intent = new Intent(activity,OrderListManageActivity.class);
        intent.putExtra("selectId",viewId);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_ui);
        ButterKnife.bind(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fManager = getSupportFragmentManager();
        orderTabView.initView();
        orderTabView.setOnChangedListener(this);

        selecId = getIntent().getIntExtra("selectId",R.id.radoNoPayment);
        if(savedInstanceState!=null){
            orderTabView.onClick(findViewById(savedInstanceState.getInt("cacheId")));
        }else{
            orderTabView.onClick(findViewById(selecId));
        }

    }

    @Override
    public void onChanged(View v, boolean isRefresh) {
        FragmentTransaction transaction = fManager.beginTransaction();
        hideFragment(transaction);
        selecId = v.getId();
        switch (v.getId()) {
            case R.id.radoNoPayment:
                if(noPaymentFragment==null){
                    noPaymentFragment = new OrderNoPaymentFragment();
                    transaction.add(R.id.OrderFrame, noPaymentFragment);
                }else{
                    transaction.show(noPaymentFragment);
                }
                break;
            case R.id.radoNoDelivery:
                if(noDeliveryFragment==null){
                    noDeliveryFragment = new OrderNoDeliveryFragment();
                    transaction.add(R.id.OrderFrame, noDeliveryFragment);
                }else{
                    transaction.show(noDeliveryFragment);
                }
                break;
            case R.id.radoNoReceived:
                if(noReceivedFragment==null){
                    noReceivedFragment = new OrderNoReceivedFragment();
                    transaction.add(R.id.OrderFrame, noReceivedFragment);
                }else{
                    transaction.show(noReceivedFragment);
                }
                break;
            case R.id.radoCompleted:
                if(completedFragment==null){
                    completedFragment = new OrderCompletedFragment();
                    transaction.add(R.id.OrderFrame, completedFragment);
                }else{
                    transaction.show(completedFragment);
                }
                break;
            default:
                break;
        }

        transaction.commit();

    }

    private void hideFragment(FragmentTransaction tt){
//        if(allFragment!=null)tt.hide(allFragment);
        if(noPaymentFragment!=null) tt.hide(noPaymentFragment);
        if(noDeliveryFragment!=null)tt.hide(noDeliveryFragment);
        if(noReceivedFragment!=null)tt.hide(noReceivedFragment);
        if(completedFragment!=null)tt.hide(completedFragment);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("cacheId", selecId);
        super.onSaveInstanceState(savedInstanceState);
    }


}
