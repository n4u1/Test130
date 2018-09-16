package com.example.n4u1.test130.views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ContentDTO;
import com.example.n4u1.test130.recyclerview.PostAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class SearchResultActivity extends AppCompatActivity {
    private FirebaseDatabase mSearchingDatabase;
    private FirebaseUser mFireBaseUser;
    private FirebaseDatabase mSortingDatabase;
    private FirebaseDatabase mDatabase;

    final ArrayList<ContentDTO> contentDTOS = new ArrayList<>();
    final PostAdapter postAdapter = new PostAdapter(this, contentDTOS);
//    final PostAdapter postAdapter_search_category = new PostAdapter(getApplicationContext(), contentDTOS_category);
//    final PostAdapter postAdapter_search_title = new PostAdapter(getApplicationContext(), contentDTOS_title);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(" ");
        }
        getSupportActionBar().setIcon(R.mipmap.ic_q_custom);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mSearchingDatabase = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mFireBaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mSortingDatabase = FirebaseDatabase.getInstance();

        final RecyclerView recyclerView_result = findViewById(R.id.recyclerView_result);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.isSmoothScrollbarEnabled();
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setReverseLayout(true);
        recyclerView_result.setLayoutManager(mLayoutManager);
//        recyclerView_result.setAdapter(postAdapter);
//        postAdapter.notifyDataSetChanged();



        //검색 플래그 (DB/users/"currentUid"/search_flag_category/"검색어명")
        //"카테고리명" 일경우 검색결과를 보여줌
        //"제목명" 일경우 검색결과를 보여줌
        mSearchingDatabase.getReference().child("users").child(mFireBaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> user = (Map<String, Object>) dataSnapshot.getValue();
                final String searchFlagCategory = user.get("search_flag_category").toString();
                final String searchFlagTitle = user.get("search_flag_title").toString();

                if (!user.get("search_flag_category").toString().equals("전체")) {
                    contentDTOS.clear();
                    Log.d("lkj searchflag_category", "전체가 아닐경우");
                    mSortingDatabase.getReference().child("user_contents").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterator<DataSnapshot> contentDTOIterator = dataSnapshot.getChildren().iterator();

                            while (contentDTOIterator.hasNext()) {
                                ContentDTO contentDTO = contentDTOIterator.next().getValue(ContentDTO.class);
                                if (contentDTO.contentType.contains(searchFlagCategory)) {
                                    contentDTOS.add(contentDTO);
                                }
                            }
                            recyclerView_result.setAdapter(postAdapter);
                            postAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else if (!user.get("search_flag_title").toString().equals("전체")) {
                    contentDTOS.clear();
                    Log.d("lkj searchflag_title", "전체가 아닐경우");
                    mSortingDatabase.getReference().child("user_contents").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterator<DataSnapshot> contentDTOIterator = dataSnapshot.getChildren().iterator();

                            while (contentDTOIterator.hasNext()) {
                                ContentDTO contentDTO = contentDTOIterator.next().getValue(ContentDTO.class);
                                if (contentDTO.title.contains(searchFlagTitle)) {
                                    contentDTOS.add(contentDTO);
                                }
                            }
                            recyclerView_result.setAdapter(postAdapter);
                            postAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_result_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            case R.id.menu_search:
                Intent intentSearch = new Intent(SearchResultActivity.this, SearchHomeActivity.class);
                overridePendingTransition(0, 0);
                startActivity(intentSearch);
                overridePendingTransition(0, 0);
                break;
            case R.id.menu_home:
                Intent intentHome = new Intent(SearchResultActivity.this, HomeActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHome);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
