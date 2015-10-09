package com.tnw.api.action;

import com.tnw.api.APIDataListener;
import com.tnw.entities.BannerInfo;
import com.tnw.entities.CartNode;
import com.tnw.entities.IndexGridNode;
import com.tnw.entities.IndexNode;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class CartAction extends BaseAction {

    public void cartList(String param,final APIDataListener<List<CartNode>> l ){
        dataApi.cartList(param, new Callback<List<CartNode>>() {
            @Override
            public void success(List<CartNode> list, Response response) {
                l.success(list);
            }

            @Override
            public void failure(RetrofitError error) {
                l.failure(error.getMessage());
            }
        });
    }



}
