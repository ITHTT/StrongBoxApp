package com.htt.strongboxapp.activitys;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.app.BaseActivity;
import com.htt.strongboxapp.utils.CommonUtils;
import com.htt.strongboxapp.views.widgets.gridpasswordview.GridPasswordView;
import com.htt.strongboxapp.views.widgets.keyboardview.KeyboardButtonClickedListener;
import com.htt.strongboxapp.views.widgets.keyboardview.KeyboardButtonEnum;
import com.htt.strongboxapp.views.widgets.keyboardview.KeyboardView;
import com.socks.library.KLog;

public class MainActivity extends BaseActivity implements KeyboardButtonClickedListener {
    private DrawerLayout drawerLayout;
    private GridPasswordView gridPasswordView;
    private KeyboardView keyboardView;
    private StringBuilder passwordBuilder=null;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initViews() {
        passwordBuilder=new StringBuilder();
        clearStatusBarColor();
        setTitleBarLeftIcon(R.mipmap.icon_main_left_menu);
        drawerLayout= (DrawerLayout) this.findViewById(R.id.layout_drawer);
        gridPasswordView=(GridPasswordView)this.findViewById(R.id.passwordView);
        keyboardView=(KeyboardView)this.findViewById(R.id.keyboardView);
        keyboardView.setKeyboardButtonClickedListener(this);
        drawerLayout.setStatusBarBackgroundColor(Color.parseColor("#ff3344"));
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
            titleBar.setPaddingTop(CommonUtils.getStatusBarHeight(this));
        }
    }

    @Override
    protected void onClickTitleBarLeft() {
       if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
           drawerLayout.closeDrawer(Gravity.LEFT);
       }else{
           drawerLayout.openDrawer(Gravity.LEFT);
       }

    }

    @Override
    protected void onClickView(View v) {

    }

    @Override
    public void onKeyboardClick(KeyboardButtonEnum keyboardButtonEnum) {
        int value=keyboardButtonEnum.getButtonValue();
        KLog.e("BoardKeyValue:"+value);
        int length=passwordBuilder.length();
        int passwordLength=gridPasswordView.getPasswordLength();

        /*点击删除键*/
        if(value==-1){
            if(length-1>=0){
                passwordBuilder.deleteCharAt(length-1);
                gridPasswordView.setPassword(passwordBuilder.toString());
            }
        }else{
            if(length<passwordLength) {
                passwordBuilder.append(value);
                gridPasswordView.setPassword(passwordBuilder.toString());
            }
        }
    }

    @Override
    public void onRippleAnimationEnd() {

    }
}
