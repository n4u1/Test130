package com.example.n4u1.test130.views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ContentDTO;
import com.example.n4u1.test130.recyclerview.PostAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.n4u1.test130.setting.SetActionBarTitle.SetActionBarTitle;

public class HomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetActionBarTitle(getSupportActionBar(), "AQA");
        setContentView(R.layout.activity_home);

        FloatingActionButton fab_addContent = findViewById(R.id.fab_addContent);
        final SwipeRefreshLayout mSwipeRefreshLayout = findViewById(R.id.swipeRFL);
        mDatabase = FirebaseDatabase.getInstance();

        final ArrayList<ContentDTO> contentDTOS = new ArrayList<>();
        RecyclerView recyclerViewList = findViewById(R.id.recyclerView_home);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());//20180730 전날꺼 보기 getApplicationContext()전에 this,?? 였음
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.isSmoothScrollbarEnabled();
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setReverseLayout(true);
        recyclerViewList.setLayoutManager(mLayoutManager);
        final PostAdapter postAdapter = new PostAdapter(getApplication(), contentDTOS); //20180730 전날꺼 보기 getApplication()전에 this,contentDTOS 였음
        recyclerViewList.setAdapter(postAdapter);

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

        //Pull to Refresh 당겨서 새로고침
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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


        fab_addContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UserContentsUploadActivity.class);
                startActivity(intent);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
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
            case R.id.menu_list:
                Intent intent = new Intent(getApplicationContext(), ContentTypeListActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_mine: break;
            case R.id.menu_setting: break;
        }
//        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
