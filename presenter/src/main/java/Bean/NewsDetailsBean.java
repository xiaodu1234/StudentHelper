package Bean;

/**
 * Created by duchaoqiang on 2017/1/5.
 * 新闻详情
 */
public class NewsDetailsBean {
    private String title;
    private String resource;//来源和时间
    private String bitmapUrl;//图片链接
    private String content;//内容

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getBitmapUrl() {
        return bitmapUrl;
    }

    public void setBitmapUrl(String bitmapUrl) {
        this.bitmapUrl = bitmapUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
