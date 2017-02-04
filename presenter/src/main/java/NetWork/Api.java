package NetWork;

import android.graphics.Bitmap;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Bean.GradeBean;
import Result.StringCallBack;
import Utils.StreamPool;
import Utils.UrlConfig;

/**
 * Created by duchaoqiang on 2017/1/5.
 */
public class Api {
    /**登录所需要的参数*/
    private String VIEWSTATE = "";
    private String button1 = "";
    private String EVENTVALIDATION = "";
    private String cobRole_VI = "bdfeb86e-3c29-4696-a19d-6c428850cea3";
    private String cobRole_DDDWS = "0:0:-1:0:0:0:0:0:";
    private String cobRole$DDD$L = "bdfeb86e-3c29-4696-a19d-6c428850cea3";
    public static Bitmap barcodeBitmap;
    public static String myCode=""; //文字验证码
    private ArrayList<GradeBean> allInfo=new ArrayList<>();
    private Api(){
    }
    private static Api mApi;
    public static Api getInstance(){
        if (mApi==null){
            mApi=new Api();
        }
        return mApi;
    }





    /**
     * 查询学生成绩
     */
    private void getGrade(String cookie) {
        Map<String,String>heander=new HashMap<>();
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
        },null,heander,StreamPool.GB);


    }
    /**
     * 解析成绩 html
     * @param html
     */
    private void parserGrade(String html){
        ArrayList<String> data=new ArrayList<>();
        Document document=Jsoup.parse(html);
//        Elements elements = document.getElementsByClass("cell dxgv");
//        for (int i=0;i<100;i++){
//            Element element = document.getElementById("grid_DXDataRow" + i);
//            String text = element.getElementsByClass("cell dxgv").text();
//            initInfo(text);
//        }
        Element element = document.getElementById("grid_DXDataRow" + 0);
        String text = element.getElementsByClass("cell dxgv").text();
        Log.i("myTag",text);
        //initInfo(text);
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
}
