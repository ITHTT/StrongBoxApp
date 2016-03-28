package com.htt.strongboxapp.activitys;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.app.BaseActivity;
import com.htt.strongboxapp.views.dialogs.PasswordSafeQuestionsDialog;

/**
 * Created by Administrator on 2016/3/21.
 */
public class RegisterActivity extends BaseActivity{
    private EditText etUserPassword;
    private EditText etUserConfirmPassword;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initViews() {
        setTitle("用户注册");
        etUserPassword=(EditText)this.findViewById(R.id.et_user_password);
        etUserConfirmPassword=(EditText)this.findViewById(R.id.et_user_confirm_password);
    }

    public void selectQuestion(View view){
        PasswordSafeQuestionsDialog dialog=new PasswordSafeQuestionsDialog(this);
        dialog.show();
    }

    @Override
    protected void onClickView(View v) {

    }

    @Override
    protected void onClickTitleBarLeft() {

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
