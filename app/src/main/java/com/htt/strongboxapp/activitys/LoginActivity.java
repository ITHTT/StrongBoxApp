package com.htt.strongboxapp.activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.htt.strongboxapp.R;
import com.htt.strongboxapp.app.BaseActivity;
import com.htt.strongboxapp.networks.HttpClientUtil;
import com.htt.strongboxapp.networks.HttpUrls;
import com.htt.strongboxapp.utils.ActivitySkipUtils;
import com.htt.strongboxapp.utils.CommonUtils;
import com.htt.strongboxapp.utils.SoftInputUtil;
import com.htt.strongboxapp.utils.ToastMsgUtil;
import com.htt.strongboxapp.utils.UserInfoUtils;
import com.socks.library.KLog;

import org.w3c.dom.Text;

import okhttp3.Call;
import okhttp3.Request;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends BaseActivity {
    private final int REQUEST_CODE_REGISTER=0x0001;
    private EditText etUserAccount;
    private EditText etUserPassword;

    private ProgressDialog progressDialog;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initViews() {
        CommonUtils.setStatusBarDarkMode(true, this);
        setTitle("用户登录");
        setTitleBarLeftVisibility(View.GONE);

        etUserAccount=(EditText)this.findViewById(R.id.et_user_account);
        etUserPassword=(EditText)this.findViewById(R.id.et_user_password);

        //430124199508251234
        if(UserInfoUtils.isLogined(this)){
            ActivitySkipUtils.skipActivity(this, MainActivity.class);
            finish();
        }
    }

    @Override
    protected void onClickView(View v) {

    }

    public void login(View view){
        SoftInputUtil.hideSoftInputFromWindow(this,etUserPassword);
        String userAccount=etUserAccount.getText().toString();
        String userPassword=etUserPassword.getText().toString();

        if(TextUtils.isEmpty(userAccount)){
            ToastMsgUtil.toastMsg(this,"请输入身份证号码");
            etUserAccount.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(userPassword)){
            ToastMsgUtil.toastMsg(this,"请输入密码");
            etUserPassword.requestFocus();
            return;
        }

        Map<String,String> params=new HashMap<>(2);
        params.put("cardId",userAccount);
        params.put("password", userPassword);
        if(progressDialog==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        HttpClientUtil.getHttpClientUtil().sendPostRequest(Tag, HttpUrls.USER_LOGIN_URL, params, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {
                progressDialog.setMessage("登录中...");
                progressDialog.show();
            }

            @Override
            public void onError(Call call, Exception error) {
                ToastMsgUtil.toastMsg(LoginActivity.this, "请求失败");

            }

            @Override
            public void onSuccess(Call call, String response) {
                //ToastMsgUtil.toastMsg(LoginActivity.this, "登录成功");
                KLog.json(response);
                if(!TextUtils.isEmpty(response)){
                    JSONObject jsonObject=JSONObject.parseObject(response);
                    if(jsonObject!=null&&!jsonObject.isEmpty()){
                        int statusCode=jsonObject.getIntValue("statusCode");
                        String message=jsonObject.getString("message");
                        if(statusCode==200){
                            if(TextUtils.isEmpty(message)){
                                message="登录成功";
                            }
                            ToastMsgUtil.toastMsg(LoginActivity.this, message);
                            JSONObject dataObj=jsonObject.getJSONObject("dataMap");
                            if(dataObj!=null&&!dataObj.isEmpty()){
                                String cardId=dataObj.getString("cardId");
                                String userId=dataObj.getString("userId");
                                String userName=dataObj.getString("userName");
                                String userPhone=dataObj.getString("mobile");
                                UserInfoUtils.setUserIDCard(LoginActivity.this,cardId);
                                UserInfoUtils.setUserId(LoginActivity.this, userId);
                                UserInfoUtils.setUserName(LoginActivity.this, userName);
                                UserInfoUtils.setUserPhone(LoginActivity.this,userPhone);
                            }
                            ActivitySkipUtils.skipActivity(LoginActivity.this, MainActivity.class);
                            finish();
                        }else{
                            if(TextUtils.isEmpty(message)){
                                message="登录失败";
                            }
                            ToastMsgUtil.toastMsg(LoginActivity.this, message);
                        }
                        return;
                    }
                }
                ToastMsgUtil.toastMsg(LoginActivity.this,"登录失败");
            }

            @Override
            public void onFinish(Call call) {
                progressDialog.dismiss();
            }
        });

    }


    public void register(View view){
        Intent intent=new Intent(this,IDCardCheckRegisterActivity.class);
        startActivityForResult(intent, REQUEST_CODE_REGISTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(resultCode==REQUEST_CODE_REGISTER){
                String cardId= UserInfoUtils.getUserId(this);
                if(!TextUtils.isEmpty(cardId)){
                    etUserAccount.setText(cardId);
                    etUserAccount.setSelection(cardId.length());
                    etUserPassword.requestFocus();
                }
            }
        }
    }
}

