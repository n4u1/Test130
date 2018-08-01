package com.example.n4u1.test130.views;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import static com.example.n4u1.test130.setting.SetActionBarTitle.SetActionBarTitle;


public class RegisterActivity extends AppCompatActivity {


    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetActionBarTitle(getSupportActionBar(), "AQA");
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        TextView textView_uidTest = findViewById(R.id.textView_uidTest);
        final EditText editText_age = findViewById(R.id.editText_age);
        final EditText editText_job = findViewById(R.id.editText_job);
        final EditText editText_sex = findViewById(R.id.editText_sex);
        Button button_login = findViewById(R.id.button_confirm);

        //device id 말고 다른거 써야할듯
        final String USER_DEVICE_ID = android.provider.Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);



        textView_uidTest.setText(USER_DEVICE_ID);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewUser(USER_DEVICE_ID, editText_sex.getText().toString(), editText_job.getText().toString(),
                        mUser.getUid(), mUser.getEmail(), Integer.parseInt(editText_age.getText().toString()));

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });


        

        
    }

    private void writeNewUser(String deviceName, String sex, String job, String uid, String email, int age) {
        User user = new User(deviceName, sex, job, uid, email, age);
        mDatabase.child("users").child(uid).setValue(user);

    }


}
