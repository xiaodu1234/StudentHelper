package com.example.bookmanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import utils.DButils;

/**
 * Created by duchaoqiang on 2016/12/29.
 */
public abstract class BaseFragment extends Fragment {

    protected Toolbar toolbar;
    private View view;
    protected Activity mActivity;
    protected DButils utils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getRootView(),null);
        mActivity=getActivity();
        utils=DButils.getInstance(mActivity);
        initView(view);
        initData();
        return view;
    }
    public abstract int getRootView();
    public abstract void initView(View view);
    public abstract void initData();

    protected void setTitle(String title){
        getActivity().setTitle(title);
    }
}
