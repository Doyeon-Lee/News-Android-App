package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileFragment extends Fragment {
    LinearLayout LinearLayout_listView;
    ListView ListView_category;

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }

    //기존에 구현해두었던 Action의 onCreate()의 내용을 onCreateView로 옮겨왔다
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_category, container, false);

        final ArrayList<String> categoryArray = new ArrayList<>(
                Arrays.asList("Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology")
        );

        LinearLayout_listView = v.findViewById(R.id.LinearLayout_listView);
        ListView_category = v.findViewById(R.id.ListView_category);
        ListViewAdapter listViewAdapter = new ListViewAdapter(categoryArray);
        Fresco.initialize(getActivity());

        ListView_category.setAdapter(listViewAdapter);
        ListView_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String category = categoryArray.get(position);
                /* fragment는 context가 아니기 때문에 부모 context를 호출해서 사용한다?
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
                 */

                Bundle bundle = new Bundle();
                bundle.putString("category", category);
                Fragment fragment = new SettingFragment();
                fragment.setArguments(bundle);
                ((NavigationActivity)getActivity()).replaceFragment(SettingFragment.newInstance());
            }
        });

        return v;
    }
}