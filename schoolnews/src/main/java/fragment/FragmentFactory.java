package fragment;

import java.util.HashMap;

import Presenter.MVPView;

/**
 * Created by duchaoqiang on 2017/1/9.
 */
public class FragmentFactory {

    private static HashMap<Integer,BaseFragment> fragmentInfo=new HashMap<>();
    public static BaseFragment createFragment(int postion){
        BaseFragment fragment=null;
        if (fragmentInfo.get(postion)!=null){
            fragment=fragmentInfo.get(postion);
            return fragment;
        }else{
            switch (postion){
                case MVPView.News_look:
                    fragment=new NewsFragment();
                    break;
                case MVPView.GRADE_QUERY:
                    fragment=new GradeFragment();
                    break;
            }
            fragmentInfo.put(postion,fragment);
            return fragment;
        }
    }

}
