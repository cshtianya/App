package com.tnw.api.action;

import com.tnw.api.APIDataListener;
import com.tnw.entities.CategoryNode;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CategoryAction extends BaseAction {
	
	public void getCategory(String param,final APIDataListener<List<CategoryNode>> l){

		dataApi.getCategory(param, new Callback<List<CategoryNode>>() {
			@Override
			public void success(List<CategoryNode> list, Response arg1) {
				// TODO Auto-generated method stub
				l.success(list);
			}
			
			@Override
			public void failure(RetrofitError error) {
				// TODO Auto-generated method stub
				l.failure(error.getMessage());
			}
		});
		
	}
		

}
