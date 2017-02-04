package com.example.bookmanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bean.StockBean;

/**
 * Created by duchaoqiang on 2016/12/29.
 */
public class SalfeFragment extends  BaseFragment {
    private EditText barcodeInput;
    private TextView name;
    private TextView price;
    private Button orderButton;
    private Button payButton;
    private StockBean bean;
    public static ArrayList<StockBean> allOrder=new ArrayList<>();
    @Override
    public int getRootView() {
        return R.layout.fragment_salfe;
    }

    @Override
    public void initView(View view) {
        setTitle("售书交易");
        barcodeInput= (EditText) view.findViewById(R.id.barcode_input);
        name= (TextView) view.findViewById(R.id.nameTxt);
        price= (TextView) view.findViewById(R.id.priceTxt);
        orderButton= (Button) view.findViewById(R.id.orderButton);
        payButton= (Button) view.findViewById(R.id.payButton);

        barcodeInput.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bean = utils.qreryStock(s.toString());
              if (bean !=null){
                  changeUi(bean);
              }else {
                  name.setText("书名： ");
                  price.setText("单价： ");
              }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean!=null){
                    showDialog(bean,false);
                    barcodeInput.setText("");
                }else
                {
                    Toast.makeText(mActivity,"请先输入条形码",Toast.LENGTH_SHORT).show();
                }

            }
        });
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (allOrder.size()>0){
                    startActivity(new Intent(mActivity,OrderDetialsActivity.class));
                    return;
                }
                  else if (bean!=null && allOrder.size()==0){
                    showDialog(bean,true);
                    return;
                }
                Toast.makeText(mActivity,"请输入条形码",Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void changeUi(StockBean bean){
        name.setText("书名： "+bean.getBookName());
        price.setText("单价： "+bean.getPrice());
    }

    @Override
    public void initData() {

    }
    private void showDialog(final StockBean sbean,final  boolean isPay){
        AlertDialog.Builder builder=new AlertDialog.Builder(mActivity);
       View v= View.inflate(mActivity,R.layout.dialog_view,null);
        TextView sure= (TextView) v.findViewById(R.id.sure);
        TextView cancel= (TextView) v.findViewById(R.id.cancel);
        final EditText numInput= (EditText) v.findViewById(R.id.numInput);
        builder.setView(v);
        final AlertDialog dialog = builder.create();
        dialog.show();
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(numInput.getText().toString().trim()) ){
                    int num = Integer.parseInt(numInput.getText().toString());
                    boolean isSucess = addOrder(num, sbean);
                    dialog.dismiss();
                    if (isPay && isSucess){
                        startActivity(new Intent(mActivity,OrderDetialsActivity.class));
                    }
                }else{
                    Toast.makeText(mActivity,"请输入购买数量",Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 判断购买数量 是否大于库存
     * @param num
     */
    private boolean addOrder(int num,StockBean sbean){
     if (sbean!=null){
         if (num<=sbean.getStock_num()){
              utils.updateStock(sbean.getStock_num()-num);
             sbean.setPay_num(num);
             allOrder.add(sbean);
             Toast.makeText(mActivity,"加入订单成功"+ num+"本书",Toast.LENGTH_SHORT).show();
             return  true;
         }else{
             Toast.makeText(mActivity,"库存量不足",Toast.LENGTH_SHORT).show();
         }
     }
        return false;
    }




}
