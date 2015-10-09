package com.tnw.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tnw.R;
import com.tnw.fragments.ProductCommentsFragment;
import com.tnw.fragments.ProductIntroFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CSH on 2015/8/12 0012.
 */
@Deprecated
public class ProductDescActivity extends BaseActivity{

    @Bind(R.id.toolbar)  Toolbar toolbar;
    @Bind(R.id.radioGroup)      RadioGroup radioGroup;
    @Bind(R.id.radoDesc)        RadioButton radoDesc;
    @Bind(R.id.radoComment)     RadioButton radoComment;

    private String productId;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
            finish();
            overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_desc);
        ButterKnife.bind(this);
        productId = getIntent().getStringExtra("productId");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.radoDesc:
                        ProductIntroFragment.newInstance(productId);
                        radoDesc.setTextColor(getResources().getColor(R.color.accent));
                        radoComment.setTextColor(getResources().getColor(R.color.textColor));
                        break;
                    case R.id.radoComment:
                        ProductCommentsFragment.newInstance(productId);
                        radoDesc.setTextColor(getResources().getColor(R.color.textColor));
                        radoComment.setTextColor(getResources().getColor(R.color.accent));
                        break;
                }
            }
        });

    }




}
