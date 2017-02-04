package Presenter;

import android.util.Log;

import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import Bean.NewsDetailsBean;
import Bean.NewsListBean;
import NetWork.OkHttpManager;
import Result.StringCallBack;
import Utils.UrlConfig;

/**
 * Created by duchaoqiang on 2017/1/10.
 */
public class NewsListPresenter extends BasePresenter {
    public NewsListPresenter(MVPView mvpView) {
        super(mvpView);
    }

    @Override
    public void init() {
        mvpView.showWaitingDialog();
        getNewsList("0");
    }

    //得到新闻列表
    public  ArrayList<NewsListBean> getNewsList(String Currentpage) {
        ArrayList<NewsListBean> allNews = new ArrayList<>();
        OkHttpManager.getOkhttpManager().get(UrlConfig.newsUrl+Currentpage,new StringCallBack<String>(){
            @Override
            public void onSuccess(Response response, String s) {
                super.onSuccess(response, s);
                Document document = Jsoup.parse(s);
                Elements neww2d_1_1 = document.getElementsByClass("neww2d_1_1");
                Elements neww2d_1_2 = document.getElementsByClass("neww2d_1_2");
                Elements neww2d_1_3 = document.getElementsByClass("neww2d_1_3");
                for (int i = 0; i < neww2d_1_1.size(); i++) {
                    NewsListBean bean = new NewsListBean();
                    bean.setDate(neww2d_1_2.get(i).text());
                    bean.setUrl(UrlConfig.newsDetailUrl+neww2d_1_1.get(i).getElementsByTag("a").attr("href"));
                    bean.setTitle(neww2d_1_1.get(i).text());
                    bean.setContent(neww2d_1_3.get(i).text());
                    allNews.add(bean);
                }
                for (int i=0;i<allNews.size();i++){
                    getNewsDetail(allNews.get(i));
                }
                mvpView.setListData(allNews);

            }
        },null);
        return allNews;
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(UrlConfig.newsUrl+Currentpage).build();
//        try {
//            Response response = client.newCall(request).execute();
//            if (response.isSuccessful()) {
//                String html = StreamPool.inToStringByByte(response.body().byteStream(),null);
//                Document document = Jsoup.parse(html);
//                Elements neww2d_1_1 = document.getElementsByClass("neww2d_1_1");
//                Elements neww2d_1_2 = document.getElementsByClass("neww2d_1_2");
//                Elements neww2d_1_3 = document.getElementsByClass("neww2d_1_3");
//                for (int i = 0; i < neww2d_1_1.size(); i++) {
//                    NewsListBean bean = new NewsListBean();
//                    bean.setDate(neww2d_1_2.get(i).text());
//                    bean.setUrl(UrlConfig.newsDetailUrl+neww2d_1_1.get(i).getElementsByTag("a").attr("href"));
//                    bean.setTitle(neww2d_1_1.get(i).text());
//                    bean.setContent(neww2d_1_3.get(i).text());
//                    allNews.add(bean);
//                }
//            }
//            //getNewsDetail( allNews.get(1).getUrl());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return allNews;
    }

    //得到新闻详情
    public  NewsDetailsBean getNewsDetail(NewsListBean listBean) {
        NewsDetailsBean bean=new NewsDetailsBean();
        OkHttpManager.getOkhttpManager().get(listBean.getUrl(),new StringCallBack<String>(){
            @Override
            public void onSuccess(Response response, String s) {
                super.onSuccess(response, s);
                Document document = Jsoup.parse(s);
                bean.setContent(document.getElementsByClass("tim4").get(0).text());
                bean.setTitle(document.getElementsByClass("tim1").text());
                bean.setResource(document.getElementsByClass("tim3").text());
                bean.setBitmapUrl(UrlConfig.newsDetailUrl+document.getElementsByClass("tim4").get(0).getElementsByTag("img").attr("src"));
                Log.i("myTag",bean.getBitmapUrl());
                listBean.setBitmapUrl(bean.getBitmapUrl());
                mvpView.hideWaitingDialog();
                mvpView.refreshList(null);
            }
        },null);

        return bean;
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(url).build();
//        Response response = null;
//        try {
//            response = client.newCall(request).execute();
//            if (response.isSuccessful()) {
//                BufferedReader buffer=new BufferedReader(new InputStreamReader(response.body().byteStream(),"utf-8"));
//                StringBuilder content = new StringBuilder();
//                String line;
//                while ((line = buffer.readLine()) != null) {
//                    content.append(line);
//                }
//                Document document = Jsoup.parse(content.toString());
//                bean.setContent(document.getElementsByClass("tim4").get(0).text());
//                bean.setTitle(document.getElementsByClass("tim1").text());
//                bean.setResource(document.getElementsByClass("tim3").text());
//                bean.setBitmapUrl(UrlConfig.newsDetailUrl+document.getElementsByClass("tim4").get(0).getElementsByTag("img").attr("src"));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Log.i("myTag",bean.getBitmapUrl());
//        return bean;

    }

}
