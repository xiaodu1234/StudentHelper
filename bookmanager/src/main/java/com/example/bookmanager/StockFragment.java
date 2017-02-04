package com.example.bookmanager;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bean.SalfeBean;
import bean.StockBean;
import bean.addBookBean;

/**
 * Created by duchaoqiang on 2016/12/29.
 * 查询界面
 */
public class StockFragment extends  BaseFragment {
    private RadioGroup radioGroup;
    private RadioButton radioStock;
    private RadioButton radioAdd;
    private RadioButton radioSalfe;
    private LinearLayout layout;
    private TextView timeTxt;
    private EditText timeInput;
    private EditText booknameInput;
    private EditText barcodeInput;
    private ListView listView;
    private Button commitButton;
    private int select=0;
    private ArrayList<?>allInfo=new ArrayList<>();
    private MyAdapter adapter;
    @Override
    public int getRootView() {
        return R.layout.fragment_stock;
    }

    @Override
    public void initView(View view) {
     radioGroup= (RadioGroup) view.findViewById(R.id.radioGroup);
        radioStock= (RadioButton) view.findViewById(R.id.radioStock);
        radioAdd= (RadioButton) view.findViewById(R.id.radioAdd);
        radioSalfe= (RadioButton) view.findViewById(R.id.radioSalfe);
        layout= (LinearLayout) view.findViewById(R.id.layoutTime);
        timeInput= (EditText) view.findViewById(R.id.timeInput);
        booknameInput= (EditText) view.findViewById(R.id.bookNameInput);
        barcodeInput= (EditText) view.findViewById(R.id.barcodeInput);
        timeTxt= (TextView) view.findViewById(R.id.timeTxt);
        listView= (ListView) view.findViewById(R.id.listview);
        commitButton= (Button) view.findViewById(R.id.buttonCommit);
        setTitle("查询界面");
        radioAdd.setChecked(true);
        //给listview 添加上下文菜单
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,1,0,"删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId()==1){
            Toast.makeText(mActivity,"逗你玩的~不能删除亲~",Toast.LENGTH_LONG).show();
        }
        return super.onContextItemSelected(item);

    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter!=null){
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void initData() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              if (checkedId==R.id.radioAdd)//购书查询
              {
                  layout.setVisibility(View.VISIBLE);
                  timeTxt.setText("采购时间：");
                  select=0;
                  clearData();
              }
                if (checkedId==R.id.radioSalfe){ //售书查询
                    layout.setVisibility(View.VISIBLE);
                   timeTxt.setText("销售时间 ：");
                    select=1;
                    clearData();
                }
                if (checkedId==R.id.radioStock){ //库存查询
                    layout.setVisibility(View.INVISIBLE);
                    select=2;
                    clearData();
                }
            }
        });
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select==2){
                    query(2,barcodeInput.getText().toString(),booknameInput.getText().toString());
                    setAdapter();
                }
                if (select==1){
                    query(1,barcodeInput.getText().toString(),booknameInput.getText().toString(),timeInput.getText().toString());
                    setAdapter();
                }
                if (select==0){
                    query(0,barcodeInput.getText().toString(),booknameInput.getText().toString(),timeInput.getText().toString());
                    setAdapter();
                }

            }
        });
    }
    //清理数据
    private void clearData(){
        barcodeInput.setText("");
        booknameInput.setText("");
        timeInput.setText("");
    }
    private void query(int witch,String... data){
        String barcode=data[0];
        String bookName=data[1];
        allInfo.clear();
        switch (witch){
            case 2:
                allInfo=utils.queryStock(barcode,bookName);
                break;
            case 0:
                String time=data[2];
                allInfo= utils.queryAddBook(barcode,bookName,time);
                break;
            case 1:
                String d=data[2];
                allInfo= utils.querySalfeBook(barcode,bookName,d);

        }
    }
    private void setAdapter(){
        if (adapter==null){
            adapter=new MyAdapter();
            listView.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();

    }

    private class MyAdapter extends BaseAdapter{

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
            convertView=mActivity.getLayoutInflater().inflate(R.layout.order_item,null);
            TextView tvBookName= (TextView) convertView.findViewById(R.id.bookName);
            TextView tvAuthor= (TextView) convertView.findViewById(R.id.author);
            TextView tvNum= (TextView) convertView.findViewById(R.id.num);
            TextView tvPress= (TextView) convertView.findViewById(R.id.press);
            TextView tvPrice= (TextView) convertView.findViewById(R.id.price);
            if (select==2){
                   ArrayList<StockBean>bInfo=(ArrayList<StockBean>)allInfo;
                    tvBookName.setText("书名 :"+bInfo.get(position).getBookName());
                    tvAuthor.setText("作者 ："+bInfo.get(position).getAuthor());
                    tvNum.setText("库存量 :"+bInfo.get(position).getStock_num());
                    tvPress.setText("出版社 :"+bInfo.get(position).getPress());
                    tvPrice.setText("零售价 ："+bInfo.get(position).getPrice());
            }
            if (select==0){ //购书表查询
                ArrayList<addBookBean>bInfo= (ArrayList<addBookBean>) allInfo;
                tvBookName.setText("书名 :"+bInfo.get(position).getBookName());
                tvAuthor.setText("作者 ："+bInfo.get(position).getAuthor());
                tvNum.setText("进货量 :"+bInfo.get(position).getNum());
                tvPress.setText("进货时间 :"+bInfo.get(position).getDate());
                tvPrice.setText("进价 ："+bInfo.get(position).getPrice());
            }
            if (select==1){//售书

                ArrayList<SalfeBean>bInfo= (ArrayList<SalfeBean>) allInfo;
                tvBookName.setText("书名 :"+bInfo.get(position).getBooName());
                tvAuthor.setText("作者 ："+bInfo.get(position).getAuthor());
                tvNum.setText("销售量 :"+bInfo.get(position).getNum());
                tvPress.setText("销售时间 :"+bInfo.get(position).getDate());
                tvPrice.setText("单价 ："+bInfo.get(position).getPrice());
            }

            return convertView;
        }
    }

}
