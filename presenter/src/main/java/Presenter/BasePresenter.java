package Presenter;

import NetWork.Api;

/**
 * Created by duchaoqiang on 2017/1/5.
 */
public class BasePresenter extends PresenterWrapper {
    protected MVPView mvpView;
    protected Api mApi;

    public BasePresenter(MVPView mvpView) {
        this.mvpView = mvpView;
        mApi=Api.getInstance();
    }
}
