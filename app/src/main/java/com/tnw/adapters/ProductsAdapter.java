package com.tnw.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.tnw.R;
import com.tnw.entities.ProductNode;
import com.tnw.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductsAdapter extends Adapter<ProductsAdapter.MViewHolder>  {

    private List<ProductNode> mList = null;
    private RecyclerViewClickListener onClickListener;

    private LayoutParams lp;

    public void setOnClickListener(RecyclerViewClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ProductsAdapter(Context context) {

        DisplayMetrics dis = context.getResources().getDisplayMetrics();
        int width = dis.widthPixels / 2 - Utils.dip2px(context, 10);

        lp = new LayoutParams(width, width);

    }

    public void setmList(List<ProductNode> mList) {
        this.mList = mList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return mList == null ? 0 : mList.size();
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        // TODO Auto-generated method stub
        View rowView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.product_list_item, viewGroup, false);

        return new MViewHolder(rowView, onClickListener);
    }

    @Override
    public void onBindViewHolder(final MViewHolder holder, final int position) {
        // TODO Auto-generated method stub
        ProductNode info = mList.get(position);
        //+CommonCfg.SIZE_480x640
        holder.ivItemImg.setLayoutParams(lp);

        String urlStr = info.getItemImage();
        Uri uri = Uri.parse(urlStr);
        holder.ivItemImg.setImageURI(uri);

        holder.txtItemName.setText(info.getItemName() + "");
        holder.txtLSellPrice.setText("￥" + info.getItemPrice() + "");
        holder.txtLOrgPrice.setText(info.getItemCostPrice() + "");
        holder.txtSellRecord.setText("已售" + info.getItemSellCount() + "件");
        holder.txtLOrgPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

    }

    public List<ProductNode> getMList() {
        // TODO Auto-generated method stub
        return mList;
    }

    public void appendList(List<ProductNode> list) {
        // TODO Auto-generated method stub
        this.mList.addAll(list);
        notifyDataSetChanged();
    }


    protected static class MViewHolder extends ViewHolder implements View.OnClickListener {

        private final RecyclerViewClickListener onClickListener;

        @Bind(R.id.ivItemImg)     SimpleDraweeView ivItemImg;
        @Bind(R.id.txtItemName)   TextView txtItemName;
        @Bind(R.id.txtLSellPrice) TextView txtLSellPrice;
        @Bind(R.id.txtLOrgPrice)  TextView txtLOrgPrice;
        @Bind(R.id.txtSellRecord) TextView txtSellRecord;

        public MViewHolder(View itemView, RecyclerViewClickListener l) {
            super(itemView);
            // TODO Auto-generated constructor stub
            ButterKnife.bind(this,itemView);
            this.onClickListener = l;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view, getAdapterPosition(), view.getX(),view.getY());
        }
    }


}

