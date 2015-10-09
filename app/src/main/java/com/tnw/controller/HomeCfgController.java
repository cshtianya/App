package com.tnw.controller;

import com.alipay.sdk.util.JsonUtils;
import com.tnw.entities.CategoryNode;
import com.tnw.mvp.HomeBaseView;
import com.tnw.utils.AssetUtils;
import com.tnw.utils.GsonTools;

/**
 * Created by Administrator on 2015/9/18 0018.
 */
public class HomeCfgController extends BaseController<String>{

    private final HomeBaseView mView;

    public HomeCfgController(HomeBaseView homeView) {
        this.mView = homeView;

    }

    @Override
    public void excute(String str) {
        mView.showLoading();

        String jsonStr = AssetUtils.readFile(mView.getContext(),"tnw-menu.json");
        mView.showMenu(GsonTools.getList(jsonStr, CategoryNode.class));
        mView.hideLoading();
    }
}
