package com.htt.strongboxapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.app.BaseFragment;

/**
 * Created by HTT on 2016/3/21.
 */
public class ThirdStepRegisterFragment extends BaseFragment{
    @Override
    protected View inflaterContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_third,container,false);
    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    public void onClick(View v) {

    }
}
