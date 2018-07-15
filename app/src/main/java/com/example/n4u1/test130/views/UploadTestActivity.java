package com.example.n4u1.test130.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.n4u1.test130.R;

import java.util.ArrayList;

public class UploadTestActivity extends AppCompatActivity {

    ArrayList<String> list;

    ArrayAdapter<String> adapter;

    ListView lv;

    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_test);


        final EditText et = findViewById(R.id.et);


        list = new ArrayList<String>();

        list.add("01");

        list.add("02");

        list.add("03");

        list.add("04");
        list.add("05");
        list.add("06");
        list.add("07");
        list.add("08");
        list.add("09");
        list.add("10");
        list.add("11");



        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list);


        lv = findViewById(R.id.lv);




        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        lv.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lv);


        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et.getText().length() != 0) {

                    list.add(et.getText().toString());

                    et.setText("");

                    // 갱신되었음을 어댑터에 통보한다.

                    adapter.notifyDataSetChanged();

                    imm.hideSoftInputFromWindow(et.getWindowToken(), 0);


                }


            }
        });



    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


}
