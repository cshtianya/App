package com.tnw.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tnw.R;
import com.tnw.adapters.CouponAdapter;
import com.tnw.controller.CouponController;
import com.tnw.entities.CouponNode;
import com.tnw.mvp.NodeListView;
import com.tnw.utils.NetUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 优惠券--已使用
 */
public class CouponInUseFragment extends BaseFragment implements NodeListView<List<CouponNode>> {

    @Bind(R.id.appListView) ListView appListView;
    private CouponController controller;
    private CouponAdapter adapter;

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        controller.stop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.app_listview,container,false);
        ButterKnife.bind(this,baseView);

        adapter = new CouponAdapter(this.getActivity());
        appListView.setAdapter(adapter);

        controller = new CouponController(this);

        if(NetUtils.isNetworkAvailable(this.getActivity())){
            controller.excute("");
        }
        return baseView;
    }


    @Override
    public void showNodeList(List<CouponNode> couponNodes, boolean isEnd) {
        adapter.setList(couponNodes,isEnd);
    }

    @Override
    public void appendNodeList(List<CouponNode> couponNodes, boolean isEnd) {

    }

    @Override
    public boolean isListEmpty() {
        return false;
    }

    @Override
    public void showActionLabel() {

    }

    @Override
    public void hideActionLabel() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public Context getContext() {
        return this.getActivity();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
