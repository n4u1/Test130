package com.example.n4u1.test130.views;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ContentDTO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class PollSingleActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseReference mDatabaseReference;

    TextView pollActivity_textView_title, pollActivity_textView_description,
            pollActivity_textView_pollMode, pollActivity_textView_contentType, pollActivity_textView_date;
    ImageView pollActivity_imageView_userAddContent_1, pollActivity_imageView_userAddContent_2,
            pollActivity_imageView_userAddContent_3, pollActivity_imageView_userAddContent_4,
            pollActivity_imageView_userAddContent_5, pollActivity_imageView_userAddContent_6,
            pollActivity_imageView_userAddContent_7, pollActivity_imageView_userAddContent_8,
            pollActivity_imageView_userAddContent_9, pollActivity_imageView_userAddContent_10;
    ImageView pollActivity_imageView_userAddContent_check_1, pollActivity_imageView_userAddContent_check_2,
            pollActivity_imageView_userAddContent_check_3, pollActivity_imageView_userAddContent_check_4,
            pollActivity_imageView_userAddContent_check_5, pollActivity_imageView_userAddContent_check_6,
            pollActivity_imageView_userAddContent_check_7, pollActivity_imageView_userAddContent_check_8,
            pollActivity_imageView_userAddContent_check_9, pollActivity_imageView_userAddContent_check_10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_single);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("  AQA");
        }
        getSupportActionBar().setIcon(R.drawable.ic_do_not_disturb_black_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        String contentKey = getIntent().getStringExtra("contentKey");
//        pollActivity_textView_title.setText(contentKey);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("user_contents").child(contentKey);

        pollActivity_textView_title = findViewById(R.id.pollActivity_textView_title);
        pollActivity_textView_description = findViewById(R.id.pollActivity_textView_description);
        pollActivity_textView_contentType = findViewById(R.id.pollActivity_textView_contentType);
        pollActivity_textView_pollMode = findViewById(R.id.pollActivity_textView_pollMode);
        pollActivity_textView_date = findViewById(R.id.pollActivity_textView_date);

        pollActivity_imageView_userAddContent_1 = findViewById(R.id.pollActivity_imageView_userAddContent_1);
        pollActivity_imageView_userAddContent_2 = findViewById(R.id.pollActivity_imageView_userAddContent_2);
        pollActivity_imageView_userAddContent_3 = findViewById(R.id.pollActivity_imageView_userAddContent_3);
        pollActivity_imageView_userAddContent_4 = findViewById(R.id.pollActivity_imageView_userAddContent_4);
        pollActivity_imageView_userAddContent_5 = findViewById(R.id.pollActivity_imageView_userAddContent_5);
        pollActivity_imageView_userAddContent_6 = findViewById(R.id.pollActivity_imageView_userAddContent_6);
        pollActivity_imageView_userAddContent_7 = findViewById(R.id.pollActivity_imageView_userAddContent_7);
        pollActivity_imageView_userAddContent_8 = findViewById(R.id.pollActivity_imageView_userAddContent_8);
        pollActivity_imageView_userAddContent_9 = findViewById(R.id.pollActivity_imageView_userAddContent_9);
        pollActivity_imageView_userAddContent_10 = findViewById(R.id.pollActivity_imageView_userAddContent_10);
        pollActivity_imageView_userAddContent_check_1 = findViewById(R.id.pollActivity_imageView_userAddContent_check_1);
        pollActivity_imageView_userAddContent_check_2 = findViewById(R.id.pollActivity_imageView_userAddContent_check_2);
        pollActivity_imageView_userAddContent_check_3 = findViewById(R.id.pollActivity_imageView_userAddContent_check_3);
        pollActivity_imageView_userAddContent_check_4 = findViewById(R.id.pollActivity_imageView_userAddContent_check_4);
        pollActivity_imageView_userAddContent_check_5 = findViewById(R.id.pollActivity_imageView_userAddContent_check_5);
        pollActivity_imageView_userAddContent_check_6 = findViewById(R.id.pollActivity_imageView_userAddContent_check_6);
        pollActivity_imageView_userAddContent_check_7 = findViewById(R.id.pollActivity_imageView_userAddContent_check_7);
        pollActivity_imageView_userAddContent_check_8 = findViewById(R.id.pollActivity_imageView_userAddContent_check_8);
        pollActivity_imageView_userAddContent_check_9 = findViewById(R.id.pollActivity_imageView_userAddContent_check_9);
        pollActivity_imageView_userAddContent_check_10 = findViewById(R.id.pollActivity_imageView_userAddContent_check_10);


        pollActivity_imageView_userAddContent_1.setOnClickListener(this);
        pollActivity_imageView_userAddContent_2.setOnClickListener(this);
        pollActivity_imageView_userAddContent_3.setOnClickListener(this);
        pollActivity_imageView_userAddContent_4.setOnClickListener(this);
        pollActivity_imageView_userAddContent_5.setOnClickListener(this);
        pollActivity_imageView_userAddContent_6.setOnClickListener(this);
        pollActivity_imageView_userAddContent_7.setOnClickListener(this);
        pollActivity_imageView_userAddContent_8.setOnClickListener(this);
        pollActivity_imageView_userAddContent_9.setOnClickListener(this);
        pollActivity_imageView_userAddContent_10.setOnClickListener(this);




        //contentDTO binding
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ContentDTO contentDTO = dataSnapshot.getValue(ContentDTO.class);
                contentDTO.getDescription();
                int itemViewCount = contentDTO.getItemViewType();
                pollActivity_textView_date.setText(contentDTO.getUploadDate());
                pollActivity_textView_title.setText(contentDTO.getTitle());
                pollActivity_textView_contentType.setText(contentDTO.getContentType());
                pollActivity_textView_description.setText(contentDTO.getDescription());
                pollActivity_textView_pollMode.setText(contentDTO.getPollMode());
//                pollActivity_textView_date.setText(contentDTO.getItemViewType());
                switch (itemViewCount) {
                    case 1 :
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
//                        pollActivity_imageView_userAddContent_check_1.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).into(pollActivity_imageView_userAddContent_1).getView();
                        break;

                    case 2 :
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).into(pollActivity_imageView_userAddContent_1).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).into(pollActivity_imageView_userAddContent_2).getView();
                        break;
                    case 3 :
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_3.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).into(pollActivity_imageView_userAddContent_1).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).into(pollActivity_imageView_userAddContent_2).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).into(pollActivity_imageView_userAddContent_3).getView();
                        break;
                    case 4 :
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_4.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).into(pollActivity_imageView_userAddContent_1).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).into(pollActivity_imageView_userAddContent_2).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).into(pollActivity_imageView_userAddContent_3).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).into(pollActivity_imageView_userAddContent_4).getView();
                        break;
                    case 5:
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_5.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).into(pollActivity_imageView_userAddContent_1).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).into(pollActivity_imageView_userAddContent_2).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).into(pollActivity_imageView_userAddContent_3).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).into(pollActivity_imageView_userAddContent_4).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).into(pollActivity_imageView_userAddContent_5).getView();
                        break;
                    case 6 :
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_6.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).into(pollActivity_imageView_userAddContent_1).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).into(pollActivity_imageView_userAddContent_2).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).into(pollActivity_imageView_userAddContent_3).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).into(pollActivity_imageView_userAddContent_4).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).into(pollActivity_imageView_userAddContent_5).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_5()).into(pollActivity_imageView_userAddContent_6).getView();
                        break;
                    case 7 :
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_7.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).into(pollActivity_imageView_userAddContent_1).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).into(pollActivity_imageView_userAddContent_2).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).into(pollActivity_imageView_userAddContent_3).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).into(pollActivity_imageView_userAddContent_4).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).into(pollActivity_imageView_userAddContent_5).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_5()).into(pollActivity_imageView_userAddContent_6).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_6()).into(pollActivity_imageView_userAddContent_7).getView();
                        break;
                    case 8 :
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_7.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_8.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).into(pollActivity_imageView_userAddContent_1).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).into(pollActivity_imageView_userAddContent_2).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).into(pollActivity_imageView_userAddContent_3).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).into(pollActivity_imageView_userAddContent_4).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).into(pollActivity_imageView_userAddContent_5).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_5()).into(pollActivity_imageView_userAddContent_6).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_6()).into(pollActivity_imageView_userAddContent_7).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_7()).into(pollActivity_imageView_userAddContent_8).getView();
                        break;
                    case 9 :
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_7.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_8.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_9.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).into(pollActivity_imageView_userAddContent_1).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).into(pollActivity_imageView_userAddContent_2).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).into(pollActivity_imageView_userAddContent_3).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).into(pollActivity_imageView_userAddContent_4).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).into(pollActivity_imageView_userAddContent_5).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_5()).into(pollActivity_imageView_userAddContent_6).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_6()).into(pollActivity_imageView_userAddContent_7).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_7()).into(pollActivity_imageView_userAddContent_8).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_8()).into(pollActivity_imageView_userAddContent_9).getView();
                        break;
                    case 10 :
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_7.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_8.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_9.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_10.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).into(pollActivity_imageView_userAddContent_1).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).into(pollActivity_imageView_userAddContent_2).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).into(pollActivity_imageView_userAddContent_3).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).into(pollActivity_imageView_userAddContent_4).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).into(pollActivity_imageView_userAddContent_5).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_5()).into(pollActivity_imageView_userAddContent_6).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_6()).into(pollActivity_imageView_userAddContent_7).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_7()).into(pollActivity_imageView_userAddContent_8).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_8()).into(pollActivity_imageView_userAddContent_9).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_9()).into(pollActivity_imageView_userAddContent_10).getView();
                        break;
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pollActivity_imageView_userAddContent_1 :
                checking_img_1();
                checking_img_2_rt();
                checking_img_3_rt();
                checking_img_4_rt();
                checking_img_5_rt();
                checking_img_6_rt();
                checking_img_7_rt();
                checking_img_8_rt();
                checking_img_9_rt();
                checking_img_10_rt();
                break;
            case R.id.pollActivity_imageView_userAddContent_2 :
                checking_img_1_rt();
                checking_img_2();
                checking_img_3_rt();
                checking_img_4_rt();
                checking_img_5_rt();
                checking_img_6_rt();
                checking_img_7_rt();
                checking_img_8_rt();
                checking_img_9_rt();
                checking_img_10_rt();
                break;
            case R.id.pollActivity_imageView_userAddContent_3 :
                checking_img_1_rt();
                checking_img_2_rt();
                checking_img_3();
                checking_img_4_rt();
                checking_img_5_rt();
                checking_img_6_rt();
                checking_img_7_rt();
                checking_img_8_rt();
                checking_img_9_rt();
                checking_img_10_rt();
                break;
            case R.id.pollActivity_imageView_userAddContent_4 :
                checking_img_1_rt();
                checking_img_2_rt();
                checking_img_3_rt();
                checking_img_4();
                checking_img_5_rt();
                checking_img_6_rt();
                checking_img_7_rt();
                checking_img_8_rt();
                checking_img_9_rt();
                checking_img_10_rt();
                break;
            case R.id.pollActivity_imageView_userAddContent_5 :
                checking_img_1_rt();
                checking_img_2_rt();
                checking_img_3_rt();
                checking_img_4_rt();
                checking_img_5();
                checking_img_6_rt();
                checking_img_7_rt();
                checking_img_8_rt();
                checking_img_9_rt();
                checking_img_10_rt();
                break;
            case R.id.pollActivity_imageView_userAddContent_6 :
                checking_img_1_rt();
                checking_img_2_rt();
                checking_img_3_rt();
                checking_img_4_rt();
                checking_img_5_rt();
                checking_img_6();
                checking_img_7_rt();
                checking_img_8_rt();
                checking_img_9_rt();
                checking_img_10_rt();
                break;
            case R.id.pollActivity_imageView_userAddContent_7 :
                checking_img_1_rt();
                checking_img_2_rt();
                checking_img_3_rt();
                checking_img_4_rt();
                checking_img_5_rt();
                checking_img_6_rt();
                checking_img_7();
                checking_img_8_rt();
                checking_img_9_rt();
                checking_img_10_rt();
                break;
            case R.id.pollActivity_imageView_userAddContent_8 :
                checking_img_1_rt();
                checking_img_2_rt();
                checking_img_3_rt();
                checking_img_4_rt();
                checking_img_5_rt();
                checking_img_6_rt();
                checking_img_7_rt();
                checking_img_8();
                checking_img_9_rt();
                checking_img_10_rt();
                break;
            case R.id.pollActivity_imageView_userAddContent_9 :
                checking_img_1_rt();
                checking_img_2_rt();
                checking_img_3_rt();
                checking_img_4_rt();
                checking_img_5_rt();
                checking_img_6_rt();
                checking_img_7_rt();
                checking_img_8_rt();
                checking_img_9();
                checking_img_10_rt();
                break;
            case R.id.pollActivity_imageView_userAddContent_10 :
                checking_img_1_rt();
                checking_img_2_rt();
                checking_img_3_rt();
                checking_img_4_rt();
                checking_img_5_rt();
                checking_img_6_rt();
                checking_img_7_rt();
                checking_img_8_rt();
                checking_img_9_rt();
                checking_img_10();
                break;
        }

    }

    public void checking_img_1() {
        pollActivity_imageView_userAddContent_1.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_1.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_outline_check_24px).into(pollActivity_imageView_userAddContent_check_1).getView();
    }
    public void checking_img_1_rt() {
        pollActivity_imageView_userAddContent_1.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_1.setVisibility(View.GONE);
    }

    public void checking_img_2() {
        pollActivity_imageView_userAddContent_2.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_2.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_outline_check_24px).into(pollActivity_imageView_userAddContent_check_2).getView();
    }
    public void checking_img_2_rt() {
        pollActivity_imageView_userAddContent_2.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_2.setVisibility(View.GONE);
    }

    public void checking_img_3() {
        pollActivity_imageView_userAddContent_3.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_3.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_outline_check_24px).into(pollActivity_imageView_userAddContent_check_3).getView();
    }
    public void checking_img_3_rt() {
        pollActivity_imageView_userAddContent_3.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_3.setVisibility(View.GONE);
    }

    public void checking_img_4() {
        pollActivity_imageView_userAddContent_4.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_4.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_outline_check_24px).into(pollActivity_imageView_userAddContent_check_4).getView();
    }
    public void checking_img_4_rt() {
        pollActivity_imageView_userAddContent_4.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_4.setVisibility(View.GONE);
    }

    public void checking_img_5() {
        pollActivity_imageView_userAddContent_5.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_5.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_outline_check_24px).into(pollActivity_imageView_userAddContent_check_5).getView();
    }
    public void checking_img_5_rt() {
        pollActivity_imageView_userAddContent_5.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_5.setVisibility(View.GONE);
    }

    public void checking_img_6() {
        pollActivity_imageView_userAddContent_6.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_6.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_outline_check_24px).into(pollActivity_imageView_userAddContent_check_6).getView();
    }
    public void checking_img_6_rt() {
        pollActivity_imageView_userAddContent_6.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_6.setVisibility(View.GONE);
    }

    public void checking_img_7() {
        pollActivity_imageView_userAddContent_7.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_7.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_outline_check_24px).into(pollActivity_imageView_userAddContent_check_7).getView();
    }
    public void checking_img_7_rt() {
        pollActivity_imageView_userAddContent_7.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_7.setVisibility(View.GONE);
    }

    public void checking_img_8() {
        pollActivity_imageView_userAddContent_8.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_8.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_outline_check_24px).into(pollActivity_imageView_userAddContent_check_8).getView();
    }
    public void checking_img_8_rt() {
        pollActivity_imageView_userAddContent_8.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_8.setVisibility(View.GONE);
    }

    public void checking_img_9() {
        pollActivity_imageView_userAddContent_9.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_9.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_outline_check_24px).into(pollActivity_imageView_userAddContent_check_9).getView();
    }
    public void checking_img_9_rt() {
        pollActivity_imageView_userAddContent_9.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_9.setVisibility(View.GONE);
    }

    public void checking_img_10() {
        pollActivity_imageView_userAddContent_10.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_10.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_outline_check_24px).into(pollActivity_imageView_userAddContent_check_10).getView();
    }
    public void checking_img_10_rt() {
        pollActivity_imageView_userAddContent_10.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_10.setVisibility(View.GONE);
    }

}
