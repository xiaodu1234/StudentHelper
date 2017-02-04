package com.example.bookmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import utils.DButils;
import utils.SharedPrefUtils;

/**
 * Created by duchaoqiang on 2016/12/29.
 */
public class LogingActivity extends AppCompatActivity {
    private EditText usernameEdit;
    private EditText passwordEdit;
    private Button loginButton;
    private CheckBox recode_pass;
    private DButils instance;
    private Boolean isSaved=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
        instance = DButils.getInstance(this);
       DButils utils= DButils.getInstance(this);
        utils.addUser("123","123");
    }

    private void initView(){
        usernameEdit= (EditText) findViewById(R.id.username_edit);
        passwordEdit= (EditText) findViewById(R.id.password_edit);
        loginButton= (Button) findViewById(R.id.login_btn);
        recode_pass= (CheckBox) findViewById(R.id.record_psw);
        if (SharedPrefUtils.getPassword(this,null)!=null){
            usernameEdit.setText(SharedPrefUtils.getUsername(this,null));
            passwordEdit.setText(SharedPrefUtils.getPassword(this,null));
        }
        recode_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSaved=isChecked;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=usernameEdit.getText().toString().trim();
                String pass=passwordEdit.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)){
                    Toast.makeText(LogingActivity.this,"账号或密码不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                if (instance.queryUser(name,pass)){
                    Toast.makeText(LogingActivity.this,"登錄成功",Toast.LENGTH_LONG).show();
                    if (isSaved){
                        SharedPrefUtils.savaUserName(LogingActivity.this,name);
                        SharedPrefUtils.savePassword(LogingActivity.this,pass);
                    }
                    startActivity(new Intent(LogingActivity.this,HomeActivity.class));
                    finish();

                }else{
                    Toast.makeText(LogingActivity.this,"賬號密碼錯誤",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
