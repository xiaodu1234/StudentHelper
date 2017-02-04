package Presenter;

import java.util.ArrayList;

import Bean.GradeBean;

/**
 * Created by duchaoqiang on 2017/1/6.
 */
public class HomePresenter extends BasePresenter {
    private ArrayList<GradeBean> allInfo=new ArrayList<>();

    public HomePresenter(MVPView mvpView) {
        super(mvpView);
    }

    @Override
    public void onItemClick(int which, int position, Object data) {
        mvpView.showWaitingDialog();
       if (which==0){
           if (data!=null){
              // getGrade(data.toString());
           }else {
               mvpView.toast("cookie");
           }
       }
    }



}
