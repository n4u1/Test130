package com.example.n4u1.test130.views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//
import com.example.n4u1.test130.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_CreateUser = findViewById(R.id.button_createUser);
        Button button_Login = findViewById(R.id.button_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();






        //d
        button_CreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editTextEmail = findViewById(R.id.editText_email);
                EditText editTextPassword = findViewById(R.id.editText_password);
                final String inputUserEmail = editTextEmail.getText().toString();
                final String inputUserPassword = editTextPassword.getText().toString();

                if (inputUserEmail.isEmpty() || inputUserPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "빈칸이 있어요!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!checkEmail(inputUserEmail)) {
                    Toast.makeText(getApplicationContext(), "이메일 형식으로 입력해주세요.", Toast.LENGTH_LONG).show();
                    return;
                }

                if (inputUserPassword.length() < 6) {
                    int test = inputUserPassword.length();
                    Toast.makeText(getApplicationContext(), "비밀번호가 너무 짧아요ㅠ_ㅠ (6자 이상)", Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "d" + test, Toast.LENGTH_LONG).show();
                    return;
                }






                //email 계정 생성
                createUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());

            }
        });

        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextEmail = findViewById(R.id.editText_email);
                EditText editTextPassword = findViewById(R.id.editText_password);
                loginUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                
            }
        });

    }

    //정규표현식으로 이메일 체킹
    private boolean checkEmail(String inputUserEmail) {
        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(inputUserEmail);
        boolean isNormal = m.matches();
        return isNormal;
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "User Login Success", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                            String uuser = mAuth.getCurrentUser().getUid();
                            intent.putExtra("uid", uuser);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "User Login Fail", Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });
    }


    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Toast.makeText(getApplicationContext(), "User Create Success", Toast.LENGTH_LONG).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "User Create Fail", Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });
    }
}
