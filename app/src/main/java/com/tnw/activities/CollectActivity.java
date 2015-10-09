package com.tnw.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.app.views.custom_views.ProgressBarCircularIndeterminate;
import com.tnw.R;
import com.tnw.adapters.CollectAdapter;
import com.tnw.controller.CollectController;
import com.tnw.controller.CollectOpreateController;
import com.tnw.entities.CollectNode;
import com.tnw.entities.CollectNode;
import com.tnw.mvp.CollectView;
import com.tnw.mvp.NodeListView;
import com.tnw.utils.NetUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by CSH on 2015/9/18 0018.
 * <br> 收藏界面
 */
public class CollectActivity extends BaseActivity implements View.OnClickListener,
        NodeListView<List<CollectNode>>,CollectView {

    private final static int CREATE_OPREATE = 10200;

    @Bind(R.id.toolbar)        Toolbar mToobar;
    @Bind(R.id.appListView)    ListView appListView;
    @Bind(R.id.circularBar)    ProgressBarCircularIndeterminate circularBar;

    private CollectAdapter adapter;
    private CollectController controller;
    private CollectOpreateController opreateController;

    private CollectNode cInfo;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if(controller!=null)controller.stop();
    }

    @Override
    public Context getContext() {return this;}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if (requestCode == CREATE_OPREATE)excute();
        }
    }

    public static void launcher(Activity activity){
        Intent intent = new Intent(activity,CollectActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);

        mToobar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new CollectAdapter(this);
        appListView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CollectAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(CollectNode itemInfo, CollectAdapter.Operate operate) {
                cInfo = itemInfo;
                switch (operate) {
                    case EDIT:

                        break;
                    case DELETE:
                        if (NetUtils.isNetworkAvailable(CollectActivity.this)) {
                            opreateController.collectionOperate(true, itemInfo.getItemId());
                        } else {
                            showMsg(getString(R.string.netNotAvailable));
                        }
                        break;
                    case SELECT:

                        break;
                }

            }
        });

        controller = new CollectController(this);
        opreateController = new CollectOpreateController(this);
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
    public void conllectOpreate(boolean isSuccess) {
        adapter.getList().remove(cInfo);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showNodeList(List<CollectNode> list, boolean isEnd) {
        adapter.setList(list,isEnd);
    }

    @Override
    public void appendNodeList(List<CollectNode> CollectNodes, boolean isEnd) {

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

    @Override
    public void onClick(View view) {


    }


}
