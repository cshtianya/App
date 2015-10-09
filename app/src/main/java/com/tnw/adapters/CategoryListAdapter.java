package com.tnw.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tnw.R;
import com.tnw.entities.CategoryNode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CSh on 2015/7/10 0001.
 * 已改为ListView，这个适配器暂时不用（保留）
 */
@Deprecated
public class CategoryListAdapter extends Adapter<CViewHolder> implements DataBindListener<List<CategoryNode>> {

    private final Context mContext;

    private List<CategoryNode> mList = null;
    private RecyclerViewClickListener onClickListener;

    private int showPosition = 0;

    public CategoryListAdapter(Context context) {
        this.mContext = context;
    }

    public void setmList(List<CategoryNode> mList) {
        this.mList = mList;
    }

    public void setShowPosition(int showPosition) {
        this.showPosition = showPosition;
        this.notifyDataSetChanged();
    }

    public int getShowPosition() {
        return showPosition;
    }

    public void setOnClickListener(RecyclerViewClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public CViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.category_list_item, parent, false);

        return new CViewHolder(rowView, onClickListener);
    }

    @Override
    public void onBindViewHolder(CViewHolder holder, int position) {
        CategoryNode info = mList.get(position);

        if (showPosition == position) {
            holder.txtItemName.setTextColor(mContext.getResources().getColor(R.color.promptColor));
            holder.txtItemName.setBackgroundColor(mContext.getResources().getColor(R.color.themeBg));

            holder.line.setVisibility(View.VISIBLE);
        } else {
            holder.txtItemName.setTextColor(mContext.getResources().getColor(R.color.textPrimary));
            holder.txtItemName.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));

            holder.line.setVisibility(View.INVISIBLE);
        }

        holder.txtItemName.setText(info.getItemName());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public List<CategoryNode> getMList() {
        return mList;
    }

    @Override
    public void appendList(List<CategoryNode> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }
}


class CViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final RecyclerViewClickListener onClickListener;
    @Bind(R.id.line)   View line;
    @Bind(R.id.txtItemName)    TextView txtItemName;

    public CViewHolder(View itemView, RecyclerViewClickListener l) {
        super(itemView);
        // TODO Auto-generated constructor stub
        ButterKnife.bind(this, itemView);

        this.onClickListener = l;
        itemView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        onClickListener.onClick(v, getAdapterPosition(), v.getX(),v.getY());
    }
}


