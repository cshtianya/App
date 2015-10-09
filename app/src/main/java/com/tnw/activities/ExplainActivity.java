package com.tnw.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tnw.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 配送说明
 * Created by CSH on 2015/7/14 0014.
 */
public class ExplainActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.toolbar)  Toolbar toolbar;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }
}
