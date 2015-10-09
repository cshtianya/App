package com.tnw.controller;

import android.util.Log;

import com.tnw.api.APIDataListener;
import com.tnw.api.Base64_Encode_PHP;
import com.tnw.api.action.ActionEnum;
import com.tnw.api.action.CategoryAction;
import com.tnw.api.apifig.ApiConfig;
import com.tnw.entities.CategoryNode;
import com.tnw.mvp.NodeListView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/7/10 0010.
 */
public class CategoryController extends BaseController<String>{

    private final CategoryAction action;
    private final NodeListView<List<CategoryNode>> mView;

    public CategoryController(NodeListView<List<CategoryNode>> view) {

        action = (CategoryAction) ActionEnum.getCategoryAction.getInstance();
        this.mView = view;

    }


    @Override
    public void excute(String str) {

        mView.showLoading();

        HashMap<String, String> map = new HashMap(1);
        map.put("parentId", str);

        String param = new Gson().toJson(map);
        if(ApiConfig.IS_DEBUG)
            Log.v("csh", "=======>" + param);
        param = Base64_Encode_PHP.encode(param);

        action.getCategory(param,listener);

    }

    private APIDataListener<List<CategoryNode>> listener = new APIDataListener<List<CategoryNode>>() {
        @Override
        public void success(List<CategoryNode> result) {
            mView.showNodeList(result,true);

            mView.hideLoading();
            mView.hideActionLabel();
        }

        @Override
        public void failure(String errorMsg) {

            mView.showMsg(errorMsg);
            mView.hideLoading();
            mView.hideActionLabel();

            mView.showMsg(errorMsg);


        }
    };

}
