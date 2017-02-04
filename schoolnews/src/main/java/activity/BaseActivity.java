package activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.example.schoolnews.R;

import Presenter.MVPInterface;
import Presenter.MVPPresenter;
import Presenter.MVPView;
import Presenter.PresenterFactor;

/**
 * Created by duchaoqiang on 2017/1/5.
 */
public abstract class BaseActivity extends AppCompatActivity implements MVPView {

    private ProgressDialog mProgressDialog;
    protected MVPPresenter mpresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setMessage("请稍后...");
        MVPInterface face=new PresenterFactor();
         mpresenter = face.getPresenter(this);
    }
    public abstract int getView();
    public void initToolBar(){
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void showWaitingDialog() {
         mProgressDialog.show();
    }

    @Override
    public void hideWaitingDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
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
