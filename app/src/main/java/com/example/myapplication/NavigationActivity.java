package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //괄호 안에 new~로 생성할 수도 있지만 가독성을 위해 this 액티비티를 인수로 전달한다.
        //그러기 위해서는 클래스에ㅔ implement Nav~Listener가 필요한데, 두 가지 중 buttom이
        //아니라 navigation으로 끝나는 것을 골라야 한다. 그리고 이를 implement하기 위해서는
        //ctrl+I로 아래의 함수를 선택해 줘야 오류가 없어진다.

        //화면 방향 전환 등이 일어났을 때 보던 프래그먼트를 그대로 보여주기 위해서
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    CategoryFragment.newInstance()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    //here we pass the menu item which is the menu that was selected
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CategoryFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_bookmark:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BookmarkFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_language:
                break;
            case R.id.nav_password:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PasswordFragment()).addToBackStack(null).commit();
                break;
        }

        //getSupportFragmentManager().beginTransaction().addToBackStack(null).commit();
        DrawerLayout_nav.closeDrawer(GravityCompat.START);
        return true;
        //return false means that no item was selected even though the action was triggered
    }

    @Override
    public void onBackPressed() {
        if (DrawerLayout_nav.isDrawerOpen(GravityCompat.START)) {
            DrawerLayout_nav.closeDrawer(GravityCompat.START);
        }
        else{
            /*
            FragmentManager fragmentManager = getSupportFragmentManager();
            if(fragmentManager.getBackStackEntryCount() > 0)
                fragmentManager.popBackStack();
             */
            super.onBackPressed();
        }
    }
}