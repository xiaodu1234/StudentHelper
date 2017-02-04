package fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.Menu;

import Presenter.MVPView;

/**
 * Created by duchaoqiang on 2017/1/9.
 */
public class FragmentWrapper extends Fragment implements MVPView {
    @Override
    public void showWaitingDialog() {

    }

    @Override
    public void hideWaitingDialog() {

    }

    @Override
    public void toast(String msg) {

    }

    @Override
    public void popDialog(int which, Object obj) {

    }

    @Override
    public void refreshList(Object data) {

    }

    @Override
    public Context getSelf() {
        return null;
    }

    @Override
    public int getIndentity() {
        return 0;
    }

    @Override
    public String getMarks() {
        return null;
    }

    @Override
    public void endSelf() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void setListData(Object obj) {

    }

    @Override
    public void setMenuData(Object... items) {

    }

    @Override
    public void setMenuData(Menu menu, Object... item) {

    }

    @Override
    public void refreshMenu() {

    }

    @Override
    public void refreshView(int which, Object data) {

    }

    @Override
    public void goFragment(int which, Object... args) {

    }

    @Override
    public void goActivity(Object obj) {

    }

    @Override
    public void goEditItemFragment(int position) {

    }

    @Override
    public void initialValue(Object... args) {

    }

    @Override
    public void setError(int which, String msg) {

    }

    @Override
    public void setText(int which, String msg) {

    }
}
