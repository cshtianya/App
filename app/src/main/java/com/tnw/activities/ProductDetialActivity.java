package com.tnw.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.tnw.MApplication;
import com.tnw.R;
import com.tnw.api.apifig.ApiParma;
import com.tnw.controller.CollectOpreateController;
import com.tnw.controller.ProductDetailController;
import com.tnw.entities.ProductDetial;
import com.tnw.fragments.ProductCommentsFragment;
import com.tnw.fragments.ProductIntroFragment;
import com.tnw.mvp.CollectView;
import com.tnw.mvp.ProductDetailView;
import com.tnw.utils.NetUtils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 商品详情界面
 * Created by CSH on 2015/7/28 0028.
 * 进入该页面需要传递参数（productId，imageUrl）
 */
public class ProductDetialActivity extends BaseActivity implements
        ProductDetailView,CollectView,RadioGroup.OnCheckedChangeListener
        ,View.OnClickListener{

    @Bind(R.id.toolbar)  Toolbar toolbar;
    @Bind(R.id.imageView)          SimpleDraweeView imageView;
    @Bind(R.id.txtProductIntro)    TextView txtProductIntro;
    @Bind(R.id.txtProductPrice)    TextView txtProductPrice;
    @Bind(R.id.txtCostPrice)       TextView txtCostPrice;

    @Bind(R.id.txtCollect) TextView txtCollect;
    @Bind(R.id.txtShare) TextView txtShare;
    @Bind(R.id.txtAddWishList) TextView txtAddWishList;
    @Bind(R.id.txtBuyNow) TextView txtBuyNow;

    @Bind(R.id.radioGroup)    RadioGroup radioGroup;

    private FragmentManager fManager;
    private ProductIntroFragment introFragment;
    private ProductCommentsFragment commentsFragment;

    private ProductDetailController controller;
    private CollectOpreateController collectionController;
    private String productId;
    private String attrFirstId;
    private LayoutParams lp;

    private boolean isCollect = false;

    public static void laucher(Activity activity,String productId,String imageUrl){
        Intent intent = new Intent(activity,ProductDetialActivity.class);
        intent.putExtra("productId",productId);
        intent.putExtra("imageUrl",imageUrl);
        activity.startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if(controller!=null)controller.stop();
        collectionController.stop();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);

        productId = getIntent().getStringExtra("productId");
        fManager = getSupportFragmentManager();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        int width = getResources().getDisplayMetrics().widthPixels;
        lp = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width);
        imageView.setLayoutParams(lp);

        radioGroup.setOnCheckedChangeListener(this);
        txtCollect.setOnClickListener(this);
        txtShare.setOnClickListener(this);
        txtAddWishList.setOnClickListener(this);
        txtBuyNow.setOnClickListener(this);

        txtCollect.setEnabled(false);
        txtShare.setEnabled(false);
        txtAddWishList.setEnabled(false);
        txtBuyNow.setEnabled(false);

        controller = new ProductDetailController(this);
        collectionController = new CollectOpreateController(this);
        if(NetUtils.isNetworkAvailable(this)){
            String iamgeUrl = getIntent().getStringExtra("imageUrl");
            if (!TextUtils.isEmpty(iamgeUrl)) {
                imageView.setImageURI(Uri.parse(iamgeUrl));
            }
            controller.excute(productId);
        }else{
            showTost(R.string.netNotAvailable);
        }

        onCheckedChanged(radioGroup,R.id.radoProductIntro);

    }

    @Override
    public void onClick(View view) {
        if(TextUtils.isEmpty(MApplication.getInstance().getUserId())){
            LoginActivity.laucher(this);
        }else{
            switch (view.getId()){
                case R.id.txtCollect://收藏或取消收藏
                    if(NetUtils.isNetworkAvailable(this)){
                        collectionController.collectionOperate(isCollect, productId);
                    }else{
                        showTost(R.string.netNotAvailable);
                    }
                    break;
                case R.id.txtShare://分享

                    break;
                case R.id.txtAddWishList://加入购物车
                    if(NetUtils.isNetworkAvailable(this)){
                        controller.addCart(productId,attrFirstId);
                    }else{
                        showTost(R.string.netNotAvailable);
                    }
                    break;
                case R.id.txtBuyNow://立即购买
                    buyProductNow();
                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        FragmentTransaction tt = fManager.beginTransaction();

        if(introFragment!=null)tt.hide(introFragment);
        if(commentsFragment!=null) tt.hide(commentsFragment);

        switch (checkedId){
            case R.id.radoProductIntro:
                if(introFragment==null){
                    introFragment = ProductIntroFragment.newInstance(productId);
                    tt.add(R.id.managerFrame, introFragment);
                }else{
                    tt.show(introFragment);
                }
                break;
            case R.id.radoProductComments:
                if(commentsFragment==null){
                    commentsFragment = ProductCommentsFragment.newInstance(productId);
                    tt.add(R.id.managerFrame, commentsFragment);
                }else{
                    tt.show(commentsFragment);
                }
                break;
        }

        tt.commit();
    }

    @Override
    public void showProduct(ProductDetial pInfo) {

        txtCollect.setEnabled(true);
        txtBuyNow.setEnabled(true);
        txtAddWishList.setEnabled(true);

        txtProductIntro.setText(pInfo.getItemName());
        txtProductPrice.setText("￥" + pInfo.getItemPrice());
        txtCostPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        txtCostPrice.setText(pInfo.getItemCostPrice());
        attrFirstId = pInfo.getItemFirstId();
        //是否收藏
        isCollect = TextUtils.equals("1", pInfo.getItemIsCollect());
        setToptDrawable(txtCollect, isCollect
                ? R.mipmap.ic_collect :
                R.mipmap.ic_uncollect);

    }

    @Override
    public void conllectOpreate(boolean isSuccess) {
        if(isSuccess){
            setToptDrawable(txtCollect,isCollect
                    ?R.mipmap.ic_uncollect:
                    R.mipmap.ic_collect);
        }
        isCollect = !isCollect;
    }

    @Override
    public void showAttr(ProductDetial pInfo) {

    }

    @Override
    public void showMsg(String msg) {
        showTost(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private void setToptDrawable(TextView txtView,int resId){
        Resources res = getResources();
        Drawable icon = res.getDrawable(resId);
        icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
        txtView.setCompoundDrawables(null, icon, null, null); //设置左图标
    }

    private void buyProductNow(){
        HashMap<String ,String> itemMap = new HashMap<>();
        itemMap.put(ApiParma.commodityId.getKey(),productId);
        itemMap.put(ApiParma.firstId.getKey(),attrFirstId);
        itemMap.put(ApiParma.secondId.getKey(),"");
        itemMap.put(ApiParma.stock.getKey(), "1");

        ArrayList<HashMap<String ,String>> list = new ArrayList<>();
        list.add(itemMap);

        HashMap<String,Object> map = new HashMap<>(2);
        map.put(ApiParma.userId.getKey(),MApplication.getInstance().getUserId());
        map.put(ApiParma.commodityList.getKey(), list);

        String param = new Gson().toJson(map);
        OrderPreActivity.laucher(this, param);

    }

}
