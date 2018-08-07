package com.example.n4u1.test130.views;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ContentDTO;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://test130-1068f.appspot.com");

        Button button = findViewById(R.id.buttonTest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentDTO contentDTO = new ContentDTO();
                contentDTO.imageUrl_0 = "url test";
                contentDTO.uid = auth.getCurrentUser().getUid();
                contentDTO.userID = auth.getCurrentUser().getEmail();
                contentDTO.title = "title test";
                contentDTO.description = "description test";
                contentDTO.pollMode = "pollMode Test"; // 단일투표 or 순위투표
                contentDTO.contentType = "contetntype test"; //아침, 패션, 정치 등..
                database.getReference().child("user_contents_test").push().setValue(contentDTO); //contentDTO에 담아서 setValue로 디비에 저장
            }
        });

        Button button1 = findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mdatabase.child("user_contents_test").push().getKey();
                ContentDTO contentDTO = new ContentDTO();
                Map<String, Object> postValues = contentDTO.toMap();

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/posts/" + key, postValues);
                childUpdates.put("/user-posts/" + auth.getCurrentUser().getUid() + "/" + key, postValues);

                mdatabase.updateChildren(childUpdates);
            }
        });





    }
}
