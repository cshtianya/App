package com.tnw.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.tnw.api.APIDataListener;
import com.tnw.api.Base64_Encode_PHP;
import com.tnw.api.action.ActionEnum;
import com.tnw.api.action.ProductsAction;
import com.tnw.api.apifig.ApiConfig;
import com.tnw.api.apifig.ApiParma;
import com.tnw.entities.CommentNode;
import com.tnw.mvp.NodeListView;

import java.util.HashMap;
import java.util.List;

public class ProductCommentsController extends BaseController<String>{

	private final ProductsAction action;
    private final NodeListView<List<CommentNode>> mPView;
	private final HashMap<String, String> mMap;

	private int mCurentPage = 1;
	private boolean isLoading = false;

	public ProductCommentsController(NodeListView<List<CommentNode>> l){
		this.mPView = l;
		mMap = new HashMap<>(3);
		mMap.put(ApiParma.pageSize.getKey(),ApiParma.pageSize.getValue());
		mMap.put(ApiParma.currentPage.getKey(),ApiParma.currentPage.getValue());

		action = (ProductsAction) ActionEnum.getProductsAction.getInstance();
	}

	@Override
	public void excute(String str) {
		mMap.put(ApiParma.commodityId.getKey(),str);

		if(mPView.isListEmpty()){
			mPView.showLoading();
		}else{
			mPView.showActionLabel();
		}

		String param = new Gson().toJson(mMap);
		if(ApiConfig.IS_DEBUG)
			Log.v("csh", "getProductComments =======>"+param);
		param = Base64_Encode_PHP.encode(param);

		action.getProductComments(param, listener);
		
	}

	public void onEndListReached(){
		++mCurentPage;
		excute("");
		isLoading = true;
	}
	
	private APIDataListener<List<CommentNode>> listener = new APIDataListener<List<CommentNode>>() {

		@Override
		public void success(List<CommentNode> result) {
			// TODO Auto-generated method stub

			if(isStop)return;

			if(mPView.isListEmpty()){
				mPView.showNodeList(result,result.size()<ApiConfig.PAGESIZE);
			}else{
				mPView.appendNodeList(result,result.size()<ApiConfig.PAGESIZE);
			}

			mPView.hideActionLabel();
			mPView.hideLoading();

			isLoading = false;
		}

		@Override
		public void failure(String errorMsg) {
			// TODO Auto-generated method stub
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
