package com.htt.strongboxapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.app.BaseFragment;

/**
 * Created by HTT on 2016/3/21.
 */
public class ThirdStepRegisterFragment extends BaseFragment{
    private EditText etUserPassword;
    private EditText etUserConfirmPassword;
    @Override
    protected View inflaterContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_register,container,false);
    }

    @Override
    protected void initViews(View view) {
        etUserPassword=(EditText)view.findViewById(R.id.et_user_password);
        requestFocus();
        etUserConfirmPassword=(EditText)view.findViewById(R.id.et_user_confirm_password);
    }

    public void requestFocus(){
        if(etUserPassword!=null) {
            etUserPassword.setFocusable(true);
            etUserPassword.requestFocus();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
