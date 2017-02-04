package NetWork;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;

import Utils.StreamPool;

/**
 * Created by duchaoqiang on 2017/1/6.
 * OkHttp 的管理工具
 */
public class OkHttpManager {

    enum HttpMethodType{
        GET,POST;
    }
    private OkHttpClient mclient;
    private Handler mHander; //用来往主线程发送消息
    //拿到 okhttpManager 的管理工具
    public static OkHttpManager mOkhttpManager;
    public static synchronized OkHttpManager getOkhttpManager(){
        if (mOkhttpManager==null){
            mOkhttpManager=new OkHttpManager();
        }
        return mOkhttpManager;
    }
    private OkHttpManager(){
        mclient=new OkHttpClient();
        mHander=new Handler(Looper.getMainLooper());
    }

    /***
     * 封装一个 request方法 无论post 还是get 都能用到
     * @param request
     * @param callback
     * encode: 编码方式
     */
    public void request(Request request,final BaseCallback callback,final String encode){
        //在请求之前做的事情比如弹出对话框
        callback.onRequestBefore();
        mclient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                  //失败时调用
                  callbackFailure(request,callback,e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                   if (response.isSuccessful()){
                       String data="";
                       if (!TextUtils.isEmpty(encode)){ //说明返回 网页的字符串
                           try {
                               data=StreamPool.inToStringByByte(response.body().byteStream(),encode);
                           } catch (Exception e) {
                               e.printStackTrace();
                           }
                       }else{  //普通的字符串
                               data=response.body().string();
                       }
                       if (callback.mType==String.class){ //如果传入的回调接口是String 类型的话
                           callbackSuccess(response,data,callback);
                       }else{ //如果是其他类型的话 用Gson 去解析
//                           try {
//                               Object o = mGson.fromJson(resString, callback.mType);
//                               callbackSuccess(response, o, callback);
//                           } catch (JsonParseException e) {
//                               e.printStackTrace();
//                               callbackError(response, callback, e);
//                           }
                       }
                   }else{ //链接失败
                       callbackError(response,callback,null);
                   }
            }
        });
    }
    /**
     * 在主线程中执行的回调
     *
     * @param response
     * @param callback
     */
    private void callbackSuccess(final Response response, final Object o, final BaseCallback callback) {
        mHander.post(() -> callback.onSuccess(response, o));
    }
    /**
     * 在主线程中执行的回调
     * @param response
     * @param callback
     * @param e
     */
    private void callbackError(final Response response, final BaseCallback callback, final Exception e) {
        mHander.post(() -> callback.onError(response, response.code(), e));
    }
    /**
     * 在主线程中执行的回调
     * @param request
     * @param callback
     * @param e
     */
    private void callbackFailure(final Request request, final BaseCallback callback, final Exception e) {
        mHander.post(() -> callback.onFailure(request, e));
    }
    /************************************************************************/
    /**对外公开的方法
    /*************************************************************************/


    /**
     * 对外公开的get 方法
     * @param url
     * @param callback
     * encode 编码格式
     */
    public void get(String url,BaseCallback callback,String encode){
        Request request=buildRequest(url,null,null,HttpMethodType.GET);
        request(request,callback,encode);
    }
    public void post(String url,BaseCallback callback,Map<String,String> params,Map<String,String> header,String encode){
        Request request = buildRequest(url, params, header,HttpMethodType.POST);
        request(request, callback,encode);
    }
    
    /**
     * 构建request 对象
     */
    private Request buildRequest(String url, Map<String,String> params,Map<String,String> hearder,HttpMethodType type){
        Request.Builder builder=new Request.Builder();
        builder.url(url);
        if (type==HttpMethodType.GET){
            builder.get();
        }else if (type==HttpMethodType.POST){
            buildHeader(builder,hearder);
            builder.post(buildRequestBody(params));
        }
        return builder.build();
    }

    /**
     *构建头部
     */
    private void buildHeader(Request.Builder builder,Map<String,String> hearder){
        if (hearder!=null){
            for (Map.Entry<String,String> enty:hearder.entrySet()){
                builder.addHeader(enty.getKey(),enty.getValue());
            }
        }

    }
    /**
     * 构建不同的请求体
     * @param params
     */
    private RequestBody buildRequestBody(Map<String,String> params){
        FormEncodingBuilder builder=new FormEncodingBuilder();
        if (params!=null){
            for (Map.Entry<String,String> enty:
                 params.entrySet()) {
             builder.add(enty.getKey(),enty.getValue());
            }
        }
        return builder.build();
    }

}
