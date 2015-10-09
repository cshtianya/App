package com.tnw.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.views.TrumpetView;
import com.app.views.custom_views.ProgressBarIndeterminate;
import com.app.views.custom_views.UIBannerView;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.tnw.R;
import com.tnw.activities.ProductDetialActivity;
import com.tnw.activities.ProductListActivity;
import com.tnw.adapters.HeaderAdapter;
import com.tnw.adapters.IndexAdapter;
import com.tnw.api.apifig.ApiConstants;
import com.tnw.controller.IndexListController;
import com.tnw.entities.BannerInfo;
import com.tnw.entities.IndexGridNode;
import com.tnw.mvp.BannerView;
import com.tnw.mvp.NodeListView;
import com.tnw.utils.NetUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CSH on 2015/6/30 0030.
 */
public class HomeFragment extends BaseFragment implements
        NodeListView<List<IndexGridNode>>,BannerView<List<BannerInfo.BannerItem>>
        ,IndexAdapter.RecyclerViewGridItemClickListener,ObservableScrollViewCallbacks {

    private int mParallaxImageHeight;

    @Bind(R.id.progressBar)  ProgressBarIndeterminate progressBar;
    @Bind(R.id.recyclerView) ObservableRecyclerView recyclerView;

    private HeaderAdapter<IndexAdapter> headerAdapter;
    private IndexAdapter indexAdapter;
    private IndexListController controller;

    private UIBannerView bannerView;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if(controller!=null)controller.stop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, baseView);

        mParallaxImageHeight = getResources().getDisplayMetrics().widthPixels/2;

        bannerView = new UIBannerView(this.getActivity());
        TrumpetView trumpetView = new TrumpetView(this.getActivity());

        LinearLayoutManager lm = new LinearLayoutManager(
                this.getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setScrollViewCallbacks(this);
        recyclerView.addOnScrollListener(scrollListener);

        indexAdapter = new IndexAdapter(this.getActivity());
        indexAdapter.setOnItemClickListener(this);
        headerAdapter = new HeaderAdapter<>(indexAdapter);
        headerAdapter.addHeader(bannerView);
        headerAdapter.addHeader(trumpetView);
        recyclerView.setAdapter(headerAdapter);

        controller = new IndexListController(this,this);
        if (NetUtils.isNetworkAvailable(this.getActivity())) {
            trumpetView.executeTask();
            controller.excute("");
            controller.showBanner();
        } else {
            showToast(getActivity(), R.string.netNotAvailable);
        }

        bannerView.setOnBannerItemClickListener(new UIBannerView.OnBannerItemClickListener() {
            @Override
            public void onItemClick(BannerInfo.BannerItem info, int position) {
                try {
                    int type = Integer.valueOf(info.getItemType());
                    Log.i("WHG","type:-----"+type);
                    switch (type){
                        case ApiConstants.A_30001:
                            ProductDetialActivity.laucher(getActivity(), info.getItemId(), info.getItemImage());
                            break;
                        case ApiConstants.A_30003:
                            Log.i("WHG","type--"+type+"ItemId---"+info.getItemId()+"ItemName"+info.getItemName());
                            ProductListActivity.laucher(getActivity()
                                    , false, type, info.getItemId(), info.getItemName()
                                    , info.getItemImage());
                        break;
                    }

                }catch (Exception e){

                }
            }
        });

        return baseView;
    }

    @Override
    public void onItemClick(IndexGridNode.GridItem info) {
        try {
            int type = Integer.valueOf(info.getParentItemId());
            switch (type){
               // case ApiConstants.A_30003:
                case ApiConstants.C_40001:
                case ApiConstants.C_40002:
                case ApiConstants.C_40003:
                case ApiConstants.C_40004:
                case ApiConstants.C_40005:

                case ApiConstants.C_41000:
                case ApiConstants.C_41001:
                case ApiConstants.C_41002:
                case ApiConstants.C_41003:
                case ApiConstants.C_41004:
                case ApiConstants.C_41005:
                case ApiConstants.C_41006:
                case ApiConstants.C_41007:
                case ApiConstants.C_41008:
                case ApiConstants.C_41009:
                case ApiConstants.C_41010:
                case ApiConstants.C_41011:

                    ProductDetialActivity.laucher(getActivity()
                            , info.getItemId(), info.getItemImage());
                    break;
                case ApiConstants.C_40006:    //
                    Log.i("WHG","type--"+type+"ItemId---"+info.getItemId()+"ItemName"+info.getItemName());
                    ProductListActivity.laucher(getActivity()
                            , false, type, info.getItemId(), info.getItemName()
                            , info.getItemImage());
                    break;
            }

        }catch (Exception e){

        }
    }

    @Override
    public void showBannerList(List<BannerInfo.BannerItem> list) {
          bannerView.showBanner(list);
    }

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

    @Override
    public void showNodeList(List<IndexGridNode> list,boolean isEnd) {
        indexAdapter.setmList(list);
        headerAdapter.notifyDataSetChanged();
        if (isEnd) recyclerView.removeOnScrollListener(scrollListener);
    }

    @Override
    public void appendNodeList(List<IndexGridNode> list, boolean isEnd) {
//        indexAdapter.appendList(list);
//        headerAdapter.notifyDataSetChanged();
//
//        if (isEnd) recyclerView.removeOnScrollListener(scrollListener);
    }

    @Override
    public boolean isListEmpty() {
        return indexAdapter.getMList()==null|| indexAdapter.getMList().isEmpty();
    }

    @Override
    public void showActionLabel() {
        if(progressBar!=null) progressBar.show();
    }

    @Override
    public void hideActionLabel() {
        if(progressBar!=null)  progressBar.hide();
    }

    @Override
    public void showMsg(String msg) {
        showToast(getActivity(), msg);
    }

    @Override
    public Context getContext() {
        return this.getActivity();
    }


    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            // TODO Auto-generated method stub
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
            int totalItemCount = recyclerView.getAdapter().getItemCount();
            int pastVisibleItems = ((LinearLayoutManager) recyclerView
                    .getLayoutManager()).findFirstVisibleItemPosition();

            if ((visibleItemCount + pastVisibleItems) >= totalItemCount
                    && !controller.isLoading()) {
                controller.onEndListReached();
            }
        }
    };

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        if(onMScollChangedListener!=null)
            onMScollChangedListener.onChanged(alpha);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    public interface OnMScollChangedListener{
        void onChanged(float alpha);
    }

    public void setOnMScollChangedListener(OnMScollChangedListener l) {
        this.onMScollChangedListener = l;
    }

    private OnMScollChangedListener onMScollChangedListener;

}
