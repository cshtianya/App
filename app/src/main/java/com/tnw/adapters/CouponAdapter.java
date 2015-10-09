package com.tnw.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tnw.R;
import com.tnw.base.ArrayListAdapter;
import com.tnw.entities.CollectNode;
import com.tnw.entities.CouponNode;

/**
 * 我的优惠券适配器
 */
public class CouponAdapter extends ArrayListAdapter<CouponNode> {

	private LayoutInflater inflater;

	public CouponAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_coupon_list_layout,parent, false);
			viewHolder = new ViewHolder();

			viewHolder.couponView = (SimpleDraweeView) convertView.findViewById(R.id.couponView);
			viewHolder.txtItemCouponName = (TextView)convertView.findViewById(R.id.txtItemCouponName);
			viewHolder.txtItemValidity = (TextView)convertView.findViewById(R.id.txtItemValidity);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final CouponNode itemInfo =  getItem(position);

		viewHolder.txtItemCouponName.setText(itemInfo.getItemName());
		viewHolder.txtItemValidity.setText(itemInfo.getItemTime());
		String urlStr = itemInfo.getItemImage();
		Uri uri = Uri.parse(urlStr);
		viewHolder.couponView.setImageURI(uri);
		
		return convertView;
	}

	static class ViewHolder {
		SimpleDraweeView couponView;
		
		TextView txtItemCouponName;
		TextView txtItemValidity;

	}

	public interface OnItemClickListener{
		void onItemClick(CollectNode itemInfo, int position);
	}
	
	private OnItemClickListener onItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}


}
