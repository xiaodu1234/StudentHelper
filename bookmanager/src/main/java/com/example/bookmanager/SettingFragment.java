package com.example.bookmanager;

import android.content.DialogInterface;
import android.os.Process;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by duchaoqiang on 2016/12/29.
 */
public class SettingFragment extends  BaseFragment {
    private TextView clearStock;
    private TextView clearAdd;
    private TextView clearSalfe;
    private TextView ediText;
    @Override
    public int getRootView() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initView(View view) {
        setTitle("设置页面");
         clearAdd= (TextView) view.findViewById(R.id.addClear);
        clearSalfe= (TextView) view.findViewById(R.id.SalfeClear);
        clearStock= (TextView) view.findViewById(R.id.stcokClear);
        ediText= (TextView) view.findViewById(R.id.exit);
        clearAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("此操作将会清理进货记录！！",1);
            }
        });
        clearStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              showDialog("此操作将会清理库存记录！！",0);
            }
        });
        clearSalfe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("此操作将会清理售货记录！！",2);
            }
        });
        ediText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出当前进程
                android.os.Process.killProcess(Process.myPid());
            }
        });
    }

    @Override
    public void initData() {

    }
    private void showDialog(String message, final int witch){
        AlertDialog.Builder builder=new AlertDialog.Builder(mActivity);
        builder.setNegativeButton("取消",null);
        builder.setTitle("注意！！");
        builder.setMessage(message);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               switch (witch){
                   case 0:
                       utils.clearStock();
                       break;
                   case 1:
                       utils.clearAddTable();
                       break;
                   case 2:
                       utils.clearSalfeTable();
                       break;
               }
                Toast.makeText(mActivity,"删除数据成功",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.show();

    }
}
