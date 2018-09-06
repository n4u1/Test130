package com.example.n4u1.test130.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.util.ListViewSearchCategoryAdapter;

import java.util.ArrayList;

public class SearchHomeActivity extends AppCompatActivity {


    private ListView listView;
    private ArrayList<String> stringArrayList;
    private ListViewSearchCategoryAdapter adapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_home);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setCatogoryData();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(" ");
        }
        getSupportActionBar().setIcon(R.mipmap.ic_q_custom);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = findViewById(R.id.searchHomeActivity_listView_category);
        adapter = new ListViewSearchCategoryAdapter(this, R.layout.search_item_listview, stringArrayList);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SearchHomeActivity.this, (String)parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }
        });





    }

    private void setCatogoryData() {
        stringArrayList = new ArrayList<>();
        stringArrayList.add("아침");
        stringArrayList.add("아무거나");
        stringArrayList.add("아거");
        stringArrayList.add("무거나");
        stringArrayList.add("점심");
        stringArrayList.add("심심무");
        stringArrayList.add("침거");
        stringArrayList.add("무사히");
        stringArrayList.add("사사로아");
        stringArrayList.add("이경준");
        stringArrayList.add("아,무,거,나");
        stringArrayList.add("야구");
        stringArrayList.add("농구");
        stringArrayList.add("축구");
        stringArrayList.add("축지법");
        stringArrayList.add("대한민국 헌법");
        stringArrayList.add("성셋별");
        stringArrayList.add("별하나");
        stringArrayList.add("대,한.민;국");
        stringArrayList.add("아니");
        stringArrayList.add("아니아니");
        stringArrayList.add("아니아니아아아아아");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_search_menu, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setFocusable(true);  //자동포커스
        searchView.setIconified(false); //자동포커스
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    adapter.filter("");
                    listView.clearTextFilter();
                } else {
                    adapter.filter(newText);
                }
                return true;
            }
        });

        return true;
    }
}
