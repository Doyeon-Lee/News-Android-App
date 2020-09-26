package com.example.myapplication;

import android.net.Uri;

import java.io.Serializable;

//직렬화; 데이터가 많고 형태가 이상할 때 하나의 데이터 구조로 바꿔서 넘겨주고 받기 위해 사용.
//Intent로 전달할 때도 사용되기도 한다
public class NewsData implements Serializable {
    private String title;
    private String urlToImage;
    private String content;
    private String newsUrl;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }
    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getNewsUrl(){return newsUrl;}
    public void setNewsUrl(String newsUrl) { this.newsUrl = newsUrl; }
}
