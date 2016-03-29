package com.htt.strongboxapp.activitys;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.app.BaseActivity;

public class MainActivity extends BaseActivity {
    private DrawerLayout drawerLayout;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initViews() {
        setTitleBarLeftIcon(R.mipmap.icon_main_left_menu);
        drawerLayout= (DrawerLayout) this.findViewById(R.id.layout_drawer);
        drawerLayout.setStatusBarBackgroundColor(Color.parseColor("#ff3344"));
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
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
}
