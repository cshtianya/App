package com.tnw.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tnw.OrderState;
import com.tnw.R;
import com.tnw.base.ArrayListAdapter;
import com.tnw.entities.OrderNodeItem;

/**
 * 
 * @project 海宁皮都
 * @class：OrderDetialsAdapter
 * @DESC：TODO(商品详情适配器) 
 * @creator：SHChen   
 * @e-mail：csh_tianya@163.com 
 * @create：2014年5月12日 下午5:41:04   
 * @mender：
 * @modify：2014年5月12日 下午5:41:04
 * @remark：
 * @version 1.0
 */
public class OrderDetialsAdapter extends ArrayListAdapter<OrderNodeItem> {

	private LayoutInflater inflater;
	
	private int oState;
	private boolean canReturn;
	
	public OrderDetialsAdapter(Context context,int orderState) {
		super(context);
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		this.oState= orderState;
	}

	@Override
	public View getView(final int position, View vView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ChildHolder childHolder = null;
		if (vView == null) {
			vView = inflater.inflate(R.layout.order_item_details_list_item,parent, false);
			childHolder = new ChildHolder();

			childHolder.ivItemImg = (SimpleDraweeView) vView.findViewById(R.id.draweeView);
			childHolder.txtItemColor = (TextView) vView.findViewById(R.id.txtItemColor);
			childHolder.txtItemCount = (TextView) vView.findViewById(R.id.txtItemCount);
			childHolder.txtItemDesc = (TextView) vView.findViewById(R.id.txtItemDesc);
			childHolder.txtItemPrice = (TextView) vView.findViewById(R.id.txtItemPrice);
			childHolder.txtItemSize = (TextView) vView.findViewById(R.id.txtItemSize);

			vView.setTag(childHolder);

		} else {
			childHolder = (ChildHolder) vView.getTag();
		}

		final OrderNodeItem cInfo = getItem(position);

		childHolder.txtItemDesc.setText(cInfo.getItemName());
		childHolder.txtItemColor.setText(cInfo.getItemFirstName());
		childHolder.txtItemPrice.setText(cInfo.getItemPrice());
		childHolder.txtItemCount.setText(cInfo.getItemStock());
		childHolder.txtItemSize.setText(cInfo.getItemSecondName());
		childHolder.ivItemImg.setImageURI(Uri.parse( cInfo.getItemImage()));


		return vView;
	}
	
	
	
	public void setShowReturn(boolean isShow) {
		this.canReturn = isShow;
	}

	static class ChildHolder {

		SimpleDraweeView ivItemImg;
		TextView txtItemDesc;
		TextView txtItemColor;
		TextView txtItemSize;
		TextView txtItemPrice;
		TextView txtItemCount;

	}
	
//	public enum Operate{
//		DETAILS,RETURN,COMMENTS,REFUND;
//	}
//
//	public interface OnItemClickListener {
//		void onItemClick(OrderNodeItem pInfo, Operate operate);
//	}
//
//	private OnItemClickListener onItemClickListener;
//	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//		this.onItemClickListener = onItemClickListener;
//	}
//
//	private class MClickListener implements OnClickListener{
//		private Operate mEnum ;
//		private OrderNodeItem sInfo;
//		public MClickListener(OrderNodeItem info,Operate operate) {
//			// TODO Auto-generated constructor stub
//			this.mEnum = operate;
//			this.sInfo = info;
//		}
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			if (onItemClickListener != null)
//				onItemClickListener.onItemClick(sInfo,mEnum);
//		}
//
//	}

}
