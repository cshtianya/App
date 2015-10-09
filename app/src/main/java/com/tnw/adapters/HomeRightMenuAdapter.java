package com.tnw.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tnw.R;
import com.tnw.base.ArrayListAdapter;
import com.tnw.entities.CategoryNode;
import com.tnw.utils.AssetUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @param
 * @author chenSH
 * @E_mail csh_tianya@163.com
 * @update 2013-6-18 下午9:37:39
 * @version V1.0
 * @copy 上海恒志信息技术有限公司
 */
public class HomeRightMenuAdapter extends ArrayListAdapter<CategoryNode> {

	private LayoutInflater inflater;
	private int selectId = -1;

	public HomeRightMenuAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_home_right_menu_list,parent, false);
			viewHolder = new ViewHolder();

			viewHolder.itemImage = (ImageView)convertView.findViewById(R.id.itemImage);
			viewHolder.txtItemName = (TextView) convertView.findViewById(R.id.txtItemName);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final CategoryNode itemInfo =  this.mList.get(position);
		viewHolder.txtItemName.setText(itemInfo.getItemName());
		if(position == selectId){
			viewHolder.itemImage.setImageBitmap(AssetUtils.readImage(mContext
					, "images/" + itemInfo.getItemImage() + "_press.png"));
		}else{
			viewHolder.itemImage.setImageBitmap(AssetUtils.readImage(mContext
					,"images/"+itemInfo.getItemImage() + ".png"));
		}

		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(onItemClickListener!=null){
					onItemClickListener.onItemClick(itemInfo,position);

				}
			}
		});
		return convertView;
	}

	static class ViewHolder {
		ImageView itemImage;
		TextView txtItemName;
	}


	public void setSelectId(int selectId) {
		this.selectId = selectId;
		this.notifyDataSetChanged();
	}

    // 修改实体对象，重新请求
	public interface OnItemClickListener{
		void onItemClick(CategoryNode itemInfo, int position);
	}
	
	private OnItemClickListener onItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}


}
