package com.htt.strongboxapp.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htt.strongboxapp.networks.HttpClientUtil;

/**
 * Created by HTT on 2016/3/21.
 */
abstract public class BaseFragment extends Fragment implements View.OnClickListener{
    protected final String Tag=this.getClass().getSimpleName();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflaterContentView(inflater,container,savedInstanceState);
        initViews(view);
        return view;
    }

    abstract protected View inflaterContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    abstract protected void initViews(View view);

    @Override
    public void onDestroy() {
        HttpClientUtil.getHttpClientUtil().cancelTag(Tag);
        super.onDestroy();
    }
}
