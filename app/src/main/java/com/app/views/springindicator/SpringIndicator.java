/*
 * Copyright 2015 chenupt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.app.views.springindicator;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tnw.R;
import com.app.views.viewpager.ScrollerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenupt@gmail.com on 2015/1/31.
 * Description : Tab layout container
 */
public class SpringIndicator extends FrameLayout{

    private static final int INDICATOR_ANIM_DURATION = 1000;

    private float acceleration = 0.5f;
    private float headMoveOffset = 0.6f;
    private float footMoveOffset = 1- headMoveOffset;
    private float radiusMax;
    private float radiusMin;
    private float radiusOffset;

    private float textSize;
    private int textColorId;
    private int selectedTextColorId;
    private int indicatorColorId;
    private int indicatorColorsId;
    private int[] indicatorColorArray;

    private LinearLayout contentView;
    private FrameLayout tabContainer;
    private SpringView springView;
    private ScrollerViewPager viewPager;

    private List<TextView> tabs;

    private ViewPager.OnPageChangeListener delegateListener;
    private TabClickListener tabClickListener;
    private ObjectAnimator indicatorColorAnim;

    public SpringIndicator(Context context) {
        this(context, null);
    }

    public SpringIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs){
        textColorId = R.color.accent;
        selectedTextColorId = R.color.accentLight;
        indicatorColorId = R.color.accent;
       
        textSize = getResources().getDimension(R.dimen.text_size_min);
        radiusMax = getResources().getDimension(R.dimen.si_default_radius_max);
        radiusMin = getResources().getDimension(R.dimen.si_default_radius);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SpringIndicator);
        textColorId = a.getResourceId(R.styleable.SpringIndicator_siTextColor, textColorId);
        selectedTextColorId = a.getResourceId(R.styleable.SpringIndicator_siSelectedTextColor, selectedTextColorId);
        textSize = a.getDimension(R.styleable.SpringIndicator_siTextSize, textSize);
        indicatorColorId = a.getResourceId(R.styleable.SpringIndicator_siIndicatorColor, indicatorColorId);
        indicatorColorsId = a.getResourceId(R.styleable.SpringIndicator_siIndicatorColors, 0);
        radiusMax = a.getDimension(R.styleable.SpringIndicator_siRadiusMax, radiusMax);
        radiusMin = a.getDimension(R.styleable.SpringIndicator_siRadiusMin, radiusMin);
        a.recycle();

        if(indicatorColorsId != 0){
            indicatorColorArray = getResources().getIntArray(indicatorColorsId);
        }
        radiusOffset = radiusMax - radiusMin;
        
    }


    public void setViewPager(final ScrollerViewPager viewPager) {
        this.viewPager = viewPager;
    }


    private LinearLayout.LayoutParams templp,lp;
    
    public void initSpringView() {
    	this.removeAllViews();

    	LinearLayout.LayoutParams lparam = new LinearLayout
        		.LayoutParams(LayoutParams.WRAP_CONTENT, (int)dp2px(30),1.0f);
    	contentView = new LinearLayout(getContext());
    	contentView.setOrientation(LinearLayout.HORIZONTAL);
    	contentView.setLayoutParams(lparam);
    	contentView.setGravity(Gravity.CENTER);
    	
//    	int sWidth = getResources().getDisplayMetrics().widthPixels;
//    	int width = (int)(sWidth*0.3);
    	templp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, (int)dp2px(30),1.0f);
    	lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, (int)dp2px(30),1.0f);
    	lp.setMargins(5,0,5,0);
    	
    	LinearLayout tempView = new LinearLayout(getContext());
    	tempView.setLayoutParams(templp);
        tempView.setGravity(Gravity.CENTER);
    	contentView.addView(tempView);
    	 
    	addTabContainerView();
        addTabItems();
        
        setUpListener();
        
    }

    private void addTabContainerView() {
        tabContainer = new FrameLayout(getContext());
        tabContainer.setLayoutParams(lp);
        contentView.setGravity(Gravity.CENTER);
        contentView.addView(tabContainer);
        
        LinearLayout tempB = new LinearLayout(getContext());
        tempB.setLayoutParams(templp);
        contentView.addView(tempB);
        contentView.setGravity(Gravity.CENTER);
        
        /*添加动画点的视图*/
        springView = new SpringView(getContext());
        springView.setIndicatorColor(getResources().getColor(indicatorColorId));
        tabContainer.addView(springView);
        
        addView(contentView);
    }

    private void addTabItems() {
    	LinearLayout tabItemView = new LinearLayout(getContext());
    	ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams
    			(LayoutParams.WRAP_CONTENT, (int)dp2px(30));
    	tabItemView.setLayoutParams(lp);
        tabItemView.setGravity(Gravity.CENTER);
    	tabItemView.setOrientation(LinearLayout.HORIZONTAL);
    	
        LinearLayout.LayoutParams layoutParams = new LinearLayout.
        		LayoutParams((int)dp2px(30),(int)dp2px(30),1.0f);
        
        int size = viewPager.getAdapter().getCount();
        tabs = new ArrayList<TextView>(size);
        for (int i = 0; i < size; i++) {
            TextView textView = new TextView(getContext());
            textView.setText(""+(i+1));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            textView.setTextColor(getResources().getColor(textColorId));
            
            textView.setLayoutParams(layoutParams);
            final int position = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tabClickListener == null || tabClickListener.onTabClick(position)){
                        viewPager.setCurrentItem(position);
                    }
                }
            });
            
            tabs.add(textView);
            tabItemView.addView(textView);
            
        }
        
        tabContainer.addView(tabItemView);
        tabContainer.setForegroundGravity(Gravity.CENTER);
    }

    /**
     * Set current point position.
     */
    private void createPoints(){
    	
    	if(tabs!=null && viewPager!=null && springView!=null){
    		View view = tabs.get(viewPager.getCurrentItem());
    		springView.getHeadPoint().setX(view.getX() + view.getWidth() / 2);
    		springView.getHeadPoint().setY(view.getY() + view.getHeight() / 2);
    		springView.getFootPoint().setX(view.getX() + view.getWidth() / 2);
    		springView.getFootPoint().setY(view.getY() + view.getHeight() / 2);
    		springView.animCreate();
    	}
    	
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        
        if(viewPager!=null){
        	createPoints();
        	setSelectedTextColor(viewPager.getCurrentItem());
        }
    }


    private void setUpListener(){
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setSelectedTextColor(position);
                if (delegateListener != null) {
                    delegateListener.onPageSelected(position);
                }

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < tabs.size() - 1) {
                    // radius
                    float radiusOffsetHead = 0.5f;
                    if (positionOffset < radiusOffsetHead) {
                        springView.getHeadPoint().setRadius(radiusMin);
                    } else {
                        springView.getHeadPoint().setRadius(((positionOffset - radiusOffsetHead) / (1 - radiusOffsetHead) * radiusOffset + radiusMin));
                    }
                    float radiusOffsetFoot = 0.5f;
                    if (positionOffset < radiusOffsetFoot) {
                        springView.getFootPoint().setRadius((1 - positionOffset / radiusOffsetFoot) * radiusOffset + radiusMin);
                    } else {
                        springView.getFootPoint().setRadius(radiusMin);
                    }

                    // x
                    float headX = 1f;
                    if (positionOffset < headMoveOffset) {
                        float positionOffsetTemp = positionOffset / headMoveOffset;
                        headX = (float) ((Math.atan(positionOffsetTemp * acceleration * 2 - acceleration) + (Math.atan(acceleration))) / (2 * (Math.atan(acceleration))));
                    }
                    springView.getHeadPoint().setX(getTabX(position) - headX * getPositionDistance(position));
                    float footX = 0f;
                    if (positionOffset > footMoveOffset) {
                        float positionOffsetTemp = (positionOffset - footMoveOffset) / (1 - footMoveOffset);
                        footX = (float) ((Math.atan(positionOffsetTemp * acceleration * 2 - acceleration) + (Math.atan(acceleration))) / (2 * (Math.atan(acceleration))));
                    }
                    springView.getFootPoint().setX(getTabX(position) - footX * getPositionDistance(position));

                    // reset radius
                    if (positionOffset == 0) {
                        springView.getHeadPoint().setRadius(radiusMax);
                        springView.getFootPoint().setRadius(radiusMax);
                    }
                } else {
                    springView.getHeadPoint().setX(getTabX(position));
                    springView.getFootPoint().setX(getTabX(position));
                    springView.getHeadPoint().setRadius(radiusMax);
                    springView.getFootPoint().setRadius(radiusMax);
                }

                // set indicator colors
                // https://github.com/TaurusXi/GuideBackgroundColorAnimation
                if (indicatorColorsId != 0) {
                    float length = (position + positionOffset) / viewPager.getAdapter().getCount();
                    int progress = (int) (length * INDICATOR_ANIM_DURATION);
                    seek(progress);
                }

                springView.postInvalidate();

                if (delegateListener != null) {
                    delegateListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (delegateListener != null) {
                    delegateListener.onPageScrollStateChanged(state);
                }
            }
        });
        
    }


    private float getPositionDistance(int position) {
        float tarX = tabs.get(position + 1).getX();
        float oriX = tabs.get(position).getX();
        return oriX - tarX;
    }

    private float getTabX(int position) {
        return tabs.get(position).getX() + tabs.get(position).getWidth() / 2;
    }

    private void setSelectedTextColor(int position){
       try {
    	   for (TextView tab : tabs) {
    		   tab.setTextColor(textColorId);
    	   }
    	   tabs.get(position).setTextColor(getResources().getColor(selectedTextColorId));
    	   
       } catch (Exception e) {
		// TODO: handle exception
       }
    }

    private void createIndicatorColorAnim(){
        indicatorColorAnim = ObjectAnimator.ofInt(springView, "indicatorColor", indicatorColorArray);
        indicatorColorAnim.setEvaluator(new ArgbEvaluator());
        indicatorColorAnim.setDuration(INDICATOR_ANIM_DURATION);
    }

    private void seek(long seekTime) {
        if (indicatorColorAnim == null) {
            createIndicatorColorAnim();
        }
        indicatorColorAnim.setCurrentPlayTime(seekTime);
    }

    public List<TextView> getTabs(){
        return tabs;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener){
        this.delegateListener = listener;
    }

    public void setOnTabClickListener(TabClickListener listener){
        this.tabClickListener = listener;
    }

    private float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
