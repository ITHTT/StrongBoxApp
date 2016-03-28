package com.htt.strongboxapp.app;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.networks.HttpClientUtil;
import com.htt.strongboxapp.views.TitleBar;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Administrator on 2016/3/21.
 */
abstract public class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    protected final String Tag=this.getClass().getSimpleName();
    /**沉浸式状态栏管理工具*/
    private SystemBarTintManager systemBarTintManager;
    protected TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStyle();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setStatusBarColor(R.color.colorPrimary);
        setContentView();
        titleBar= (TitleBar) this.findViewById(R.id.layout_title_bar);
        if(titleBar!=null){
            titleBar.setTitleBarLeftOnClickListener(this);
        }
        initViews();
    }

    protected void setTranslucentStyle(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            systemBarTintManager=new SystemBarTintManager(this);
        }
    }

    /**
     * 设置状态栏颜色
     * 也就是所谓沉浸式状态栏
     */
    public void setStatusBarColor(int color) {
        /**
         * Android4.4以上可用
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            systemBarTintManager.setStatusBarAlpha(1f);
            systemBarTintManager.setStatusBarTintResource(color);
            systemBarTintManager.setStatusBarTintEnabled(true);
        }
    }

    public void clearStatusBarColor(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            systemBarTintManager.setStatusBarAlpha(0f);
        }
    }

    public void setTitle(String title){
        if(!TextUtils.isEmpty(title)){
            if(titleBar!=null){
                titleBar.setTitle(title);
            }
        }
    }

    public void setTitleBarLeftVisibility(int visibility){
        if(titleBar!=null){
            titleBar.setTitleBarLeftVisibility(visibility);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.layout_title_bar_left:
                onClickTitleBarLeft();
                break;
        }
        onClickView(v);
    }

    protected void onClickTitleBarLeft(){
        this.finish();
    }

    abstract protected void setContentView();

    abstract protected void initViews();

    abstract protected void onClickView(View v);

    @Override
    protected void onDestroy() {
        HttpClientUtil.getHttpClientUtil().cancelTag(Tag);
        super.onDestroy();

    }
}
