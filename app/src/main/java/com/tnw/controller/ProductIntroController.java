package com.tnw.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.tnw.api.APIDataListener;
import com.tnw.api.Base64_Encode_PHP;
import com.tnw.api.action.ActionEnum;
import com.tnw.api.action.ProductsAction;
import com.tnw.api.apifig.ApiConfig;
import com.tnw.api.apifig.ApiConstants;
import com.tnw.api.apifig.ApiParma;
import com.tnw.entities.ProductIntro;
import com.tnw.entities.ProductIntro;
import com.tnw.mvp.NodeListView;

import java.util.HashMap;
import java.util.List;

@SuppressLint("UseSparseArrays")
public class ProductIntroController extends BaseController<String>{

	private final ProductsAction action;
    private final NodeListView<List<ProductIntro>> mPView;
	private final HashMap<String, String> mMap;

	private boolean isLoading = false;

	public ProductIntroController(NodeListView<List<ProductIntro>> l,String productId){
		this.mPView = l;

		mMap = new HashMap<>(1);
		mMap.put(ApiParma.commodityId.getKey(),productId);
		
		action = (ProductsAction) ActionEnum.getProductsAction.getInstance();
	}

	@Override
	public void excute(String str) {
		// TODO Auto-generated method stub

		if(isStop)return;

		if(mPView.isListEmpty()){
			mPView.showLoading();
		}else{
			mPView.showActionLabel();
		}

		String param = new Gson().toJson(mMap);
		if(ApiConfig.IS_DEBUG)
			Log.v("csh", "getProductIntro =======>"+param);
		param = Base64_Encode_PHP.encode(param);
		
		action.getProductIntro(param, listener);
		
	}

	public void onEndListReached(){
		
	}
	
	private APIDataListener<List<ProductIntro>> listener = new APIDataListener<List<ProductIntro>>() {

		@Override
		public void success(List<ProductIntro> result) {
			// TODO Auto-generated method stub

			if(isStop)return;

			if(mPView.isListEmpty()){
				mPView.showNodeList(result,true);
			}else{
				mPView.appendNodeList(result,true);
			}
			mPView.hideLoading();
			mPView.hideActionLabel();

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
