package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.schoolnews.R;

import Presenter.MVPInterface;
import Presenter.MVPView;
import fragment.BaseFragment;
import fragment.FragmentFactory;

/**
 * Created by duchaoqiang on 2017/1/6.
 */
public class HomeActivity extends BaseActivity {
    private NavigationView navigationView;
    private FrameLayout contentLayout;
    private DrawerLayout drawerLayout;
    private Toolbar mToolbar;
    public String cookie;

    @Override
    public int getIndentity() {
        return MVPInterface.GRADE;
    }

    @Override
    public int getView() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        navigationView= (NavigationView) findViewById(R.id.navigationVieww);
        contentLayout= (FrameLayout) findViewById(R.id.cotentLayout);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        initSlidingmenu();
        Intent intent = getIntent();
        cookie = intent.getStringExtra("cookie");
    }
    private void initSlidingmenu(){
        mToolbar.setTitle("主页面");
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.open, R.string.close);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(item ->{
            item.setChecked(true);
            drawerLayout.closeDrawers();
            switch (item.getItemId()){
                case R.id.action_Grade:
                    mpresenter.onItemClick(0,0,cookie);
                    setTopFragment(MVPView.GRADE_QUERY);
                    break;
                case R.id.action_Schedule:
                    break;
                case R.id.action_news:
                    setTopFragment(MVPView.News_look);
                    break;
            }

            return true;
        } );
    }
    private void setTopFragment(int postion){
        BaseFragment fragment = FragmentFactory.createFragment(postion);
        getSupportFragmentManager().beginTransaction().replace(R.id.cotentLayout,fragment,null).commit();

    }

}
