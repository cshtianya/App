package com.tnw.controller;

import android.annotation.SuppressLint;
import android.util.Log;

import com.tnw.api.APIDataListener;
import com.tnw.api.Base64_Encode_PHP;
import com.tnw.api.action.ActionEnum;
import com.tnw.api.action.IndexAction;
import com.tnw.api.apifig.ApiConfig;
import com.tnw.api.apifig.ApiParma;
import com.tnw.entities.BannerInfo;
import com.tnw.entities.IndexGridNode;
import com.tnw.entities.IndexGridNode;
import com.tnw.mvp.BannerView;
import com.tnw.mvp.NodeListView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

@SuppressLint("UseSparseArrays")
public class IndexListController extends BaseController<String>{

	private final IndexAction action;
    private final NodeListView<List<IndexGridNode>> mPView;
    private final BannerView<List<BannerInfo.BannerItem>> mBannerView;

//	private final HashMap<String, String> mMap;

	private int mCurentPage = 1;
	private boolean isLoading = false;


	public IndexListController(NodeListView<List<IndexGridNode>> nodeListView,BannerView<List<BannerInfo.BannerItem>> bannerView){
		this.mPView = nodeListView;
//        mMap = new HashMap<>(3);
//        mMap.put(ApiParma.status.getKey(),"2");
//        mMap.put(ApiParma.pageSize.getKey(),ApiParma.pageSize.getValue()+"");

		action = (IndexAction) ActionEnum.getIndexAction.getInstance();

        this.mBannerView = bannerView;

	}

	@Override
	public void excute(String s) {
		// TODO Auto-generated method stub

		if(mPView.isListEmpty()){
			mPView.showLoading();
		}else{
			mPView.showActionLabel();
		}
		
//		mMap.put(ApiParma.currentPage.getKey(), mCurentPage+"");
//		String param = new Gson().toJson(mMap);
//		if(ApiConfig.IS_DEBUG)
//			Log.v("csh", "getIndexGridList =======>"+param);
//		param = Base64_Encode_PHP.encode(param);
		
		action.getIndexGridList("", listener);
	}

    public void showBanner(){
        action.getIndexADV(new APIDataListener<List<BannerInfo.BannerItem>>() {

			@Override
			public void success(List<BannerInfo.BannerItem> result) {
				if (result != null) {
					mBannerView.showBannerList(result);
				}
			}

			@Override
			public void failure(String errorMsg) {

			}
		});
    }

	public void onEndListReached(){
		++mCurentPage;
        excute("");
		isLoading = true;
	}
	
	APIDataListener<List<IndexGridNode>> listener = new APIDataListener<List<IndexGridNode>>() {

		@Override
		public void success(List<IndexGridNode> result) {

			if(isStop)return;

			if(mPView.isListEmpty()){
				mPView.showNodeList(result,result.size() < ApiConfig.PAGESIZE);
				mPView.hideLoading();
			}else{
				mPView.appendNodeList(result, result.size() < ApiConfig.PAGESIZE);
				mPView.hideActionLabel();
			}
			
			isLoading = false;
		}

		@Override
		public void failure(String errorMsg) {
			if(isStop)return;
			mPView.showMsg(errorMsg);
			mPView.hideLoading();
			mPView.hideActionLabel();
			isLoading = false;
			
		}
		
	};

	public boolean isLoading() {
		return isLoading;
	}

}
