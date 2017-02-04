package fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.schoolnews.R;

import java.util.ArrayList;

import Bean.GradeBean;
import Presenter.MVPInterface;

/**
 * Created by duchaoqiang on 2017/1/10.
 */
public class GradeFragment extends BaseFragment {
    private ListView gradeList;
    private MyAdapter adapter;
    @Override
    public int getIndentity() {
        return MVPInterface.GRADE;
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_grade;
    }

    @Override
    public void afterView(View view) {
        gradeList= (ListView) view.findViewById(R.id.gradeList);
    }

    @Override
    public void initData() {
        Log.i("myTag","发送的cookie"+mActivity.cookie);
        mpresenter.initWithValue(mActivity.cookie);

    }

    @Override
    public void setListData(Object obj) {
       setAdapter((ArrayList<GradeBean>) obj);
    }



    private void setAdapter(ArrayList<GradeBean> info){
        if (adapter==null){
            adapter=new MyAdapter(info);
            gradeList.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();
    }

    private class MyAdapter extends BaseAdapter{

        private ArrayList<GradeBean>scoreInfo;
        public MyAdapter(ArrayList<GradeBean> allInfo){
           scoreInfo=allInfo;
        }
        @Override
        public int getCount() {
            return scoreInfo.size();
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
            convertView=View.inflate(mActivity,R.layout.item_grade,null);
            TextView courseName= (TextView) convertView.findViewById(R.id.courseNameTxt);
            TextView credit= (TextView) convertView.findViewById(R.id.creditTxt);
            TextView score= (TextView) convertView.findViewById(R.id.scoreTxt);
            TextView status= (TextView) convertView.findViewById(R.id.statusTxt);
            TextView term= (TextView) convertView.findViewById(R.id.termTxt);
            LinearLayout head= (LinearLayout) convertView.findViewById(R.id.headLayout);
            if (scoreInfo.get(position).getSchoolTerm()!=null){
                term.setVisibility(View.VISIBLE);
                head.setVisibility(View.VISIBLE);
                term.setText(scoreInfo.get(position).getSchoolTerm());
                courseName.setText(scoreInfo.get(position).getCourseName());
                credit.setText(scoreInfo.get(position).getCredit());
                score.setText(scoreInfo.get(position).getScore());
                status.setText(scoreInfo.get(position).getStatus());
            }else{
                head.setVisibility(View.GONE);
                term.setVisibility(View.GONE);
                courseName.setText(scoreInfo.get(position).getCourseName());
                credit.setText(scoreInfo.get(position).getCredit());
                score.setText(scoreInfo.get(position).getScore());
                status.setText(scoreInfo.get(position).getStatus());
            }
            return convertView;
        }
    }

}
