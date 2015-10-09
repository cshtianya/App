package com.app.views.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.tnw.R;

/**
 *
 */
public class UICouponTabView extends LinearLayout implements View.OnClickListener {

    private RadioButton radoTabUse,radoInuse,radoOverdue;

    private int cacheId;

    public UICouponTabView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public UICouponTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.setOrientation(HORIZONTAL);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        changeView((RadioButton) v);
        if (onChangedListener != null)
            onChangedListener.onChanged(v, false);

        cacheId = v.getId();
    }

    public void initView() {
        radoTabUse = (RadioButton) findViewById(R.id.radoTabUse);
        radoInuse = (RadioButton) findViewById(R.id.radoInuse);
        radoOverdue = (RadioButton) findViewById(R.id.radoOverdue);


        radoTabUse.setOnClickListener(this);
        radoInuse.setOnClickListener(this);
        radoOverdue.setOnClickListener(this);


    }


    private void changeView(RadioButton itemView) {
        radoTabUse.setChecked(false);
        radoInuse.setChecked(false);
        radoOverdue.setChecked(false);


        itemView.setChecked(true);

    }

    public int getCacheId() {
        return cacheId;
    }

    public interface OnChangedListener {
        void onChanged(View v, boolean isRefresh);
    }

    private OnChangedListener onChangedListener;

    public void setOnChangedListener(OnChangedListener onChangedListener) {
        this.onChangedListener = onChangedListener;
    }

}
