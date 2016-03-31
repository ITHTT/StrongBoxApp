package com.htt.strongboxapp.activitys;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.app.BaseActivity;
import com.htt.strongboxapp.utils.ActivitySkipUtils;
import com.htt.strongboxapp.utils.SoftInputUtil;
import com.htt.strongboxapp.utils.ToastMsgUtil;
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
        setContentView(R.layout.activity_register_phone_check);
    }

    @Override
    protected void initViews() {
        setTitle("手机验证");
        etUserName=(EditText)this.findViewById(R.id.et_user_name);
        etUserPhone=(EditText)this.findViewById(R.id.et_user_phone);
        etUserVCode=(EditText)this.findViewById(R.id.et_user_vcode);
        getIdCard();
    }

    protected void getIdCard(){
        Intent intent=getIntent();
        if(intent!=null){
            idCard=intent.getStringExtra("userIDCard");
            KLog.e("idCard:"+idCard);
        }
    }

    public void phoneCheck(View view){
        SoftInputUtil.hideSoftInputFromWindow(this,etUserName);
        String userName=etUserName.getText().toString();
        String userPhone=etUserPhone.getText().toString();
        if(TextUtils.isEmpty(userName)){
            ToastMsgUtil.toastMsg(this,"请输入用户名");
            etUserName.requestFocus();
            return ;
        }
        if(TextUtils.isEmpty(userPhone)){
            ToastMsgUtil.toastMsg(this,"请输入手机号码");
            etUserPhone.requestFocus();
            return;
        }

        Intent intent=new Intent(this,PasswordSettingRegisterActivity.class);
        intent.putExtra("userName",userName);
        intent.putExtra("userPhone",userPhone);
        intent.putExtra("userIDCard",idCard);
        startActivityForResult(intent, 0x0002);
    }

    @Override
    protected void onClickView(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }
}
