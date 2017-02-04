package fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import Presenter.MVPInterface;
import Presenter.MVPPresenter;
import Presenter.PresenterFactor;
import activity.HomeActivity;

/**
 * Created by duchaoqiang on 2017/1/9.
 */
public abstract class BaseFragment extends FragmentWrapper {
    protected MVPPresenter mpresenter;
    protected ProgressDialog mprogressDialog;
    protected HomeActivity mActivity;
    public abstract int getRootView();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MVPInterface face=new PresenterFactor();
        mpresenter = face.getPresenter(this);
        View view = inflater.inflate(getRootView(), container, false);
        mActivity= (HomeActivity) getActivity();
        mprogressDialog=new ProgressDialog(mActivity);
        mprogressDialog.setMessage("正在加载...");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterView(view);
        initData();
    }


    public void setTitle(String title){
        mActivity.setTitle(title);
    }
    //初始化界面
    public abstract void  afterView(View view);

    public abstract void initData();

    @Override
    public void showWaitingDialog() {
     mprogressDialog.show();
    }

    @Override
    public void hideWaitingDialog() {
        mprogressDialog.hide();
    }
}

