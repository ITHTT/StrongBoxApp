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
public class FirstStepRegisterFragment extends BaseFragment{
    @Override
    protected View inflaterContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_first,container,false);
    }

    @Override
    protected void initViews(View view) {
        view.findViewById(R.id.bt_first_step).setOnClickListener(this);

    }

    private void firstStepRegister(){
        RegisterActivity activity= (RegisterActivity) this.getActivity();
        activity.setCurrentRegisterFragment(1);
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
