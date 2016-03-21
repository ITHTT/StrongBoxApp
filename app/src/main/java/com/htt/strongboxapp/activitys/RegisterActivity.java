package com.htt.strongboxapp.activitys;

import android.support.v4.app.FragmentTransaction;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.app.BaseActivity;
import com.htt.strongboxapp.fragments.FirstStepRegisterFragment;
import com.htt.strongboxapp.fragments.SecondStepRegisterFragment;
import com.htt.strongboxapp.fragments.ThirdStepRegisterFragment;

/**
 * Created by Administrator on 2016/3/21.
 */
public class RegisterActivity extends BaseActivity{
    private FirstStepRegisterFragment firstStepRegisterFragment;
    private SecondStepRegisterFragment secondStepRegisterFragment;
    private ThirdStepRegisterFragment thirdStepRegisterFragment;

    private int currentId=-1;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initViews() {
        setCurrentRegisterFragment(0);
    }

    /**
     * 设置当前注册界面
     * @param id
     */
    public void setCurrentRegisterFragment(int id){
        FragmentTransaction transaction=null;
        if(this.currentId!=id){
            transaction=this.getSupportFragmentManager().beginTransaction();
            hideFragments(currentId,transaction);
        }else{
            return;
        }
        switch(id){
            case 0:
                if(firstStepRegisterFragment==null){
                    firstStepRegisterFragment=new FirstStepRegisterFragment();
                    transaction.add(R.id.layout_container,firstStepRegisterFragment);
                }else{
                    transaction.show(firstStepRegisterFragment);
                }
                break;
            case 1:
                if(secondStepRegisterFragment==null){
                    secondStepRegisterFragment=new SecondStepRegisterFragment();
                    transaction.add(R.id.layout_container,secondStepRegisterFragment);
                }else{
                    transaction.show(secondStepRegisterFragment);
                }
                break;
            case 2:
                if(thirdStepRegisterFragment==null){
                    thirdStepRegisterFragment=new ThirdStepRegisterFragment();
                    transaction.add(R.id.layout_container,thirdStepRegisterFragment);
                }else{
                    transaction.show(thirdStepRegisterFragment);
                }
                break;
        }
        currentId=id;
        transaction.commitAllowingStateLoss();
    }

    private void hideFragments(int id,FragmentTransaction transaction){
        if(id==0){
            if(firstStepRegisterFragment!=null){
                transaction.hide(firstStepRegisterFragment);
            }
        }else if(id==1){
            if(secondStepRegisterFragment!=null){
                transaction.hide(secondStepRegisterFragment);
            }
        }else if(id==2){
            if(thirdStepRegisterFragment!=null){
                transaction.hide(thirdStepRegisterFragment);
            }
        }
    }

    public void backRegisterFragment(){
        if(currentId==0){
            this.finish();
        }else{
            int id=currentId-1;
            setCurrentRegisterFragment(id);
        }
    }

    @Override
    public void onBackPressed() {
        backRegisterFragment();
    }
}
