package com.htt.strongboxapp.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.activitys.PasswordSettingRegisterActivity;
import com.htt.strongboxapp.app.BaseFragment;
import com.htt.strongboxapp.networks.HttpClientUtil;
import com.htt.strongboxapp.networks.HttpUrls;
import com.htt.strongboxapp.utils.SoftInputUtil;
import com.htt.strongboxapp.utils.ToastMsgUtil;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by HTT on 2016/3/21.
 */
public class FirstStepRegisterFragment extends BaseFragment{
    private EditText etUserAccount;
    private ProgressDialog progressDialog=null;

    @Override
    protected View inflaterContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_register_idcard_check,container,false);
    }

    @Override
    protected void initViews(View view) {
        view.findViewById(R.id.bt_first_step).setOnClickListener(this);
        etUserAccount=(EditText)view.findViewById(R.id.et_user_account);
    }

    private void firstStepRegister(){
        String cardId=etUserAccount.getText().toString();
        if(TextUtils.isEmpty(cardId)){
            ToastMsgUtil.toastMsg(this.getActivity(),"请输入身份证号码");
            return;
        }

//        if(!CommonUtils.isCardId(cardId)){
//            ToastMsgUtil.toastMsg(this.getActivity(),"请输入合法的身份证号码");
//            return;
//        }
        SoftInputUtil.hideSoftInputFromWindow(this.getActivity(),etUserAccount);
        checkCardId(cardId);
    }

    protected void checkCardId(String cardId){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(this.getActivity());
        }
        Map<String,String> params=new HashMap<String,String>(1);
        params.put("cardId",cardId);
        HttpClientUtil.getHttpClientUtil().sendPostRequest(Tag, HttpUrls.CHECK_USER_CARDID_URL, params, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {
                progressDialog.setMessage("验证中...");
               progressDialog.show();
            }

            @Override
            public void onError(Call call, Exception error) {
                if(error!=null){
                    KLog.e("error:"+error.getMessage());
                }
                ToastMsgUtil.toastMsg(getActivity(),"验证失败");
            }

            @Override
            public void onSuccess(Call call, String response) {
                ToastMsgUtil.toastMsg(getActivity(),"验证成功");
                KLog.json(Tag, response);
                PasswordSettingRegisterActivity activity= (PasswordSettingRegisterActivity) getActivity();
            }

            @Override
            public void onFinish(Call call) {
                progressDialog.dismiss();

            }
        });
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.bt_first_step:
                firstStepRegister();
                break;
        }
    }
}
