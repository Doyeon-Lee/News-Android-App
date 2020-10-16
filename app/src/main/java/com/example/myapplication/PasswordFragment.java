package com.example.myapplication;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class PasswordFragment extends Fragment {
    private EditText EditText_original_pw, EditText_new_pw, EditText_new_pw2;
    private TextView TextView_warning, TextView_warning2, TextView_warning3;
    private ToggleButton ToggleButton_pw1, ToggleButton_pw2, ToggleButton_pw3;
    private Button Button_submit;

    public PasswordFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PasswordFragment newInstance(String param1, String param2) {
        PasswordFragment fragment = new PasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_password, container, false);
        EditText_original_pw = v.findViewById(R.id.EditText_original_pw);
        EditText_new_pw = v.findViewById(R.id.EditText_new_pw);
        EditText_new_pw2 = v.findViewById(R.id.EditText_new_pw2);
        TextView_warning = v.findViewById(R.id.TextView_warning);
        TextView_warning2 = v.findViewById(R.id.TextView_warning2);
        TextView_warning3 = v.findViewById(R.id.TextView_warning3);
        Button_submit = v.findViewById(R.id.Button_submit);
        ToggleButton_pw1 = v.findViewById(R.id.ToggleButton_pw1);
        ToggleButton_pw2 = v.findViewById(R.id.ToggleButton_pw2);
        ToggleButton_pw3 = v.findViewById(R.id.ToggleButton_pw3);

        TextView_warning3.setText(R.string.password_qualification);

        ToggleButton_pw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    EditText_original_pw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else
                    EditText_original_pw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        ToggleButton_pw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    EditText_new_pw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else
                    EditText_new_pw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        ToggleButton_pw3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    EditText_new_pw2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else
                    EditText_new_pw2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });


        Button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String original_pw = EditText_original_pw.getText().toString();
                String new_pw = EditText_new_pw.getText().toString();
                String new_pw2 = EditText_new_pw2.getText().toString();
                boolean flag;

                String password = MainActivity.user.getPassword();

                if(original_pw.equals(password)){
                    flag = true;
                    TextView_warning.setVisibility(View.INVISIBLE);
                }
                else{
                    flag = false;
                    TextView_warning.setVisibility(View.VISIBLE);
                }

                if(new_pw.equals(new_pw2))
                    TextView_warning2.setVisibility(View.INVISIBLE);
                else{
                    flag = false;
                    TextView_warning2.setVisibility(View.VISIBLE);
                }

                if(MainActivity.isPassword(new_pw))
                    TextView_warning3.setVisibility(View.INVISIBLE);
                else{
                    flag = false;
                    TextView_warning3.setVisibility(View.VISIBLE);
                }

                if(flag){ //비밀번호 변경 성공
                    MainActivity.user.setPassword(new_pw);
                    MainActivity.saveData();
                    getActivity().getSupportFragmentManager().popBackStack();
                }

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        });

        return v;
    }
}