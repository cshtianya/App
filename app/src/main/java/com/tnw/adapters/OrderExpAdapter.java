package com.tnw.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tnw.R;
import com.tnw.entities.OrderPreInfo;
import com.tnw.entities.OrderPreInfo.ShopListEntity;
import com.tnw.entities.OrderPreInfo.ItemListEntity;

import java.util.ArrayList;
import java.util.List;



public class OrderExpAdapter extends BaseExpandableListAdapter {

	private LayoutInflater mInflater;
	private ArrayList<ShopListEntity> allList;
	private Resources res;

	public OrderExpAdapter(Context context) {
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(context);
		allList = new ArrayList<>();
		res = context.getResources();
	}

	public void setList(List<ShopListEntity> list, boolean boo) {
		if (list != null) {
			this.allList.addAll(list);
			if (boo) {
				notifyDataSetChanged();
			}
		}
	}

	@Override
	public OrderPreInfo.ItemListEntity getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return (ItemListEntity)
				getGroup(groupPosition).getItemList().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getGroupView(final int groupPosition, final boolean isExpanded,
			View vView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vHolder = null;
		if (vView == null) {
			vView = mInflater.inflate(R.layout.order_item_nav_layout,parent, false);
			vHolder = new ViewHolder();

			vHolder.txtItemName = (TextView) vView.findViewById(R.id.txtItemName);
			vHolder.ivExpandIcon = (ImageView) vView.findViewById(R.id.ivExpandIcon);
			vHolder.txtSubCount = (TextView) vView.findViewById(R.id.txtSubCount);
			vHolder.txtSubPrice = (TextView) vView.findViewById(R.id.txtSubPrice);

			vView.setTag(vHolder);

		} else {
			vHolder = (ViewHolder) vView.getTag();
		}

		final ShopListEntity pInfo = getGroup(groupPosition);
		vHolder.txtSubCount.setText(res.getString(R.string.cart_goods_total)
				+ subSubCount(groupPosition)
				+ res.getString(R.string.cart_count_unit));

		vHolder.txtSubPrice.setText("￥" + subSubPrice(groupPosition));
		vHolder.txtItemName.setText(pInfo.getShopName());

		vView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (onExpandListener != null)
					onExpandListener.onExpanded(pInfo, isExpanded,groupPosition);
			}
		});

		vHolder.ivExpandIcon
				.setImageResource(isExpanded ? R.mipmap.collection_arrow_down
						: R.mipmap.collection_arrow_up);

		return vView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View vView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ChildHolder vHolder = null;
		if (vView == null) {
			vView = mInflater.inflate(R.layout.order_item_desc_layout,parent, false);
			vHolder = new ChildHolder();

			vHolder.txtItemColor = (TextView) vView.findViewById(R.id.txtItemColor);
			vHolder.txtItemCount = (TextView) vView.findViewById(R.id.txtItemCount);
			vHolder.txtItemDesc = (TextView) vView.findViewById(R.id.txtItemDesc);
			vHolder.txtItemPrice = (TextView) vView.findViewById(R.id.txtItemPrice);
			vHolder.txtItemSize = (TextView) vView.findViewById(R.id.txtItemSize);
			vHolder.ivItemImg = (SimpleDraweeView) vView.findViewById(R.id.ivItemImg);
			vHolder.txtItemSubTotal = (TextView) vView.findViewById(R.id.txtItemSubTotal);

			vView.setTag(vHolder);

		} else {
			vHolder = (ChildHolder) vView.getTag();
		}

		final ItemListEntity cInfo = getChild(groupPosition, childPosition);
		vHolder.txtItemColor.setText(cInfo.getItemFirstName());
		vHolder.txtItemCount.setText(cInfo.getItemStock());
		vHolder.txtItemDesc.setText(cInfo.getItemCommodityName());
		vHolder.txtItemPrice.setText(cInfo.getItemPrice());
		vHolder.txtItemSize.setText(cInfo.getItemSecondName());
		vHolder.txtItemSubTotal.setText("￥"+ String.format("%.2f"
				, itemPrice(cInfo.getItemPrice(), cInfo.getItemStock())));

		String urlStr = cInfo.getItemImage();
		Uri uri = Uri.parse(urlStr);
		vHolder.ivItemImg.setImageURI(uri);


		return vView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return getGroup(groupPosition).getItemList().size();
	}

	@Override
	public ShopListEntity getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return allList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return allList.size();
	}

	@Override
	public int getChildTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getChildType(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return (getChildrenCount(groupPosition) - 1) == childPosition ? 0 : 1;
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}

	static class ViewHolder {
		TextView txtItemName;
		TextView txtSubCount;
		TextView txtSubPrice;
		ImageView ivExpandIcon;
	}

	static class ChildHolder {
		SimpleDraweeView ivItemImg;
		TextView txtItemDesc;
		TextView txtItemColor;
		TextView txtItemSize;
		TextView txtItemPrice;
		TextView txtItemCount;
		TextView txtItemSubTotal;
	}

	private int subSubCount(int groupPosition) {
		int result = 0;
		ShopListEntity pInfo = getGroup(groupPosition);

		for (int i = 0; i < pInfo.getItemList().size(); i++) {
			ItemListEntity cInfo = pInfo.getItemList().get(i);
			result += Integer.valueOf(cInfo.getItemStock());
		}
		return result;
	}

	private String subSubPrice(int groupPosition) {
		double result = 0.00;
		ShopListEntity pInfo = getGroup(groupPosition);

		for (int i = 0; i < pInfo.getItemList().size(); i++) {
			ItemListEntity cInfo = pInfo.getItemList().get(i);
			result += itemPrice(cInfo.getItemPrice(), cInfo.getItemStock());
		}
		return String.format("%.2f", result);
	}

	public double itemPrice(String itemPrice, String itemCount) {
		int count = Integer.valueOf(itemCount);
		float price = Float.valueOf(itemPrice);
//		String.format("%.2f", result);
		return count * price;
	}

	public interface OnExpandListener {
		void onExpanded(ShopListEntity pInfo, boolean isExpanded,
						int groupPosition);
	}

	private OnExpandListener onExpandListener;

	public void setOnExpandListener(OnExpandListener onExpandListener) {
		this.onExpandListener = onExpandListener;
	}

}
