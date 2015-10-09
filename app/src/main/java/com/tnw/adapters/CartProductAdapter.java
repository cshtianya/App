package com.tnw.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tnw.R;
import com.tnw.base.ArrayListAdapter;
import com.tnw.entities.CartItem;
import com.tnw.entities.CartNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class CartProductAdapter extends ArrayListAdapter<Object>{

	private LayoutInflater mInflater;
	private HashMap<String,Integer> countSize;
	private HashMap<String,Double> priceSize;

	public CartProductAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(context);
		countSize = new HashMap<>();
		priceSize = new HashMap<>();
	}

	public HashMap<String, Integer> getCountSize() {
		return countSize;
	}

	public HashMap<String, Double> getPriceSize() {
		return priceSize;
	}


	public void clear(){
		mList.clear();
		notifyDataSetChanged();
	}

	public int getViewTypeCount() {
		return 2;
	}

	public int getItemViewType(int position) {
		Object item = getItem(position);
		return item instanceof CartNode?0:1;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		int type = getItemViewType(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			switch (type) {
				case 0:
					convertView = mInflater.inflate(R.layout.cart_item_nav_layout,parent, false);

					viewHolder.rlayNavItem = (RelativeLayout)convertView.findViewById(R.id.rlayItem);
					viewHolder.txtNavName = (TextView) convertView.findViewById(R.id.txtItemName);
					viewHolder.ivNavChoose = (ImageView) convertView.findViewById(R.id.cboxChoose);
					break;
				case 1:
					convertView = mInflater.inflate(R.layout.cart_item_desc_layout,parent, false);

					viewHolder.txtItemColor =(TextView) convertView.findViewById(R.id.txtItemColor);
					viewHolder.txtItemCount = (TextView) convertView.findViewById(R.id.txtItemCount);
					viewHolder.txtItemDesc = (TextView) convertView.findViewById(R.id.txtItemDesc);
					viewHolder.txtItemPrice = (TextView) convertView.findViewById(R.id.txtItemPrice);
					viewHolder.txtItemSize = (TextView) convertView.findViewById(R.id.txtItemSize);
					viewHolder.ivItemImg = (SimpleDraweeView) convertView.findViewById(R.id.ivItemImg);
					viewHolder.ivItemChoose = (ImageView) convertView.findViewById(R.id.cboxChoose);
					viewHolder.txtItemSubTotal = (TextView)convertView.findViewById(R.id.txtItemSubTotal);
					viewHolder.layStock = (LinearLayout)convertView.findViewById(R.id.layStock);
					viewHolder.txtStockAmount = (TextView)convertView.findViewById(R.id.txtStockAmount);

					break;
				default:
					break;
			}

			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		switch (type) {
			case 0:
				final CartNode pInfo = (CartNode) getItem(position);

				viewHolder.txtNavName.setText(pInfo.getShopName());
				final boolean opreate = checkAllContains(pInfo);
				viewHolder.ivNavChoose.setImageResource(opreate
						?R.mipmap.icon_radio_p:R.mipmap.icon_radio_n);
				viewHolder.ivNavChoose.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(opreate){
							removeAll(pInfo);
						}else{
							addAllChoose(pInfo);
						}

						if(onCheckedListener!=null)
							onCheckedListener.onChecked(opreate,pInfo);

					}
				});

				break;
			case 1:
				final CartItem cInfo = (CartItem) getItem(position);
				viewHolder.txtItemColor.setText(cInfo.getItemFirstName());
				viewHolder.txtItemCount.setText(cInfo.getItemStock());
				viewHolder.txtItemDesc.setText(cInfo.getItemCommodityName());
				viewHolder.txtItemPrice.setText(cInfo.getItemPrice());
				viewHolder.txtItemSize.setText(cInfo.getItemSecondName());
				viewHolder.ivItemImg.setImageURI(Uri.parse(cInfo.getItemImage()));

				final int amount = Integer.valueOf(cInfo.getItemStockAmount());
				final int itemStock = Integer.valueOf(cInfo.getItemStock());

				if(itemStock>amount){
					viewHolder.layStock.setVisibility(View.VISIBLE);
					viewHolder.txtStockAmount.setText(" 库存数："+amount);
				}else{
					viewHolder.layStock.setVisibility(View.GONE);
				}

				viewHolder.txtItemSubTotal.setText("￥"+
						String.format("%.2f", subTotal(Double.valueOf(cInfo.getItemPrice())
								,itemStock)));
				final boolean itemCheck = checkItemContains(cInfo);
				viewHolder.ivItemChoose.setImageResource(itemCheck
						?R.mipmap.icon_radio_p:R.mipmap.icon_radio_n);
				viewHolder.ivItemChoose.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(itemCheck){
							removeChild(cInfo);
						}else{
							addChild(cInfo);
						}
						if(onCheckedListener!=null)
							onCheckedListener.onChecked(itemCheck,cInfo);

					}
				});


//				viewHolder.rlayContent.setLongClickable(true);
//				viewHolder.rlayContent.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						if(onNItemClickListener!=null)
//							onNItemClickListener.onItemClick(cInfo);
//					}
//				});
//				viewHolder.rlayContent.setOnLongClickListener(new OnLongClickListener() {
//					@Override
//					public boolean onLongClick(View v) {
//						// TODO Auto-generated method stub
//						if(onItemLongClickListener!=null)
//							onItemLongClickListener.onLongItemClick(cInfo);
//						return false;
//					}
//				});

				break;
			default:
				break;
		}

		return convertView;
	}

	public void addAllChoose(CartNode pInfo){
		List<CartItem> itemList = pInfo.getItemList();

		for (int i = 0; i < itemList.size(); i++) {
			CartItem cInfo = itemList.get(i);
			addChild(cInfo);
		}
	}

	public void removeAll(CartNode pInfo){
		List<CartItem> itemList = pInfo.getItemList();

		for (int i = 0; i < itemList.size(); i++) {
			CartItem cInfo = itemList.get(i);
			removeChild(cInfo);
		}

	}

	public void addChild(CartItem cInfo){
		int amount = Integer.valueOf(cInfo.getItemStockAmount());
		int itemStock = Integer.valueOf(cInfo.getItemStock());
		final double price = Double.valueOf(cInfo.getItemPrice());
		if(itemStock>amount)itemStock = amount;

		priceSize.put(cInfo.getItemId(), price);
		countSize.put(cInfo.getItemId(), itemStock);

	}

	public void removeChild(CartItem cInfo){
		priceSize.remove(cInfo.getItemId());
		countSize.remove(cInfo.getItemId());
	}

	public boolean checkItemContains(CartItem cInfo){
		return countSize.containsKey(cInfo.getItemId());
	}

	public boolean checkAllContains(CartNode pInfo){
		List<CartItem> itemList = pInfo.getItemList();
		boolean result = true;
		for (int i = 0; i < itemList.size(); i++) {
			CartItem cInfo = itemList.get(i);
			if(!checkItemContains(cInfo)){
				result = false;
				break;
			}
		}
		return result;
	}


	private double subTotal(double itemPrice,int itemCount){
		int count = itemCount;
		double price = itemPrice;
		return count*price;
	}

	public double totalPrice(){
		double result = 0;
		for(Iterator<String> it =  priceSize.keySet().iterator();it.hasNext();){
			String strKey = it.next();
			double itemPrice = priceSize.get(strKey);
			result += subTotal(itemPrice,countSize.get(strKey));
		}
		return result;
	}

	public int totalCount(){
		int result = 0;
		for(Iterator<String> it =  countSize.keySet().iterator();it.hasNext();){
			int itemCount = countSize.get(it.next());
			result += itemCount;
		}
		return result;
	}



	public interface OnCheckedListener{
		void onChecked(boolean isExist,Object info);
	}

	private OnCheckedListener onCheckedListener;

	public void setOnCheckedListener(OnCheckedListener onCheckedListener) {
		this.onCheckedListener = onCheckedListener;
	}

	public interface OnNItemClickListener{
		void onItemClick(CartItem info);
	}

	private OnNItemClickListener onNItemClickListener;

	public void setOnNItemClickListener(OnNItemClickListener onNItemClickListener) {
		this.onNItemClickListener = onNItemClickListener;
	}

	public interface OnItemLongClickListener{
		void onLongItemClick(CartItem itemInfo);
	}

	private OnItemLongClickListener onItemLongClickListener;
	public void setOnItemLongClickListener(
			OnItemLongClickListener onItemLongClickListener) {
		this.onItemLongClickListener = onItemLongClickListener;
	}


	static class ViewHolder {
		RelativeLayout rlayNavItem;
		TextView txtNavName;
		ImageView ivNavChoose;

		SimpleDraweeView ivItemImg;
		ImageView ivItemChoose;
		TextView txtItemDesc;
		TextView txtItemColor;
		TextView txtItemSize;
		TextView txtItemPrice;
		TextView txtItemCount;
		TextView txtItemSubTotal;
		LinearLayout layStock;
		TextView txtStockAmount;
	}
	
}
