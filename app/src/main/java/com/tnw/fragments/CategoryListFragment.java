package com.tnw.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tnw.R;
import com.tnw.controller.CategoryController;
import com.tnw.entities.CategoryNode;
import com.tnw.mvp.NodeListView;
import com.tnw.utils.NetUtils;
import com.app.views.custom_views.ProgressBarCircularIndeterminate;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by CSH on 2015/6/30 0030.
 */
public class CategoryListFragment extends BaseFragment implements NodeListView<List<CategoryNode>>
    ,MListAdapter.OnMClickListener {

    @Bind(R.id.listView)       ListView listView;
    @Bind(R.id.circularBar)
    ProgressBarCircularIndeterminate circularBar;

    private CategoryController controller;
    private MListAdapter adapter;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if(controller!=null)controller.stop();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.fragment_category_left_list, container, false);
        ButterKnife.bind(this, baseView);

        if (NetUtils.isNetworkAvailable(this.getActivity())) {
            controller = new CategoryController(this);
            controller.excute("");
        } else {
            showMsg("暂无网络");
        }

        return baseView;
    }

    @Override
    public void onClick(int position, CategoryNode node) {
        try{
            adapter.setShowPosition(position);
            EventBus.getDefault().post(node);
        }catch (Exception e){

        }
    }


    @Override
    public void showNodeList(List<CategoryNode> categoryNodes, boolean isEnd) {
        adapter = new MListAdapter(this.getContext(),categoryNodes);
        listView.setAdapter(adapter);
        adapter.setOnMClickListener(this);
        EventBus.getDefault().post(categoryNodes.get(0));
    }

    @Override
    public void appendNodeList(List<CategoryNode> categoryNodes, boolean isEnd) { }

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
    public void showMsg(String msg) {
    }

    @Override
    public Context getContext() {
        return this.getActivity();
    }

    @Override
    public void showLoading() {
        circularBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        circularBar.setVisibility(View.GONE);
    }

}


 class MListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    private List<CategoryNode> mList = null;
    private int showPosition = 0;

    private OnMClickListener onMClickListener;

    public void setOnMClickListener(OnMClickListener l) {
        this.onMClickListener = l;
    }

    public MListAdapter(Context context,List<CategoryNode> list ) {
        this.mContext = context;
        this.mList = list;
        inflater = LayoutInflater.from(context);
    }

    public void setShowPosition(int showPosition) {
        this.showPosition = showPosition;
        this.notifyDataSetChanged();
    }

    public int getShowPosition() {
        return showPosition;
    }

    public void setmList(List<CategoryNode> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CategoryNode getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.category_list_item,viewGroup, false);
            holder = new ViewHolder();

            holder.line = convertView.findViewById(R.id.line);
            holder.txtItemName = (TextView) convertView.findViewById(R.id.txtItemName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (showPosition == position) {
            holder.txtItemName.setTextColor(mContext.getResources().getColor(R.color.promptColor));
            holder.txtItemName.setBackgroundColor(mContext.getResources().getColor(R.color.themeBg));

            holder.line.setVisibility(View.VISIBLE);
        } else {
            holder.txtItemName.setTextColor(mContext.getResources().getColor(R.color.textPrimary));
            holder.txtItemName.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));

            holder.line.setVisibility(View.INVISIBLE);
        }

        final CategoryNode info = getItem(position);
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
        View line;
        TextView txtItemName;
    }

 }