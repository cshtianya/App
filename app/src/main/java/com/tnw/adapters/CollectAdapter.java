package com.tnw.adapters;

import android.content.Context;
import android.content.SyncStatusObserver;
import android.graphics.Paint;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tnw.R;
import com.tnw.base.ArrayListAdapter;
import com.tnw.entities.CollectNode;
import com.tnw.entities.CollectNode;

/**
 * 收藏列表适配器
 */
public class CollectAdapter extends ArrayListAdapter<CollectNode> {


	public CollectAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_collect_list_layout,parent, false);
			viewHolder = new ViewHolder();

			viewHolder.ivItemImage = (SimpleDraweeView) convertView.findViewById(R.id.draweeView);
			viewHolder.itemName = (TextView)convertView.findViewById(R.id.txtItemName);
			viewHolder.txtitemCollecLike = (TextView)convertView.findViewById(R.id.txtItemCollecLike);
			viewHolder.itemPrice = (TextView)convertView.findViewById(R.id.txtItemPrice);
			viewHolder.itemDisPrice = (TextView)convertView.findViewById(R.id.txtItemDisPrice);

			viewHolder.ivCancelCollect = (ImageView)convertView.findViewById(R.id.ivCancelCollect);



			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final CollectNode itemInfo =  this.mList.get(position);


		viewHolder.itemDisPrice.setText("￥" + itemInfo.getItemPrice());
		viewHolder.itemName.setText(itemInfo.getItemName());
//		viewHolder.itemPrice.setText("￥"+ itemInfo.getItemPrice());
		//viewHolder.txtitemCollecLike.setText("收藏人气  " + itemInfo.getItemCollecLike());
//		viewHolder.itemPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

		viewHolder.ivCancelCollect.setOnClickListener(new MClickListener(itemInfo));


		String urlStr = itemInfo.getItemImage();
		Uri uri = Uri.parse(urlStr);
		viewHolder.ivItemImage.setImageURI(uri);




		return convertView;
	}

	static class ViewHolder {
		SimpleDraweeView ivItemImage;
		TextView itemName;
		TextView txtitemCollecLike;
		TextView itemPrice;
		TextView itemDisPrice;
		ImageView ivCancelCollect; //取消收藏

	}



	public interface OnItemClickListener{
		void onItemClick(CollectNode itemInfo, Operate position);
	}
	
	private OnItemClickListener onItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public enum Operate{
		EDIT, DELETE,SELECT;
	}

	class MClickListener implements View.OnClickListener {

		private final CollectNode cInfo;

		public MClickListener(CollectNode info){
			this.cInfo = info;
		}

		@Override
		public void onClick(View view) {
			if(onItemClickListener!=null)
				switch (view.getId()){
					case R.id.ivCancelCollect:
						onItemClickListener.onItemClick(cInfo,Operate.DELETE);
						break;
				}
		}
	}


}
