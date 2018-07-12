package com.example.n4u1.test130.views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//
import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mEmailDatabaseReference;
    private ValueEventListener mEmailListener;
    private ChildEventListener mChildEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_CreateUser = findViewById(R.id.button_createUser);
        Button button_Login = findViewById(R.id.button_login);
        Button button_aLogin = findViewById(R.id.button_aLogin);
        Button button_bLogin = findViewById(R.id.button_bLogin);
        Button button_cLogin = findViewById(R.id.button_cLogin);
        Button button_dLogin = findViewById(R.id.button_dLogin);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mEmailDatabaseReference = mDatabase.getReference("users");

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
                    Toast.makeText(getApplicationContext(), "이메일 형식으로 입력해주세요!!!!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (inputUserPassword.length() < 6) {
                    int test = inputUserPassword.length();
                    Toast.makeText(getApplicationContext(), "비밀번호가 너무 짧아요ㅠ_ㅠ (6자 이상)", Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "현재 몇글자??? >>> " + test, Toast.LENGTH_LONG).show();
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

        button_aLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser("a@a.com", "aaaaaa");
            }
        });

        button_bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser("b@b.com", "bbbbbb");
            }
        });

        button_cLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser("c@c.com", "cccccc");
            }
        });

        button_dLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser("d@d.com", "dddddd");
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

    private void loginUser(final String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Toast.makeText(getApplicationContext(), "User Login Success", Toast.LENGTH_LONG).show();

                            mEmailDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Iterable<DataSnapshot> emailIterable = dataSnapshot.getChildren();
                                    Iterator<DataSnapshot> emailIterator = emailIterable.iterator();


                                    int userCount = (int)dataSnapshot.getChildrenCount();
                                    int loopCount = 0;

                                    while (emailIterator.hasNext()) {
                                        User user = emailIterator.next().getValue(User.class);
                                        if (!user.getEmail().equals(email)) {
                                            loopCount++;
                                        }
                                    }
                                    if ( loopCount == userCount) {
                                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                                        finish();
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        finish();
                                        startActivity(intent);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


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
                            Toast.makeText(getApplicationContext(), "User Create Fail : " + task.getException(), Toast.LENGTH_LONG).show();
                            Log.d("getException", "createUserWithEmail:failure", task.getException());


                        }

                    }
                });
    }
}
