package com.tnw.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.views.UIScrollViewNestGridView;
import com.tnw.R;
import com.tnw.entities.IndexGridNode;
import com.tnw.entities.IndexNode;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/7/1 0001.
 */
public class IndexAdapter extends Adapter<IndexViewHolder> implements DataBindListener<List<IndexGridNode>>{

    private List<IndexGridNode> mList = null;

    public interface  RecyclerViewGridItemClickListener{
        void onItemClick(IndexGridNode.GridItem info);
    };

    public void setOnItemClickListener(RecyclerViewGridItemClickListener l) {
        this.onItemClickListener = l;
    }

    private RecyclerViewGridItemClickListener onItemClickListener;

    private final Context mContext;

    public IndexAdapter(Context context) {
        this.mContext = context;
    }

    public void setmList(List<IndexGridNode> mList) {
        String itemId =  "40006";
    //取消 特农网专卖
       for (int i = 0;i < mList.size();i++){
            if(itemId.equals(mList.get(i).getItemId())){
                mList.remove(i);
            }
        }

        this.mList = mList;
        this.notifyDataSetChanged();
    }

    @Override
    public IndexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_index_list_item, parent, false);
        return new IndexViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(IndexViewHolder holder, int position) {
        IndexGridNode info = mList.get(position);

        holder.txtItemName.setText(info.getItemName());


        if(info.getItemList()!=null){
            List<IndexGridNode.GridItem> itemList = info.getItemList();
            IndexGridAdapter gridAdapter = null;
            if(itemList.size()>6){
                gridAdapter = new IndexGridAdapter(mContext,itemList.subList(0,6));
            }else{
                gridAdapter = new IndexGridAdapter(mContext,itemList);
            }
            holder.nestGridView.setAdapter(gridAdapter);
            holder.nestGridView.setOnItemClickListener(new TypeItemClick(gridAdapter));
        }


    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public List<IndexGridNode> getMList() {
        return mList;
    }

    @Override
    public void appendList(List<IndexGridNode> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }


    public class TypeItemClick implements AdapterView.OnItemClickListener {
        IndexGridAdapter gridAdapter;
        public TypeItemClick(IndexGridAdapter gridAdapter)
        {
            this.gridAdapter = gridAdapter;
        }

        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int position,long arg3) {
            if(position<gridAdapter.getSize())            {
                IndexGridNode.GridItem gridItem = gridAdapter.getItem(position);
                onItemClickListener.onItemClick(gridItem);
            }
        }
    }

}


class IndexViewHolder extends ViewHolder{

    @Bind(R.id.txtItemName) TextView txtItemName;
    @Bind(R.id.nestGridView) UIScrollViewNestGridView nestGridView;

    public IndexViewHolder(View itemView) {
        super(itemView);
        // TODO Auto-generated constructor stub
        ButterKnife.bind(this, itemView);

    }

}