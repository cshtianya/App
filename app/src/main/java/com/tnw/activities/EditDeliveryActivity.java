package com.tnw.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.app.views.UIBottomPopupView;
import com.app.views.wheel.views.OnWheelChangedListener;
import com.app.views.wheel.views.OnWheelScrollListener;
import com.app.views.wheel.views.WheelView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tnw.R;
import com.tnw.adapters.WheelTextAdapter;
import com.tnw.controller.DeliveryOpreateController;
import com.tnw.entities.DeliveryNode;
import com.tnw.mvp.MVPView;
import com.tnw.utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CSH on 2015/9/23 0023.
 */
public class EditDeliveryActivity extends BaseActivity implements View.OnClickListener
    ,MVPView,OnWheelChangedListener,OnWheelScrollListener {

    private String[] strProvince = {"浙江省"};
    private String[] strCity = {"海盐县"};
    private String[] strArea = {"武原街道"};
           // {"硖石街道","海洲街道","海昌街道","马桥街道","许村镇","长安镇","盐官镇","斜桥镇","袁花镇","丁桥镇","黄湾镇","周王庙镇"};

    @Bind(R.id.toolbar)    Toolbar mToolbar;
    @Bind(R.id.txtFinish)   TextView txtFinish;
    @Bind(R.id.txtSelectAre)   TextView txtSelectAre;
    @Bind(R.id.etxtAddress)    MaterialEditText etxtAddress;
    @Bind(R.id.etxtReciverName)    MaterialEditText etxtReciverName;
    @Bind(R.id.etxtPhone)    MaterialEditText etxtPhone;
    @Bind(R.id.etxtPostCode)    MaterialEditText etxtPostCode;
    @Bind(R.id.ckSetDefault)    CheckBox ckSetDefault;
    @Bind(R.id.uiPopupView)    UIBottomPopupView uiPopupView;

    private TextView txtSelectFinish;
    private WheelView wvProvince,wvCity,wvArea;
    private WheelTextAdapter provinceAdapter,cityAdapter,areaAdapter;

    private DeliveryOpreateController controller;

    private DeliveryNode mInfo;
    private String province,city,area;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        controller.stop();
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && uiPopupView.isShow()){
            uiPopupView.hiden();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public static void launcher(Activity activity,DeliveryNode info,int requestCode){
        Intent intent = new Intent(activity,EditDeliveryActivity.class);
        if(info!=null)intent.putExtra("deliveryInfo",info);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delivery);
        ButterKnife.bind(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtFinish.setOnClickListener(this);
        txtSelectAre.setOnClickListener(this);

        View popView = LayoutInflater.from(this).inflate(R.layout.popview_delivery_select_layout, null);
        uiPopupView.setContentView(popView);
        txtSelectFinish = (TextView)popView.findViewById(R.id.txtSelectFinish);
        wvProvince = (WheelView)popView.findViewById(R.id.wvProvince);
        wvCity = (WheelView)popView.findViewById(R.id.wvCity);
        wvArea = (WheelView)popView.findViewById(R.id.wvArea);

        txtSelectFinish.setOnClickListener(this);

        try {
            mInfo = (DeliveryNode) getIntent().getSerializableExtra("deliveryInfo");
            if(mInfo!=null){

                etxtPostCode.setVisibility(View.VISIBLE);

                etxtAddress.setText(mInfo.getItemAddress());
                etxtPhone.setText(mInfo.getItemPhone());
                etxtPostCode.setText(mInfo.getItemZipcode());
                etxtReciverName.setText(mInfo.getItemName());

                province = mInfo.getItemProvince();
                city = mInfo.getItemCity();
                area = mInfo.getItemArea();
                txtSelectAre.setText(province + "  " + city + "  " + area);

                boolean isDefault = TextUtils.equals("1", mInfo.getItemIsDefault());
                ckSetDefault.setChecked(isDefault);
                ckSetDefault.setEnabled(!isDefault);
            }

            initWheelAdapter();

        } catch (Exception e) {
            // TODO: handle exception
        }

        controller = new DeliveryOpreateController(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtFinish:
                if(mInfo!=null && emptyValidate(true)){
                    controller.createDelivery(mInfo.getItemId(),province,city,area
                            ,etxtAddress.getText().toString(),etxtReciverName.getText().toString()
                            ,etxtPhone.getText().toString(),etxtPostCode.getText().toString()
                            ,mInfo.getItemIsDefault());
                }else if(mInfo==null && emptyValidate(false)){
                    controller.createDelivery(null,province,city,area
                            ,etxtAddress.getText().toString(),etxtReciverName.getText().toString()
                            ,etxtPhone.getText().toString(),"315000",ckSetDefault.isChecked()?"1":"0");
                }
                break;
            case R.id.txtSelectFinish:
                txtSelectAre.setText(province+"  "+city+"  "+area);
                uiPopupView.hiden();
                break;
            case R.id.txtSelectAre:
                if(uiPopupView.isShow()){
                    uiPopupView.hiden();
                }else{
                    uiPopupView.show();
                }
                break;
        }
    }

    private void initWheelAdapter(){
        //省份
        ArrayList<String> pList = new ArrayList<>(strProvince.length);
        for (String str:strProvince) {
            pList.add(str);
        }
        provinceAdapter = new WheelTextAdapter(this, pList, 0);
        wvProvince.setVisibleItems(3);
        wvProvince.setViewAdapter(provinceAdapter);
        wvProvince.addChangingListener(this);
        wvProvince.addScrollingListener(this);
        province = (String)provinceAdapter.getItemText(0);


        //城市
        ArrayList<String> cList = new ArrayList<>(strCity.length);
        for (String str:strCity) {
            cList.add(str);
        }
        cityAdapter = new WheelTextAdapter(this, cList, 0);
        wvCity.setVisibleItems(3);
        wvCity.setViewAdapter(cityAdapter);
        wvCity.addChangingListener(this);
        wvCity.addScrollingListener(this);
        city = (String)cityAdapter.getItemText(0);

        //区
        ArrayList<String> aList = new ArrayList<>(strArea.length);
        for (String str:strArea) {
            aList.add(str);
        }
        areaAdapter = new WheelTextAdapter(this, aList, 0);
        wvArea.setVisibleItems(3);
        wvArea.setViewAdapter(areaAdapter);
        wvArea.addChangingListener(this);
        wvArea.addScrollingListener(this);
        area = (String)areaAdapter.getItemText(0);

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMsg(String msg) {
        if(TextUtils.equals("0",msg)){
            Intent intent  = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }else{
            showTost(msg);
        }

    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        switch (wheel.getId()){
            case R.id.wvProvince:
                String pText = (String)provinceAdapter.getItemText(wheel.getCurrentItem());
                provinceAdapter.setTextviewSize(pText, provinceAdapter);
                province = pText;
                break;
            case R.id.wvCity:
                String cText = (String)cityAdapter.getItemText(wheel.getCurrentItem());
                cityAdapter.setTextviewSize(cText, cityAdapter);
                city = cText;
                break;
            case R.id.wvArea:
                String aText = (String)areaAdapter.getItemText(wheel.getCurrentItem());
                areaAdapter.setTextviewSize(aText, areaAdapter);
                area = aText;
                break;
        }
    }

    @Override
    public void onScrollingStarted(WheelView wheel) {

    }

    @Override
    public void onScrollingFinished(WheelView wheel) {
        switch (wheel.getId()){
            case R.id.wvProvince:
                String pText = (String)provinceAdapter.getItemText(wheel.getCurrentItem());
                provinceAdapter.setTextviewSize(pText, provinceAdapter);
                province = pText;
                break;
            case R.id.wvCity:
                String cText = (String)cityAdapter.getItemText(wheel.getCurrentItem());
                cityAdapter.setTextviewSize(cText, cityAdapter);
                city = cText;
                break;
            case R.id.wvArea:
                String aText = (String)areaAdapter.getItemText(wheel.getCurrentItem());
                areaAdapter.setTextviewSize(aText, areaAdapter);
                area = aText;
                break;
        }
    }


    private boolean emptyValidate(boolean isEdit){
        String receiverName = etxtReciverName.getText().toString();
        String mobileNum = etxtPhone.getText().toString();
        String zipCode = etxtPostCode.getText().toString();
        String address = etxtAddress.getText().toString();

        if(TextUtils.isEmpty(receiverName)){
            showTost(R.string.cre_vali_name_wrong);
            return false;
        }else if(TextUtils.isEmpty(mobileNum)){
            showTost(R.string.lrg_regist_phone);
            return false;
        }else if(TextUtils.isEmpty(address)){
            showTost(R.string.create_detial_address);
            return false;
        }else if(!Utils.checkMobile(mobileNum)){
            showTost(R.string.p_l_phone_error);
            return false;
        }else if(isEdit){
            if(TextUtils.isEmpty(zipCode)){
                showTost(R.string.cre_vali_zipcode_wrong);
                return false;
            }else if(!Utils.checkZipCode(zipCode)){
                showTost(R.string.cre_vali_zipcode_wrong);
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }

    }


}
