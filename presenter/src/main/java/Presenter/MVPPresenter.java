package Presenter;

import android.view.Menu;

/**
 * Created by duchaoqiang on 2017/1/5.
 */
public interface MVPPresenter {
    public void init();

    public void initWithValue(Object... data);//将初始化的数据存放到Model中去

    public void initMenu(Menu menu);

    public void onClick(int which, Object obj);

    public boolean onBackPressed();

    public void onLongClick(int which, int position);

    public void onCheckedChanged(int which, boolean checked);

    public void onResume();

    public void onCreateOptionsMenu();

    public void onItemClick(int which, int position);

    public void onItemClick(int which, int position, Object data);

    public void submit(Object... args);

    public void onDestroy();
}
