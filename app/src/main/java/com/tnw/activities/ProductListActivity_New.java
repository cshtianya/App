package com.tnw.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tnw.R;
import com.tnw.adapters.ProductsAdapter;
import com.tnw.adapters.RecyclerViewClickListener;
import com.tnw.controller.ProductListController;
import com.tnw.entities.ProductNode;
import com.tnw.mvp.NodeListView;
import com.tnw.utils.NetUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by CSH on 2015/7/13 0013.
 */
@Deprecated
public class ProductListActivity_New extends SlidingUpBaseActivity implements NodeListView<List<ProductNode>>
        , RecyclerViewClickListener,View.OnClickListener{

    private ProductsAdapter adapter;
    private ProductListController controller;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_productlist_new;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        controller.stop();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        GridLayoutManager gm = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gm);
        recyclerView.addOnScrollListener(scrollListener);

        adapter = new ProductsAdapter(this);
        adapter.setOnClickListener(this);
        recyclerView.setAdapter(adapter);

        mFab.setOnClickListener(this);

        String title = getIntent().getStringExtra("titleName");
        mTitle.setText(title);
        mToolbarTitle.setText(title);

        controller = new ProductListController(this,this.getIntent());
        if (NetUtils.isNetworkAvailable(this)) {
            if(getIntent().getBooleanExtra("isStore",false)){
                mInterceptionLayout.setScrollInterceptionListener(mInterceptionListener);
                mImageView.setImageURI(Uri.parse(getIntent().getStringExtra("imageUrl")));
            }
            controller.excute("");
        } else {
            showMsg("暂无网络");
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:

                break;
        }
    }

    @Override
    public void onClick(View v, int position, float x, float y) {
        ProductNode info = adapter.getMList().get(position);
        Intent intent = new Intent(this,ProductDetialActivity.class);
        intent.putExtra("productId",info.getItemId());
        startActivity(intent);

    }

    @Override
    public void showNodeList(List<ProductNode> productNodes, boolean isEnd) {
        adapter.setmList(productNodes);
        if (isEnd) recyclerView.removeOnScrollListener(scrollListener);
    }

    @Override
    public void appendNodeList(List<ProductNode> productNodes, boolean isEnd) {
        adapter.appendList(productNodes);

        if (isEnd) recyclerView.removeOnScrollListener(scrollListener);
    }

    @Override
    public boolean isListEmpty() {
        return adapter.getMList() == null || adapter.getMList().isEmpty();
    }

    @Override
    public void showActionLabel() {
        progressBar.show();
    }

    @Override
    public void hideActionLabel() {
        progressBar.hide();
    }

    @Override
    public void showMsg(String msg) {
        showTost(msg);
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

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            // TODO Auto-generated method stub
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = recyclerView.getLayoutManager()
                    .getChildCount();
            int totalItemCount = recyclerView.getAdapter().getItemCount();
            int pastVisibleItems = ((GridLayoutManager) recyclerView
                    .getLayoutManager()).findFirstVisibleItemPosition();

            if ((visibleItemCount + pastVisibleItems) >= totalItemCount && !controller.isLoading()) {
                controller.onEndListReached();
            }

        }
    };

}
