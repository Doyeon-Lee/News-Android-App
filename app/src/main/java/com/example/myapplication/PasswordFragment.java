package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PasswordFragment extends Fragment {
    EditText EditText_original_pw, EditText_new_pw, EditText_new_pw2;
    TextView TextView_warning, TextView_warning2;
    Button Button_submit;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PasswordFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PasswordFragment newInstance(String param1, String param2) {
        PasswordFragment fragment = new PasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        Button_submit = v.findViewById(R.id.Button_submit);

        Button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String original_pw = EditText_original_pw.getText().toString();
                String new_pw = EditText_new_pw.getText().toString();
                String new_pw2 = EditText_new_pw2.getText().toString();
                boolean flag;

                String password = ((MainActivity)getContext()).user.getPassword();
                //String password = ((MainActivity)MainActivity.context).user.password;

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

                if(flag){ //비밀번호 변경 성공

                }
            }
        });

        return v;
    }
}