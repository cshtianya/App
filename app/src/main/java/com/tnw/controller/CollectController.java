package com.tnw.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.tnw.MApplication;
import com.tnw.api.APIDataListener;
import com.tnw.api.Base64_Encode_PHP;
import com.tnw.api.action.ActionEnum;
import com.tnw.api.action.ProductsAction;
import com.tnw.api.action.UserAction;
import com.tnw.api.apifig.ApiConfig;
import com.tnw.api.apifig.ApiParma;
import com.tnw.entities.CollectNode;
import com.tnw.entities.CollectNode;
import com.tnw.mvp.NodeListView;

import java.util.HashMap;
import java.util.List;


public class CollectController extends BaseController<String>{

	private final UserAction action;
    private final NodeListView<List<CollectNode>> mPView;
	private final HashMap<String, String> mMap;
	private int mCurentPage = 1;
	private boolean isLoading = false;


	public CollectController(NodeListView<List<CollectNode>> nodeListView){
		this.mPView = nodeListView;
		mMap = new HashMap<>(3);

		action = (UserAction) ActionEnum.getUserAction.getInstance();
	}

	@Override
	public void excute(String s) {
		// TODO Auto-generated method stub
		
		if(mPView.isListEmpty()){
			mPView.showLoading();
		}else{
			mPView.showActionLabel();
		}

		mMap.put(ApiParma.user_id.getKey(), MApplication.getInstance().getUserId());
		mMap.put(ApiParma.pageSize.getKey(), ApiParma.pageSize.getValue());
		mMap.put(ApiParma.currentPage.getKey(), mCurentPage+"");

		String param = new Gson().toJson(mMap);
		if(ApiConfig.IS_DEBUG)
			Log.v("csh", "deliveryList =======>" + param);
		param = Base64_Encode_PHP.encode(param);

		action.collenctionList(param, listener);

	}



	public void onEndListReached(){
		++mCurentPage;
        excute("");
		isLoading = true;
	}
	
	private APIDataListener<List<CollectNode>> listener = new APIDataListener<List<CollectNode>>() {

		@Override
		public void success(List<CollectNode> result) {
			// TODO Auto-generated method stub
			if(isStop)return;

			if(mPView.isListEmpty()){
				mPView.showNodeList(result,result.size() < ApiConfig.PAGESIZE);
				mPView.hideLoading();
			}else{
				if(mCurentPage>1){
					mPView.appendNodeList(result,result.size()<ApiConfig.PAGESIZE);
				}else{
					mPView.showNodeList(result,result.size()<ApiConfig.PAGESIZE);
				}
				mPView.appendNodeList(result, result.size() < ApiConfig.PAGESIZE);
				mPView.hideActionLabel();
			}
			isLoading = false;
		}

		@Override
		public void failure(String errorMsg) {
			// TODO Auto-generated method stub
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
