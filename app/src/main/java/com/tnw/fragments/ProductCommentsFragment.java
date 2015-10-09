package com.tnw.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tnw.R;
import com.tnw.base.ArrayListAdapter;
import com.tnw.controller.ProductCommentsController;
import com.tnw.entities.CommentNode;
import com.tnw.mvp.NodeListView;
import com.tnw.utils.NetUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CSH on 2015/7/30 0030.
 */
public class ProductCommentsFragment extends BaseFragment implements NodeListView<List<CommentNode>> {

    @Bind(R.id.appListView)    ListView appListView;

    private PCommentsAdapter adapter;
    private String productId;

    private ProductCommentsController controller;

    public static ProductCommentsFragment newInstance(String productId) {
        ProductCommentsFragment f = new ProductCommentsFragment();
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

        adapter = new PCommentsAdapter(getActivity());
        appListView.setAdapter(adapter);

        controller = new ProductCommentsController(this);
        if(NetUtils.isNetworkAvailable(this.getActivity())){
            controller.excute(productId);
        }else{
            showToast(getActivity(),R.string.netNotAvailable);
        }

        return baseView;
    }


    @Override
    public void showNodeList(List<CommentNode> CommentNodes, boolean isEnd) {
        adapter.setList(CommentNodes,true);
    }

    @Override
    public void appendNodeList(List<CommentNode> CommentNodes, boolean isEnd) {

    }

    @Override
    public boolean isListEmpty() {
        return adapter.getList()==null|| adapter.getList().isEmpty();
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


class PCommentsAdapter extends ArrayListAdapter<CommentNode> {

    private LayoutInflater inflater;
    private int sWidth;

    public PCommentsAdapter(Context context) {
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
            convertView = inflater.inflate(R.layout.item_product_comments_layout,parent, false);
            vh = new ViewHolder();
            vh.draweeView = (SimpleDraweeView) convertView.findViewById(R.id.draweeView);
            vh.txtItemName = (TextView) convertView.findViewById(R.id.txtItemName);
            vh.txtItemContent = (TextView) convertView.findViewById(R.id.txtItemContent);
            vh.txtItemTime = (TextView) convertView.findViewById(R.id.txtItemTime);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        CommentNode info = getItem(position);

        vh.txtItemName.setText(info.getItemUserName());
        vh.txtItemContent.setText(info.getItemContent());
        vh.txtItemTime.setText(info.getItemDate());

        String urlStr = info.getItemUserImage();
        Uri uri = Uri.parse(urlStr);
        vh.draweeView.setImageURI(uri);

        return convertView;
    }

    static class ViewHolder {
        SimpleDraweeView draweeView;
        TextView txtItemName,txtItemContent,txtItemTime;

    }

}
