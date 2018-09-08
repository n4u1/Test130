package com.example.n4u1.test130.views;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ContentDTO;
import com.example.n4u1.test130.models.User;
import com.example.n4u1.test130.recyclerview.PostAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class HomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private FirebaseDatabase mDatabase;
    private FirebaseDatabase mSortingDatabase;
    private FirebaseUser mFireBaseUser;

    final ArrayList<ContentDTO> contentDTOS = new ArrayList<>();
    final ArrayList<ContentDTO> contentDTOS_title = new ArrayList<>();
    final ArrayList<ContentDTO> contentDTOS_category = new ArrayList<>();
    final PostAdapter postAdapter = new PostAdapter(this, contentDTOS);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SetActionBarTitle(getSupportActionBar(), "AQA");
        setContentView(R.layout.activity_home);

//        //테스트용 액티비티
//        Button button = findViewById(R.id.button_test);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, TestActivity.class);
//                startActivity(intent);
//            }
//        });

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(" ");
        }
        getSupportActionBar().setIcon(R.mipmap.ic_q_custom);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab_addContent = findViewById(R.id.fab_addContent);
        final SwipeRefreshLayout mSwipeRefreshLayout = findViewById(R.id.swipeRFL);
        mDatabase = FirebaseDatabase.getInstance();
        FirebaseDatabase mSearchingDatabase = FirebaseDatabase.getInstance();
        mSortingDatabase = FirebaseDatabase.getInstance();
        mFireBaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final RecyclerView recyclerViewList = findViewById(R.id.recyclerView_home);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());//20180730 전날꺼 보기 getApplicationContext()전에 this,?? 였음
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.isSmoothScrollbarEnabled();
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setReverseLayout(true);
        recyclerViewList.setLayoutManager(mLayoutManager);
//        final PostAdapter postAdapter = new PostAdapter(getApplication(), contentDTOS); //20180730 전날꺼 보기 getApplication()전에 this,contentDTOS 였음
        recyclerViewList.setAdapter(postAdapter);
        postAdapter.notifyDataSetChanged();



        //onCreate시 액티비티 최초1회 바인딩
        mDatabase.getReference().child("user_contents").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDatabase.getReference().child("users").child(mFireBaseUser.getUid()).child("search_flag_category").setValue("전체");
                mDatabase.getReference().child("users").child(mFireBaseUser.getUid()).child("search_flag_title").setValue("전체");
                contentDTOS.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ContentDTO contentDTO = snapshot.getValue(ContentDTO.class);
                    contentDTOS.add(contentDTO);
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //검색 플래그 (DB/"users"/currentUid/search_flag_category)
        //"전체" 일경우 기본
        //"카테고리명" 일경우 검색결과를 보여줌
        mSearchingDatabase.getReference().child("users").child(mFireBaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> user = (Map<String, Object>) dataSnapshot.getValue();
                final String search_flag_category = user.get("search_flag_category").toString();
                final String search_flag_title = user.get("search_flag_title").toString();
                Log.d("lkj searchflag", user.get("search_flag_category").toString());

                //search_flag가 전체면 기본 HomeActivity, search_flag가 전체가 아니라면 해당 string 로 검색
                //카테고리 검색
                if (!user.get("search_flag_category").toString().equals("전체")) {

                    final PostAdapter postAdapter_search_category = new PostAdapter(getApplicationContext(), contentDTOS_category);
                    contentDTOS_category.clear();
                    contentDTOS_title.clear();
                    Log.d("lkj searchflag_category", "전체가 아닐경우");
                    mSortingDatabase.getReference().child("user_contents").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterator<DataSnapshot> contentDTOIterator = dataSnapshot.getChildren().iterator();

                            while (contentDTOIterator.hasNext()) {
                                final ContentDTO contentDTO = contentDTOIterator.next().getValue(ContentDTO.class);
                                if (contentDTO.contentType.contains(search_flag_category)) {
                                    contentDTOS_category.add(contentDTO);
                                }
                            }
                            recyclerViewList.setAdapter(postAdapter_search_category);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    postAdapter_search_category.notifyDataSetChanged();
                }
                //제목 검색
                if (!user.get("search_flag_title").toString().equals("전체")) {
                    final PostAdapter postAdapter_search_title = new PostAdapter(getApplicationContext(), contentDTOS_title);
                    contentDTOS_category.clear();
                    contentDTOS_title.clear();
                    Log.d("lkj searchflag_title", "전체가 아닐경우");
                    mSortingDatabase.getReference().child("user_contents").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterator<DataSnapshot> contentDTOIterator = dataSnapshot.getChildren().iterator();

                            while (contentDTOIterator.hasNext()) {
                                final ContentDTO contentDTO = contentDTOIterator.next().getValue(ContentDTO.class);
                                if (contentDTO.title.contains(search_flag_title)) {
                                    contentDTOS_title.add(contentDTO);
                                }
                            }

                            recyclerViewList.setAdapter(postAdapter_search_title);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    postAdapter_search_title.notifyDataSetChanged();
                }
                else {
                    recyclerViewList.setAdapter(postAdapter);
                    postAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //Pull to Refresh 당겨서 새로고침
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDatabase.getReference().child("users").child(mFireBaseUser.getUid()).child("search_flag_category").setValue("전체");
                mDatabase.getReference().child("users").child(mFireBaseUser.getUid()).child("search_flag_title").setValue("전체");
                mDatabase.getReference().child("user_contents").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        contentDTOS.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ContentDTO contentDTO = snapshot.getValue(ContentDTO.class);
                            contentDTOS.add(contentDTO);
                        }
                        postAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        //글쓰기 FloatingActionButton
        fab_addContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UserContentsUploadActivity.class);
                startActivity(intent);

            }
        });
    }

    //좋아요 클릭후 HomeActivity 새로고침
    public void resetActivity() {
        mDatabase.getReference().child("user_contents").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contentDTOS.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ContentDTO contentDTO = snapshot.getValue(ContentDTO.class);
                    contentDTOS.add(contentDTO);
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public void onRefresh() {


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            case R.id.menu_search:
                Intent intent = new Intent(HomeActivity.this, SearchHomeActivity.class);
                overridePendingTransition(0, 0);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            case R.id.menu_home:
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                break;
            case R.id.menu_mine:
                break;
            case R.id.menu_setting:
                break;

        }
//        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
