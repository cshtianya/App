package com.tnw.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.app.views.custom_views.UITabView;
import com.tnw.R;
import com.tnw.fragments.CartFragment;
import com.tnw.fragments.HomeManageFragment;
import com.tnw.fragments.UserCenterFragment;

import java.util.Timer;
import java.util.TimerTask;

public class ManageActivity extends BaseActivity implements UITabView.OnChangedListener {

    private FragmentManager fManager;

    private HomeManageFragment homeFragment;
//    private CategoryFragment categoryFragment;
    private CartFragment cartFragment;
    private UserCenterFragment centerFragment;

    private UITabView toolBarView;
    private int selecId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //海信状态栏临时解决方案
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }*/

        setContentView(R.layout.activity_main_ui);


/*        // 创建状态栏的管理实例
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // 激活状态栏设置
        tintManager.setStatusBarTintEnabled(true);
        // 激活导航栏设置
        tintManager.setNavigationBarTintEnabled(true);*/

        fManager = getSupportFragmentManager();

        toolBarView = (UITabView) findViewById(R.id.tabView);
        toolBarView.initView();
        toolBarView.setOnChangedListener(this);

        selecId = R.id.radoHome;
        if(savedInstanceState!=null){
            toolBarView.onClick(findViewById(savedInstanceState.getInt("cacheId")));
        }else{
            toolBarView.onClick(findViewById(selecId));
        }
    }

    @Override
    public void onChanged(View v, boolean isRefresh) {
        FragmentTransaction transaction = fManager.beginTransaction();
        hideFragment(transaction);
        selecId = v.getId();
        switch (v.getId()) {
            case R.id.radoHome:
                if(homeFragment==null){
                    homeFragment = new HomeManageFragment();
                    transaction.add(R.id.managerFrame, homeFragment);
                }else{
                    transaction.show(homeFragment);
                }
                break;
//            case R.id.radoCategory:
//                if(categoryFragment==null){
//                    categoryFragment = new CategoryFragment();
//                    transaction.add(R.id.managerFrame, categoryFragment);
//                }else{
//                    transaction.show(categoryFragment);
//                }
//                break;
            case R.id.radoCart:
                if(cartFragment==null){
                    cartFragment = new CartFragment();
                    transaction.add(R.id.managerFrame, cartFragment);
                }else{
                    transaction.show(cartFragment);
                }
                break;
            case R.id.radoAccount:
                if(centerFragment==null){
                    centerFragment = new UserCenterFragment();
                    transaction.add(R.id.managerFrame, centerFragment);
                }else{
                    transaction.show(centerFragment);
                }
                break;
            default:
                break;
        }

        transaction.commit();

    }

    private void hideFragment(FragmentTransaction tt){
        if(homeFragment!=null)tt.hide(homeFragment);
        if(cartFragment!=null) tt.hide(cartFragment);
        if(centerFragment!=null)tt.hide(centerFragment);
//        if(categoryFragment!=null)tt.hide(categoryFragment);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("cacheId", selecId);
        super.onSaveInstanceState(savedInstanceState);
    }


    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK){
           if(homeFragment.isMenuShow()){
               homeFragment.closeMenu();
           }else{
               exitBy2Click();
           }
        }
        return false;
    }

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            showTost("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

}
