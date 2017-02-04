package com.example.bookmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

/**
 * Created by duchaoqiang on 2016/12/29.
 */
public class HomeActivity extends BaseActivity {
    private RadioGroup group;
    private RadioButton radioSalfe;
    private RadioButton radioSetting;
    private RadioButton radioAdd;
    private RadioButton radioStock;
    private ViewPager container;
    private ArrayList<BaseFragment> allFragment;
    private static final int pagerNum=4;
    @Override
    public int getRootView() {
        return R.layout.activity_home;
    }
    @Override
    public void initView() {
        initToolBar();
        //设置返回键有效
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//       group= (RadioGroup) findViewById(R.id.group);
//        radioAdd= (RadioButton) findViewById(R.id.radio_add);
//        radioSalfe= (RadioButton) findViewById(R.id.radio_salfe);
//        radioSetting= (RadioButton) findViewById(R.id.radio_setting);
//        radioStock= (RadioButton) findViewById(R.id.radio_stock);
        container= (ViewPager) findViewById(R.id.pager);
    }

    @Override
    public void initData() {
        initFragment();
        FragmentManager manager=getSupportFragmentManager();
        container.setAdapter(new Myadapter(manager));
    }
    private void initFragment(){
        if (allFragment==null){
            allFragment=new ArrayList<>();
        }
        allFragment.clear();
        for (int i=0;i<pagerNum;i++){
            allFragment.add(FragmentFactory.getFragment(i));
        }
    }

    private class Myadapter extends FragmentPagerAdapter{

        public Myadapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return allFragment.get(position);
        }

        @Override
        public int getCount() {
            return allFragment.size();
        }
    }
}
