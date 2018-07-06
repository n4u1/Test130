package com.example.n4u1.test130.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.n4u1.test130.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView textView_uidTest = findViewById(R.id.textView_uidTest);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        textView_uidTest.setText(mUser.getUid());


    }
}
