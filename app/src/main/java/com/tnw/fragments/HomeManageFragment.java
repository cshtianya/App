package com.tnw.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.views.UITopPopupView;
import com.app.views.wheel.views.OnWheelChangedListener;
import com.app.views.wheel.views.OnWheelScrollListener;
import com.app.views.wheel.views.WheelView;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.tnw.R;
import com.tnw.activities.EditDeliveryActivity;
import com.tnw.activities.ProductListActivity;
import com.tnw.adapters.HomeRightMenuAdapter;
import com.tnw.adapters.WheelTextAdapter;
import com.tnw.controller.HomeCfgController;
import com.tnw.entities.CategoryNode;
import com.tnw.mvp.HomeBaseView;
import com.tnw.utils.AppDateUtil;
import com.tnw.utils.NetUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CSH on 2015/9/7 0007.
 */
public class HomeManageFragment extends BaseFragment implements View.OnClickListener
        ,HomeBaseView{

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.ivMenu) ImageView ivMenu;
    @Bind(R.id.ivDrawerHander) ImageView ivDrawerHander;
    @Bind(R.id.ivDrawerMenu) ImageView ivDrawerMenu;

    @Bind(R.id.rlayTitle) RelativeLayout rlayTitle;
    @Bind(R.id.txtServiceAddress) TextView txtServiceAddress;
    @Bind(R.id.txtServiceTime) TextView txtServiceTime;

    @Bind(R.id.UIpopupView) UITopPopupView UIpopupView;

    private Button btnAdd;
    private WheelView wvAddress;
    private WheelView wvYear;
    private WheelView wvTime;

    @Bind(R.id.drawer_layout) DrawerLayout drawer_layout;
    @Bind(R.id.rlayMenu) RelativeLayout rlayMenu;
    @Bind(R.id.lvRightList) ListView lvRightList;

    private String[] strTime = {"09:00-10:00","15:00-16:00","16:00-17:00","17:00-18:00","18:00-19:00","19:00-20:00"};
    private WheelTextAdapter mYearAdapter;

    private HomeRightMenuAdapter adapter;  //右侧分类搜索适配器

    private FragmentManager fManager;
    private HomeFragment homeFragment;//首页默认视图

    private int baseColor;
    private float mAlpha = 0.0f;

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fManager = getFragmentManager();
    }

    @Override
    public Context getContext() {
        return this.getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_manager, container,false);
        ButterKnife.bind(this, view);
        baseColor = getResources().getColor(R.color.primaryColor);//primaryColor

        ivMenu.setOnClickListener(this);
        ivDrawerHander.setOnClickListener(this);
        ivDrawerMenu.setOnClickListener(this);
        rlayTitle.setOnClickListener(this);
        txtServiceTime.setText(AppDateUtil.getCurrentDateByOffset(
                AppDateUtil.dateFormatYMD, Calendar.DATE, 1) + " 18:00-19:00");

        View popView = inflater.inflate(R.layout.popview_home_top_layout,null);
        btnAdd = (Button)popView.findViewById(R.id.btnAdd);
        wvAddress = (WheelView)popView.findViewById(R.id.wvAddress);
        wvYear = (WheelView)popView.findViewById(R.id.wvYear);
        wvTime = (WheelView)popView.findViewById(R.id.wvTime);
        UIpopupView.setContentView(popView);

        btnAdd.setOnClickListener(this);

        //去掉背景变暗
        drawer_layout.setScrimColor(Color.TRANSPARENT);
        adapter = new HomeRightMenuAdapter(getActivity());
        lvRightList.setAdapter(adapter);
        adapter.setOnItemClickListener(new HomeRightMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CategoryNode itemInfo, int position) {
                adapter.setSelectId(position);
                Log.i("WHG", "点击了" + "---ItemName----"
                        + itemInfo.getItemName() + "---ItemId----" + itemInfo.getItemId() +
                        "----position----" + position);
                String typeItemId = "";
                switch (position){
                    case 0: //便利净菜
                        typeItemId =  "1393464383262525880";
                        ProductListActivity.laucher(getActivity(), false, 41000,
                                typeItemId,itemInfo.getItemName(),"");
                        break;
                    case 1: //海鲜水产
                        typeItemId =  "1393464415302602459";
                        ProductListActivity.laucher(getActivity(), false, 41001,
                                typeItemId,itemInfo.getItemName(),"");
                        break;
                    case 2: //肉禽冻品
                        typeItemId =  "1393464403747243958";
                        ProductListActivity.laucher(getActivity(), false, 41002,
                                typeItemId,itemInfo.getItemName(),"");
                        break;
                    case 3: //新鲜蔬菜
                        typeItemId =  "1393464392247288617";
                        ProductListActivity.laucher(getActivity(), false, 41003,
                                typeItemId,itemInfo.getItemName(),"");
                        break;
                    case 4: //时令水果
                        typeItemId =  "1437402730755151515";
                        ProductListActivity.laucher(getActivity(), false, 41004,
                                typeItemId,itemInfo.getItemName(),"");

                        break;
                    case 5: //粮油调料
                        typeItemId =  "1437402698946338212";
                        ProductListActivity.laucher(getActivity(), false, 41005,
                                typeItemId,itemInfo.getItemName(),"");
                        break;
                    case 6: //豆乳蛋面
                        typeItemId =  "1437402740729247500";
                        ProductListActivity.laucher(getActivity(), false, 41006,
                                typeItemId,itemInfo.getItemName(),"");
                        break;
                    case 7: //酒水饮料
                        typeItemId =  "1437402748546416893";
                        ProductListActivity.laucher(getActivity(), false, 41007,
                                typeItemId,itemInfo.getItemName(),"");
                        break;
                    case 8: //保健香料
                        typeItemId =  "1437402756123221670";
                        ProductListActivity.laucher(getActivity(), false, 41008,
                                typeItemId,itemInfo.getItemName(),"");
                        break;
                    case 9: //定制套餐
                        typeItemId =  "1437402761861466774";
                        ProductListActivity.laucher(getActivity(), false, 41009,
                                typeItemId,itemInfo.getItemName(),"");
                        break;
                    case 10: //土特产
                        typeItemId =  "1437402769344766570";
                        ProductListActivity.laucher(getActivity(), false, 41010,
                                typeItemId,itemInfo.getItemName(),"");
                        break;
                    case 11: //礼盒卡券
                        typeItemId =  "1437402775336372564";
                        ProductListActivity.laucher(getActivity(), false, 41011,
                                typeItemId,itemInfo.getItemName(),"");
                        break;

                }
                //跳转 商品分类




            }
        });

        homeFragment = new HomeFragment();
        fManager.beginTransaction().add(R.id.content_frame, homeFragment)
                .commit();

        homeFragment.setOnMScollChangedListener(new HomeFragment.OnMScollChangedListener() {
            @Override
            public void onChanged(float alpha) {
                mAlpha = alpha;
                mToolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
            }
        });

        initYearWheelView();
        initTimeWheelView();

        if (NetUtils.isNetworkAvailable(this.getActivity())) {
            HomeCfgController controller = new HomeCfgController(this);
            controller.excute("");
        } else {
            showToast(getActivity(),R.string.netNotAvailable);
        }

        return view;
    }

    private void setTitleBg(int color,float alpha){
        mToolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, color));
    }

    public boolean isMenuShow(){
        return UIpopupView.isShow()||drawer_layout.isDrawerOpen(rlayMenu);
    }

    public void closeMenu(){
        UIpopupView.hiden();
        drawer_layout.closeDrawer(rlayMenu);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivMenu:
                if(UIpopupView.isShow()){
                    UIpopupView.hiden();
                }

                if(drawer_layout.isDrawerOpen(rlayMenu)){
                    drawer_layout.closeDrawer(rlayMenu);
                    setTitleBg(baseColor,mAlpha);
                }else{
                    drawer_layout.openDrawer(rlayMenu);
                    setTitleBg(baseColor, 1);
                }
                break;
            case R.id.ivDrawerHander:
                ivMenu.performClick();
                break;
            case R.id.ivDrawerMenu:
                ivMenu.performClick();
                break;
            case R.id.rlayTitle:
                if(drawer_layout.isDrawerOpen(rlayMenu)){
                    drawer_layout.closeDrawer(rlayMenu);
                }

                if(UIpopupView.isShow()){
                    UIpopupView.hiden();
                    setTitleBg(baseColor, mAlpha);
                }else{
                    UIpopupView.show();
                    setTitleBg(baseColor,1);
                }
                break;
            case R.id.btnAdd:
                EditDeliveryActivity.launcher(getActivity(),null,-1);
                break;
        }
    }

    @Override
    public void showMenu(List<CategoryNode> list) {
        adapter.setList(list, false);
    }

    @Override
    public void showAddress(List<CategoryNode> list) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMsg(String msg) {

    }

    private void initYearWheelView(){
        ArrayList<String> listYear = new ArrayList<>(5);
        for (int i = 1;i<6;i++){
            listYear.add(AppDateUtil.getCurrentDateByOffset(
                    AppDateUtil.dateFormatYMD, Calendar.DATE, i));
        }
        mYearAdapter = new WheelTextAdapter(getActivity()
                , listYear, 0);
        wvYear.setVisibleItems(3);
        wvYear.setViewAdapter(mYearAdapter);
        wvYear.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                mTimeAdapter.setTextviewSize(currentText, mYearAdapter);
            }
        });
        wvYear.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                mTimeAdapter.setTextviewSize(currentText, mYearAdapter);
            }
        });

    }

    private WheelTextAdapter mTimeAdapter;
    private void initTimeWheelView(){
        ArrayList<String> listTime = new ArrayList<>(6);
        for (String str:strTime) {
            listTime.add(str);
        }
        mTimeAdapter = new WheelTextAdapter(getActivity()
                , listTime, 0);
        wvTime.setVisibleItems(3);
        wvTime.setViewAdapter(mTimeAdapter);
        wvTime.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mTimeAdapter.getItemText(wheel.getCurrentItem());
                mTimeAdapter.setTextviewSize(currentText, mTimeAdapter);
            }
        });
        wvTime.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mTimeAdapter.getItemText(wheel.getCurrentItem());
                mTimeAdapter.setTextviewSize(currentText, mTimeAdapter);
            }
        });
    }


}
