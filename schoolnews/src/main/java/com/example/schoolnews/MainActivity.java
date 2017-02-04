//package com.example.schoolnews;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//
//import com.squareup.okhttp.FormEncodingBuilder;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.Response;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//
//import Bean.GradeBean;
//import Utils.NewsHttp;
//import Utils.StreamPool;
//
//public class MainActivity extends AppCompatActivity {
//
//    private String loginUrl = "http://202.199.248.12/tmweb/login.aspx";
//    private String code = "http://202.199.248.12/tmweb/VerifyCode.aspx?";
//    private String gradeUrl = "http://202.199.248.12/tmweb/Grade/SStudentGradeSelect.aspx";
//    private String userId = "1308020917";
//    private String password = "xdy1994";
//    private String VIEWSTATE = "";
//    private String txtVolidate = "";
//    private String button1 = "";
//    private String EVENTVALIDATION = "";
//    private String cobRole_VI = "bdfeb86e-3c29-4696-a19d-6c428850cea3";
//    private String cobRole_DDDWS = "0:0:-1:0:0:0:0:0:";
//    private String cobRole$DDD$L = "bdfeb86e-3c29-4696-a19d-6c428850cea3";
//    private StringBuilder cookie;
//    private ArrayList<GradeBean> allInfo=new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                getAction();
//                log();
//                NewsHttp.getNewsList("0");
//            }
//        }).start();
//    }
//
//    private void getHtml(String murl) {
//        String html = "";
//        try {
//            URL url = new URL(murl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setConnectTimeout(5000);
//            conn.setDoInput(true);
//            conn.setDoInput(true);
//            conn.connect();
//            if (conn.getResponseCode() == 200) {
//                InputStream in = conn.getInputStream();
//                html = StreamPool.inToStringByByte(in,null);
//                // Log.e("Main",html);
//                initData(html);
//                init(html);
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void initData(String html) {
//        Document doc = Jsoup.parse(html);
////        Elements elements = doc.select("div.menu");
////        Log.i("mytag",elements.select("a").attr("href"));
//        Elements me = doc.getElementsByClass("me");
//        for (Element e :
//                me) {
//
//            Element id = e.getElementById("jsddm");
//            id.getElementsByTag("a").attr("href");
//            Log.i("myTag", id.text() + "----------" + id.getElementsByTag("a").attr("href"));
//        }
//
//
//    }
//
//    private void init(String html) {
//        Document doc = Jsoup.parse(html);
//        Element news = doc.getElementsByClass("tnewsk").first();
//        Elements titleElements = news.getElementsByClass("tnew1");
//        Log.i("myTag", titleElements.get(0).getElementsByTag("a").attr("href"));
//    }
//
//    private void login() {
//        try {
//            URL url = new URL(loginUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            //设置参数
//            conn.setDoOutput(true);   //需要输出
//            conn.setDoInput(true);   //需要输入
//            conn.setUseCaches(false);  //不允许缓存
//            conn.setRequestMethod("POST");   //设置POST方式连接
//            //设置请求属性
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
//            conn.setRequestProperty("Charset", "UTF-8");
//            String params = "User_ID=" + URLEncoder.encode(userId, "UTF-8") + "&User_Pass=" + URLEncoder.encode(password + "UTF-8") + "&txtVolidate=" + URLEncoder.encode(txtVolidate, "UTF-8") + "&Button1=" + URLEncoder.encode(button1, "UTF-8") + "&__VIEWSTATE=" + URLEncoder.encode(VIEWSTATE, "UTF-8");
//            DataOutputStream bos = new DataOutputStream(conn.getOutputStream());
//            bos.writeBytes(params);
//            bos.flush();
//            if (conn.getResponseCode() == 302) {
//                Log.i("myTag", "登录成功");
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void log() {
//        //final StringBuilder cookie = getCookie();
//        cookie = getCode();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpClient httpClient = new OkHttpClient();
//                RequestBody formBody = new FormEncodingBuilder()
//                        .add("User_ID", userId)
//                        .add("User_Pass", password)
//                        .add("Button1", button1)
//                        .add("__VIEWSTATE", VIEWSTATE)
//                        .add("cobRole_VI", cobRole_VI)
//                        .add("txtVolidate", myCode)
//                        .add("__EVENTVALIDATION", EVENTVALIDATION)
//                        .add("cobRole_DDDWS", cobRole_DDDWS)
//                        .add("cobRole$DDD$L", cobRole$DDD$L)
//                        .build();
//                Request request = new Request.Builder()
//                        .url(loginUrl)
//                        .addHeader("Cookie", cookie.toString())
//                        .addHeader("Connection", "keep-alive")
//                        .addHeader("Host", "202.199.248.12")
//                        .addHeader("Origin", "http://202.199.248.12")
//                        .addHeader("Referer", "http://202.199.248.12/tmweb/login.aspx")
//                        .post(formBody)
//                        .build();
//                try {
//                    Response response = httpClient.newCall(request).execute( );
//                    if (response.code() == 302 || response.isSuccessful()) {
//                        Log.i("myTag", response.request().urlString());
//                        getGrade();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//    }
//
//    private void getBitmap() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String bittURL = "http://202.199.248.12/tmweb/VerifyCode.aspx?";
//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request.Builder().url(bittURL).build();
//                Response response = null;
//                try {
//                    response = client.newCall(request).execute();
//                    Log.i("myTag", response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
//
//
//    }
//
//    /**
//     * 拿到 验证码 和 cookie
//     *
//     * @param isCode
//     * @return
//     */
//    private String myCode = "";
//
//    private StringBuilder getCode() {
//        String cookie = null;
//        String cookieVal = "";
//        String key = null;
//        StringBuilder sb = new StringBuilder();
//        try {
//            URL url = new URL(code);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            if (conn.getResponseCode() == 200) {
//                String cookieskey = "Set-Cookie";
//                //String cookieskey="ookie";
//                for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
//                    if (key.contains(cookieskey)) {
//                        cookieVal = conn.getHeaderField(i);
//                        cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
//                        Log.i("myTag", cookieVal);
//                        if (cookieVal.contains("CheckCode=")) {
//                            myCode += cookieVal.substring(10);
//                            // myCode=cookieVal.replace(("CheckCode="),"").trim();
//                            Log.i("myTag", myCode);
//                        }
//                        sb.append(cookieVal + "; ");
//                    }
//                }
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        sb.append("TZ=0" + ";");
//        return sb;
//    }
//
//    //获取cookies
//    public StringBuilder getCookie() {
//        String cookie = null;
//        String cookieVal = "";
//        String key = null;
//        StringBuilder sb = new StringBuilder();
//        try {
//            URL url = new URL(loginUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            if (conn.getResponseCode() == 200) {
//                String cookieskey = "Set-Cookie";
//                //String cookieskey="ookie";
//                for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
//                    if (key.contains(cookieskey)) {
//                        cookieVal = conn.getHeaderField(i);
//                        cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
//                        Log.i("myTag", cookieVal);
//                        sb.append(cookieVal + ";");
//                    }
//                }
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return sb;
//    }
//
//    /**
//     * 拿到网页动态的值
//     */
//    private void getAction() {
//        try {
//            URL url = new URL(loginUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.connect();
//            InputStream in = conn.getInputStream();
//            String html = StreamPool.inToStringByByte(in,null);
//            Document parse = Jsoup.parse(html);
//            VIEWSTATE += parse.getElementById("__VIEWSTATE").attr("value");
//            EVENTVALIDATION += parse.getElementById("__EVENTVALIDATION").attr("value");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 查询学生成绩
//     */
//    private void getGrade() {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(gradeUrl)
//                .addHeader("Cookie", cookie.toString())
//                .addHeader("Connection", "keep-alive")
//                .addHeader("Host", "202.199.248.12")
//                .addHeader("Referer", "http://202.199.248.12/tmweb/ischool.aspx")
//                .build();
//        Response response = null;
//        try {
//            response = client.newCall(request).execute();
//            if (response.isSuccessful()) {
//                InputStream is = response.body().byteStream();
//                BufferedReader bs=new BufferedReader(new InputStreamReader(is,"gb2312"));
//                StringBuilder sb=new StringBuilder();
//                String line;
//                while ((line = bs.readLine()) != null) {
//                    sb.append(line);
//                }
//               parserGrade(sb.toString());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 解析成绩 html
//     * @param html
//     */
//    private void parserGrade(String html){
//        ArrayList<String> data=new ArrayList<>();
//        Document document=Jsoup.parse(html);
////        Elements elements = document.getElementsByClass("cell dxgv");
////        for (int i=0;i<100;i++){
////            Element element = document.getElementById("grid_DXDataRow" + i);
////            String text = element.getElementsByClass("cell dxgv").text();
////            initInfo(text);
////        }
//        Element element = document.getElementById("grid_DXDataRow" + 0);
//        String text = element.getElementsByClass("cell dxgv").text();
//        Log.i("myTag",text);
//        initInfo(text);
//        Log.i("myTag","allInfo.size="+allInfo.size());
//    }
//
//    /*
//     提取数据 去掉空格
//     */
//    private void initInfo(String text){
//            String[] split = text.split("\\s+");
//            initBean(split);
//    }
//    /*
//     初始化Bean
//     */
//    private void initBean(String[] data){
//        Log.i("myTag",data[0]);
//        GradeBean bean=new GradeBean();
//        int currentIndex=0;//当前下标为0
//        int size=data.length;
//        if (size==17){
//            bean.setSchoolYear(data[0]);
//            bean.setYearPoint(data[1]);
//            bean.setSchoolTerm(data[2]);
//            bean.setTermPoint(data[3]);
//            currentIndex=4;
//            Log.i("myTag","执行了 17");
//        }
//        if (size==15){
//            bean.setSchoolYear("");
//            bean.setYearPoint("");
//            bean.setSchoolTerm(data[0]);
//            bean.setTermPoint(data[1]);
//            currentIndex=2;
//            Log.i("myTag","执行了 15");
//        }
//        bean.setCouseNum(data[currentIndex]);
//        bean.setCourseName(data[currentIndex+1]);
//        bean.setTeachMethod(data[currentIndex+2]);
//        bean.setCourseType(data[currentIndex+3]);
//        bean.setCourseCategory(data[currentIndex+4]);
//        bean.setHours(data[currentIndex+5]);
//        bean.setCredit(data[currentIndex+6]);
//        bean.setScore(data[currentIndex+7]);
//        bean.setExamMethod(data[size-2]);
//        bean.setStatus(data[size-1]);
//        Log.i("myTag","执行了 10");
//        allInfo.add(bean);
//    }
//}
