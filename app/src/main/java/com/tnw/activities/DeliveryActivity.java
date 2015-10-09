package com.tnw.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.tnw.R;
import com.tnw.adapters.DeliveryAdapter;
import com.tnw.controller.DeliveryListController;
import com.tnw.controller.DeliveryOpreateController;
import com.tnw.entities.DeliveryNode;
import com.tnw.mvp.NodeListView;
import com.tnw.utils.NetUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CSH on 2015/9/21 0021.
 */
public class DeliveryActivity extends BaseActivity implements View.OnClickListener
        ,NodeListView<List<DeliveryNode>> {

    private final static int CREATE_OPREATE = 10100;

    @Bind(R.id.toolbar)    Toolbar mToolbar;
    @Bind(R.id.txtCreate)    TextView txtCreate;
    @Bind(R.id.appListView)    ListView appListView;

    private DeliveryAdapter adapter;
    private DeliveryListController controller;
    private DeliveryOpreateController opreateController;

    private DeliveryNode mInfo;
    private boolean isSelect = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        controller.stop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CREATE_OPREATE)excute();
        }

    }

    public static void launcher(Activity activity,boolean isSelect,int requestCode){
        Intent intent = new Intent(activity,DeliveryActivity.class);
        intent.putExtra("isSelect",isSelect);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        ButterKnife.bind(this);

        isSelect = getIntent().getBooleanExtra("isSelect",false);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtCreate.setOnClickListener(this);

        adapter = new DeliveryAdapter(this,getIntent().getBooleanExtra("isSelect",false));
        appListView.setAdapter(adapter);
        adapter.setOnItemClickListener(new DeliveryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DeliveryNode itemInfo, DeliveryAdapter.Operate operate) {
                mInfo = itemInfo;
                switch (operate) {
                    case EDIT:
                        EditDeliveryActivity.launcher(DeliveryActivity.this, itemInfo, CREATE_OPREATE);
                        break;
                    case DELETE:
                        if (NetUtils.isNetworkAvailable(DeliveryActivity.this)) {
                            opreateController.deliveryDelete(itemInfo.getItemId());
                        } else {
                            showMsg(getString(R.string.netNotAvailable));
                        }
                        break;
                    case SELECT:
                        if(isSelect){
                            Intent it = new Intent();
                            it.putExtra("deliveryInfo",itemInfo);
                            setResult(Activity.RESULT_OK, it);
                            finish();
                        }
                        break;
                }
            }
        });

        controller = new DeliveryListController(this);
        opreateController = new DeliveryOpreateController(this);
        excute();


    }

    private void excute(){
        if(NetUtils.isNetworkAvailable(this)){
            controller.excute("");
        }else{
            showMsg(getString(R.string.netNotAvailable));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtCreate:
                EditDeliveryActivity.launcher(this,null,CREATE_OPREATE);
                break;
        }
    }

    @Override
    public void showNodeList(List<DeliveryNode> deliveryNodes, boolean isEnd) {
        adapter.setList(deliveryNodes,true);
    }

    @Override
    public void appendNodeList(List<DeliveryNode> deliveryNodes, boolean isEnd) {
        adapter.setList(deliveryNodes, true);
    }

    @Override
    public boolean isListEmpty() {
        return adapter.getList().isEmpty();
    }

    @Override
    public void showActionLabel() {

    }

    @Override
    public void hideActionLabel() {

    }

    @Override
    public void showMsg(String msg) {
        if(TextUtils.equals("0", msg)){
            adapter.getList().remove(mInfo);
            adapter.notifyDataSetChanged();
        }else{
            showTost(msg);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
