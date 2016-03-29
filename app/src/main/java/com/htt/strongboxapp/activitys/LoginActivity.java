package com.htt.strongboxapp.activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.app.BaseActivity;
import com.htt.strongboxapp.utils.ActivitySkipUtils;
import com.htt.strongboxapp.utils.CommonUtils;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends BaseActivity {
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initViews() {
        CommonUtils.setStatusBarDarkMode(true,this);
        setTitle("用户登录");
        setTitleBarLeftVisibility(View.GONE);
    }

    @Override
    protected void onClickView(View v) {

    }

    public void login(View view){
        ActivitySkipUtils.skipActivity(this,MainActivity.class);
    }

    public void register(View view){
        Intent intent=new Intent(this,IDCardCheckRegisterActivity.class);
        startActivity(intent);
    }
}

