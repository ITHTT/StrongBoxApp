package com.htt.strongboxapp.activitys;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.app.BaseActivity;
import com.htt.strongboxapp.utils.ActivitySkipUtils;
import com.socks.library.KLog;

/**
 * Created by Administrator on 2016/3/28.
 */
public class PhoneCheckRegisterActivity extends BaseActivity{
    private EditText etUserName;
    private EditText etUserPhone;
    private EditText etUserVCode;
    private String idCard;

    @Override
    protected void setContentView() {
        setTitle("手机验证");
        setContentView(R.layout.activity_register_phone_check);
    }

    @Override
    protected void initViews() {
        etUserName=(EditText)this.findViewById(R.id.et_user_name);
        etUserPhone=(EditText)this.findViewById(R.id.et_user_phone);
        etUserVCode=(EditText)this.findViewById(R.id.et_user_vcode);
        getIdCard();
    }

    protected void getIdCard(){
        Intent intent=getIntent();
        if(intent!=null){
            idCard=intent.getStringExtra(Tag);
            KLog.e("idCard:"+idCard);
        }
    }

    public void phoneCheck(View view){
        ActivitySkipUtils.skipActivity(this,RegisterActivity.class);
    }

    @Override
    protected void onClickView(View v) {

    }
}
