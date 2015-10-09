package com.tnw.adapters;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.views.IphoneTreeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tnw.OrderState;
import com.tnw.R;
import com.tnw.base.ExpBaseListAdapter;
import com.tnw.entities.OrderNode;
import com.tnw.entities.OrderNodeItem;

public class OWaitPayAdapter extends ExpBaseListAdapter<OrderNode> implements
		IphoneTreeView.IphoneTreeHeaderAdapter {

	private LayoutInflater mInflater;
	private LayoutParams lp;

	private final int mOrderState;

	public OWaitPayAdapter(Activity context,int orderState) {
		super(context);
		// TODO Auto-generated constructor stub
		mOrderState = orderState;
		mInflater = LayoutInflater.from(context);
		int height = (int)context.getResources().getDimension(R.dimen.navigatorSize);
		int marging = (int)context.getResources().getDimension(R.dimen.margin);
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, height);
		lp.setMargins(0, 0, 0, marging);
}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GroupHolder viewHolder = null;
		if(convertView==null){
			viewHolder = new GroupHolder();
			convertView = mInflater.inflate(R.layout.o_item_nav_layout,parent, false);
			
			viewHolder.rlayNavItem = (RelativeLayout)convertView.findViewById(R.id.rlayNavItem);
			viewHolder.txtNavName = (TextView) convertView.findViewById(R.id.txtNavName);
			viewHolder.txtNavSubPrice = (TextView) convertView.findViewById(R.id.txtNavSubPrice);
			viewHolder.ivDetial = (ImageView) convertView.findViewById(R.id.ivDetial);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (GroupHolder) convertView.getTag();
		}
		
		final OrderNode pInfo = getGroup(groupPosition);
		
		viewHolder.txtNavName.setText(pInfo.getOrderShopName());
		viewHolder.rlayNavItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (onExpGroupClickListener != null)
					onExpGroupClickListener.onGroupClick(pInfo,false);
			}
		});
		
		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildHolder childHolder = null;
		if (convertView == null) {
			childHolder = new ChildHolder();
			convertView = mInflater.inflate(R.layout.o_item_wait_pay_child_layout,parent, false);

			childHolder.txtItemColor = (TextView) convertView.findViewById(R.id.txtItemColor);
			childHolder.txtItemCount = (TextView) convertView.findViewById(R.id.txtItemCount);
			childHolder.txtItemDesc = (TextView) convertView.findViewById(R.id.txtItemDesc);
			childHolder.txtItemPrice = (TextView) convertView.findViewById(R.id.txtItemPrice);
			childHolder.txtItemSize = (TextView) convertView.findViewById(R.id.txtItemSize);
			childHolder.ivItemImg = (SimpleDraweeView) convertView.findViewById(R.id.draweeView);
			childHolder.rlayPayMent = (RelativeLayout) convertView.findViewById(R.id.rlayPayMent);
			childHolder.btnPayment = (Button) convertView.findViewById(R.id.btnPayment);

			convertView.setTag(childHolder);
		} else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		childHolder.rlayPayMent.setLayoutParams(lp);
		childHolder.rlayPayMent.setVisibility(isLastChild ? View.VISIBLE: View.GONE);
		if(isLastChild){
			final OrderNode pInfo = getGroup(groupPosition);
			childHolder.btnPayment.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(onExpGroupClickListener!=null)
						onExpGroupClickListener.onGroupClick(pInfo,true);
				}
			});
		}

		if(mOrderState == OrderState.WAIT_PAY){
			childHolder.btnPayment.setText("付 款");
		}else if(mOrderState == OrderState.PAID){
			childHolder.btnPayment.setText("未派送");
		}else if(mOrderState == OrderState.DELIVERED){
			childHolder.btnPayment.setText("待收货");
		}else if(mOrderState == OrderState.COMPLETED){
			childHolder.btnPayment.setText("待评价");
		}

		final OrderNodeItem cInfo = getChild(groupPosition, childPosition);
		childHolder.txtItemDesc.setText(cInfo.getItemName());
		childHolder.txtItemColor.setText(cInfo.getItemFirstName());
		childHolder.txtItemPrice.setText(cInfo.getItemPrice());
		childHolder.txtItemCount.setText(cInfo.getItemStock());
		childHolder.txtItemSize.setText(cInfo.getItemSecondName());

		String urlStr = cInfo.getItemImage();
		Uri uri = Uri.parse(urlStr);
		childHolder.ivItemImg.setImageURI(uri);
		
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(onExpGroupClickListener!=null)
					onExpGroupClickListener.onGroupClick(getGroup(groupPosition),false);
			}
		});
		
		return convertView;
	}

	@Override
	public OrderNodeItem getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		final OrderNode pInfo = getGroup(groupPosition);
		return pInfo.getItemList().get(childPosition);
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		final OrderNode pInfo = getGroup(groupPosition);
		return pInfo == null ? 0 : pInfo.getItemList().size();
	}

	@Override
	public int getTreeHeaderState(int groupPosition, int childPosition) {
		final int childCount = getChildrenCount(groupPosition);
		if (childPosition == childCount - 1) {
			return PINNED_HEADER_PUSHED_UP;
		} else {
			return PINNED_HEADER_VISIBLE;
		}
	}

	static class GroupHolder {
		RelativeLayout rlayNavItem;
		TextView txtNavSubPrice;
		TextView txtNavName;
		ImageView ivDetial;
	}

	static class ChildHolder {
		SimpleDraweeView ivItemImg;
		TextView txtItemDesc;
		TextView txtItemColor;
		TextView txtItemSize;
		TextView txtItemPrice;
		TextView txtItemCount;
		RelativeLayout rlayPayMent;
		Button btnPayment;
	}

	@Override
	public void configureTreeHeader(View header, int groupPosition,
			int childPosition, int alpha) {
		// TODO Auto-generated method stub
		final OrderNode pInfo = getGroup(groupPosition);
		((TextView) header.findViewById(R.id.txtNavName)).setText(pInfo
				.getOrderShopName());

	}

	public interface OnExpGroupClickListener {
		void onGroupClick(OrderNode pInfo, boolean isOprete);
	}

	private OnExpGroupClickListener onExpGroupClickListener;

	public void setOnExpGroupClickListener(
			OnExpGroupClickListener onExpGroupClickListener) {
		this.onExpGroupClickListener = onExpGroupClickListener;
	}

	@Override
	public void onHeadViewClick(int groupPosition) {
		// TODO Auto-generated method stub
		final OrderNode pInfo = getGroup(groupPosition);
		if (onExpGroupClickListener != null)
			onExpGroupClickListener.onGroupClick(pInfo,false);

	}

}
