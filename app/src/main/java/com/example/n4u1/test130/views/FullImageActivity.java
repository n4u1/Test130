package com.example.n4u1.test130.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.n4u1.test130.R;
import com.github.chrisbanes.photoview.PhotoView;

public class FullImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);


        PhotoView photoView = findViewById(R.id.photoView);

        String imgUrl = getIntent().getStringExtra("imgUrl");

        Glide.with(this).load(imgUrl).into(photoView);


    }
}
