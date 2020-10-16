package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.Vector;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    public static Context mcontext;

    private TextInputEditText TextInputEditText_email, TextInputEditText_password;
    private TextView TextView_signUp, TextView_login;
    private RelativeLayout RelativeLayout_login;

    private static SharedPreferences mPreferences;
    private static String SharedPrefFile = "UserData";
    public static User user;

    private String input_email = "";
    private String input_password = "";
    long lastTimeBackPressed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext = this;

        TextInputEditText_email = findViewById(R.id.TextInputEditText_email);
        TextInputEditText_password = findViewById(R.id.TextInputEditText_password);
        RelativeLayout_login = findViewById(R.id.RelativeLayout_login);
        TextView_signUp = findViewById(R.id.TextView_signUp);
        TextView_login = findViewById(R.id.TextView_login);
        RelativeLayout_login.setClickable(false);

        //직접 dp를 px로 변환하는 식도 가능하지만, dimens.xml에 원하는 dp값을 선언하여 아래와 같이 사용한다
        int val = this.getResources().getDimensionPixelSize(R.dimen.status_bar_padding);

        // Status Bar 높이만큼 Padding 부여   
        // 코드에서 setPadding은 px단위만 가능하기 때문에 xml의 dp와는 다르다 
        TextView_login.setPadding(0, getStatusBarHeight()+val, 0, val);

        loadData();

        user = new User();

        RelativeLayout_login.setClickable(true);
        RelativeLayout_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_email = TextInputEditText_email.getText().toString();
                input_password = TextInputEditText_password.getText().toString();

                if (validation()) {
                    Intent loginSuccess = new Intent(MainActivity.this, NavigationActivity.class);
                    startActivity(loginSuccess);
                } else {
                    String loginFail = "The password is incorrect or the email doesn't exist.";
                    Toast toast = Toast.makeText(MainActivity.this, loginFail, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        TextView_signUp.setClickable(true);
        TextView_signUp.setEnabled(true);
        TextView_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertSignUp();
            }
        });
    }

    public boolean validation() {
        try {
            if (input_email.equals(user.getEmail()) && input_password.equals(user.getPassword()))
                return true;
            else return false;
        }catch (NullPointerException e){
            e.printStackTrace(); //오류 출력(방법은 여러가지)
            Toast.makeText(mcontext, "NPE Error", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void alertSignUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Sign Up");
        builder.setMessage("Enter your email and password");

        //alert는 하나의 뷰만을 담을 수 있다. 따라서 두 개의 edittext를 표시하려면
        //별도의 view에 포함시켜야 한다. 아래는 xml을 사용하지 않고 linearLayout을 만드는 방법이다.
        //레이아웃 생성
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        //레이아웃 파라미터 생성
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        //텍스트 뷰를 생성한 후에 파라미터 설정
        final EditText EditText_email = new EditText(this);
        final EditText EditText_password = new EditText(this);

        EditText_email.setLayoutParams(layoutParams);
        EditText_password.setLayoutParams(layoutParams);

        EditText_email.setHint("email");
        EditText_password.setHint("password");

        //최대 한 줄 설정
        EditText_email.setSingleLine(true);
        EditText_password.setSingleLine(true);

        //생성한 텍스트뷰를 LinearLayout에 추가
        layout.addView(EditText_email);
        layout.addView(EditText_password);

        //비밀번호는 디폴트로 보이지 않게 한다
        EditText_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //비밀번호의 visible을 변경하는 체크박스 추가
        final CheckBox checkBox = new CheckBox(this);
        checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        checkBox.setText("Show password");

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked())
                    EditText_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else
                    EditText_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                EditText_password.setSelection(EditText_password.getText().length());
            }
        });

        layout.addView(checkBox);

        final TextView warning = new TextView(this);
        warning.setText(R.string.password_qualification);
        warning.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        warning.setVisibility(View.INVISIBLE);
        layout.addView(warning);

        //생성한 LinearLayout을 alert로 표시
        builder.setView(layout);
        builder.setPositiveButton("Sign Up", null);
        final AlertDialog alert = builder.create();
        alert.show();

        Button signUpButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_email = EditText_email.getText().toString();
                input_password = EditText_password.getText().toString();
                if (isEmail(input_email) && isPassword(input_password)) {
                    user.setEmail(input_email);
                    user.setPassword(input_password);
                    saveData();
                    alert.dismiss();
                } else
                    warning.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onBackPressed() {
        String endString = "'뒤로' 버튼을 한 번 더 누르면 앱을 종료합니다.";
        if (System.currentTimeMillis() - lastTimeBackPressed < 2000)
            finish();
            //super.onBackPressed();
        else {
            Toast.makeText(this, endString, Toast.LENGTH_SHORT).show();
            lastTimeBackPressed = System.currentTimeMillis();
        }
    }

    public static void saveData() {
        //- SharedPreferences 객체에 edit()라는 메소드로 Editor 생성 --> put 메소드로 데이터 입력
        //- apply()메소드로 적용
        mPreferences = mcontext.getSharedPreferences(SharedPrefFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        //데이터입력
        Gson gson = new Gson();
        String json = gson.toJson(user);
        preferencesEditor.putString("MyObject", json);

        //적용
        preferencesEditor.apply();
    }

    public void loadData() {
        //Shared Preferences 객체 선언
        mPreferences = getSharedPreferences(SharedPrefFile, MODE_PRIVATE);

        //Activity가 재생성 될때 onCreate()에서 SharedPreferences 객체에서 데이터 불러오기
        Gson gson = new Gson();
        String json = mPreferences.getString("MyObject", "");
        if(!json.equals("null")) user = gson.fromJson(json, User.class);
    }

    public boolean isEmail(String str) {
        return Pattern.matches("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$", str);
    }

    public static boolean isPassword(String str) {
        return Pattern.matches("^[a-z0-9A-Z!@#$%^&*]*$", str) && str.length() >= 6;
    }

    //status bar의 높이 계산; 기기별로 높이가 다를 수 있으므로
    public int getStatusBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);

        return result;
    }
}