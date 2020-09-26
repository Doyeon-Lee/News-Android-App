package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

public class ViewNewsActivity extends AppCompatActivity {

    TextView TextView_title, TextView_content, TextView_newsLink;
    SimpleDraweeView SimpleDraweeView_title;

    ImageView ImageView_close;
    WebView webView;
    long lastTimeBackPressed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);

        //WebView를 사용하여 앱 내부에서 기사를 보여주는 방법
        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();

        Intent getNewsData = getIntent();
        NewsData newsData = (NewsData) getNewsData.getSerializableExtra("clickNewsData");
        String url = newsData.getNewsUrl();

        webView.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true); //로드할 페이지가 화면보다 크다면 크기 맞춤
        webSettings.setSupportZoom(true); //줌 가능하도록
        webSettings.setDomStorageEnabled(true); //로컬 저장소에 허용할지 여부
        //webSettings 기타 속성들
        /*
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(false); //새 창 띄우기 허용 여부?
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false); //자바스크립트 새창 띄우기(멀티뷰) 허용 여부?
        webSettings.setLoadWithOverviewMode(true); //메타태그 허용 여부
        webSettings.setUseWideViewPort(true); //화면 사이즈 맞추기 허용 여부?
        webSettings.setSupportZoom(true); //화면 줌 허용 여부
        webSettings.setBuiltInZoomControls(true); //화면 확대 축소 허용 여부
        //화면 줌과 확대,축소의 차이는?
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //컨텐츠 사이즈 맞추기
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //브라우저 캐시 허용 여부
        webSettings.setDomStorageEnabled(true); //로컬 저장소 허용 여부
         */
        webView.loadUrl(url);

        //아래는 webView가 아니라, url정보를 직렬화로 받아 intent를 사용해 외부 크롬으로 연결해주는 방법
        /*
        TextView_title = findViewById(R.id.TextView_title);
        TextView_content = findViewById(R.id.TextView_content);
        SimpleDraweeView_title = findViewById(R.id.SimpleDraweeView_title);
        TextView_newsLink = findViewById(R.id.TextView_newsLink);

        NewsData newsData = (NewsData) getNewsData.getSerializableExtra("clickNewsData");
        TextView_title.setText(newsData.getTitle());
        TextView_content.setText(newsData.getContent());
        TextView_newsLink.setText(newsData.getNewsUrl());

        String imageUrl = newsData.getUrlToImage();
        if(imageUrl.equals("null"))
            imageUrl =  "https://upload.wikimedia.org/wikipedia/commons/0/0a/No-image-available.png";
        Uri uri  = Uri.parse(imageUrl);
        SimpleDraweeView_title.setImageURI(uri);

        final String NewsUrl = newsData.getNewsUrl();
        TextView_newsLink.setClickable(true);
        TextView_newsLink.setEnabled(true);
        TextView_newsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com"));
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(NewsUrl));
                startActivity(i);
            }
        });

         */

        //X 아이콘을 클릭하면 바로 웹뷰가 종료되도록 한다
        ImageView_close = findViewById(R.id.ImageView_close);
        ImageView_close.setClickable(true);
        ImageView_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        String endString = "'뒤로' 버튼을 한 번 더 누르면 앱을 종료합니다.";

        if(webView.canGoBack()) webView.goBack();
        else {
            //2초 이내에 두 번 눌렀다면
            if (System.currentTimeMillis() - lastTimeBackPressed < 2000)
                finish();
            //처음 누른 거라면
            else {
                Toast.makeText(this, endString, Toast.LENGTH_SHORT).show();
                lastTimeBackPressed = System.currentTimeMillis();
            }
        }
    }
}