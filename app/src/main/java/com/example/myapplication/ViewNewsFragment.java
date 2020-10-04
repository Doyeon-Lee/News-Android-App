package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class ViewNewsFragment extends Fragment {
    ImageView ImageView_close;
    WebView webView;

    private static final String NEWSURL = "clickNewsData";
    private static String url = "https://www.naver.com";

    public static ViewNewsFragment newInstance(String args) {
        ViewNewsFragment fragment = new ViewNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NEWSURL, args);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(NEWSURL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_view_news, container, false);

        //WebView를 사용하여 앱 내부에서 기사를 보여주는 방법
        webView = view.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webView.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true); //로드할 페이지가 화면보다 크다면 크기 맞춤
        webSettings.setSupportZoom(true); //줌 가능하도록
        webSettings.setDomStorageEnabled(true); //로컬 저장소에 허용할지 여부

        //false setting이 많은 경우 webView에서 동영상 재생이 원활하지 않을 수 있다
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);
        webView.loadUrl(url);

        Log.d("CURRENT URL = ", url);

        //X 아이콘을 클릭하면 바로 웹뷰가 종료되도록 한다
        ImageView_close = view.findViewById(R.id.ImageView_close);
        ImageView_close.setClickable(true);
        ImageView_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().remove(ViewNewsFragment.this).commit();
                fm.popBackStack();
            }
        });

        return view;
    }

    public void onBackPressed() {
        if(webView.canGoBack())
            webView.goBack();
    }

}