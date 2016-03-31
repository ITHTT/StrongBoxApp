package com.htt.strongboxapp.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.app.BaseActivity;
import com.htt.strongboxapp.networks.HttpClientUtil;
import com.htt.strongboxapp.networks.HttpUrls;
import com.htt.strongboxapp.utils.ActivitySkipUtils;
import com.htt.strongboxapp.utils.SoftInputUtil;
import com.htt.strongboxapp.utils.ToastMsgUtil;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/3/28.
 */
public class IDCardCheckRegisterActivity extends BaseActivity{
    private EditText etUserAccount;
    private ProgressDialog progressDialog=null;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_register_idcard_check);
    }

    @Override
    protected void initViews() {
        setTitle("身份证验证");
        etUserAccount=(EditText)this.findViewById(R.id.et_user_account);
    }

    public void checkIdCard(View view){
        String cardId=etUserAccount.getText().toString();
        if(TextUtils.isEmpty(cardId)){
            ToastMsgUtil.toastMsg(this,"请输入身份证号码");
            return;
        }

//        if(!CommonUtils.isCardId(cardId)){
//            ToastMsgUtil.toastMsg(this.getActivity(),"请输入合法的身份证号码");
//            return;
//        }
        SoftInputUtil.hideSoftInputFromWindow(this, etUserAccount);
        checkCardId(cardId);
    }

    protected void checkCardId(final String cardId){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(this);
        }
        Map<String,String> params=new HashMap<String,String>(1);
        params.put("cardId", cardId);
        HttpClientUtil.getHttpClientUtil().sendPostRequest(Tag, HttpUrls.CHECK_USER_CARDID_URL, params, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {
                progressDialog.setMessage("验证中...");
                progressDialog.show();
            }

            @Override
            public void onError(Call call, Exception error) {
                if (error != null) {
                    KLog.e("error:" + error.getMessage());
                }
                ToastMsgUtil.toastMsg(IDCardCheckRegisterActivity.this, "请求失败");
            }

            @Override
            public void onSuccess(Call call, String response) {
                ToastMsgUtil.toastMsg(IDCardCheckRegisterActivity.this, "验证成功");
                KLog.json(Tag, response);
                Intent intent = new Intent(IDCardCheckRegisterActivity.this, PhoneCheckRegisterActivity.class);
                intent.putExtra("userIDCard", cardId);
                IDCardCheckRegisterActivity.this.startActivityForResult(intent, 0x0001);
            }

            @Override
            public void onFinish(Call call) {
                progressDialog.dismiss();

            }
        });
    }

    private void nextStepRegister(String idCard){


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
