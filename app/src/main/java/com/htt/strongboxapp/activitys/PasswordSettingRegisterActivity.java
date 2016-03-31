package com.htt.strongboxapp.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.htt.strongboxapp.R;
import com.htt.strongboxapp.app.BaseActivity;
import com.htt.strongboxapp.networks.HttpClientUtil;
import com.htt.strongboxapp.networks.HttpUrls;
import com.htt.strongboxapp.utils.SoftInputUtil;
import com.htt.strongboxapp.utils.ToastMsgUtil;
import com.htt.strongboxapp.utils.UserInfoUtils;
import com.htt.strongboxapp.views.dialogs.PasswordSafeQuestionsDialog;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/3/21.
 */
public class PasswordSettingRegisterActivity extends BaseActivity implements PasswordSafeQuestionsDialog.OnSelecteQuestionListener{
    private EditText etUserPassword;
    private EditText etUserConfirmPassword;
    private TextView tvQuestion01;
    private TextView tvQuestion02;
    private TextView tvQuestion03;
    private EditText etSafeQuestion01;
    private EditText etSafeQuestion02;
    private EditText etSafeQuestion03;

    private ProgressDialog progressDialog;

    private PasswordSafeQuestionsDialog passwordSafeQuestionsDialog;
    private int currentSelectedId;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_register_password_setting);
    }

    @Override
    protected void initViews() {
        setTitle("设置密码");
        etUserPassword=(EditText)this.findViewById(R.id.et_user_password);
        etUserConfirmPassword=(EditText)this.findViewById(R.id.et_user_confirm_password);

        tvQuestion01=(TextView)this.findViewById(R.id.tv_question_01);
        tvQuestion02=(TextView)this.findViewById(R.id.tv_question_02);
        tvQuestion03=(TextView)this.findViewById(R.id.tv_question_03);

        etSafeQuestion01=(EditText)this.findViewById(R.id.et__password_safe_question_01);
        etSafeQuestion02=(EditText)this.findViewById(R.id.et__password_safe_question_02);
        etSafeQuestion03=(EditText)this.findViewById(R.id.et__password_safe_question_03);
    }

    public void selectQuestion(View view){
        SoftInputUtil.hideSoftInputFromWindow(this, etUserConfirmPassword);
        currentSelectedId=view.getId();
        if(passwordSafeQuestionsDialog==null){
            passwordSafeQuestionsDialog=new PasswordSafeQuestionsDialog(this);
            passwordSafeQuestionsDialog.setOnSelecteQuestionListener(this);
        }
        if(view instanceof TextView) {
            String currentSelectedQuestion = ((TextView) view).getText().toString();
            passwordSafeQuestionsDialog.setCurrentSelectedQuestion(currentSelectedQuestion);
        }
        passwordSafeQuestionsDialog.show();
    }

    @Override
    protected void onClickView(View v) {

    }

    @Override
    protected void onClickTitleBarLeft() {
        super.onClickTitleBarLeft();
    }

    @Override
    public void onSelectedQuestion(String question) {
        if(!TextUtils.isEmpty(question)){
           TextView tv= (TextView) this.findViewById(currentSelectedId);
            tv.setText(question);
            setCurrentQuestionView(currentSelectedId);
        }
    }

    private void setCurrentQuestionView(int id){
        if(id==R.id.tv_question_01){
            etSafeQuestion01.setVisibility(View.VISIBLE);
            this.findViewById(R.id.line1).setVisibility(View.VISIBLE);
            etSafeQuestion01.requestFocus();
        }else if(id==R.id.tv_question_02){
            etSafeQuestion02.setVisibility(View.VISIBLE);
            etSafeQuestion02.requestFocus();
            this.findViewById(R.id.line2).setVisibility(View.VISIBLE);
        }else if(id==R.id.tv_question_03){
            etSafeQuestion03.setVisibility(View.VISIBLE);
            etSafeQuestion03.requestFocus();
            this.findViewById(R.id.line3).setVisibility(View.VISIBLE);
        }
    }

    private boolean checkPassword(String password){
        boolean isSuccess=true;
        if(TextUtils.isEmpty(password)){
            ToastMsgUtil.toastMsg(this,"请设置密码");
            isSuccess=false;
        }else if(password.length()<6||password.length()>16){
            ToastMsgUtil.toastMsg(this,"密码长度必须为6~16位");
            isSuccess=false;
        }
        if(!isSuccess){
            etUserPassword.requestFocus();
        }
        return isSuccess;
    }

    public void register(View view){
        SoftInputUtil.hideSoftInputFromWindow(this,etUserPassword);
        String password=etUserPassword.getText().toString();
        String confirmPassword=etUserConfirmPassword.getText().toString();
        String safeQuestion01=tvQuestion01.getText().toString();
        String safeQuestionAnswer01=etSafeQuestion01.getText().toString();
        String safeQuestion02=tvQuestion02.getText().toString();
        String safeQuestionAnswer02=etSafeQuestion02.getText().toString();
        String safeQuestion03=tvQuestion03.getText().toString();
        String safeQuestionAnswer03=etSafeQuestion03.getText().toString();

        if(!checkPassword(password)){
            return;
        }

        if(TextUtils.isEmpty(confirmPassword)){
            ToastMsgUtil.toastMsg(this,"请输入确认密码");
            etUserConfirmPassword.requestFocus();
            return;
        }

        if(!password.equals(confirmPassword)){
            ToastMsgUtil.toastMsg(this,"确认密码与密码输入不一致");
            etUserConfirmPassword.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(safeQuestion01)){
            ToastMsgUtil.toastMsg(this, "请设置密保问题一");
            return;
        }

        if(TextUtils.isEmpty(safeQuestionAnswer01)){
            ToastMsgUtil.toastMsg(this,"请输入密保问题一的答案");
            etSafeQuestion01.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(safeQuestion02)){
            ToastMsgUtil.toastMsg(this,"请设置密保问题二");
            return;
        }

        if(TextUtils.isEmpty(safeQuestionAnswer02)){
            ToastMsgUtil.toastMsg(this,"请输入密保问题二的答案");
            etSafeQuestion02.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(safeQuestion03)){
            ToastMsgUtil.toastMsg(this,"请设置密保问题三");
            return;
        }

        if(TextUtils.isEmpty(safeQuestionAnswer03)){
            ToastMsgUtil.toastMsg(this,"请输入密保问题三的答案");
            etSafeQuestion01.requestFocus();
            return;
        }

        Intent intent=this.getIntent();
        String IDCard=intent.getStringExtra("userIDCard");
        String userName=intent.getStringExtra("userName");
        String userPhone=intent.getStringExtra("userPhone");

        Map<String,String> params=new HashMap<String,String>();
        params.put("cardId",IDCard);
        params.put("userName",userName);
        params.put("mobile",userPhone);
        params.put("password",password);
        params.put("question1",safeQuestion01);
        params.put("answer1",safeQuestionAnswer01);
        params.put("question2",safeQuestion02);
        params.put("answer2",safeQuestionAnswer02);
        params.put("question03",safeQuestion03);
        params.put("answer3",safeQuestionAnswer03);

        if(progressDialog==null){
            progressDialog=new ProgressDialog(PasswordSettingRegisterActivity.this);
        }

        HttpClientUtil.getHttpClientUtil().sendPostRequest(Tag, HttpUrls.USER_REGISTER_URL, params, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {
                progressDialog.setMessage("提交中...");
                progressDialog.show();
            }

            @Override
            public void onError(Call call, Exception error) {
                ToastMsgUtil.toastMsg(PasswordSettingRegisterActivity.this,"请求失败");
            }

            @Override
            public void onSuccess(Call call, String response) {
                KLog.json(response);
                if(!TextUtils.isEmpty(response)){
                    JSONObject jsonObject=JSONObject.parseObject(response);
                    if(jsonObject!=null&&!jsonObject.isEmpty()){
                        int statusCode=jsonObject.getIntValue("statusCode");
                        String message=jsonObject.getString("message");
                        if(statusCode==200){
                            if(TextUtils.isEmpty(message)){
                                message="注册成功";
                            }
                            ToastMsgUtil.toastMsg(PasswordSettingRegisterActivity.this, message);
                            JSONObject dataObj=jsonObject.getJSONObject("dataMap");
                            if(dataObj!=null&&!dataObj.isEmpty()){
                                String cardId=dataObj.getString("cardId");
                                String userId=dataObj.getString("userId");
                                UserInfoUtils.setUserIDCard(PasswordSettingRegisterActivity.this,cardId);
                                UserInfoUtils.setUserId(PasswordSettingRegisterActivity.this,userId);
                            }
                            setResult(RESULT_OK);
                            finish();
                        }else{
                            if(TextUtils.isEmpty(message)){
                                message="注册失败";
                            }
                            ToastMsgUtil.toastMsg(PasswordSettingRegisterActivity.this, message);
                        }
                        return;
                    }
                }
                ToastMsgUtil.toastMsg(PasswordSettingRegisterActivity.this, "注册失败");
            }

            @Override
            public void onFinish(Call call) {
                progressDialog.dismiss();
            }
        });


    }


//    /**
//     * 设置当前注册界面
//     * @param id
//     */
//    public void setCurrentRegisterFragment(int id){
//        FragmentTransaction transaction=null;
//        if(this.currentId!=id){
//            transaction=this.getSupportFragmentManager().beginTransaction();
//            //transaction.setCustomAnimations(R.anim.fragment_slide_in_from_left, R.anim.fragment_slide_out_from_right);
//            transaction.setCustomAnimations(R.anim.enter_forward,R.anim.exit_backward,R.anim.exit_forward,R.anim.enter_backward);
//        }else{
//            return;
//        }
//        switch(id){
//            case 0:
//                if(firstStepRegisterFragment==null){
//                    firstStepRegisterFragment=new FirstStepRegisterFragment();
//                    transaction.add(R.id.layout_container,firstStepRegisterFragment);
//                }else{
//                    transaction.show(firstStepRegisterFragment);
//                }
//                break;
//            case 1:
//                if(secondStepRegisterFragment==null){
//                    secondStepRegisterFragment=new SecondStepRegisterFragment();
//                    transaction.add(R.id.layout_container,secondStepRegisterFragment);
//                }else{
//                    transaction.show(secondStepRegisterFragment);
//                }
//                secondStepRegisterFragment.requestFocus();
//                break;
//            case 2:
//                if(thirdStepRegisterFragment==null){
//                    thirdStepRegisterFragment=new ThirdStepRegisterFragment();
//                    transaction.add(R.id.layout_container,thirdStepRegisterFragment);
//                }else{
//                    transaction.show(thirdStepRegisterFragment);
//                }
//                thirdStepRegisterFragment.requestFocus();
//                break;
//        }
//        hideFragments(currentId,transaction);
//        currentId=id;
//        transaction.commitAllowingStateLoss();
//    }
//
//    private void hideFragments(int id,FragmentTransaction transaction){
//        if(id==0){
//            if(firstStepRegisterFragment!=null){
//                transaction.hide(firstStepRegisterFragment);
//            }
//        }else if(id==1){
//            if(secondStepRegisterFragment!=null){
//                transaction.hide(secondStepRegisterFragment);
//            }
//        }else if(id==2){
//            if(thirdStepRegisterFragment!=null){
//                transaction.hide(thirdStepRegisterFragment);
//            }
//        }
//    }
//
//    public void backRegisterFragment(){
//        if(currentId==0){
//            this.finish();
//        }else{
//            int id=currentId-1;
//            setCurrentRegisterFragment(id);
//        }
//    }
}
