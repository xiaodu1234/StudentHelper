package Presenter;

/**
 * Created by duchaoqiang on 2017/1/5.
 */
public class PresenterFactor implements MVPInterface {
    @Override
    public MVPPresenter getPresenter(MVPView view) {
        MVPPresenter presenter=null;
        switch (view.getIndentity()){
            case MVPInterface.LOGIN:
                presenter=new LoginPresenter(view);
                break;
            case MVPInterface.GRADE:
                presenter=new GradePresenter(view);
                break;
            case MVPInterface.News:
                presenter=new NewsListPresenter(view);
        }
        return presenter;
    }
}
