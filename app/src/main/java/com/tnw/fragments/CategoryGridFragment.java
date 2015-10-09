package com.tnw.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tnw.R;
import com.tnw.activities.ProductListActivity;
import com.tnw.api.apifig.ApiConstants;
import com.tnw.controller.CategoryController;
import com.tnw.entities.CategoryNode;
import com.tnw.mvp.NodeListView;
import com.tnw.utils.Utils;
import com.app.views.custom_views.ProgressBarCircularIndeterminate;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by CSH on 2015/6/30 0030.
 */
public class CategoryGridFragment extends Fragment implements NodeListView<List<CategoryNode>>
,MGridAdapter.OnMClickListener {

    @Bind(R.id.gridView)       GridView gridView;
    @Bind(R.id.circularBar)
    ProgressBarCircularIndeterminate circularBar;

    private CategoryController controller;

    private MGridAdapter adapter;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if(controller!=null)controller.stop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.fragment_category_right_gridview, container, false);
        ButterKnife.bind(this, baseView);

        adapter = new MGridAdapter(this.getActivity());
        adapter.setOnMClickListener(this);
        gridView.setAdapter(adapter);

        return baseView;
    }

    public void onEventMainThread(CategoryNode node){

        if(node!=null){
            controller = new CategoryController(this);
            controller.excute(node.getItemId());
        }

    }

    @Override
    public void onClick(int position, CategoryNode node) {

        Intent intent = new Intent(getActivity(),ProductListActivity.class);
        intent.putExtra("itemId", node.getItemId());
        intent.putExtra("itemKey", ApiConstants.C_40001);
        intent.putExtra("titleName", node.getItemName());
        startActivity(intent);
    }


    @Override
    public void showNodeList(List<CategoryNode> categoryNodes, boolean isEnd) {
        adapter.setmList(categoryNodes);
    }

    @Override
    public void appendNodeList(List<CategoryNode> categoryNodes, boolean isEnd) {

    }

    @Override
    public boolean isListEmpty() {
        return true;
    }

    @Override
    public void showActionLabel() {

    }

    @Override
    public void hideActionLabel() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public Context getContext() {
        return this.getActivity();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

}

class MGridAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<CategoryNode> mList = null;

    private OnMClickListener onMClickListener;
    private LinearLayout.LayoutParams lp;

    public void setOnMClickListener(OnMClickListener l) {
        this.onMClickListener = l;
    }

    public MGridAdapter(Context context) {
        inflater = LayoutInflater.from(context);

        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.75)- Utils.dip2px(context, 40);
        int itemWidth = width/3- Utils.dip2px(context,20);
        lp = new LinearLayout.LayoutParams(itemWidth ,itemWidth);
    }


    public void setmList(List<CategoryNode> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList!=null ? mList.size():0;
    }

    @Override
    public CategoryNode getItem(int i) {
        return mList!=null?mList.get(i):null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.category_grid_item,viewGroup, false);
            holder = new ViewHolder();

            holder.draweeView =  (SimpleDraweeView) convertView.findViewById(R.id.draweeView);
            holder.txtItemName = (TextView) convertView.findViewById(R.id.txtItemName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        final CategoryNode info = getItem(position);
        holder.draweeView.setLayoutParams(lp);
        holder.draweeView.setImageURI(Uri.parse(info.getItemImage()));
        holder.txtItemName.setText(info.getItemName());

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onMClickListener.onClick(position,info);
            }
        });

        return convertView;
    }

    public interface OnMClickListener{
        void onClick(int position,CategoryNode node);
    }

    static class ViewHolder {
        SimpleDraweeView draweeView;
        TextView txtItemName;
    }

}