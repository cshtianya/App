package com.tnw.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tnw.R;
import com.tnw.base.ArrayListAdapter;
import com.tnw.entities.DeliveryNode;

/**
 * 收货地址列表适配器
 * Created by CSH on 2015/9/21 0021.
 */
public class DeliveryAdapter extends ArrayListAdapter<DeliveryNode> {

    private final boolean mSelect ;

    public DeliveryAdapter(Context context,boolean isSelect) {
        super(context);
        this.mSelect = isSelect;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_delivery_list_item,parent, false);
            vh = new ViewHolder();

            vh.laySelect = (LinearLayout)convertView.findViewById(R.id.laySelect);
            vh.txtItemName = (TextView) convertView.findViewById(R.id.txtItemName);
            vh.txtPhoneNum = (TextView) convertView.findViewById(R.id.txtPhoneNum);
            vh.txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
            vh.txtIsDefault = (TextView) convertView.findViewById(R.id.txtIsDefault);

            vh.rlayToolbar = (RelativeLayout)convertView.findViewById(R.id.rlayToolbar);
            vh.ivDelete = (ImageView)convertView.findViewById(R.id.ivDelete);
            vh.ivEdit = (ImageView)convertView.findViewById(R.id.ivEdit);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.rlayToolbar.setVisibility(mSelect ? View.GONE : View.VISIBLE);

        final DeliveryNode info = mList.get(position);
        vh.txtItemName.setText(info.getItemName());
        vh.txtPhoneNum.setText(info.getItemPhone());
        vh.txtAddress.setText(
                info.getItemProvince() + " " +
                    info.getItemCity() + " " +
                    info.getItemArea() + " " +
                    info.getItemAddress());

        boolean isDefault = TextUtils.equals("1",info.getItemIsDefault());
        vh.txtIsDefault.setVisibility(isDefault?View.VISIBLE:View.GONE);
        vh.ivDelete.setVisibility(isDefault?View.INVISIBLE:View.VISIBLE);

        vh.laySelect.setOnClickListener(new MClickListener(info));
        vh.ivDelete.setOnClickListener(new MClickListener(info));
        vh.ivEdit.setOnClickListener(new MClickListener(info));

        return convertView;
    }

    static class ViewHolder {
        LinearLayout laySelect;
        RelativeLayout rlayToolbar;
        TextView txtItemName,txtIsDefault,txtPhoneNum,txtAddress;
        ImageView ivEdit,ivDelete;

    }

    public interface OnItemClickListener{
        void onItemClick(DeliveryNode itemInfo,Operate operate);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public enum Operate{
        EDIT, DELETE,SELECT;
    }

    class MClickListener implements OnClickListener {

        private final DeliveryNode mInfo;

        public MClickListener(DeliveryNode info){
            this.mInfo = info;
        }

        @Override
        public void onClick(View view) {
            if(onItemClickListener!=null)
            switch (view.getId()){
                case R.id.ivEdit:
                    onItemClickListener.onItemClick(mInfo,Operate.EDIT);
                    break;
                case R.id.ivDelete:
                    onItemClickListener.onItemClick(mInfo,Operate.DELETE);
                    break;
                case R.id.laySelect:
                    onItemClickListener.onItemClick(mInfo,Operate.SELECT);
                    break;
            }
        }
    }

}
