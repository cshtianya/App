package com.tnw.api.action;

import com.tnw.api.APIDataListener;
import com.tnw.entities.CommentNode;
import com.tnw.entities.ProductDetial;
import com.tnw.entities.ProductIntro;
import com.tnw.entities.ProductNode;
import com.tnw.entities.ResultMsg;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProductsAction extends BaseAction{

	/**
	 * 商品列表
	 * @param param
	 * @param l
	 */
	public void getProductsList(String param,final APIDataListener<List<ProductNode>> l){
		
		dataApi.getProductsList(param, new Callback<List<ProductNode>>() {
			@Override
			public void success(List<ProductNode> result, Response arg1) {
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
	
	/**
	 * 商品详情
	 * @param param
	 * @param l
	 */
	public void getProductDetial(String param,final APIDataListener<ProductDetial> l){
		dataApi.getProductDetials(param, new Callback<ProductDetial>() {
			@Override
			public void success(ProductDetial result, Response arg1) {
				// TODO Auto-generated method stub
				l.success(result);
			}

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				l.failure(arg0.getMessage());
			}
		});

	}

	/**
	 * 商品介绍
	 * @param param
	 * @param l
	 */
	public void getProductIntro(String param,final APIDataListener<List<ProductIntro>> l){
		dataApi.getProductIntro(param, new Callback<List<ProductIntro>>() {
			@Override
			public void success(List<ProductIntro> result, Response arg1) {
				// TODO Auto-generated method stub
				l.success(result);
			}

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				l.failure(arg0.getMessage());
			}
		});

	}

	/**
	 * 商品评论
	 * @param param
	 * @param l
	 */
	public void getProductComments(String param,final APIDataListener<List<CommentNode>> l){
		dataApi.getCommentsList(param, new Callback<List<CommentNode>>() {
			@Override
			public void success(List<CommentNode> result, Response arg1) {
				// TODO Auto-generated method stub
				l.success(result);
			}

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				l.failure(arg0.getMessage());
			}
		});

	}

	/**
	 * 商品添加删除/收藏
	 * @param param 参数
	 * @param l 回调监听
	 */
	public void collectionOperate(String param,final APIDataListener<ResultMsg> l){

		dataApi.collectionOperate(param, new Callback<ResultMsg>() {
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
	 * 添加或修改购物车商品
	 * @param param 参数
	 * @param l 回调监听
	 */
	public void cartEdit(String param,final APIDataListener<ResultMsg> l){

		dataApi.cartEdit(param, new Callback<ResultMsg>() {
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


}
