package com.example.n4u1.test130.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ContentDTO;
import com.example.n4u1.test130.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class TestActivity extends AppCompatActivity {
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference mdatabase;
    String key;
    ContentDTO contentDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference();

        StorageReference storageRef = storage.getReferenceFromUrl("gs://test130-1068f.appspot.com");

        Button button = findViewById(R.id.buttonTest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = mdatabase.child("user_contents_tests").push().getKey();
                contentDTO = new ContentDTO();
                contentDTO.contentKey = key;
                contentDTO.imageUrl_0 = "4321url test";
                contentDTO.uid = auth.getCurrentUser().getUid();
                contentDTO.userID = auth.getCurrentUser().getEmail();
                contentDTO.title = "4321title test";
                contentDTO.description = "421description test";
                contentDTO.pollMode = "4321pollMode Test"; // 단일투표 or 순위투표
                contentDTO.contentType = "4321contetntype test"; //아침, 패션, 정치 등..

                mdatabase.child("user_contents_tests").child(key).setValue(contentDTO);
                Log.d("lkjlkj", key);
            }
        });


        Button button1 = findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdatabase.child("user_contents_tests").child(key).child(contentDTO.description).push().setValue("?????");
            }
        });

    }
}
