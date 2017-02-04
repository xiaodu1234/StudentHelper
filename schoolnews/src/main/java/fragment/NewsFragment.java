package fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.schoolnews.R;

import java.util.ArrayList;

import Bean.NewsListBean;
import Presenter.MVPInterface;
import Utils.ImageLoadUtils;

/**
 * Created by duchaoqiang on 2017/1/9.
 */
public class NewsFragment extends BaseFragment {
    private ListView newsList;
    private Myadapter myadapter;
    @Override
    public int getIndentity() {
        return MVPInterface.News;
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_news;
    }

    @Override
    public void afterView(View view) {
     newsList= (ListView) view.findViewById(R.id.newsList);
    }

    @Override
    public void initData() {
     mpresenter.init();
    }

    @Override
    public void refreshList(Object data) {
        setAdapter(null);
    }

    @Override
    public void setListData(Object obj) {
        setAdapter((ArrayList<NewsListBean>) obj);
    }
    private void setAdapter(ArrayList<NewsListBean> info){
        if (myadapter==null){
            myadapter=new Myadapter(info);
            newsList.setAdapter(myadapter);
        }
        myadapter.notifyDataSetChanged();
    }

    private class Myadapter extends BaseAdapter{

        private ArrayList<NewsListBean>allInfo;
        public Myadapter(ArrayList<NewsListBean> allInfo){
            this.allInfo=allInfo;
        }
        @Override
        public int getCount() {
            return allInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=View.inflate(mActivity,R.layout.news_item,null);
            ImageView headImag= (ImageView) convertView.findViewById(R.id.headImage);
            TextView title= (TextView) convertView.findViewById(R.id.titleTxt);
            TextView content= (TextView) convertView.findViewById(R.id.contentTXT);
            TextView time= (TextView) convertView.findViewById(R.id.timeTxt);
            ImageLoadUtils.setNormalImage(mActivity,headImag,allInfo.get(position).getBitmapUrl(),R.mipmap.ic_launcher,R.mipmap.ic_launcher);
            title.setText(allInfo.get(position).getTitle());
            content.setText(allInfo.get(position).getContent());
            time.setText(allInfo.get(position).getDate());


            return convertView;
        }
    }

}
