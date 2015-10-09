package com.app.views.custom_views;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tnw.R;
import com.tnw.entities.BannerInfo;
import com.app.views.springindicator.SpringIndicator;
import com.app.views.viewpager.ScrollerViewPager;

import java.util.List;

/**
 * Created by CSH on 2015/7/3 0003.
 */
public class UIBannerView extends LinearLayout{

   ScrollerViewPager indicatorPager;
   SpringIndicator indicator;

    private BannerAdapter adapter;
    private RelativeLayout.LayoutParams lp;

    public UIBannerView(Context context) {
        this(context, null, 0);
        // TODO Auto-generated constructor stub
    }

    public UIBannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub

    }

    public UIBannerView(Context context, AttributeSet attrs, int style) {
        super(context, attrs,style);
        // TODO Auto-generated constructor stub
        int width = getResources().getDisplayMetrics().widthPixels;
        lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, width/2);
        View baseView = LayoutInflater.from(context).inflate(R.layout.banner_layout,null);
        this.addView(baseView);

        indicatorPager = (ScrollerViewPager)findViewById(R.id.indicatorPager);
        indicator = (SpringIndicator)findViewById(R.id.indicator);
        indicatorPager.setLayoutParams(lp);

    }

    public void showBanner(List<BannerInfo.BannerItem> resutl){
        adapter = new BannerAdapter(resutl);
        indicatorPager.setAdapter(adapter);

        indicator.setViewPager(indicatorPager);
        indicatorPager.fixScrollSpeed();

        indicator.initSpringView();

    }

    public interface OnBannerItemClickListener{
        void onItemClick(BannerInfo.BannerItem info, int position);
    }

    private OnBannerItemClickListener onBannerItemClickListener;

    public void setOnBannerItemClickListener(OnBannerItemClickListener listener) {
        this.onBannerItemClickListener = listener;
    }

    public class BannerAdapter extends PagerAdapter {

        private final List<BannerInfo.BannerItem> mList;

        public BannerAdapter(List<BannerInfo.BannerItem> list){
            this.mList = list;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.banner_item, null, false);
            SimpleDraweeView ivImage= (SimpleDraweeView)view.findViewById(R.id.draweeView);

            final BannerInfo.BannerItem info = mList.get(position);
            String url =  info.getItemImage();
            ivImage.setImageURI(Uri.parse(url));
            container.addView(view);

            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(onBannerItemClickListener!=null)
                        onBannerItemClickListener.onItemClick(info,position);
                }
            });

            return view;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            container.removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

}


