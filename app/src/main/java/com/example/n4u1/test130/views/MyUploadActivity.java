package com.example.n4u1.test130.views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ContentDTO;
import com.example.n4u1.test130.recyclerview.PostAdapterMine;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyUploadActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private FirebaseDatabase mDatabaseUser;
    private FirebaseUser mFireBaseUser;

    final ArrayList<ContentDTO> contentDTOS = new ArrayList<>();
    final PostAdapterMine postAdapterMine = new PostAdapterMine(this, contentDTOS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_upload);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(" ");
        }
        getSupportActionBar().setIcon(R.mipmap.ic_q_custom);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseUser = FirebaseDatabase.getInstance();
        mFireBaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final RecyclerView recyclerView_myUpload = findViewById(R.id.recyclerView_myUpload);
        final TextView textView_myUpload = findViewById(R.id.textView_myUpload);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.isSmoothScrollbarEnabled();
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setReverseLayout(true);
        recyclerView_myUpload.setLayoutManager(mLayoutManager);

        recyclerView_myUpload.setAdapter(postAdapterMine);
        postAdapterMine.notifyDataSetChanged();


        //onCreate시 업로드한 게시물 추려서 리스트업
        mDatabase.getReference().child("user_contents").addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<String> key = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                mDatabaseUser.getReference().child("users").child(mFireBaseUser.getUid()).child("uploadContent").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                        Map<String, Object> uploadContent = (Map<String, Object>) dataSnapshots.getValue();
                        int tempCount = 0;

                        if (uploadContent == null) {
                            textView_myUpload.setVisibility(View.VISIBLE);
                            recyclerView_myUpload.setVisibility(View.GONE);
//                            Toast toast = Toast.makeText(getApplicationContext(), "진행하신 투표가 아직 없습니다!", Toast.LENGTH_LONG);
//                            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
//                            toast.show();
                        } else {
                            textView_myUpload.setVisibility(View.GONE);
                            recyclerView_myUpload.setVisibility(View.VISIBLE);
                            Set set = uploadContent.keySet();
                            Iterator iterator = set.iterator();
                            while (iterator.hasNext()) {
                                key.add((String) iterator.next());
                                tempCount++;
                            }

                            contentDTOS.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                ContentDTO contentDTO = snapshot.getValue(ContentDTO.class);
                                for (int i = 0; i < tempCount; i++) {
                                    if (contentDTO.getContentKey().contains(key.get(i))) {
                                        contentDTOS.add(contentDTO);
                                    }
                                }
                            }
                            postAdapterMine.notifyDataSetChanged();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mypoll_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            case R.id.menu_home:
                Intent intentHome = new Intent(MyUploadActivity.this, HomeActivity.class);
                startActivity(intentHome);
                break;
            case R.id.menu_back:
                break;


        }
//        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
