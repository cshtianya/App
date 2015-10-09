package com.tnw.api.action;

import com.tnw.api.APIDataListener;
import com.tnw.entities.CollectNode;
import com.tnw.entities.ProductNode;
import com.tnw.entities.ResultMsg;
import com.tnw.entities.UserBaseInfo;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserAction extends BaseAction {


	public void userLogin(String param,final APIDataListener<UserBaseInfo> l){

		dataApi.userLogin(param, new Callback<UserBaseInfo>() {
			@Override
			public void success(UserBaseInfo userInfo, Response arg1) {
				// TODO Auto-generated method stub
				l.success(userInfo);
			}

			@Override
			public void failure(RetrofitError error) {
				// TODO Auto-generated method stub
				l.failure(error.getMessage());
			}
		});

	}

	public void userRegister(String param,final APIDataListener<ResultMsg> l){

		dataApi.userRegister(param, new Callback<ResultMsg>() {
			@Override
			public void success(ResultMsg userInfo, Response arg1) {
				// TODO Auto-generated method stub
				l.success(userInfo);
			}

			@Override
			public void failure(RetrofitError error) {
				// TODO Auto-generated method stub
				l.failure(error.getMessage());
			}
		});

	}

	public void getAuthCode(String param,final APIDataListener<ResultMsg> l){

		dataApi.userAuthCode(param, new Callback<ResultMsg>() {
			@Override
			public void success(ResultMsg userInfo, Response arg1) {
				// TODO Auto-generated method stub
				l.success(userInfo);
			}

			@Override
			public void failure(RetrofitError error) {
				// TODO Auto-generated method stub
				l.failure(error.getMessage());
			}
		});

	}

	public void userResetPwd(String param,final APIDataListener<ResultMsg> l){

		dataApi.userResetPwd(param, new Callback<ResultMsg>() {
			@Override
			public void success(ResultMsg userInfo, Response arg1) {
				// TODO Auto-generated method stub
				l.success(userInfo);
			}

			@Override
			public void failure(RetrofitError error) {
				// TODO Auto-generated method stub
				l.failure(error.getMessage());
			}
		});

	}

	/**
	 * 收藏列表
	 * @param param
	 * @param l
	 */
	public void collenctionList(String param,final APIDataListener<List<CollectNode>> l){

		dataApi.collenctionList(param, new Callback<List<CollectNode>>() {
			@Override
			public void success(List<CollectNode> result, Response arg1) {
				// TODO Auto-generated method stub
				l.success(result);
			}

			@Override
			public void failure(RetrofitError error) {
				// TODO Auto-generated method stub
				l.failure(error.getMessage());
			}
		});
	}



}
