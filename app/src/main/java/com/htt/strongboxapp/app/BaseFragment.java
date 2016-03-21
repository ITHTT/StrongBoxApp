package com.htt.strongboxapp.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by HTT on 2016/3/21.
 */
abstract public class BaseFragment extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflaterContentView(inflater,container,savedInstanceState);
        initViews(view);
        return view;
    }

    abstract protected View inflaterContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    abstract protected void initViews(View view);
}
