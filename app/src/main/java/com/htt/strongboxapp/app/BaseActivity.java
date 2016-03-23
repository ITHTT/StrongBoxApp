package com.htt.strongboxapp.app;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.networks.HttpClientUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Administrator on 2016/3/21.
 */
abstract public class BaseActivity extends AppCompatActivity{
    protected final String Tag=this.getClass().getSimpleName();
    /**沉浸式状态栏管理工具*/
    private SystemBarTintManager systemBarTintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStyle();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setStatusBarColor(R.color.colorPrimary);
        setContentView();
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

    abstract protected void setContentView();

    abstract protected void initViews();

    @Override
    protected void onDestroy() {
        HttpClientUtil.getHttpClientUtil().cancelTag(Tag);
        super.onDestroy();

    }
}
