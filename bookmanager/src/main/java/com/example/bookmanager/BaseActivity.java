package com.example.bookmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import utils.DButils;

/**
 * Created by duchaoqiang on 2016/12/29.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar toolbar;
    protected DButils utils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRootView());
        utils = DButils.getInstance(this);
        initView();
        initData();
    }
    public void initToolBar(){
       toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public abstract int getRootView();

    public abstract void initView();
    public abstract void initData();


}
