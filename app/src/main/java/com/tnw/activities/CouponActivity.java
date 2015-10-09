package com.tnw.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.app.views.custom_views.UICouponTabView;
import com.app.views.custom_views.UIOrderTabView;
import com.tnw.R;
import com.tnw.fragments.CouponInUseFragment;
import com.tnw.fragments.CouponOverdueFragment;
import com.tnw.fragments.CouponUseFragment;
import com.tnw.fragments.OrderAllFragment;
import com.tnw.fragments.OrderNoDeliveryFragment;
import com.tnw.fragments.OrderNoPaymentFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的优惠券
 */
public class CouponActivity extends BaseActivity implements UICouponTabView.OnChangedListener {

    @Bind(R.id.toolbar)    Toolbar mToolbar;
    @Bind(R.id.tabCouponView) UICouponTabView tabCouponView;

    private FragmentManager fManager;

    private CouponUseFragment useFragment;
    private CouponInUseFragment InUseFragment;
    private CouponOverdueFragment OverdueFragment;


    private int selecId;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_ui);
        ButterKnife.bind(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fManager = getSupportFragmentManager();
        tabCouponView.initView();
        tabCouponView.setOnChangedListener(this);

        selecId = R.id.radoTabUse;
        if(savedInstanceState!=null){
            tabCouponView.onClick(findViewById(savedInstanceState.getInt("cacheId")));
        }else{
            tabCouponView.onClick(findViewById(selecId));
        }

    }

    @Override
    public void onChanged(View v, boolean isRefresh) {
        FragmentTransaction transaction = fManager.beginTransaction();
        hideFragment(transaction);
        selecId = v.getId();
        switch (v.getId()) {
            case R.id.radoTabUse:
                if(useFragment==null){
                    useFragment = new CouponUseFragment();
                    transaction.add(R.id.CouponFrame, useFragment);
                }else{
                    transaction.show(useFragment);
                }
                break;
            case R.id.radoInuse:
                if(InUseFragment==null){
                    InUseFragment = new CouponInUseFragment();
                    transaction.add(R.id.CouponFrame, InUseFragment);
                }else{
                    transaction.show(InUseFragment);
                }
                break;
            case R.id.radoOverdue:
                if(OverdueFragment==null){
                    OverdueFragment = new CouponOverdueFragment();
                    transaction.add(R.id.CouponFrame, OverdueFragment);
                }else{
                    transaction.show(OverdueFragment);
                }
                break;


            default:
                break;
        }

        transaction.commit();

    }

    private void hideFragment(FragmentTransaction tt){
        if(useFragment!=null)tt.hide(useFragment);
        if(InUseFragment!=null) tt.hide(InUseFragment);
        if(OverdueFragment!=null)tt.hide(OverdueFragment);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("cacheId", selecId);
        super.onSaveInstanceState(savedInstanceState);
    }


}
