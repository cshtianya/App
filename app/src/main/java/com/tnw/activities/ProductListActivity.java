package com.tnw.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tnw.R;
import com.tnw.adapters.ProductsHeaderAdapter;
import com.tnw.adapters.RecyclerViewClickListener;
import com.tnw.api.apifig.ApiConstants;
import com.tnw.controller.ProductListController;
import com.tnw.entities.ProductNode;
import com.tnw.mvp.NodeListView;
import com.tnw.utils.NetUtils;
import com.app.views.custom_views.ProgressBarIndeterminate;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CSH on 2015/7/13 0013.
 */
public class ProductListActivity extends BaseActivity implements NodeListView<List<ProductNode>>
        , RecyclerViewClickListener ,ObservableScrollViewCallbacks {

    @Bind(R.id.toolbar)         Toolbar mToolbar;
    @Bind(R.id.ivBandIamge)     SimpleDraweeView mImageView;
    @Bind(R.id.viewBg)          View viewBg;
    @Bind(R.id.fab)             View mFab;
    @Bind(R.id.title)           TextView mTitleView;
    @Bind(R.id.recycler)        ObservableRecyclerView recyclerView;
    @Bind(R.id.progressBar)     ProgressBarIndeterminate progressBar;

    private int mActionBarSize;
    private int mFlexibleSpaceShowFabOffset;
    private int mFlexibleSpaceImageHeight;
    private int mFabMargin;
    private boolean mFabIsShown;

    private ProductsHeaderAdapter adapter;
    private ProductListController controller;

    private int baseColor;
    private int width;
    private boolean isStore;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (controller!=null) controller.stop();
    }


    public static void laucher(Activity activity,boolean isStore,int itemKey,
                               String itemId,String tittleName,String imageUrl){
        Intent intent = new Intent(activity,ProductListActivity.class);
        intent.putExtra("isStore",isStore);
        intent.putExtra("itemKey", itemKey);
        intent.putExtra("itemId", itemId);
        intent.putExtra("titleName", tittleName);
        intent.putExtra("imageUrl",imageUrl);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);
        ButterKnife.bind(this);
        baseColor = getResources().getColor(R.color.primaryColor);

        isStore = getIntent().getBooleanExtra("isStore",false);
        mFab.setVisibility(isStore?View.VISIBLE:View.GONE);

        width = getResources().getDisplayMetrics().widthPixels;
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, width/2);
        mImageView.setLayoutParams(lp);
        mImageView.setVisibility(isStore ? View.VISIBLE : View.GONE);

        mFlexibleSpaceImageHeight = width/2;
        mFlexibleSpaceShowFabOffset = mFlexibleSpaceImageHeight/2;
        mActionBarSize = getResources().getDimensionPixelSize(R.dimen.actionbarSize);
        mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTitleView.setText(getIntent().getStringExtra("titleName"));

        ViewHelper.setScaleX(mFab, 0);
        ViewHelper.setScaleY(mFab, 0);

        if(isStore){
            recyclerView.setScrollViewCallbacks(this);
        }else{
            mToolbar.setBackgroundColor(baseColor);
            recyclerView.setPadding(0, mActionBarSize,0,0);
        }


        viewBg.post(new Runnable() {
            @Override
            public void run() {
                ViewHelper.setTranslationY(viewBg, mFlexibleSpaceImageHeight);
            }
        });

        final View headerView = LayoutInflater.from(this).inflate(R.layout.recycler_header, null);
        if(isStore){
            headerView.post(new Runnable() {
                @Override
                public void run() {
                    headerView.getLayoutParams().height = mFlexibleSpaceImageHeight;
                }
            });
        }

        final GridLayoutManager gm = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gm);
        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.setHasFixedSize(true);
        adapter = new ProductsHeaderAdapter(this,isStore?headerView:null);
        adapter.setOnClickListener(this);
        recyclerView.setAdapter(adapter);

        if(isStore){
            gm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return position==0 ? gm.getSpanCount() : 1;
                }
            });
        }

        controller = new ProductListController(this,this.getIntent());
        if (NetUtils.isNetworkAvailable(this)) {
            if(isStore){
                mImageView.setImageURI(Uri.parse(getIntent().getStringExtra("imageUrl")));
            }
            ;
             if(getIntent().getIntExtra("itemKey",0) == ApiConstants.A_30003){  //判定为广告
                 controller.excute("");
             }else{
                 controller.excute(getIntent().getStringExtra("itemId")); //分类检索
             }



        } else {
            showTost(R.string.netNotAvailable);
        }

    }

    @Override
    public void onClick(View v, int position, float x, float y) {
        ProductNode info = adapter.getMList().get(position);
        ProductDetialActivity.laucher(this, info.getItemId(), info.getItemImage());
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
        if(progressBar!=null)progressBar.show();
    }

    @Override
    public void hideActionLabel() {
        if (progressBar!=null)progressBar.hide();
    }

    @Override
    public void showMsg(String msg) {
        if (!TextUtils.isEmpty(msg))showTost(msg);
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


    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

        float alpha = Math.min(1, (float) scrollY /(isStore?mFlexibleSpaceImageHeight:width));
        mToolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));

        int minOverlayTransitionY = mActionBarSize - mImageView.getHeight();
        ViewHelper.setTranslationY(mImageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Translate list background
        ViewHelper.setTranslationY(viewBg, Math.max(0, -scrollY + mFlexibleSpaceImageHeight));

        // Translate FAB
        int maxFabTranslationY = mFlexibleSpaceImageHeight - mFab.getHeight() / 2+ mFabMargin;

        float fabTranslationY = ScrollUtils.getFloat(
                -scrollY + mFlexibleSpaceImageHeight - mFab.getHeight() / 2+ mFabMargin,
                mActionBarSize - mFab.getHeight() / 2,
                maxFabTranslationY);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            // On pre-honeycomb, ViewHelper.setTranslationX/Y does not set margin,
            // which causes FAB's OnClickListener not working.
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFab.getLayoutParams();
            lp.leftMargin = mImageView.getWidth() - mFabMargin - mFab.getWidth();
            lp.topMargin = (int) fabTranslationY+mFabMargin;
            mFab.requestLayout();
        } else {
            ViewHelper.setTranslationX(mFab, mImageView.getWidth() - mFabMargin - mFab.getWidth());
            ViewHelper.setTranslationY(mFab, fabTranslationY);
        }

        // Show/hide FAB
        if (fabTranslationY < mFlexibleSpaceShowFabOffset) {
            hideFab();
        } else {
            showFab();
        }
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }


    private void showFab() {
        if (!mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(1).scaleY(1).setDuration(200).start();
            mFabIsShown = true;
        }
    }

    private void hideFab() {
        if (mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(0).scaleY(0).setDuration(200).start();
            mFabIsShown = false;
        }
    }

}
