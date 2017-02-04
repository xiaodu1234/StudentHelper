package Presenter;

/**
 * Created by duchaoqiang on 2017/1/5.
 */
public interface MVPInterface {
    public static final int LOGIN=0;
    public static final int GRADE=1;//成绩查询
    public static final int News=2;
    public MVPPresenter getPresenter(MVPView view);
}
