package com.example.n4u1.test130.dialog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.n4u1.test130.R;

public class RankingChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.Theme_AppCompat_Dialog);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);




//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        this.setContentView(R.layout.activity_ranking_choice);
        setContentView(R.layout.activity_ranking_choice);

        TextView textView = findViewById(R.id.txtText);
        Button button = findViewById(R.id.buttonTest);

        //데이터 가져오기
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");







    }
}
