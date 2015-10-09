package com.app.views;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.tnw.R;
import com.tnw.entities.BannerInfo;

import java.util.List;

/**
 * Created by Administrator on 2015/9/14 0014.
 */
public class TrumpetView extends LinearLayout{

    private Context mContext;

    private ViewFlipper viewFlipper;

    public TrumpetView(Context context) {
        this(context, null);
    }

    public TrumpetView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public TrumpetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init(){
        this.removeAllViews();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.trumpet_layout,null);
        this.addView(view);

        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(
                getContext(), R.anim.abc_slide_in_top));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
                getContext(), R.anim.abc_slide_out_bottom));

    }

    public void executeTask(){
        new RequstDataTask().execute();
    }

    private void bindNotices(){
        viewFlipper.removeAllViews();
        int i = 1;
        while(i<6){
            String text = "公告:恭喜你，中奖了 5000w------"+i;
            TextView textView = new TextView(getContext());
            textView.setText(text);
            textView.setTextAppearance(getContext(),R.style.textPrimary);
            textView.setOnClickListener(new NoticeTitleOnClickListener(i+text));
            LayoutParams lp = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            viewFlipper.addView(textView,lp);
            i++;
        }

        viewFlipper.startFlipping();
    }

    class NoticeTitleOnClickListener implements OnClickListener{
        private String titleid;

        public NoticeTitleOnClickListener(String whichText){
            this.titleid = whichText;
        }
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Toast.makeText(getContext(),titleid,Toast.LENGTH_SHORT).show();
        }

    }

    private class RequstDataTask extends AsyncTask<String, Void, List<BannerInfo.BannerItem>> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected List<BannerInfo.BannerItem> doInBackground(String... params) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        protected void onPostExecute(List<BannerInfo.BannerItem> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            bindNotices();
        }

    }

}
