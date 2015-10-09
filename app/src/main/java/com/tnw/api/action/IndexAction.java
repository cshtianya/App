package com.tnw.api.action;

import com.tnw.api.APIDataListener;
import com.tnw.entities.BannerInfo;
import com.tnw.entities.IndexGridNode;
import com.tnw.entities.IndexNode;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class IndexAction extends BaseAction {

    public void getIndexGridList(String param,final APIDataListener<List<IndexGridNode>> l){

        dataApi.getIndexGridList(param, new Callback<List<IndexGridNode>>() {
            @Override
            public void success(List<IndexGridNode> list, Response response) {
                l.success(list);
            }

            @Override
            public void failure(RetrofitError error) {
                l.failure(error.getMessage());
            }
        });

    }

	public void getIndexActivityList(String param,final APIDataListener<List<IndexNode>> l){

        dataApi.getIndexActivityList(param, new Callback<List<IndexNode>>() {
            @Override
            public void success(List<IndexNode> list, Response response) {
                l.success(list);
            }

            @Override
            public void failure(RetrofitError error) {
                l.failure(error.getMessage());
            }
        });

	}

    public void getIndexTopActivity(final APIDataListener<BannerInfo> l ){
        dataApi.getTopActiivity("", new Callback<BannerInfo>() {
            @Override
            public void success(BannerInfo indexAdvs, Response response) {
                l.success(indexAdvs);
            }

            @Override
            public void failure(RetrofitError error) {
                l.failure(error.getMessage());
            }
        });

    }

    public void getIndexADV(final APIDataListener<List<BannerInfo.BannerItem>> l ){
        dataApi.getIndexADV("", new Callback<List<BannerInfo.BannerItem>>() {
            @Override
            public void success(List<BannerInfo.BannerItem> list, Response response) {
                l.success(list);
            }

            @Override
            public void failure(RetrofitError error) {
                l.failure(error.getMessage());
            }
        });

    }


}
