package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.Arrays;

public class CategoryActivity extends AppCompatActivity {
    LinearLayout LinearLayout_listView;
    ListView ListView_category;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        final ArrayList<String> categoryArray = new ArrayList<>(
                Arrays.asList("Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology")
        );

        LinearLayout_listView = findViewById(R.id.LinearLayout_listView);
        ListView_category = findViewById(R.id.ListView_category);
        ListViewAdapter listViewAdapter = new ListViewAdapter(categoryArray);
        Fresco.initialize(this);

        ListView_category.setAdapter(listViewAdapter);
        ListView_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String category = categoryArray.get(position);
                Intent intent = new Intent(CategoryActivity.this, NewsActivity.class);
                intent.putExtra("category", category);

                startActivity(intent);
            }
        });
    }
}