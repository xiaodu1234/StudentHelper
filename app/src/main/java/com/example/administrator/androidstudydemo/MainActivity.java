package com.example.administrator.androidstudydemo;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton button;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private ArrayList<TextView>datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (FloatingActionButton) findViewById(R.id.floatButton);
        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        tabLayout= (TabLayout)findViewById(R.id.tableLayout);
        tabLayout.setTabTextColors(Color.WHITE,Color.BLUE);
        tabLayout.addTab(tabLayout.newTab().setText("第一个"),false);
        tabLayout.addTab(tabLayout.newTab().setText("第二个"),false);
        tabLayout.addTab(tabLayout.newTab().setText("第三个"),false);
        initData(tabLayout.getTabCount());
        button.setRippleColor(Color.RED);
        button.setOnClickListener(new activity.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(button,"hah",Snackbar.LENGTH_LONG).setAction("delete", new activity.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"delete",Toast.LENGTH_LONG).show();
                    }
                }).show();

            }
        });
        Adapter adapter = new Adapter();
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }
    private void initData(int count){
        if (datas==null){
            datas=new ArrayList<>();
        }
        for (int i=0;i<count;i++){
            TextView textView=new TextView(this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setText("第"+i+"页");
            textView.setTextColor(Color.RED);
            textView.setTextSize(22);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(params);
            datas.add(textView);
        }
    }
    public  class  Adapter extends PagerAdapter{

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }

}
