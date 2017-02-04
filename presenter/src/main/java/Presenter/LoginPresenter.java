package Presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import NetWork.OkHttpManager;
import Result.StringCallBack;
import Utils.StreamPool;
import Utils.UrlConfig;

/**
 * Created by duchaoqiang on 2017/1/5.
 */
public class LoginPresenter extends  BasePresenter  {
    public static Bitmap barcodeBitmap;
    public static String myCode=""; //文字验证码
    private String VIEWSTATE = "";
    private String button1 = "";
    private String EVENTVALIDATION = "";
    private String cobRole_VI = "bdfeb86e-3c29-4696-a19d-6c428850cea3";
    private String cobRole_DDDWS = "0:0:-1:0:0:0:0:0:";
    private String cobRole$DDD$L = "bdfeb86e-3c29-4696-a19d-6c428850cea3";
    private String userId = "1308020917";
    private String password = "xdy1994";
    private StringBuilder sbCookie;
    public LoginPresenter(MVPView mvpView) {
        super(mvpView);
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mvpView.hideWaitingDialog();
            mvpView.initialValue(msg.obj,myCode);
        }
    };
    @Override
    public void initWithValue(final Object... data) {
        mvpView.showWaitingDialog();
        new  Thread(() -> {
            sbCookie=getCode();
        }).start();
    }

    @Override
    public void submit(Object... args) {
      getAction(args[0].toString(),args[1].toString(),sbCookie.toString());
    }
    /**
     * 拿到网页动态的值
     */
    public void getAction(String username,String password,String cookie) {
        mvpView.showWaitingDialog();
        OkHttpManager manager = OkHttpManager.getOkhttpManager();
        manager.get(UrlConfig.ActionUrl,new StringCallBack<String>(){
            @Override
            public void onSuccess(Response response, String s) {
                super.onSuccess(response, s);
                Document parse = Jsoup.parse(s);
                if (parse==null){
                    return;
                }
                String attr = parse.getElementById("__VIEWSTATE").attr("value");
                if (!TextUtils.isEmpty(attr)){
                    VIEWSTATE += attr;
                }
                String value = parse.getElementById("__EVENTVALIDATION").attr("value");
                if (!TextUtils.isEmpty(value)){
                    EVENTVALIDATION += value;
                }
                Log.i("myTag",VIEWSTATE);
                login(username,password,cookie);
            }
        }, StreamPool.UTF_8);

    }
    private void login(String username,String password,String cookie){
        Map<String,String> params=new HashMap<>();
        Map<String,String> header=new HashMap<>();
        params.put("User_ID", username);
        params.put("User_Pass", password);
        params.put("Button1", button1);
        params.put("__VIEWSTATE", VIEWSTATE);
        params.put("cobRole_VI", cobRole_VI);
        params.put("txtVolidate", myCode);
        params.put("__EVENTVALIDATION", EVENTVALIDATION);
        params.put("cobRole_DDDWS", cobRole_DDDWS);
        params.put("cobRole$DDD$L", cobRole$DDD$L);
        header.put("Cookie", cookie);
        header.put("Connection", "keep-alive");
        header.put("Host", "202.199.248.12");
        header.put("Origin", "http://202.199.248.12");
        header.put("Referer", "http://202.199.248.12/tmweb/login.aspx");
        OkHttpManager.getOkhttpManager().post(UrlConfig.loginUrl,new StringCallBack<String>(){
            @Override
            public void onSuccess(Response response, String s) {
                super.onSuccess(response, s);
                Log.i("myTag",response.request().urlString());
                if (response.request().urlString().equals(UrlConfig.homeUrl)){ //账号密码正确的情况下
                    mvpView.toast("登录成功");
                    mvpView.hideWaitingDialog();
                    mvpView.goActivity(cookie);
                }else{
                    //账号密码错误
                    mvpView.toast("账号密码错误");
                    mvpView.hideWaitingDialog();
                }
            }
        },params,header,StreamPool.UTF_8);
    }

    /**
     * 显示验证码 和 拿到cookie
     *
     * @param
     * @return
     */
    public StringBuilder getCode() {
        String cookie = null;
        String cookieVal = "";
        String key = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(UrlConfig.barcodeUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                //拿到验证码图片的输入流
                InputStream inputStream = conn.getInputStream();
                //将验证码发送的主线程
                barcodeBitmap = BitmapFactory.decodeStream(inputStream);
                Message message=Message.obtain();
                message.obj=barcodeBitmap;
                handler.sendMessage(message);
                String cookieskey = "Set-Cookie";
                for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
                    if (key.contains(cookieskey)) {
                        cookieVal = conn.getHeaderField(i);
                        cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
                        Log.i("myTag", cookieVal);
                        if (cookieVal.contains("CheckCode=")) {
                            myCode += cookieVal.substring(10);
                            // myCode=cookieVal.replace(("CheckCode="),"").trim();
                            Log.i("myTag", myCode);
                        }
                        sb.append(cookieVal + "; ");
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sb.append("TZ=0" + ";");
        return sb;
    }

}
