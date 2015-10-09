package com.tnw.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.views.wheel.adapters.AbstractWheelTextAdapter;
import com.tnw.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/23 0023.
 */
public class WheelTextAdapter extends AbstractWheelTextAdapter {

    private final ArrayList<String> list;
    private final static int maxTextSize = 20;
    private final static int minTextSize = 14;

    public WheelTextAdapter(Context context, ArrayList<String> list, int currentItem) {
        super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxTextSize, minTextSize);
        this.list = list;

        setItemTextResource(R.id.tempValue);
    }

    @Override
    public View getItem(int index, View cachedView, ViewGroup parent) {
        View view = super.getItem(index, cachedView, parent);
        return view;
    }

    @Override
    public int getItemsCount() {
        return list.size();
    }

    @Override
    public CharSequence getItemText(int index) {
        return list.get(index) + "";
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, WheelTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(maxTextSize);
            } else {
                textvew.setTextSize(minTextSize);
            }
        }
    }

}
