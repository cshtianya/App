package com.tnw.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tnw.MApplication;
import com.tnw.R;
import com.tnw.activities.OrderPreActivity;
import com.tnw.adapters.CartProductAdapter;
import com.tnw.api.apifig.ApiParma;
import com.tnw.controller.CartListController;
import com.tnw.entities.CartNode;
import com.tnw.entities.CartItem;
import com.tnw.mvp.NodeListView;
import com.tnw.utils.NetUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CSH on 2015/9/25 0014.
 */
public class CartFragment extends BaseFragment implements NodeListView<List<CartNode>>
    ,View.OnClickListener{

    @Bind(R.id.appListView)    ListView appListView;
    @Bind(R.id.txtSelectAll)   TextView txtSelectAll;
    @Bind(R.id.txtPayCart)     TextView txtPayCart;
    @Bind(R.id.txtAllCount)    TextView txtAllCount;
    @Bind(R.id.txtServiceTip)  TextView txtServiceTip;

    private CartProductAdapter adapter;
    private ArrayList<Object> allDataList = new ArrayList<>();
    private HashMap<String, CartNode> allMap = new HashMap<>();
    private HashMap<String, CartItem> orderList = new HashMap<>();

    private CartListController controller;
    private HashMap<String,Object> paramMap;

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        controller.stop();
    }

    @Override
    public void onResume() {
        super.onResume();

        allDataList.clear();
        allMap.clear();
        orderList.clear();
        if(paramMap!=null)paramMap.clear();

        if(NetUtils.isNetworkAvailable(this.getActivity())){
            controller.excute("");
        }else{
            showToast(getActivity(),R.string.netNotAvailable);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.fragment_cart,container,false);
        ButterKnife.bind(this, baseView);
        adapter = new CartProductAdapter(this.getActivity());
        appListView.setAdapter(adapter);

        paramMap = new HashMap<>(2);

        controller = new CartListController(this);
        txtPayCart.setOnClickListener(this);
        txtSelectAll.setOnClickListener(this);

        adapter.setOnCheckedListener(new CartProductAdapter.OnCheckedListener() {

            @Override
            public void onChecked(boolean isExist, Object info) {
                // TODO Auto-generated method stub

                if (info instanceof CartNode) {
                    CartNode pInfo = (CartNode) info;

                    if (isExist) {
                        removeAll(pInfo);
                    } else {
                        addAllChoose(pInfo);
                    }

                } else if (info instanceof CartItem) {
                    CartItem cInfo = (CartItem) info;
                    if (isExist) {
                        removeChild(cInfo);
                    } else {
                        addChild(cInfo);
                    }
                }

                chandView();
                adapter.notifyDataSetChanged();
            }
        });

        radioAll = getResources().getDrawable(R.mipmap.icon_radio_p);
        radioItem = getResources().getDrawable(R.mipmap.icon_radio_n);
        radioAll.setBounds(0, 0, radioAll.getMinimumWidth(), radioAll.getMinimumHeight());
        radioItem.setBounds(0, 0, radioItem.getMinimumWidth(), radioItem.getMinimumHeight());
        return baseView;
    }

    private boolean isSelectAll = false;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtSelectAll:
                Iterator<Map.Entry<String, CartNode>> iter = allMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, CartNode> entry = iter.next();
                    CartNode pInfo =  entry.getValue();
                    if(isSelectAll){
                        removeAll(pInfo);
                        adapter.removeAll(pInfo);
                    }else{
                        addAllChoose(pInfo);
                        adapter.addAllChoose(pInfo);
                    }
                }
                isSelectAll = !isSelectAll;
                adapter.notifyDataSetChanged();
                chandView();
                break;
            case R.id.txtPayCart:
                if(orderList.size()== 0){
                    showToast(getActivity(), R.string.hint_cart_buy_zero);
                }else{

                    ArrayList<HashMap<String ,String>> list = new ArrayList<>(orderList.size());
                    Iterator<Map.Entry<String, CartItem>> iterator = orderList.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, CartItem> entry = iterator.next();
                        CartItem itemInfo =  entry.getValue();
                        HashMap<String ,String> itemMap = new HashMap<>();
                        itemMap.put(ApiParma.commodityId.getKey(),itemInfo.getItemCommodityId());
                        itemMap.put(ApiParma.firstId.getKey(),itemInfo.getItemFirstId());
                        itemMap.put(ApiParma.secondId.getKey(),"");
                        itemMap.put(ApiParma.stock.getKey(), itemInfo.getItemStock());
                        list.add(itemMap);
                    }

                    paramMap.put(ApiParma.userId.getKey()
                            , MApplication.getInstance().getUserId());
                    paramMap.put(ApiParma.commodityList.getKey(), list);

                    String param = new Gson().toJson(paramMap);
                    OrderPreActivity.laucher(getActivity(),param);
                }
                break;
        }
    }

    private Drawable radioAll,radioItem;

    private void chandView(){
        double totalPrice = adapter.totalPrice();
        txtAllCount.setText(String.format("%.2f", totalPrice));
        txtServiceTip.setVisibility(totalPrice <= 50 ? View.VISIBLE : View.GONE);
        txtSelectAll.setCompoundDrawables(isSelectAll ? radioAll : radioItem, null, null, null);
    }

    @Override
    public void showNodeList(List<CartNode> result, boolean isEnd) {
        if(result.size()>0){

            for (int i = 0; i < result.size(); i++) {
                CartNode pInfo = result.get(i);
                String key = pInfo.getShopId();
                if(allMap.containsKey(key)){
                    List<CartItem>  itemList = allMap.get(key).getItemList();
                    itemList.addAll(pInfo.getItemList());
                }else{
                    allMap.put(pInfo.getShopId(), pInfo);
                }
            }

            Iterator<Map.Entry<String, CartNode>> iter = allMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, CartNode> entry = iter.next();
                CartNode pInfo =  entry.getValue();
                allDataList.add(pInfo);
                allDataList.addAll(pInfo.getItemList());
            }

            adapter.setList(allDataList, true);

            chandView();

        }

    }

    @Override
    public void appendNodeList(List<CartNode> cartNodes, boolean isEnd) {

    }

    @Override
    public boolean isListEmpty() {
        return false;
    }

    @Override
    public void showActionLabel() {

    }

    @Override
    public void hideActionLabel() {

    }

    @Override
    public Context getContext() {
        return null;
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

    private void addAllChoose(CartNode pInfo){
        List<CartItem> itemList = pInfo.getItemList();

        for (int i = 0; i < itemList.size(); i++) {
            CartItem cInfo = itemList.get(i);
            addChild(cInfo);
        }

    }

    private void removeAll(CartNode pInfo){
        List<CartItem> itemList = pInfo.getItemList();

        for (int i = 0; i < itemList.size(); i++) {
            CartItem cInfo = itemList.get(i);
            removeChild(cInfo);
        }
    }

    private void addChild(CartItem cInfo){
        int amount = Integer.valueOf(cInfo.getItemStockAmount());
        int itemStock = Integer.valueOf(cInfo.getItemStock());
        if(itemStock>amount){
            CartItem item = new CartItem();
            item.setItemCommodityId(cInfo.getItemCommodityId());
            item.setItemFirstId(cInfo.getItemFirstId());
            item.setItemSecondId(cInfo.getItemSecondId());
            item.setItemStock(amount+"");
            orderList.put(cInfo.getItemId(), item);
        }else{
            orderList.put(cInfo.getItemId(), cInfo);
        }

    }

    public void removeChild(CartItem cInfo){
        orderList.remove(cInfo.getItemId());
    }

}
