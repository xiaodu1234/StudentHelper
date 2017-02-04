package com.example.rxjavademo;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {
    private final String TAG="syso:";
    private Subscriber<String> subscriber;
    private TextView showTxt;
    private Flowable<String> flowable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showTxt= (TextView) findViewById(R.id.show);
//        createObserverable();
//        createSubscriber();
//        //将发送器与接收器连接在一起
//        flowable.subscribe(subscriber);

//        //直接发送
//        Flowable<String>flowable1=Flowable.just("hello","RXjava 2","ok");
//        //消费者
//        Consumer consumer = new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println(s);
//            }
//        };

        //testRxJavaTwo();
       // testRxJavaThree();
       // createObserverable();
        getData();
    }


    private void testRxJavaOne(){
        Flowable.just("hello","RXJAVA2")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return s+"------ xiaodu";
                    }
                })
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return s.hashCode();
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer s) throws Exception {
                        log("accept:"+s);
                    }
                });
    }

    private void testRxJavaTwo(){
        List<Integer>list=new ArrayList<>();
        list.add(0);
        list.add(3);
        list.add(1);
        list.add(2);
        Flowable.just(list)
                .flatMap(new Function<List<Integer>, Publisher<Integer>>() {
                    @Override
                    public Publisher<Integer> apply(List<Integer> integers) throws Exception {
                        return Flowable.fromIterable(integers);
                    }
                })
               .subscribe(new Consumer<Integer>() {
                   @Override
                   public void accept(Integer integer) throws Exception {
                     log("accept:"+integer);
                   }
               });

    }

    private void testRxJavaThree(){
        Flowable.just(1,3,2,10,20,5)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer.intValue()>1;
                    }
                })
                .doOnNext(new Consumer<Integer>() { //在接收数据之前做的事情 可以保存日志等
                    @Override
                    public void accept(Integer integer) throws Exception {
                        log("保存："+integer);
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        log("accept:"+integer);
                    }
                });
    }




    //创建观察者 2.0 一般不这么用了
    private void createObserver() {

        Observer<String> observer=new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                log("next:"+s);
            }

            @Override
            public void onError(Throwable e) {
                log("error:"+e);
            }

            @Override
            public void onComplete() {
                log("complete:");
            }
        };
    }

    //这也是创建观察者， 其中Subscriber 是 Observer 的抽象子类
    private void createSubscriber(){
        subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
              log("请求参数");
                //不限制请求数量，不写这条代码，则 onNext不执行和onComplete 不执行
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
            //s 则是被观察者发送来的值
                log("onNext:"+s);
            }

            @Override
            public void onError(Throwable t) {
              log("error");
            }

            @Override
            public void onComplete() {
                log("complete:");
            }
        };
    }
    //创建被观察者
    private void createObserverable(){
    Flowable.create(new FlowableOnSubscribe<String>() {

        @Override
        public void subscribe(FlowableEmitter<String> e) throws Exception {
            e.onNext("将在10秒钟后变化：");
            for (int i=0;i<10;i++){
                SystemClock.sleep(10000);
                e.onNext(i+"");
            }
            e.onComplete();
        }
    }, BackpressureStrategy.BUFFER)
            .subscribeOn(Schedulers.io())//在 io 子线程中执行 发送数据
            .observeOn(AndroidSchedulers.mainThread())//在主线程中接收数据（订阅者在主线程中执行）
            .subscribe(new Subscriber<String>() {
                @Override
                public void onSubscribe(Subscription s) {

                    s.request(Long.MAX_VALUE);
                }

                @Override
                public void onNext(String s) {

                    showTxt.setText(s);
                }

                @Override
                public void onError(Throwable t) {

                    log("error"+t);
                }

                @Override
                public void onComplete() {

                    log("complete");
                }
            });
    }

    private void log(String message){
        Log.i(TAG,message);
    }

    //定义接口
    public interface BaiDuService {
        @GET("/")
        Flowable<ResponseBody>getText();
    }
    //使用Retrofit 与 RxJava 结合在一起

    //拿到百度的数据

    private void getData(){


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://www.baidu.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加RxJava2的适配器支持
                .build();
        BaiDuService service=retrofit.create(BaiDuService.class);
        service.getText()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            showTxt.setText(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}

