package com.example.bookmanager;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import bean.StockBean;
import bean.addBookBean;

/**
 * Created by duchaoqiang on 2016/12/29.
 */
public class AddFragment extends  BaseFragment {
    private EditText barcode;
    private EditText bookName;
    private EditText author;
    private EditText prcess;
    private EditText price;
    private EditText addMoney;
    private EditText num;
    private EditText date;
    private Button commitButton;
    StockBean stockBean;

    @Override
    public int getRootView() {
        return R.layout.fragment_addbook;
    }

    @Override
    public void initView(View view) {
        setTitle("进货");
        barcode= (EditText) view.findViewById(R.id.myBarcode);
        bookName= (EditText) view.findViewById(R.id.bookName);
        author= (EditText) view.findViewById(R.id.author);
        prcess= (EditText) view.findViewById(R.id.prcess);
        price= (EditText) view.findViewById(R.id.price);
        addMoney= (EditText) view.findViewById(R.id.addMoney);
        num= (EditText) view.findViewById(R.id.num);
        date= (EditText) view.findViewById(R.id.date);
        commitButton= (Button) view.findViewById(R.id.buttonCommit);
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (isEmpty()){
                   addBookBean addBookBean=new addBookBean();
                   addBookBean.setDate(date.getText().toString().trim());
                   addBookBean.setNum(Integer.parseInt(num.getText().toString()));
                   addBookBean.setBarcode(Integer.parseInt(barcode.getText().toString()));
                   addBookBean.setPrice(Integer.parseInt(addMoney.getText().toString()));
                   utils.addBookTable(addBookBean);
                   stockBean = utils.qreryStock(barcode.getText().toString());
                   if (stockBean==null){ //在库存中没有记录这种书
                       stockBean=new StockBean();
                       stockBean.setPrice(Integer.parseInt(price.getText().toString()));
                       stockBean.setBarcode(Integer.parseInt(barcode.getText().toString()));
                       stockBean.setAuthor(author.getText().toString());
                       stockBean.setPress(prcess.getText().toString());
                       stockBean.setBookName(bookName.getText().toString());
                       stockBean.setStock_num(Integer.parseInt(num.getText().toString().trim()));
                       utils.addStockTable(stockBean);
                   }else
                   { //书已经存在 更新库存表
                       utils.updateStock(stockBean.getStock_num()+Integer.parseInt(num.getText().toString().trim()));
                       Toast.makeText(mActivity,"书已经存在，库存更新",Toast.LENGTH_SHORT).show();
                   }
                   Toast.makeText(mActivity,"操作成功",Toast.LENGTH_SHORT).show();
                   barcode.setText("");
                   bookName.setText("");
                   author.setText("");
                   prcess.setText("");
                   price.setText("");
                   date.setText("");
               }
            }
        });
        date.setText(getCurrentTime());
        barcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                StockBean bean = utils.qreryStock(s.toString());
                if (bean!=null){
                   initView(bean);
                }else
                {
                    addMoney.setText("");
                    num.setText("");
                    bookName.setText("");
                    author.setText("");
                    prcess.setText("");
                    price.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean isEmpty(){
        if (TextUtils.isEmpty(barcode.getText()) || TextUtils.isEmpty(bookName.getText())||TextUtils.isEmpty(author.getText()) || TextUtils.isEmpty(prcess.getText())||TextUtils.isEmpty(price.getText())||TextUtils.isEmpty(addMoney.getText())||TextUtils.isEmpty(num.getText()) || TextUtils.isEmpty(date.getText())){
            Toast.makeText(mActivity,"请补全信息",Toast.LENGTH_SHORT).show();
            return  false;
        }
        return  true;
    }



    private void initView(StockBean bean){
        bookName.setText(bean.getBookName());
        author.setText(bean.getAuthor());
        prcess.setText(bean.getPress());
        price.setText(bean.getPrice()+"");
    }
    private String getCurrentTime(){
        Date date=new Date();
        SimpleDateFormat sp=new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
       return sp.format(date);
    }


    @Override
    public void initData() {

    }
}
