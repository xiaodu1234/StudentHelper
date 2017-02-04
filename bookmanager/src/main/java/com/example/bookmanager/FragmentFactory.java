package com.example.bookmanager;

/**
 * Created by duchaoqiang on 2016/12/29.
 */
public class FragmentFactory {
    public static  BaseFragment getFragment(int witch){
         BaseFragment fragment=null;
        switch (witch){
            case 0:
                fragment=new SalfeFragment();
                break;
            case 1:
                fragment=new AddFragment();
                break;
            case 2:
                fragment=new StockFragment();
                break;
            case 3:
                fragment=new SettingFragment();
        }
        if (fragment!=null){
            return fragment;
        }
       return null;
    }

}
