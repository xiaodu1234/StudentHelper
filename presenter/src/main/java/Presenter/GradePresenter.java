package Presenter;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Bean.GradeBean;
import Bean.TermBean;
import NetWork.OkHttpManager;
import Result.StringCallBack;
import Utils.StreamPool;
import Utils.UrlConfig;

/**
 * Created by duchaoqiang on 2017/1/10.
 */
public class GradePresenter extends BasePresenter {
    private ArrayList<GradeBean> allInfo=new ArrayList<>();
    public GradePresenter(MVPView mvpView) {
        super(mvpView);
    }

    @Override
    public void initWithValue(Object... data) {
        mvpView.showWaitingDialog();
        if (data[0].toString()!=null){
            getGrade(data[0].toString());
        }else{
            mvpView.toast("cookie 失效");
        }

    }

    /**
     * 查询学生成绩
     */
    private void getGrade(String cookie) {
        Map<String,String> heander=new HashMap<>();
        OkHttpClient client = new OkHttpClient();
        heander.put("Cookie", cookie);
        heander.put("Connection", "keep-alive");
        heander.put("Host", "202.199.248.12");
        heander.put("Referer", "http://202.199.248.12/tmweb/ischool.aspx");
        OkHttpManager.getOkhttpManager().post(UrlConfig.getGradeUrl,new StringCallBack<String>(){
            @Override
            public void onSuccess(Response response, String s) {
                super.onSuccess(response, s);
                parserGrade(s);
            }
        },null,heander, StreamPool.GB);


    }
    /**
     * 解析成绩 html
     * @param html
     */
    private void parserGrade(String html){
        ArrayList<String> data=new ArrayList<>();
        Document document= Jsoup.parse(html);
        Elements elements = document.getElementsByClass("cell dxgv");
        for (int i=0;i<100;i++){
            Element element = document.getElementById("grid_DXDataRow" + i);
            if (element!=null){
                String text = element.getElementsByClass("cell dxgv").text();
                if (!TextUtils.isEmpty(text)){
                    initInfo(text);
                }
            }else{
                i=100;
            }

        }
//        Element element = document.getElementById("grid_DXDataRow" + 0);
//        String text = element.getElementsByClass("cell dxgv").text();
//        Log.i("myTag",text);

        //getOneTermData();
        mvpView.hideWaitingDialog();
        mvpView.setListData(allInfo);
    }
    /*
    提取数据 去掉空格
    */
    private void initInfo(String text){
        String[] split = text.split("\\s+");
        initBean(split);
    }
    /*
     初始化Bean
     */
    private void initBean(String[] data){
        Log.i("myTag",data[0]);
        GradeBean bean=new GradeBean();
        int currentIndex=0;//当前下标为0
        int size=data.length;
        if (size==17){
            bean.setSchoolYear(data[0]);
            bean.setYearPoint(data[1]);
            bean.setSchoolTerm(data[2]);
            bean.setTermPoint(data[3]);
            currentIndex=4;
            Log.i("myTag","执行了 17");
        }
        if (size==15){
            bean.setSchoolYear("");
            bean.setYearPoint("");
            bean.setSchoolTerm(data[0]);
            bean.setTermPoint(data[1]);
            currentIndex=2;
            Log.i("myTag","执行了 15");
        }
        bean.setCouseNum(data[currentIndex]);
        bean.setCourseName(data[currentIndex+1]);
        bean.setTeachMethod(data[currentIndex+2]);
        bean.setCourseType(data[currentIndex+3]);
        bean.setCourseCategory(data[currentIndex+4]);
        bean.setHours(data[currentIndex+5]);
        bean.setCredit(data[currentIndex+6]);
        bean.setScore(data[currentIndex+7]);
        bean.setExamMethod(data[size-2]);
        bean.setStatus(data[size-1]);
        Log.i("myTag","执行了 10");
        allInfo.add(bean);
    }

    ArrayList<GradeBean> info=new ArrayList<>();
    ArrayList<TermBean> termInfo=new ArrayList<>();
    TermBean bean;
    /**将所有的数据按学期分开**/
    private void getOneTermData(){
        for (int i=0;i<allInfo.size();i++){
            //如果学期的数据不为空
            if (!TextUtils.isEmpty(allInfo.get(i).getSchoolTerm())){
                if (info.size()>0){
                    bean.setTermInfo(info);
                    termInfo.add(bean);
                    info.clear();
                }
               bean =new TermBean();

            }
            info.add(allInfo.get(i));
        }
    }
}
