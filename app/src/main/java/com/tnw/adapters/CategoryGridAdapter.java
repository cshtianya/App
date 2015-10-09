package com.tnw.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tnw.R;
import com.tnw.entities.CategoryNode;
import com.tnw.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CSh on 2015/7/13 0013.
 */
public class CategoryGridAdapter extends Adapter<GViewHolder>  implements DataBindListener<List<CategoryNode>> {

    private List<CategoryNode> mList;
    private RecyclerViewClickListener onClickListener;

    private LinearLayout.LayoutParams lp;

    public CategoryGridAdapter(Context context){

        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.75)- Utils.dip2px(context, 40);
        int itemWidth = width/3- Utils.dip2px(context,20);
        lp = new LinearLayout.LayoutParams(itemWidth ,itemWidth);
    }

    public void setOnClickListener(RecyclerViewClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setmList(List<CategoryNode> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public GViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rowView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.category_grid_item, parent, false);

        return new GViewHolder(rowView,onClickListener);
    }

    @Override
    public void onBindViewHolder(GViewHolder holder, int position) {
        CategoryNode info = mList.get(position);

        holder.draweeView.setLayoutParams(lp);
        holder.draweeView.setImageURI(Uri.parse(info.getItemImage()));
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

    }
}


 class GViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final RecyclerViewClickListener onClickListener;
    @Bind(R.id.draweeView)     SimpleDraweeView draweeView;
    @Bind(R.id.txtItemName)    TextView txtItemName;

    public GViewHolder(View itemView, RecyclerViewClickListener l) {
        super(itemView);
        // TODO Auto-generated constructor stub
        ButterKnife.bind(this, itemView);

        this.onClickListener = l;

        itemView.setOnClickListener(this);
    }

     @Override
     public void onClick(View view) {
         onClickListener.onClick(view, getAdapterPosition(), view.getX(),
                 view.getY());
     }


 }
