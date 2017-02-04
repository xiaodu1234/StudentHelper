package Utils;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Bean.NewsDetailsBean;
import Bean.NewsListBean;

/**
 * Created by duchaoqiang on 2017/1/5.
 */
public class NewsHttp {


    //得到新闻列表
    public static void getNewsList(String Currentpage) {
        ArrayList<NewsListBean> allNews = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(UrlConfig.newsUrl+Currentpage).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String html = StreamPool.inToStringByByte(response.body().byteStream(),null);
                Document document = Jsoup.parse(html);
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
            }
           getNewsDetail( allNews.get(1).getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //得到新闻详情
    public static NewsDetailsBean getNewsDetail(String url) {
        NewsDetailsBean bean=new NewsDetailsBean();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                BufferedReader buffer=new BufferedReader(new InputStreamReader(response.body().byteStream(),"utf-8"));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = buffer.readLine()) != null) {
                    content.append(line);
                }
                Document document = Jsoup.parse(content.toString());
                bean.setContent(document.getElementsByClass("tim4").get(0).text());
                bean.setTitle(document.getElementsByClass("tim1").text());
                bean.setResource(document.getElementsByClass("tim3").text());
                bean.setBitmapUrl(UrlConfig.newsDetailUrl+document.getElementsByClass("tim4").get(0).getElementsByTag("img").attr("src"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("myTag",bean.getBitmapUrl());
        return bean;

    }
}
