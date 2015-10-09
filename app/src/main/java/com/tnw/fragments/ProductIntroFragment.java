package com.tnw.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tnw.R;
import com.tnw.base.ArrayListAdapter;
import com.tnw.controller.ProductIntroController;
import com.tnw.entities.ProductIntro;
import com.tnw.mvp.NodeListView;
import com.tnw.utils.NetUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CSH on 2015/7/30 0030.
 */
public class ProductIntroFragment extends BaseFragment implements NodeListView<List<ProductIntro>> {

    @Bind(R.id.appListView)    ListView appListView;

    private GDNPicTextAdapter adapter;
    private String productId;

    private ProductIntroController controller;

    public static ProductIntroFragment newInstance(String productId) {
        ProductIntroFragment f = new ProductIntroFragment();
        Bundle bundle = new Bundle();
        bundle.putString("productId", productId);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if(controller!=null)controller.stop();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productId = getArguments().getString("productId");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.fragment_product_baseinfo, container, false);
        ButterKnife.bind(this, baseView);

        adapter = new GDNPicTextAdapter(getActivity());
        appListView.setAdapter(adapter);

        controller = new ProductIntroController(this,productId);
        if(NetUtils.isNetworkAvailable(this.getActivity())){
            controller.excute("");
        }else{
            showToast(getActivity(),R.string.netNotAvailable);
        }

        return baseView;
    }


    @Override
    public void showNodeList(List<ProductIntro> productIntros, boolean isEnd) {
        adapter.setList(productIntros,true);
    }

    @Override
    public void appendNodeList(List<ProductIntro> productIntros, boolean isEnd) {

    }

    @Override
    public boolean isListEmpty() {
        return adapter.getList()==null || adapter.getList().isEmpty();
    }

    @Override
    public void showActionLabel() {

    }

    @Override
    public void hideActionLabel() {

    }

    @Override
    public void showMsg(String msg) {
        showToast(getActivity(),msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}


class GDNPicTextAdapter extends ArrayListAdapter<ProductIntro> {

    private LayoutInflater inflater;
    private int sWidth;

    public GDNPicTextAdapter(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        sWidth = context.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder vh = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_product_intro_layout,parent, false);
            vh = new ViewHolder();
            vh.draweeView = (SimpleDraweeView) convertView.findViewById(R.id.draweeView);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final ProductIntro info = mList.get(position);
        float scale = sWidth/Float.valueOf(info.getItemWidth());
        double h = Float.valueOf(info.getItemHeight())*scale;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)sWidth, (int)h);
        vh.draweeView.setLayoutParams(lp);

        String urlStr = getItem(position).getItemImage();
        Uri uri = Uri.parse(urlStr);
        vh.draweeView.setImageURI(uri);

        return convertView;
    }

    static class ViewHolder {
        SimpleDraweeView draweeView;
    }

}
