package com.htt.strongboxapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.activitys.PasswordSettingRegisterActivity;
import com.htt.strongboxapp.app.BaseFragment;

/**
 * Created by HTT on 2016/3/21.
 */
public class SecondStepRegisterFragment extends BaseFragment{
    private EditText etUserName;
    private EditText etUserPhone;
    private EditText etUserVCode;

    @Override
    protected View inflaterContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_register_phone_check,container,false);
    }

    @Override
    protected void initViews(View view) {
        view.findViewById(R.id.bt_second_step).setOnClickListener(this);
        etUserName=(EditText)view.findViewById(R.id.et_user_name);
        requestFocus();
        etUserPhone=(EditText)view.findViewById(R.id.et_user_phone);
        etUserVCode=(EditText)view.findViewById(R.id.et_user_vcode);
    }

    public void requestFocus(){
        if(etUserName!=null) {
            etUserName.setFocusable(true);
            etUserName.requestFocus();
        }
    }

    private void secondStepRegister(){
        PasswordSettingRegisterActivity activity= (PasswordSettingRegisterActivity) this.getActivity();
        //activity.setCurrentRegisterFragment(2);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_second_step:
                secondStepRegister();
                break;
        }
    }
}
