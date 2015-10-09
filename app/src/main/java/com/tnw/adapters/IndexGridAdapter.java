package com.tnw.adapters;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tnw.R;
import com.tnw.entities.IndexGridNode.GridItem;
import com.tnw.utils.Utils;

import java.util.List;

/**
 * Created by CSH on 2015/9/16 0016.
 */
public class IndexGridAdapter extends BaseAdapter{

    private final List<GridItem> mList;
    private LayoutInflater inflater;

    private LayoutParams lp;

    public IndexGridAdapter(Context context,List<GridItem> list) {
        this.mList = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        DisplayMetrics dis = context.getResources().getDisplayMetrics();
        int width = dis.widthPixels- Utils.dip2px(context,3)*2;
        lp = new LayoutParams(width/3, width/3);

    }

    public int getSize(){
        return null == mList?0:mList.size();
    }

    @Override
    public int getCount(){
        return mList.size();
    }

    @Override
    public GridItem getItem(int arg0) {
        return mList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int arg0, View convertView, ViewGroup arg2)
    {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_index_grid_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.txtItemName = (TextView)convertView.findViewById(R.id.txtItemName);
            viewHolder.txtItemPrice = (TextView)convertView.findViewById(R.id.txtItemPrice);
            viewHolder.draweeView = (SimpleDraweeView)convertView.findViewById(R.id.draweeView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(getItem(arg0)!=null){
            viewHolder.txtItemName.setText(getItem(arg0).getItemName());
            String price = getItem(arg0).getItemPrice();
            if(TextUtils.isEmpty(price)){
                viewHolder.txtItemPrice.setVisibility(View.GONE);
            }else{
                viewHolder.txtItemPrice.setText("￥"+price);
            }

            String urlStr = getItem(arg0).getItemImage();
            Uri uri = Uri.parse(urlStr);
            viewHolder.draweeView.setLayoutParams(lp);
            viewHolder.draweeView.setImageURI(uri);
        }
        return convertView;
    }

    static class ViewHolder{
        TextView txtItemName;
        TextView txtItemPrice;
        SimpleDraweeView draweeView;
    }


}
