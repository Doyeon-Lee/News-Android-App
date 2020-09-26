package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ListViewAdapter extends BaseAdapter{
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<String> listViewItemList = new ArrayList<>() ;

    // ListViewAdapter의 생성자
    public ListViewAdapter(ArrayList<String> listViewItemList) {
        this.listViewItemList = listViewItemList;
    }

    @Override // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    public int getCount() {
        return listViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //LayoutInflater layoutInflater = LayoutInflater.from(context);
            //두번째 인자에 null을 넣으면 inflate된 레이아웃의 root에 대한 모든 layout 파라미터들이
            //무시된다는 것이다. 그래서 아래와 같이 사용하면, false가 2번째 인자를 사용하지 않겠다는
            //의미이기 때문에 2번째 인자는 크게 중요하지 않다.
            convertView = inflater.inflate(R.layout.row_category, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView TextView_category = convertView.findViewById(R.id.TextView_category);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        // 아이템 내 각 위젯에 데이터 반영
        TextView_category.setText(listViewItemList.get(position));

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    @Override
    public Object getItem(int position) {
        return null;
    }

}