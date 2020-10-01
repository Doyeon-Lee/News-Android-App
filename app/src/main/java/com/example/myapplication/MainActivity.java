package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    TextInputEditText TextInputEditText_email, TextInputEditText_password;
    TextView TextView_signUp;
    RelativeLayout RelativeLayout_login;

    public class User{
        String email = "abcd@gmail.com";
        String password = "1234";
        Vector<Object> v;
    }
    User user = new User();

    String input_email = "";
    String input_password = "";
    long lastTimeBackPressed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //리소스R의 id라는 녀석이 TextInputEditText_email이었어
        TextInputEditText_email = findViewById(R.id.TextInputEditText_email);
        TextInputEditText_password = findViewById(R.id.TextInputEditText_password);
        RelativeLayout_login = findViewById(R.id.RelativeLayout_login);
        TextView_signUp = findViewById(R.id.TextView_signUp);
        RelativeLayout_login.setClickable(false);

        /*
        TextInputEditText_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //charSequence: 입력값(매 char마다), i: length, i1: 입력중인가, i2: 지우는중인가 1/0
                if(charSequence != null) {
                    input_email = charSequence.toString();
                    RelativeLayout_login.setClickable(validation());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
         */

        RelativeLayout_login.setClickable(true);
        RelativeLayout_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_email = TextInputEditText_email.getText().toString();
                input_password = TextInputEditText_password.getText().toString();

                if(validation()){
                    Intent loginSuccess = new Intent(MainActivity.this, CategoryActivity.class);
                    startActivity(loginSuccess);
                }
                else{
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

    public boolean validation(){
        if(input_email.equals(user.email) && input_password.equals(user.password))
            return true;
        else return false;
    }

    public void alertSignUp(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Sign Up");
        alert.setMessage("Enter your email and password");

        //alert는 하나의 뷰만을 담을 수 있다. 따라서 두 개의 edittext를 표시하려면
        //별도의 view에 포함시켜야 한다. 아래는 xml을 사용하지 않고 linearLayout을 만드는 방법이다.
        //레이아웃 생성
        LinearLayout layout = new LinearLayout(this);
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
        EditText_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //비밀번호의 visible을 변경하는 체크박스 추가
        final CheckBox checkBox = new CheckBox(this);
        checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        checkBox.setText("Show password");

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()) EditText_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else EditText_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);

                EditText_password.setSelection(EditText_password.getText().length());
            }
        });

        layout.addView(checkBox);

        //생성한 LinearLayout을 alert로 표시
        alert.setView(layout);

        alert.setPositiveButton("Sign Up", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user.email = EditText_email.getText().toString();
                user.password = EditText_password.getText().toString();
            }
        });

        alert.show();
    }

    @Override
    public void onBackPressed() {
        String endString = "'뒤로' 버튼을 한 번 더 누르면 앱을 종료합니다.";
        if(System.currentTimeMillis() - lastTimeBackPressed < 2000)
            finish();
            //super.onBackPressed();
        else {
            Toast.makeText(this, endString, Toast.LENGTH_SHORT).show();
            lastTimeBackPressed = System.currentTimeMillis();
        }
    }
}