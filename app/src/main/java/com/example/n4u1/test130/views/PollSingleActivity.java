package com.example.n4u1.test130.views;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ContentDTO;
import com.example.n4u1.test130.models.ReplyDTO;
import com.example.n4u1.test130.models.User;
import com.example.n4u1.test130.recyclerview.ReplyAdapter;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

public class PollSingleActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean ACTIVITY_REPLY_FLAG;
    private boolean ACTIVITY_RESULT_FLAG;
    private int pickCandidate = 0;

    private FirebaseAuth auth;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mDatabaseReferencePicker;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseDatabase mFirebaseDatabase;

    final ArrayList<ReplyDTO> replyDTOS = new ArrayList<>();
    final ReplyAdapter replyAdapter = new ReplyAdapter(this, replyDTOS);

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

    HorizontalBarChart pollActivity_horizontalBarChart_result;
    ImageView pollActivity_imageView_result_downButton, pollActivity_imageView_reply_upButton,
            pollActivity_imageView_result_upButton, pollActivity_imageView_reply_downButton;
    Button pollActivity_button_statistic;
    ImageView pollActivity_button_replySend;
    TextInputLayout pollActivity_textInputLayout_reply;
    EditText pollActivity_editText_reply;
    RecyclerView pollActivity_recyclerView_reply;
    RelativeLayout pollActivity_relativeLayout_result, pollActivity_relativeLayout_reply;
    TextView pollActivity_textView_result, pollActivity_textView_reply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_single);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("  AQA");
        }
        getSupportActionBar().setIcon(R.drawable.ic_do_not_disturb_black_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        final int contentAmount = getIntent().getIntExtra("itemViewType", 0);
        final String contentKey = getIntent().getStringExtra("contentKey");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("user_contents").child(contentKey);
        mDatabaseReferencePicker = FirebaseDatabase.getInstance().getReference("users");
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

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
        pollActivity_relativeLayout_result = findViewById(R.id.pollActivity_relativeLayout_result);
        pollActivity_relativeLayout_reply = findViewById(R.id.pollActivity_relativeLayout_reply);
//        pollActivity_imageView_test = findViewById(R.id.pollActivity_imageView_test);
        pollActivity_horizontalBarChart_result = findViewById(R.id.pollActivity_horizontalBarChart_result);
        pollActivity_imageView_result_downButton = findViewById(R.id.pollActivity_imageView_result_downButton);
        pollActivity_imageView_result_upButton = findViewById(R.id.pollActivity_imageView_result_upButton);
        pollActivity_imageView_reply_downButton = findViewById(R.id.pollActivity_imageView_reply_downButton);
        pollActivity_imageView_reply_upButton = findViewById(R.id.pollActivity_imageView_reply_upButton);
        pollActivity_recyclerView_reply = findViewById(R.id.pollActivity_recyclerView_reply);
        pollActivity_textView_result = findViewById(R.id.pollActivity_textView_result);
        pollActivity_textView_reply = findViewById(R.id.pollActivity_textView_reply);
        pollActivity_editText_reply = findViewById(R.id.pollActivity_editText_reply);
        pollActivity_button_replySend = findViewById(R.id.pollActivity_button_replySend);

        pollActivity_button_statistic = findViewById(R.id.pollActivity_button_statistic);

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



        //통계테스트 버튼
        pollActivity_button_statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseReference.child("contentPicker").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Object> contentPicker = (Map<String, Object>) dataSnapshot.getValue();
                        Set set = contentPicker.keySet();
                        int contentHit = set.size();
                        ArrayList<String> pickerUid = new ArrayList<>(set);
                        Log.d("statistic1", pickerUid.get(0));
                        Log.d("statistic2", pickerUid.get(1));
                        Log.d("statistic3", pickerUid.get(2));
                        Log.d("statistic4", pickerUid.get(3));

                        


                        mDatabaseReferencePicker.child(pickerUid.get(1)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Map<String, Object> user = (Map<String, Object>) dataSnapshot.getValue();
                                Log.d("lkj user", user.get("job").toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        //처음 댓글펼치기 버튼의 setText (리플 갯수 넣기위함)
        firebaseDatabase.getReference().child("user_contents").child(contentKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ContentDTO contentDTO = dataSnapshot.getValue(ContentDTO.class);
                int replyCount = contentDTO.getReplyCount();
                pollActivity_textView_reply.setText("댓글 펼치기(" + replyCount + ")");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //투표하고 결과보기
        pollActivity_relativeLayout_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResultClicked(firebaseDatabase.getReference().child("user_contents").child(contentKey), currentPick());
            }
        });


        //댓글 펼치기
        pollActivity_relativeLayout_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReply(contentKey);
                firebaseDatabase.getReference().child("reply").child(contentKey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        replyDTOS.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ReplyDTO replyDTO = snapshot.getValue(ReplyDTO.class);
                            replyDTOS.add(replyDTO);
                        }
                        replyAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        //댓글 리사이클러뷰
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());//getApplicationContext()전에 this,?? 였음
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.isSmoothScrollbarEnabled();
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setReverseLayout(false);
        pollActivity_recyclerView_reply.setLayoutManager(mLayoutManager);
//        final PostAdapter postAdapter = new PostAdapter(getApplication(), contentDTOS); //20180730 전날꺼 보기 getApplication()전에 this,contentDTOS 였음
        pollActivity_recyclerView_reply.setAdapter(replyAdapter);


        //reply button click, 댓글달기버튼
        pollActivity_button_replySend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = getDate();
                onReplyClicked(firebaseDatabase.getReference().child("user_contents").child(contentKey));
                ReplyDTO replyDTO = new ReplyDTO();
                replyDTO.setDate(date);
                replyDTO.setId(auth.getCurrentUser().getEmail());
                replyDTO.setReply(pollActivity_editText_reply.getText().toString());
                replyDTO.setContentKey(contentKey);
                firebaseDatabase.getReference().child("reply").child(contentKey).push().setValue(replyDTO);
                firebaseDatabase.getReference().child("users").child(auth.getCurrentUser().getUid()).child("reply").child(contentKey).push().setValue(replyDTO);
                pollActivity_editText_reply.setText(null);//editText 초기화
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //키보드 숨기기
                inputMethodManager.hideSoftInputFromWindow(pollActivity_editText_reply.getWindowToken(), 0); //키보드 숨기기


            }
        });


        //contentDTO binding
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ContentDTO contentDTO = dataSnapshot.getValue(ContentDTO.class);
                pollActivity_textView_date.setText(contentDTO.getUploadDate());
                pollActivity_textView_title.setText(contentDTO.getTitle());
                pollActivity_textView_contentType.setText(contentDTO.getContentType());
                pollActivity_textView_description.setText(contentDTO.getDescription());
                pollActivity_textView_pollMode.setText(contentDTO.getPollMode());
                switch (contentDTO.getItemViewType()) {
                    case 1:
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).into(pollActivity_imageView_userAddContent_1).getView();
                        break;
                    case 2:
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).into(pollActivity_imageView_userAddContent_1).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).into(pollActivity_imageView_userAddContent_2).getView();
                        break;
                    case 3:
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_3.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).into(pollActivity_imageView_userAddContent_1).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).into(pollActivity_imageView_userAddContent_2).getView();
                        Glide.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).into(pollActivity_imageView_userAddContent_3).getView();
                        break;
                    case 4:
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
                    case 6:
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
                    case 7:
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
                    case 8:
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
                    case 9:
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
                    case 10:
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


    private boolean pollChecking() {
        if (pollActivity_imageView_userAddContent_1.getAlpha() == 0.7f ||
                pollActivity_imageView_userAddContent_2.getAlpha() == 0.7f ||
                pollActivity_imageView_userAddContent_3.getAlpha() == 0.7f ||
                pollActivity_imageView_userAddContent_4.getAlpha() == 0.7f ||
                pollActivity_imageView_userAddContent_5.getAlpha() == 0.7f ||
                pollActivity_imageView_userAddContent_6.getAlpha() == 0.7f ||
                pollActivity_imageView_userAddContent_7.getAlpha() == 0.7f ||
                pollActivity_imageView_userAddContent_8.getAlpha() == 0.7f ||
                pollActivity_imageView_userAddContent_9.getAlpha() == 0.7f ||
                pollActivity_imageView_userAddContent_10.getAlpha() == 0.7f) {
            return true;
        } else {
            return false;
        }


    }

    /**
     * onCreate()
     */

    //댓글펼치기
    private void openReply(String currentContentKey) {
        firebaseDatabase.getReference().child("user_contents").child(currentContentKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ContentDTO contentDTO = dataSnapshot.getValue(ContentDTO.class);
                int replyCount = contentDTO.getReplyCount();
                if (!ACTIVITY_REPLY_FLAG) {
                    pollActivity_imageView_reply_downButton.setVisibility(View.GONE);
                    pollActivity_imageView_reply_upButton.setVisibility(View.VISIBLE);
                    pollActivity_textView_reply.setText("접기");
                    pollActivity_recyclerView_reply.setVisibility(View.VISIBLE);
                    pollActivity_editText_reply.setVisibility(View.VISIBLE);
                    pollActivity_button_replySend.setVisibility(View.VISIBLE);
                    ACTIVITY_REPLY_FLAG = true;
                } else {
                    pollActivity_imageView_reply_downButton.setVisibility(View.VISIBLE);
                    pollActivity_imageView_reply_upButton.setVisibility(View.GONE);
                    pollActivity_textView_reply.setText("댓글 펼치기 (" + replyCount + ")");
                    pollActivity_recyclerView_reply.setVisibility(View.GONE);
                    pollActivity_editText_reply.setVisibility(View.GONE);
                    pollActivity_button_replySend.setVisibility(View.GONE);
                    ACTIVITY_REPLY_FLAG = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //투표하고 결과보기
    private void openResult(int contentN) {
        if (!ACTIVITY_RESULT_FLAG) {
            pollActivity_imageView_result_downButton.setVisibility(View.GONE);
            pollActivity_imageView_result_upButton.setVisibility(View.VISIBLE);
            pollActivity_textView_result.setText("접기");
            pollActivity_horizontalBarChart_result.setVisibility(View.VISIBLE);
            setChartData(contentN, 100); //bar개수 : contentN개, 가로 최대길이 range
            int cp = currentPick();
            Log.d("pickCandidate", String.valueOf(cp));
            ACTIVITY_RESULT_FLAG = true;
        } else {
            pollActivity_imageView_result_downButton.setVisibility(View.VISIBLE);
            pollActivity_imageView_result_upButton.setVisibility(View.GONE);
            pollActivity_textView_result.setText("결과보기");
            pollActivity_horizontalBarChart_result.setVisibility(View.GONE);
            ACTIVITY_RESULT_FLAG = false;
        }
    }

    private void setChartData(final int contentN, final int range) {
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> contentDTO = (Map<String, Object>) dataSnapshot.getValue();

                ArrayList<BarEntry> yValue = new ArrayList<>();
                ArrayList<Integer> tmp = new ArrayList<>();
                float barWidth = 1.2f;
                float spaceForBar = 2f;

                Object object0 = contentDTO.get("candidateScore_0");
                Object object1 = contentDTO.get("candidateScore_1");
                Object object2 = contentDTO.get("candidateScore_2");
                Object object3 = contentDTO.get("candidateScore_3");
                Object object4 = contentDTO.get("candidateScore_4");
                Object object5 = contentDTO.get("candidateScore_5");
                Object object6 = contentDTO.get("candidateScore_6");
                Object object7 = contentDTO.get("candidateScore_7");
                Object object8 = contentDTO.get("candidateScore_8");
                Object object9 = contentDTO.get("candidateScore_9");

                tmp.add(Integer.parseInt(object0.toString()));
                tmp.add(Integer.parseInt(object1.toString()));
                tmp.add(Integer.parseInt(object2.toString()));
                tmp.add(Integer.parseInt(object3.toString()));
                tmp.add(Integer.parseInt(object4.toString()));
                tmp.add(Integer.parseInt(object5.toString()));
                tmp.add(Integer.parseInt(object6.toString()));
                tmp.add(Integer.parseInt(object7.toString()));
                tmp.add(Integer.parseInt(object8.toString()));
                tmp.add(Integer.parseInt(object9.toString()));

                for (int i = 0; i < contentN; i++) {
                    yValue.add(new BarEntry((float)i+1 , tmp.get(i)));
                }

                BarDataSet set1;
                set1 = new BarDataSet(yValue, null);
                set1.setColor(Color.GRAY);
                Legend legend = pollActivity_horizontalBarChart_result.getLegend();
                BarData data1 = new BarData(set1);
                legend.setEnabled(true);
                set1.setHighlightEnabled(true);
                set1.setHighLightColor(Color.GREEN);
                data1.setBarWidth(0.8f); //바 크기
                pollActivity_horizontalBarChart_result.setTouchEnabled(true);
                pollActivity_horizontalBarChart_result.setDragEnabled(false);
                pollActivity_horizontalBarChart_result.setDoubleTapToZoomEnabled(false);
                pollActivity_horizontalBarChart_result.setPinchZoom(false);
                pollActivity_horizontalBarChart_result.setDescription(null);
                pollActivity_horizontalBarChart_result.animateY(3000);
                pollActivity_horizontalBarChart_result.setFitBars(true);
                pollActivity_horizontalBarChart_result.getXAxis().setPosition(XAxis.XAxisPosition.TOP);
                pollActivity_horizontalBarChart_result.getXAxis().setXOffset(-350);
                pollActivity_horizontalBarChart_result.getAxisLeft().setEnabled(false);
                pollActivity_horizontalBarChart_result.getAxisRight().setEnabled(false);
                pollActivity_horizontalBarChart_result.getXAxis().setValueFormatter(new LargeValueFormatter());
                pollActivity_horizontalBarChart_result.getXAxis().setEnabled(false);
                pollActivity_horizontalBarChart_result.setDrawGridBackground(false);
//        mChart.setBackgroundColor(Color.YELLOW);
                pollActivity_horizontalBarChart_result.setData(data1);
                pollActivity_horizontalBarChart_result.invalidate(); //refresh



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    //이미지 선택시 체크하기
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pollActivity_imageView_userAddContent_1:
                if (pollActivity_imageView_userAddContent_1.getAlpha() == 0.7f) {
                    checking_img_1_rt();
                } else {
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
                }
                break;
            case R.id.pollActivity_imageView_userAddContent_2:
                if (pollActivity_imageView_userAddContent_2.getAlpha() == 0.7f) {
                    checking_img_2_rt();
                } else {
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
                }
                break;
            case R.id.pollActivity_imageView_userAddContent_3:
                if (pollActivity_imageView_userAddContent_3.getAlpha() == 0.7f) {
                    checking_img_3_rt();
                } else {
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
                }
                break;
            case R.id.pollActivity_imageView_userAddContent_4:
                if (pollActivity_imageView_userAddContent_4.getAlpha() == 0.7f) {
                    checking_img_4_rt();
                } else {
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
                }
                break;
            case R.id.pollActivity_imageView_userAddContent_5:
                if (pollActivity_imageView_userAddContent_5.getAlpha() == 0.7f) {
                    checking_img_5_rt();
                } else {
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
                }
                break;
            case R.id.pollActivity_imageView_userAddContent_6:
                if (pollActivity_imageView_userAddContent_6.getAlpha() == 0.7f) {
                    checking_img_6_rt();
                } else {
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
                }
                break;
            case R.id.pollActivity_imageView_userAddContent_7:
                if (pollActivity_imageView_userAddContent_7.getAlpha() == 0.7f) {
                    checking_img_7_rt();
                } else {
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
                }
                break;
            case R.id.pollActivity_imageView_userAddContent_8:
                if (pollActivity_imageView_userAddContent_8.getAlpha() == 0.7f) {
                    checking_img_8_rt();
                } else {
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
                }
                break;
            case R.id.pollActivity_imageView_userAddContent_9:
                if (pollActivity_imageView_userAddContent_9.getAlpha() == 0.7f) {
                    checking_img_9_rt();
                } else {
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
                }
                break;
            case R.id.pollActivity_imageView_userAddContent_10:
                if (pollActivity_imageView_userAddContent_10.getAlpha() == 0.7f) {
                    checking_img_10_rt();
                } else {
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
                }
                break;
        }
    }

    private void onReplyClicked(final DatabaseReference postRef) {
        final String date = getDate();
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                ContentDTO contentDTO = mutableData.getValue(ContentDTO.class);
                if (contentDTO == null) {
                    return Transaction.success(mutableData);
                }
                contentDTO.reply.put(date, pollActivity_editText_reply.getText().toString());
                mutableData.setValue(contentDTO);
                firebaseDatabase.getReference().child("reply").child(postRef.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        firebaseDatabase.getReference().child("user_contents").child(postRef.getKey()).child("replyCount").setValue(dataSnapshot.getChildrenCount());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {


                // Transaction completed

            }
        });
    }


    private void onResultClicked(final DatabaseReference postRef, int candidate) {
        final int contentAmount = getIntent().getIntExtra("itemViewType", 0);
        Log.d("lkj contentAmount", String.valueOf(contentAmount));
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                ContentDTO contentDTO = mutableData.getValue(ContentDTO.class);
                if (contentDTO == null) {
                    return Transaction.success(mutableData);
                }
                if (contentDTO.contentPicker.containsKey(auth.getCurrentUser().getUid())) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            openResult(contentAmount);
                        }
                    });

                } else {

                    if (pollChecking()) { //체크되있으면 true, 안되있으면 false
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                openResult(contentAmount);
                            }
                        });
                        contentDTO.contentHit = contentDTO.contentHit + 1;
                        if (currentPick() == 0)
                            contentDTO.candidateScore_0 = contentDTO.candidateScore_0 + 1;
                        if (currentPick() == 1)
                            contentDTO.candidateScore_1 = contentDTO.candidateScore_1 + 1;
                        if (currentPick() == 2)
                            contentDTO.candidateScore_2 = contentDTO.candidateScore_2 + 1;
                        if (currentPick() == 3)
                            contentDTO.candidateScore_3 = contentDTO.candidateScore_3 + 1;
                        if (currentPick() == 4)
                            contentDTO.candidateScore_4 = contentDTO.candidateScore_4 + 1;
                        if (currentPick() == 5)
                            contentDTO.candidateScore_5 = contentDTO.candidateScore_5 + 1;
                        if (currentPick() == 6)
                            contentDTO.candidateScore_6 = contentDTO.candidateScore_6 + 1;
                        if (currentPick() == 7)
                            contentDTO.candidateScore_7 = contentDTO.candidateScore_7 + 1;
                        if (currentPick() == 8)
                            contentDTO.candidateScore_8 = contentDTO.candidateScore_8 + 1;
                        if (currentPick() == 9)
                            contentDTO.candidateScore_9 = contentDTO.candidateScore_9 + 1;
                        contentDTO.contentPicker.put(auth.getCurrentUser().getUid(), currentPick());
                        String key = getIntent().getStringExtra("contentKey");
                        firebaseDatabase.getReference()
                                .child("users")
                                .child(auth.getCurrentUser().getUid())
                                .child("pickContent")
                                .child(key)
                                .setValue(currentPick());
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "투표를 먼저 해주세요~!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                // Set value and report transaction success
                mutableData.setValue(contentDTO);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d("lkjlkj", "postTransaction:onComplete:" + databaseError);
            }
        });
    }


    public String getDate() {
        TimeZone timeZone;
        timeZone = TimeZone.getTimeZone("Asia/Seoul");
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyMMdd-HHmmss-SS", Locale.KOREAN);
        df.setTimeZone(timeZone);
        String currentDate = df.format(date);
        return currentDate;
    }


    private int currentPick() {
        if (pollActivity_imageView_userAddContent_1.getAlpha() == 0.7f) {
            return 0;
        } else if (pollActivity_imageView_userAddContent_2.getAlpha() == 0.7f) {
            return 1;
        } else if (pollActivity_imageView_userAddContent_3.getAlpha() == 0.7f) {
            return 2;
        } else if (pollActivity_imageView_userAddContent_4.getAlpha() == 0.7f) {
            return 3;
        } else if (pollActivity_imageView_userAddContent_5.getAlpha() == 0.7f) {
            return 4;
        } else if (pollActivity_imageView_userAddContent_6.getAlpha() == 0.7f) {
            return 5;
        } else if (pollActivity_imageView_userAddContent_7.getAlpha() == 0.7f) {
            return 6;
        } else if (pollActivity_imageView_userAddContent_8.getAlpha() == 0.7f) {
            return 7;
        } else if (pollActivity_imageView_userAddContent_9.getAlpha() == 0.7f) {
            return 8;
        } else if (pollActivity_imageView_userAddContent_10.getAlpha() == 0.7f) {
            return 9;
        } else return 0;

    }

    public void checking_img_1() {

        pollActivity_imageView_userAddContent_1.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_1.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_check_black_24dp).into(pollActivity_imageView_userAddContent_check_1).getView();
    }


    public void checking_img_1_rt() {
        pickCandidate = 0;
        pollActivity_imageView_userAddContent_1.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_1.setVisibility(View.GONE);
    }

    public void checking_img_2() {
        pickCandidate = 2;
        pollActivity_imageView_userAddContent_2.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_2.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_check_black_24dp).into(pollActivity_imageView_userAddContent_check_2).getView();
    }

    public void checking_img_2_rt() {
        pickCandidate = 0;
        pollActivity_imageView_userAddContent_2.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_2.setVisibility(View.GONE);
    }

    public void checking_img_3() {
        pickCandidate = 3;
        pollActivity_imageView_userAddContent_3.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_3.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_check_black_24dp).into(pollActivity_imageView_userAddContent_check_3).getView();
    }

    public void checking_img_3_rt() {
        pickCandidate = 0;
        pollActivity_imageView_userAddContent_3.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_3.setVisibility(View.GONE);
    }

    public void checking_img_4() {
        pickCandidate = 4;
        pollActivity_imageView_userAddContent_4.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_4.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_check_black_24dp).into(pollActivity_imageView_userAddContent_check_4).getView();
    }

    public void checking_img_4_rt() {
        pickCandidate = 0;
        pollActivity_imageView_userAddContent_4.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_4.setVisibility(View.GONE);
    }

    public void checking_img_5() {
        pickCandidate = 5;
        pollActivity_imageView_userAddContent_5.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_5.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_check_black_24dp).into(pollActivity_imageView_userAddContent_check_5).getView();
    }

    public void checking_img_5_rt() {
        pickCandidate = 0;
        pollActivity_imageView_userAddContent_5.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_5.setVisibility(View.GONE);
    }

    public void checking_img_6() {
        pickCandidate = 6;
        pollActivity_imageView_userAddContent_6.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_6.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_check_black_24dp).into(pollActivity_imageView_userAddContent_check_6).getView();
    }

    public void checking_img_6_rt() {
        pickCandidate = 0;
        pollActivity_imageView_userAddContent_6.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_6.setVisibility(View.GONE);
    }

    public void checking_img_7() {
        pickCandidate = 7;
        pollActivity_imageView_userAddContent_7.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_7.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_check_black_24dp).into(pollActivity_imageView_userAddContent_check_7).getView();
    }

    public void checking_img_7_rt() {
        pickCandidate = 0;
        pollActivity_imageView_userAddContent_7.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_7.setVisibility(View.GONE);
    }

    public void checking_img_8() {
        pickCandidate = 8;
        pollActivity_imageView_userAddContent_8.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_8.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_check_black_24dp).into(pollActivity_imageView_userAddContent_check_8).getView();
    }

    public void checking_img_8_rt() {
        pickCandidate = 0;
        pollActivity_imageView_userAddContent_8.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_8.setVisibility(View.GONE);
    }

    public void checking_img_9() {
        pickCandidate = 9;
        pollActivity_imageView_userAddContent_9.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_9.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_check_black_24dp).into(pollActivity_imageView_userAddContent_check_9).getView();
    }

    public void checking_img_9_rt() {
        pickCandidate = 0;
        pollActivity_imageView_userAddContent_9.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_9.setVisibility(View.GONE);
    }

    public void checking_img_10() {
        pickCandidate = 10;
        pollActivity_imageView_userAddContent_10.setAlpha(0.7f);
        pollActivity_imageView_userAddContent_check_10.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_check_black_24dp).into(pollActivity_imageView_userAddContent_check_10).getView();
    }

    public void checking_img_10_rt() {
        pickCandidate = 0;
        pollActivity_imageView_userAddContent_10.setAlpha(1.0f);
        pollActivity_imageView_userAddContent_check_10.setVisibility(View.GONE);
    }

}
