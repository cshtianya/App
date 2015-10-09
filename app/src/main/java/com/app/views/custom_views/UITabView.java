package com.app.views.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.tnw.R;

/**
 * Created by Administrator on 2015/6/23 0023.
 */
public class UITabView extends LinearLayout implements View.OnClickListener {

    private RadioButton radoHome, radoCart, radoAccount;//, radoMore，radoCategory;

    private int cacheId;

    public UITabView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public UITabView(Context context, AttributeSet attrs) {
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
        radoHome = (RadioButton) findViewById(R.id.radoHome);
        radoCart = (RadioButton) findViewById(R.id.radoCart);
        radoAccount = (RadioButton) findViewById(R.id.radoAccount);

        radoHome.setOnClickListener(this);
        radoCart.setOnClickListener(this);
        radoAccount.setOnClickListener(this);

    }


    private void changeView(RadioButton itemView) {
        radoHome.setChecked(false);
        radoCart.setChecked(false);
        radoAccount.setChecked(false);

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
