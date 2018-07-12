package com.example.n4u1.test130.views;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n4u1.test130.R;
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

public class HomeActivity extends AppCompatActivity {

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mDatabaseReference;
    private DatabaseReference mEmailDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView textView_uidTest = findViewById(R.id.textView_uidTest);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
//        Button test_button = findViewById(R.id.button_test);
        mFirebaseUser = mAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance();
        mEmailDatabaseReference = mDatabaseReference.getReference("users").child(mFirebaseUser.getUid());

//        textView_uidTest.setText(mUser.getUid());
//
//        test_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                mEmailDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        User user = dataSnapshot.getValue(User.class);
//                        String testUserEmail = user.getEmail();
//                        Toast.makeText(getApplicationContext(), " email ?? : " + testUserEmail, Toast.LENGTH_LONG).show();
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//        });


        ArrayList<PostItem> listItem = new ArrayList<>();
        RecyclerView recyclerViewList = findViewById(R.id.recyclerView_home);

        for (int i = 0; i < 5; i++) {

            PostItem item = new PostItem(true, 122, "irene", "http://image.hankookilbo.com/i.aspx?Guid=2017\\11\\18\\02dcbfbf70ec42f0bf812ebb337949a2&Month=Entertainment&size=640", "wowffffffffff");
            listItem.add(i, item);
        }

        PostAdapter postAdapter = new PostAdapter(this, listItem);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        recyclerViewList.setAdapter(postAdapter);

    }
}
