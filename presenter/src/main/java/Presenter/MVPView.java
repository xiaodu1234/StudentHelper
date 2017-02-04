package Presenter;

import android.content.Context;
import android.view.Menu;

/**
 * Created by duchaoqiang on 2017/1/5.
 */
public interface MVPView {

    public static final  int GRADE_QUERY=0; //成绩查询Fragment
    public static final  int News_look=1;
    public void showWaitingDialog();

    public void hideWaitingDialog();

    public void toast(String msg);

    public void popDialog(int which, Object obj);

    public void refreshList(Object data);

    public Context getSelf();

    public int getIndentity();

    public String getMarks();

    public void endSelf();

    public void reset();

    public void setListData(Object obj);

    public void setMenuData(Object... items);

    public void setMenuData(Menu menu, Object... item);

    public void refreshMenu();

    public void refreshView(int which, Object data);

    public void goFragment(int which, Object... args);

    public void goActivity(Object obj);

    public void goEditItemFragment(int position);

    public void initialValue(Object... args);

    public void setError(int which, String msg);

    public void setText(int which, String msg);

}
