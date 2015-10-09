package com.tnw.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
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

public class ProductsHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<ProductNode> mList = null;
    private RecyclerViewClickListener onClickListener;

    private LayoutParams lp;

    private View mHeaderView;

    public void setOnClickListener(RecyclerViewClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ProductsHeaderAdapter(Context context, View headerView) {

        DisplayMetrics dis = context.getResources().getDisplayMetrics();
        int width = dis.widthPixels / 2 - Utils.dip2px(context,10);

        lp = new LayoutParams(width, width);
        this.mHeaderView = headerView;

    }

    public void setmList(List<ProductNode> mList) {
        this.mList = mList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        int size = mList == null ? 0 : mList.size();
        return mHeaderView == null?size:size+1;
    }

    @Override
    public int getItemViewType(int position) {
        return mHeaderView == null?VIEW_TYPE_ITEM
                :((position == 0) ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(viewType == VIEW_TYPE_HEADER){
            return new HeaderViewHolder(mHeaderView);
        }else{
            View rowView = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.product_list_item, viewGroup, false);
            return new MViewHolder(rowView, onClickListener,mHeaderView==null);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        // TODO Auto-generated method stub
        if (viewHolder instanceof MViewHolder) {
            MViewHolder  holder = (MViewHolder) viewHolder;

            ProductNode info = mList.get(mHeaderView == null?position:position-1);
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

    protected static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }


    protected static class MViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RecyclerViewClickListener onClickListener;
        private final boolean mStore;

        @Bind(R.id.ivItemImg)     SimpleDraweeView ivItemImg;
        @Bind(R.id.txtItemName)   TextView txtItemName;
        @Bind(R.id.txtLSellPrice) TextView txtLSellPrice;
        @Bind(R.id.txtLOrgPrice)  TextView txtLOrgPrice;
        @Bind(R.id.txtSellRecord) TextView txtSellRecord;

        public MViewHolder(View itemView, RecyclerViewClickListener l,boolean isStore) {
            super(itemView);
            // TODO Auto-generated constructor stub
            ButterKnife.bind(this,itemView);
            this.onClickListener = l;
            itemView.setOnClickListener(this);
            mStore = isStore;
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view, mStore?getAdapterPosition():getAdapterPosition()-1, view.getX(),view.getY());
        }
    }


}

