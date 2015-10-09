package com.tnw.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.app.views.custom_views.ProgressBarCircularIndeterminate;
import com.tnw.R;
import com.tnw.adapters.VantagesAdapter;
import com.tnw.controller.VantagesController;
import com.tnw.entities.VantagesNode;
import com.tnw.mvp.NodeListView;
import com.tnw.utils.NetUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CSH on 2015/9/18 0018.
 * <br> 积分
 */
public class VantagesActivity extends BaseActivity implements NodeListView<List<VantagesNode>> {

    @Bind(R.id.toolbar)        Toolbar mToobar;
    @Bind(R.id.appListView)    ListView appListView;
    @Bind(R.id.circularBar)    ProgressBarCircularIndeterminate circularBar;

    private VantagesAdapter adapter;
    private VantagesController controller;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (controller!=null) controller.stop();
    }

    @Override
    public Context getContext() {return this;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vantages);
        ButterKnife.bind(this);

        mToobar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new VantagesAdapter(this);
        appListView.setAdapter(adapter);

        controller = new VantagesController(this);
        if(NetUtils.isNetworkAvailable(this)){
            controller.excute("");
        }else{
            showTost(R.string.netNotAvailable);
        }

    }


    @Override
    public void showNodeList(List<VantagesNode> vantagesNodes, boolean isEnd) {
        adapter.setList(vantagesNodes,isEnd);
    }

    @Override
    public void appendNodeList(List<VantagesNode> vantagesNodes, boolean isEnd) {

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
        showTost(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
