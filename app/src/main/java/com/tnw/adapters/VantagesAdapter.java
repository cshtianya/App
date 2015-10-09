package com.tnw.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tnw.R;
import com.tnw.base.ArrayListAdapter;
import com.tnw.entities.VantagesNode;


public class VantagesAdapter extends ArrayListAdapter<VantagesNode> {

	private LayoutInflater inflater;
	private int selectId = -1;

	public VantagesAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_vantages_list_layout,parent, false);
			viewHolder = new ViewHolder();

			viewHolder.txtItemName = (TextView) convertView.findViewById(R.id.txtItemName);
			viewHolder.txtItemTime = (TextView)convertView.findViewById(R.id.txtItemTime);
			viewHolder.txtItemScore = (TextView)convertView.findViewById(R.id.txtItemScore);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final VantagesNode itemInfo =  this.mList.get(position);
		viewHolder.txtItemName.setText(itemInfo.getItemName());
		viewHolder.txtItemTime.setText(itemInfo.getItemCreateTime());
		String signStr = TextUtils.equals("0", itemInfo.getItemType())?"-":"+";
		viewHolder.txtItemScore.setText(signStr + itemInfo.getItemScore());

		return convertView;
	}

	static class ViewHolder {
		TextView txtItemName;
		TextView txtItemTime;
		TextView txtItemScore;
	}


	public void setSelectId(int selectId) {
		this.selectId = selectId;
		this.notifyDataSetChanged();
	}


	public interface OnItemClickListener{
		void onItemClick(VantagesNode itemInfo, int position);
	}
	
	private OnItemClickListener onItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}


}
