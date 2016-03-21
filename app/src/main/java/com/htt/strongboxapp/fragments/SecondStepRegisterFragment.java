package com.htt.strongboxapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.activitys.RegisterActivity;
import com.htt.strongboxapp.app.BaseFragment;

/**
 * Created by HTT on 2016/3/21.
 */
public class SecondStepRegisterFragment extends BaseFragment{
    @Override
    protected View inflaterContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_second,container,false);
    }

    @Override
    protected void initViews(View view) {
        view.findViewById(R.id.bt_second_step).setOnClickListener(this);

    }

    private void secondStepRegister(){
        RegisterActivity activity= (RegisterActivity) this.getActivity();
        activity.setCurrentRegisterFragment(2);
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
