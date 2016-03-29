package com.htt.strongboxapp.activitys;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.app.BaseActivity;
import com.htt.strongboxapp.utils.SoftInputUtil;
import com.htt.strongboxapp.views.dialogs.PasswordSafeQuestionsDialog;

/**
 * Created by Administrator on 2016/3/21.
 */
public class PasswordSettingRegisterActivity extends BaseActivity implements PasswordSafeQuestionsDialog.OnSelecteQuestionListener{
    private EditText etUserPassword;
    private EditText etUserConfirmPassword;
    private EditText etSafeQuestion01;
    private EditText etSafeQuestion02;
    private EditText etSafeQuestion03;

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

        etSafeQuestion01=(EditText)this.findViewById(R.id.et__password_safe_question_01);
        etSafeQuestion02=(EditText)this.findViewById(R.id.et__password_safe_question_02);
        etSafeQuestion03=(EditText)this.findViewById(R.id.et__password_safe_question_03);
    }

    public void selectQuestion(View view){
        SoftInputUtil.hideSoftInputFromWindow(this,etUserConfirmPassword);
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
