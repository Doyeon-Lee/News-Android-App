package com.example.myapplication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

public class NavigationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout DrawerLayout_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);







        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        //This will set out Toolbar as the Actionbar and this will for example place the
        //title of our activity in the toolbar or place the options menu there if we create one
        //툴바를 액티비티의 액션바로 사용할 수 있게 한다. 액티비티에서 툴바가 일반적인 뷰(View) 위젯으로
        //다루어진다는 것은 액티비티에 포함된 액션바에 비해 몇 가지 장점을 가지는데, 스타일 변경,
        //위치 조절, 애니메이션, 그리고 표시 형태 제어 등을 좀 더 손 쉽게 수행할 수 있다는 것이다.
        //그리고 액티비티의 고정된 위치에 단 하나만 표시할 수 있는 액션바와 달리, 툴바는 각각 분리된
        //영역에 여러 개를 사용할 수 있다.

        DrawerLayout_nav = findViewById(R.id.DrawerLayout_nav);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, DrawerLayout_nav, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        DrawerLayout_nav.addDrawerListener(toggle);
        //화면을 슬라이드 함에 따라 햄버거가 같이 회전하도록 한다
        toggle.syncState(); //햄버거를 화면에 보이게 해 준다
        //This will take care of rotating the hamburger icon together with the drawer itself

    }

    @Override
    public void onBackPressed() {
        if(DrawerLayout_nav.isDrawerOpen(GravityCompat.START)){
            DrawerLayout_nav.closeDrawer(GravityCompat.START);
        }else super.onBackPressed();
    }
}