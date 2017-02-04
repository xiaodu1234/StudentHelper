package com.example.bookmanager;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import bean.SalfeBean;
import bean.StockBean;

/**
 * Created by duchaoqiang on 2016/12/29.
 */
public class OrderDetialsActivity extends BaseActivity {
    private ListView orderList;
    private TextView allMoney;
    private TextView outMoney;
    private EditText receiveMoney;
    private Button salfeButton;
    //
    private ArrayList<StockBean> allInfo=new ArrayList<>();
    //售书表
    private ArrayList<SalfeBean> order;

    private int sumMoney=0;
    @Override
    public int getRootView() {
        return R.layout.activity_order;
    }

    @Override
    public void initView() {
      orderList= (ListView) findViewById(R.id.orderListView);
        allMoney= (TextView) findViewById(R.id.allMoneyTxt);
        outMoney= (TextView) findViewById(R.id.outMoneyTxt);
        receiveMoney= (EditText) findViewById(R.id.receiveInput);
        salfeButton= (Button) findViewById(R.id.salfeButton);
        initToolBar();
        getSupportActionBar().setTitle("订单详情");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initData() {

        if (SalfeFragment.allOrder.size()>0){
            allInfo.addAll(SalfeFragment.allOrder);
        }
        orderList.setAdapter(new Myadapter());
        for (int i=0;i<allInfo.size();i++){
            sumMoney+=allInfo.get(i).getPrice()*allInfo.get(i).getPay_num();
        }
        allMoney.setText("总价 ："+sumMoney);
        receiveMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) return;
               if (Integer.parseInt(s.toString())>0){
                   changeOutMoney(s.toString(),sumMoney);
               }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //完成售书
        salfeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order==null){
                    order=new ArrayList<SalfeBean>();
                }
                order.clear();
               for (int i=0;i<allInfo.size();i++){
                   SalfeBean bean=new SalfeBean();
                   bean.setBarcode(allInfo.get(i).getBarcode());
                   bean.setNum(allInfo.get(i).getPay_num());
                   bean.setDate(getCurrentTime());
                   order.add(bean);
                   Toast.makeText(OrderDetialsActivity.this,"交易成功",Toast.LENGTH_SHORT).show();
               }
                utils.addSalfeTable(order);
                //清理订单数据
                SalfeFragment.allOrder.clear();
                allInfo.clear();
                finish();
            }
        });
    }
    private String getCurrentTime(){
        Date date=new Date();
        SimpleDateFormat sp=new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return sp.format(date);
    }

    /**
     * 改变 找零
     * @param money
     */
    private void changeOutMoney(String money,int allMoney){
        outMoney.setText("找零 ："+ (Integer.parseInt(money)-allMoney<0  ? 0: Integer.parseInt(money)-allMoney)+"元");
    }
    private class  Myadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return allInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=getLayoutInflater().inflate(R.layout.order_item,null);
            TextView tvBookName= (TextView) convertView.findViewById(R.id.bookName);
            TextView tvAuthor= (TextView) convertView.findViewById(R.id.author);
            TextView tvNum= (TextView) convertView.findViewById(R.id.num);
            TextView tvPress= (TextView) convertView.findViewById(R.id.press);
            TextView tvPrice= (TextView) convertView.findViewById(R.id.price);
            tvBookName.setText("书名 :"+allInfo.get(position).getBookName());
            tvAuthor.setText("作者 ："+allInfo.get(position).getAuthor());
            tvNum.setText("购买数量 :"+allInfo.get(position).getPay_num());
            tvPress.setText("出版社 :"+allInfo.get(position).getPress());
            tvPrice.setText("单价 ："+allInfo.get(position).getPrice());
            return convertView;
        }
    }
}
