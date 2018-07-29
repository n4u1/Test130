package com.example.n4u1.test130.views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ContentDTO;
import com.example.n4u1.test130.models.PostItem;
import com.example.n4u1.test130.models.User;
import com.example.n4u1.test130.recyclerview.PostAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.n4u1.test130.setting.SetActionBarTitle.SetActionBarTitle;

public class HomeActivity extends AppCompatActivity {

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private FirebaseUser mFireBaseUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mEmailDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetActionBarTitle(getSupportActionBar(), "AQA");
        setContentView(R.layout.activity_home);

        FloatingActionButton fab_addContent = findViewById(R.id.fab_addContent);



        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mFireBaseUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
//        mEmailDatabaseReference = mDatabase.getReference("users").child(mFireBaseUser.getUid());



        fab_addContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UserContentsUploadActivity.class);
                startActivity(intent);

            }
        });


        final ArrayList<ContentDTO> contentDTOS = new ArrayList<>();
        RecyclerView recyclerViewList = findViewById(R.id.recyclerView_home);

        recyclerViewList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final PostAdapter postAdapter = new PostAdapter(this, contentDTOS);
        recyclerViewList.setAdapter(postAdapter);

        mDatabase.getReference().child("user_contents").addValueEventListener(new ValueEventListener() {
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
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}
