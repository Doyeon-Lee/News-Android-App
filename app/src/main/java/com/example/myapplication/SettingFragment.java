package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends Fragment {
    private TextView TextView_category;
    private RecyclerView RecyclerView_news;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private RequestQueue queue;

    public static SettingFragment newInstance(){
        return new SettingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_news, container, false);

        Bundle bundle = getArguments();
        String category = "default";

        if(bundle != null) category = bundle.getString("category");

        TextView_category = v.findViewById(R.id.TextView_category);
        RecyclerView_news = v.findViewById(R.id.RecyclerView_news);

        String url = "http://newsapi.org/v2/top-headlines?country=us&category=";
        /*
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
         */
        TextView_category.setText(category);
        url = url + category + "&apiKey=1e5aa05c3cd24f4a9f83117abcd010fc";

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        RecyclerView_news.setHasFixedSize(true);
        RecyclerView_news.setAdapter(null);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView_news.setLayoutManager(layoutManager);

        //1. 화면이 로딩되면 뉴스 정보를 받아온다
        //2. 정보를 어뎁터에 넘겨준다
        //3. 받아온 정보를 세팅, 화면에 표시한다

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(getActivity());
        getNews(url);

        ((NavigationActivity)getActivity()).replaceFragment();
        return v;
    }

    public void getNews(String url){
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray jsonArray = jsonObj.getJSONArray("articles");
                            //final을 붙이면 그 내용을 더이상 바꿀 수 없으며, 아래의 clickListener와 같이
                            //익명 클래스 내부에서 지역 변수 등을 사용하기 위해서는 final 선언이 필요하다
                            final List<NewsData> news = new ArrayList<>();

                            for(int i = 0;i < jsonArray.length();i++){
                                NewsData newsData = new NewsData();
                                JSONObject tmp = jsonArray.getJSONObject(i);

                                newsData.setTitle(tmp.getString("title"));
                                String content = tmp.getString("content");

                                if(content.equals("null")) content = "No content";
                                newsData.setContent(content);
                                newsData.setNewsUrl( tmp.getString("url"));
                                newsData.setUrlToImage(tmp.getString("urlToImage"));

                                news.add(newsData);

                                mAdapter = new MyAdapter(news, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Object obj = v.getTag();
                                        if(obj != null){
                                            int position = (int)obj;
                                            Intent newsClickIntent = new Intent(getActivity(), ViewNewsActivity.class);
                                            newsClickIntent.putExtra("clickNewsData", news.get(position));
                                            startActivity(newsClickIntent);
                                        }
                                    }
                                });
                                RecyclerView_news.setAdapter(mAdapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}