package com.tnw.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.tnw.api.APIDataListener;
import com.tnw.api.action.ActionEnum;
import com.tnw.api.action.ProductsAction;
import com.tnw.api.apifig.ApiConfig;
import com.tnw.api.apifig.ApiConstants;
import com.tnw.api.apifig.ApiParma;
import com.tnw.entities.ProductNode;
import com.tnw.mvp.NodeListView;
import com.tnw.api.Base64_Encode_PHP;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

@SuppressLint("UseSparseArrays")
public class ProductListController extends BaseController<String>{
	
	private HashMap<Integer, String> keyMap = new HashMap<Integer, String>(3);
	
	private final ProductsAction action;
    private final NodeListView<List<ProductNode>> mPView;
	private final HashMap<String, String> mMap;
	
	private int mCurentPage = 1;
	private boolean isLoading = false;
	
	
	public ProductListController(NodeListView<List<ProductNode>> l,Intent intent){
		this.mPView = l;
		
		keyMap.put(ApiConstants.C_40006, "brandId");
		keyMap.put(ApiConstants.C_40001, "categoryId");
		keyMap.put(ApiConstants.A_30003, "activityId");

		int keyNum = intent.getIntExtra("itemKey",ApiConstants.A_30003);
		String itemId = intent.getStringExtra("itemId");
		mMap = new HashMap<>(3);
		mMap.put(keyMap.get(keyNum), TextUtils.isEmpty(itemId) ? "" : itemId);
		
		action = (ProductsAction) ActionEnum.getProductsAction.getInstance();
	}

	@Override
	public void excute(String str) {
		// TODO Auto-generated method stub

		if(mPView.isListEmpty()){
			mPView.showLoading();
		}else{
			mPView.showActionLabel();
		}
		Log.i("WHG","-----------"+str+"--------");
		if(str!=""){
			mMap.put("categoryId", str);  // 增加获取商品列表
		}

		mMap.put(ApiParma.pageSize.getKey(), ApiParma.pageSize.getValue());
		mMap.put(ApiParma.currentPage.getKey(), mCurentPage+"");
		
		String param = new Gson().toJson(mMap);
		if(ApiConfig.IS_DEBUG)
			Log.v("csh", "getProductsList =======>"+param);

		param = Base64_Encode_PHP.encode(param);
		
		action.getProductsList(param, listener);
		
	}

	public void onEndListReached(){
		++mCurentPage;
        excute("");
		isLoading = true;
	}
	
	private APIDataListener<List<ProductNode>> listener = new APIDataListener<List<ProductNode>>() {

		@Override
		public void success(List<ProductNode> result) {
			// TODO Auto-generated method stub

			if(isStop)return;

			if(mPView.isListEmpty()){
				mPView.showNodeList(result,result.size()<ApiConfig.PAGESIZE);
			}else{
				mPView.appendNodeList(result,result.size()<ApiConfig.PAGESIZE);
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
