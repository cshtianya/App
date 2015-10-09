package com.tnw.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tnw.MApplication;
import com.tnw.R;
import com.tnw.activities.CollectActivity;
import com.tnw.activities.CouponActivity;
import com.tnw.activities.DeliveryActivity;
import com.tnw.activities.ExplainActivity;
import com.tnw.activities.LoginActivity;
import com.tnw.activities.OrderListManageActivity;
import com.tnw.activities.SettingActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tnw.activities.UserActivity;
import com.tnw.activities.VantagesActivity;
import com.tnw.utils.IShareUtils;
import com.tnw.utils.ShareUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CSH on 2015/7/14 0014.
 */
public class UserCenterFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.ivCenterIcon)   SimpleDraweeView ivCenterIcon;
    @Bind(R.id.txtNickName)  TextView txtNickName;
    @Bind(R.id.laySetting)     LinearLayout laySetting;//设置
    @Bind(R.id.layOrder)        LinearLayout layOrder; //我的订单
    @Bind(R.id.layAddress)     LinearLayout layAddress;//地址管理

    @Bind(R.id.txtInter)    TextView txtInter;//我的积分
    @Bind(R.id.txtCollect)    TextView txtCollect;//我的收藏
    @Bind(R.id.txtEdit)  ImageView txtEdit;  //编辑个人信息
    @Bind(R.id.layTicket)  LinearLayout layTicket;  //我的优惠券
    @Bind(R.id.layServerIntro)  LinearLayout layServerIntro;  //配送说明

    private  ShareUtils mShareUtils;
    private  MApplication application;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = MApplication.getInstance();
        mShareUtils =  application.getShareUtils();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (isLogin()){
            txtNickName.setText(mShareUtils.getStringValues(IShareUtils.USERNAME));
        }else{
            txtNickName.setText("");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.fragment_user_center,container,false);
        ButterKnife.bind(this,baseView);

        ivCenterIcon.setOnClickListener(this);
        txtInter.setOnClickListener(this);
        txtCollect.setOnClickListener(this);

        laySetting.setOnClickListener(this);
        layOrder.setOnClickListener(this);
        layAddress.setOnClickListener(this);
        txtEdit.setOnClickListener(this);
        layTicket.setOnClickListener(this);
        layServerIntro.setOnClickListener(this);


        return baseView;
    }

    @Override
    public void onClick(View view) {
        try {
            Intent intent = new Intent();
            if(isLogin()){
                switch (view.getId()){
                    case R.id.ivCenterIcon://编辑个人信息页面
                        intent.setClass(this.getActivity(), UserActivity.class);
                        break;
                    case R.id.txtInter:
                        intent.setClass(this.getActivity(), VantagesActivity.class);
                        break;
                    case R.id.txtCollect:
                        intent.setClass(this.getActivity(), CollectActivity.class);
                        break;
                    case R.id.laySetting:
                        intent.setClass(this.getActivity(), SettingActivity.class);
                        break;
                    case R.id.layOrder:
                        intent.setClass(this.getActivity(), OrderListManageActivity.class);
                        break;
                    case R.id.layAddress:
                        intent.setClass(this.getActivity(), DeliveryActivity.class);
                        break;
                    case R.id.txtEdit:
                        intent.setClass(this.getActivity(), DeliveryActivity.class);
                        break;
                    case R.id.layTicket:
                        intent.setClass(this.getActivity(), CouponActivity.class);
                        break;
                    case R.id.layServerIntro:
                        intent.setClass(this.getActivity(), ExplainActivity.class);
                        break;
                }
            }else{
                intent.setClass(this.getActivity(), LoginActivity.class);
            }
            startActivity(intent);
        }catch (Exception e){

        }

    }

    private boolean isLogin(){
//        return true;
        return !TextUtils.isEmpty(application.getUserId());
    }

}
