package com.example.n4u1.test130.views;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ContentDTO;
import com.example.n4u1.test130.util.ListViewSearchCategoryAdapter;
import com.example.n4u1.test130.util.ListViewSearchTitleAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class SearchHomeActivity extends AppCompatActivity {




    private ListView listViewCategory;
    private ListView listViewTitle;
    private ArrayList<String> stringArrayCategoryList;
    private ArrayList<String> stringArrayTitleList;


    private ListViewSearchCategoryAdapter adapterCategory;
    private ListViewSearchTitleAdapter adapterTitle;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_home);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(" ");
        }
        getSupportActionBar().setIcon(R.mipmap.ic_q_custom);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listViewCategory = findViewById(R.id.searchHomeActivity_listView_category);
        listViewTitle = findViewById(R.id.searchHomeActivity_listView_title);



//        https://stackoverflow.com/questions/31966453/firebase-where-to-call-addchildeventlistener-so-that-i-can-save-bandwidth
//        위의 주소에서처럼 많은량의 user_contents가 누적되었을경우 SharedPreferences(?) 를 이용해서 이어서 검색하게함
//        Query newItems = mDatabaseReference.orderByKey().startAt(lastChildKey);
        //검색 대상인 title을 arraylist로 뽑아서 세팅
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("user_contents");
        mDatabaseReference.orderByChild("title").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<String> tmp = new ArrayList<>();

                Iterator<DataSnapshot> contentDTOIterator = dataSnapshot.getChildren().iterator();

                while (contentDTOIterator.hasNext()) {
                    final ContentDTO contentDTO = contentDTOIterator.next().getValue(ContentDTO.class);
                    tmp.add(contentDTO.title);
                }
                setTitleData(tmp);
                setCategoryData();

                adapterCategory = new ListViewSearchCategoryAdapter(SearchHomeActivity.this, R.layout.search_item_listview, stringArrayCategoryList);
                adapterTitle = new ListViewSearchTitleAdapter(SearchHomeActivity.this, R.layout.search_item_title_listview, stringArrayTitleList);
                listViewCategory.setAdapter(adapterCategory);
                listViewTitle.setAdapter(adapterTitle);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        listViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SearchHomeActivity.this, (String)parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

            }
        });


        listViewTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SearchHomeActivity.this, (String)parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }
        });





    }


    //게시글 제목 검색결과 list
    private void setTitleData(ArrayList<String> tmpString) {
        stringArrayTitleList = new ArrayList<>();
        stringArrayTitleList.addAll(tmpString);
    }


    //카테고리 검색결과 list
    private void setCategoryData() {
        stringArrayCategoryList = new ArrayList<>();
        stringArrayCategoryList.add("아침");
        stringArrayCategoryList.add("아무거나");
        stringArrayCategoryList.add("아거");
        stringArrayCategoryList.add("무거나");
        stringArrayCategoryList.add("점심");
        stringArrayCategoryList.add("심심무");
        stringArrayCategoryList.add("침거");
        stringArrayCategoryList.add("무사히");
        stringArrayCategoryList.add("사사로아");
        stringArrayCategoryList.add("이경준");
        stringArrayCategoryList.add("아,무,거,나");
        stringArrayCategoryList.add("야구");
        stringArrayCategoryList.add("농구");
        stringArrayCategoryList.add("축구");
        stringArrayCategoryList.add("축지법");
        stringArrayCategoryList.add("대한민국 헌법");
        stringArrayCategoryList.add("성셋별");
        stringArrayCategoryList.add("별하나");
        stringArrayCategoryList.add("대,한.민;국");
        stringArrayCategoryList.add("아니");
        stringArrayCategoryList.add("아니아니");
        stringArrayCategoryList.add("아니아니아아아아아");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SearchView searchView;
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
                    adapterCategory.filter("");
                    adapterTitle.filter("");
                    listViewCategory.clearTextFilter();
                    listViewTitle.clearTextFilter();
                } else {
                    adapterTitle.filter(newText);
                    adapterCategory.filter(newText);
                }
                return true;
            }
        });

        return true;
    }
}
