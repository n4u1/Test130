package com.example.n4u1.test130.views;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.n4u1.test130.R;
import com.example.n4u1.test130.dialog.ContentChoiceDialog;
import com.example.n4u1.test130.dialog.PollResultDialog;
import com.example.n4u1.test130.models.ContentDTO;
import com.example.n4u1.test130.models.ReplyDTO;
import com.example.n4u1.test130.models.User;
import com.example.n4u1.test130.recyclerview.PostViewHolder2;
import com.example.n4u1.test130.recyclerview.ReplyAdapter;
import com.example.n4u1.test130.util.GlideApp;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.net.URI;
import java.text.DecimalFormat;
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
    private String isImageFitToScreen;

    private FirebaseAuth auth;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mDatabaseReferencePicker;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseDatabase mFirebaseDatabase;

    final ArrayList<ReplyDTO> replyDTOS = new ArrayList<>();
    final ReplyAdapter replyAdapter = new ReplyAdapter(this, replyDTOS);

    private GestureDetector mGestureDetector;

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

    ImageView pollActivity_imageView_choice_1, pollActivity_imageView_choice_2,
            pollActivity_imageView_choice_3, pollActivity_imageView_choice_4,
            pollActivity_imageView_choice_5, pollActivity_imageView_choice_6,
            pollActivity_imageView_choice_7, pollActivity_imageView_choice_8,
            pollActivity_imageView_choice_9, pollActivity_imageView_choice_10,
            pollActivity_imageView_around_1, pollActivity_imageView_around_2,
            pollActivity_imageView_around_3, pollActivity_imageView_around_4,
            pollActivity_imageView_around_5, pollActivity_imageView_around_6,
            pollActivity_imageView_around_7, pollActivity_imageView_around_8,
            pollActivity_imageView_around_9, pollActivity_imageView_around_10;

    HorizontalBarChart pollActivity_horizontalBarChart_result;
    ImageView pollActivity_imageView_result_downButton, pollActivity_imageView_reply_upButton,
            pollActivity_imageView_result_upButton, pollActivity_imageView_reply_downButton;
    //    Button pollActivity_button_statistic;
    ImageView pollActivity_button_replySend;
    EditText pollActivity_editText_reply;
    RecyclerView pollActivity_recyclerView_reply;
    RelativeLayout pollActivity_relativeLayout_result, pollActivity_relativeLayout_reply;
    TextView pollActivity_textView_result, pollActivity_textView_reply;


    TextView pollActivity_textView_check_1, pollActivity_textView_check_2,
            pollActivity_textView_check_3, pollActivity_textView_check_4,
            pollActivity_textView_check_5, pollActivity_textView_check_6,
            pollActivity_textView_check_7, pollActivity_textView_check_8,
            pollActivity_textView_check_9, pollActivity_textView_check_10;


    final ArrayList<String> pickerAge = new ArrayList<>();
    final ArrayList<String> pickerJob = new ArrayList<>();
    final ArrayList<String> pickerJob_ = new ArrayList<>();
    final ArrayList<String> pickerGender = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_single);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(" ");
        }
        getSupportActionBar().setIcon(R.drawable.aqa);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        final String contentKey = getIntent().getStringExtra("contentKey");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("user_contents").child(contentKey);
        mDatabaseReferencePicker = FirebaseDatabase.getInstance().getReference("users");
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();


        pollActivity_imageView_around_1 = findViewById(R.id.pollActivity_imageView_around_1);

//        이미지뷰 라운드 처리는...
//        GradientDrawable gradientDrawable = getApplicationContext().getDrawable(R.drawable.background_rounding);
//        pollActivity_imageView_choice_1.setBackground(gradientDrawable);
//        pollActivity_imageView_choice_1.setClipToOutline(true);
//        pollActivity_imageView_around_1.setBackground(gradientDrawable);
//        pollActivity_imageView_around_1.setClipToOutline(true);


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

        pollActivity_imageView_choice_1 = findViewById(R.id.pollActivity_imageView_choice_1);
        pollActivity_imageView_choice_2 = findViewById(R.id.pollActivity_imageView_choice_2);
        pollActivity_imageView_choice_3 = findViewById(R.id.pollActivity_imageView_choice_3);
        pollActivity_imageView_choice_4 = findViewById(R.id.pollActivity_imageView_choice_4);
        pollActivity_imageView_choice_5 = findViewById(R.id.pollActivity_imageView_choice_5);
        pollActivity_imageView_choice_6 = findViewById(R.id.pollActivity_imageView_choice_6);
        pollActivity_imageView_choice_7 = findViewById(R.id.pollActivity_imageView_choice_7);
        pollActivity_imageView_choice_8 = findViewById(R.id.pollActivity_imageView_choice_8);
        pollActivity_imageView_choice_9 = findViewById(R.id.pollActivity_imageView_choice_9);
        pollActivity_imageView_choice_10 = findViewById(R.id.pollActivity_imageView_choice_10);

        pollActivity_textView_check_1 = findViewById(R.id.pollActivity_textView_check_1);
        pollActivity_textView_check_2 = findViewById(R.id.pollActivity_textView_check_2);
        pollActivity_textView_check_3 = findViewById(R.id.pollActivity_textView_check_3);
        pollActivity_textView_check_4 = findViewById(R.id.pollActivity_textView_check_4);
        pollActivity_textView_check_5 = findViewById(R.id.pollActivity_textView_check_5);
        pollActivity_textView_check_6 = findViewById(R.id.pollActivity_textView_check_6);
        pollActivity_textView_check_7 = findViewById(R.id.pollActivity_textView_check_7);
        pollActivity_textView_check_8 = findViewById(R.id.pollActivity_textView_check_8);
        pollActivity_textView_check_9 = findViewById(R.id.pollActivity_textView_check_9);
        pollActivity_textView_check_10 = findViewById(R.id.pollActivity_textView_check_10);

        pollActivity_imageView_around_1 = findViewById(R.id.pollActivity_imageView_around_1);
        pollActivity_imageView_around_2 = findViewById(R.id.pollActivity_imageView_around_2);
        pollActivity_imageView_around_3 = findViewById(R.id.pollActivity_imageView_around_3);
        pollActivity_imageView_around_4 = findViewById(R.id.pollActivity_imageView_around_4);
        pollActivity_imageView_around_5 = findViewById(R.id.pollActivity_imageView_around_5);
        pollActivity_imageView_around_6 = findViewById(R.id.pollActivity_imageView_around_6);
        pollActivity_imageView_around_7 = findViewById(R.id.pollActivity_imageView_around_7);
        pollActivity_imageView_around_8 = findViewById(R.id.pollActivity_imageView_around_8);
        pollActivity_imageView_around_9 = findViewById(R.id.pollActivity_imageView_around_9);
        pollActivity_imageView_around_10 = findViewById(R.id.pollActivity_imageView_around_10);

//        pollActivity_button_statistic = findViewById(R.id.pollActivity_button_statistic);

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
        pollActivity_textView_check_1.setOnClickListener(this);
        pollActivity_textView_check_2.setOnClickListener(this);
        pollActivity_textView_check_3.setOnClickListener(this);
        pollActivity_textView_check_4.setOnClickListener(this);
        pollActivity_textView_check_5.setOnClickListener(this);
        pollActivity_textView_check_6.setOnClickListener(this);
        pollActivity_textView_check_7.setOnClickListener(this);
        pollActivity_textView_check_8.setOnClickListener(this);
        pollActivity_textView_check_9.setOnClickListener(this);
        pollActivity_textView_check_10.setOnClickListener(this);


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


        //투표하고 결과보기 클릭
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


        //이미지 크게보기
//        isImageFitToScreen = getIntent().getStringExtra("fullScreenIndicator");
//        if ("y".equals(isImageFitToScreen)) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getSupportActionBar().hide();
//
//            Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/test130-1068f.appspot.com/o/images%2F3qq998d5x3ire7qynj72.jpg?alt=media&token=feddba03-1e1d-4131-a713-b0f4042a7653");
//
//            pollActivity_imageView_userAddContent_1.setImageURI(uri);
//            pollActivity_imageView_userAddContent_1.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
//            pollActivity_imageView_userAddContent_1.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
//            pollActivity_imageView_userAddContent_1.setAdjustViewBounds(false);
//            pollActivity_imageView_userAddContent_1.setScaleType(ImageView.ScaleType.FIT_XY);
//        }


        //contentDTO init binding
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
                        pollActivity_textView_check_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_1).getView();
                        break;
                    case 2:
                        pollActivity_textView_check_1.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_2).getView();
                        break;
                    case 3:
                        pollActivity_textView_check_1.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_2.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_3.setVisibility(View.VISIBLE);
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_2).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_3).getView();
                        break;
                    case 4:
                        pollActivity_textView_check_1.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_2.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_3.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_4.setVisibility(View.VISIBLE);
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_2).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_3).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_4).getView();
                        break;
                    case 5:
                        pollActivity_textView_check_1.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_2.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_3.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_4.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_5.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_5.setVisibility(View.VISIBLE);
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_2).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_3).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_4).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_5).getView();
                        break;
                    case 6:
                        pollActivity_textView_check_1.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_2.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_3.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_4.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_5.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_6.setVisibility(View.VISIBLE);
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_2).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_3).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_4).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_5).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_5()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_6).getView();
                        break;
                    case 7:
                        pollActivity_textView_check_1.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_2.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_3.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_4.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_5.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_6.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_7.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_7.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_7.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_7.setVisibility(View.VISIBLE);
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_2).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_3).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_4).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_5).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_5()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_6).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_6()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_7).getView();
                        break;
                    case 8:
                        pollActivity_textView_check_1.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_2.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_3.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_4.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_5.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_6.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_7.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_8.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_7.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_8.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_7.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_8.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_7.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_8.setVisibility(View.VISIBLE);
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_2).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_3).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_4).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_5).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_5()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_6).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_6()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_7).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_7()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_8).getView();
                        break;
                    case 9:
                        pollActivity_textView_check_1.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_2.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_3.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_4.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_5.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_6.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_7.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_8.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_9.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_7.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_8.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_9.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_7.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_8.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_9.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_7.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_8.setVisibility(View.VISIBLE);
                        pollActivity_imageView_userAddContent_9.setVisibility(View.VISIBLE);
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_2).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_3).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_4).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_5).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_5()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_6).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_6()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_7).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_7()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_8).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_8()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_9).getView();
                        break;
                    case 10:
                        pollActivity_textView_check_1.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_2.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_3.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_4.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_5.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_6.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_7.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_8.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_9.setVisibility(View.VISIBLE);
                        pollActivity_textView_check_10.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_7.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_8.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_9.setVisibility(View.VISIBLE);
                        pollActivity_imageView_around_10.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_1.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_2.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_3.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_4.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_5.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_6.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_7.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_8.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_9.setVisibility(View.VISIBLE);
                        pollActivity_imageView_choice_10.setVisibility(View.VISIBLE);
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
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_2).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_3).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_4).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_5).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_5()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_6).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_6()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_7).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_7()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_8).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_8()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_9).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_9()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loading_25)).into(pollActivity_imageView_userAddContent_10).getView();
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * onCreate()
     */


    //직업별 투표 결과
    private void getStatisticsJob(int j, ArrayList<String> uid) {
        int statisticsJob;
        final ArrayList<String> pickerUid = new ArrayList<>();
        for (int i = 0; i < j; i++) {
            mDatabaseReferencePicker.child(uid.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Map<String, Object> user = (Map<String, Object>) dataSnapshot.getValue();
                    Log.d("lkj job", user.get("job").toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    //나이별 투표 결과
    private void getStatisticsAge(int j, ArrayList<String> uid) {
        int statisticsAge;
        final ArrayList<String> pickerUid = new ArrayList<>();
        for (int i = 0; i < j; i++) {
            mDatabaseReferencePicker.child(uid.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Map<String, Object> user = (Map<String, Object>) dataSnapshot.getValue();
                    Log.d("lkj age", user.get("age").toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    //성별 투표 결과
    private void getStatisticsGender(int j, ArrayList<String> uid) {
        int statisticsGender;
        final ArrayList<String> pickerUid = new ArrayList<>();
        for (int i = 0; i < j; i++) {
            mFirebaseDatabase.getReference().child("users").child(uid.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Map<String, Object> user = (Map<String, Object>) dataSnapshot.getValue();
//                    User user = dataSnapshot.getValue(User.class);
                    Log.d("lkj gender", user.get("sex").toString());
//                    Log.d("lkj gender", user.getSex());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }


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


    //현재 Activity 에서의 투표 체크 유무
    private boolean pollChecking() {
        if (((ColorDrawable) pollActivity_imageView_choice_1.getBackground()).getColor() == 0xff4485c9
                || ((ColorDrawable) pollActivity_imageView_choice_2.getBackground()).getColor() == 0xff4485c9
                || ((ColorDrawable) pollActivity_imageView_choice_3.getBackground()).getColor() == 0xff4485c9
                || ((ColorDrawable) pollActivity_imageView_choice_4.getBackground()).getColor() == 0xff4485c9
                || ((ColorDrawable) pollActivity_imageView_choice_5.getBackground()).getColor() == 0xff4485c9
                || ((ColorDrawable) pollActivity_imageView_choice_6.getBackground()).getColor() == 0xff4485c9
                || ((ColorDrawable) pollActivity_imageView_choice_7.getBackground()).getColor() == 0xff4485c9
                || ((ColorDrawable) pollActivity_imageView_choice_8.getBackground()).getColor() == 0xff4485c9
                || ((ColorDrawable) pollActivity_imageView_choice_9.getBackground()).getColor() == 0xff4485c9
                || ((ColorDrawable) pollActivity_imageView_choice_10.getBackground()).getColor() == 0xff4485c9) {
            return true;
        } else {
            return false;
        }
    }


    //투표하고 결과보기
    private void openResult(int contentN) {
        if (!ACTIVITY_RESULT_FLAG) {
            pollActivity_imageView_result_downButton.setVisibility(View.GONE);
            pollActivity_imageView_result_upButton.setVisibility(View.VISIBLE);
            pollActivity_textView_result.setText("접기");
            pollActivity_horizontalBarChart_result.setVisibility(View.VISIBLE);
//            setChartData(contentN, 100); //bar개수 : contentN개, 가로 최대길이 range
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


    //
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
                    //투표가 되어있으면 PollResultDialog
                    PollResultDialog pollResultDialog = new PollResultDialog();
                    Bundle bundle = new Bundle();
                    bundle.putInt("imagePick", currentPick());
                    bundle.putInt("imageN", getIntent().getIntExtra("itemViewType", 100));
                    bundle.putString("currentContent", getIntent().getStringExtra("contentKey"));
                    pollResultDialog.setArguments(bundle);
                    pollResultDialog.show(getSupportFragmentManager(), "pollResultDialog");
//                            openResult(contentAmount);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//
//                        }
//                    });

                } else {

                    if (pollChecking()) { //체크되있으면 true, 안되있으면 false
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                PollResultDialog pollResultDialog = new PollResultDialog();
//                                Bundle bundle = new Bundle();
//                                bundle.putInt("imagePick", currentPick());
//                                bundle.putInt("imageN", getIntent().getIntExtra("itemViewType",100));
//                                bundle.putString("currentContent", "11");
//                                pollResultDialog.setArguments(bundle);
//                                pollResultDialog.show(getSupportFragmentManager(), "pollResultDialog");
////                                openResult(contentAmount);
//                            }
//                        });
                        PollResultDialog pollResultDialog = new PollResultDialog();
                        Bundle bundle = new Bundle();
                        bundle.putInt("imagePick", currentPick());
                        bundle.putInt("imageN", getIntent().getIntExtra("itemViewType", 100));
                        bundle.putString("currentContent", getIntent().getStringExtra("contentKey"));
                        pollResultDialog.setArguments(bundle);
                        pollResultDialog.show(getSupportFragmentManager(), "pollResultDialog");

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
        if (((ColorDrawable) pollActivity_imageView_choice_1.getBackground()).getColor() == 0xff4485c9) {
            return 0;
        } else if (((ColorDrawable) pollActivity_imageView_choice_2.getBackground()).getColor() == 0xff4485c9) {
            return 1;
        } else if (((ColorDrawable) pollActivity_imageView_choice_3.getBackground()).getColor() == 0xff4485c9) {
            return 2;
        } else if (((ColorDrawable) pollActivity_imageView_choice_4.getBackground()).getColor() == 0xff4485c9) {
            return 3;
        } else if (((ColorDrawable) pollActivity_imageView_choice_5.getBackground()).getColor() == 0xff4485c9) {
            return 4;
        } else if (((ColorDrawable) pollActivity_imageView_choice_6.getBackground()).getColor() == 0xff4485c9) {
            return 5;
        } else if (((ColorDrawable) pollActivity_imageView_choice_7.getBackground()).getColor() == 0xff4485c9) {
            return 6;
        } else if (((ColorDrawable) pollActivity_imageView_choice_8.getBackground()).getColor() == 0xff4485c9) {
            return 7;
        } else if (((ColorDrawable) pollActivity_imageView_choice_9.getBackground()).getColor() == 0xff4485c9) {
            return 8;
        } else if (((ColorDrawable) pollActivity_imageView_choice_10.getBackground()).getColor() == 0xff4485c9) {
            return 9;
        } else return 100;


    }


    public void checking_img_1() {
        pickCandidate = 1;
        pollActivity_imageView_around_1.setBackgroundColor(0xff4485c9);
        pollActivity_imageView_choice_1.setBackgroundColor(0xff4485c9);
    }


    public void checking_img_1_rt() {
        pickCandidate = 0;
        pollActivity_imageView_around_1.setBackgroundColor(0xfff2f2f2);
        pollActivity_imageView_choice_1.setBackgroundColor(0xfff2f2f2);
    }


    public void checking_img_2() {
        pickCandidate = 2;
        pollActivity_imageView_around_2.setBackgroundColor(0xff4485c9);
        pollActivity_imageView_choice_2.setBackgroundColor(0xff4485c9);
    }


    public void checking_img_2_rt() {
        pickCandidate = 0;
        pollActivity_imageView_around_2.setBackgroundColor(0xfff2f2f2);
        pollActivity_imageView_choice_2.setBackgroundColor(0xfff2f2f2);
    }



    public void checking_img_3() {
        pickCandidate = 3;
        pollActivity_imageView_around_3.setBackgroundColor(0xff4485c9);
        pollActivity_imageView_choice_3.setBackgroundColor(0xff4485c9);
    }


    public void checking_img_3_rt() {
        pickCandidate = 0;
        pollActivity_imageView_around_3.setBackgroundColor(0xfff2f2f2);
        pollActivity_imageView_choice_3.setBackgroundColor(0xfff2f2f2);
    }



    public void checking_img_4() {
        pickCandidate = 4;
        pollActivity_imageView_around_4.setBackgroundColor(0xff4485c9);
        pollActivity_imageView_choice_4.setBackgroundColor(0xff4485c9);
    }


    public void checking_img_4_rt() {
        pickCandidate = 0;
        pollActivity_imageView_around_4.setBackgroundColor(0xfff2f2f2);
        pollActivity_imageView_choice_4.setBackgroundColor(0xfff2f2f2);
    }



    public void checking_img_5() {
        pickCandidate = 5;
        pollActivity_imageView_around_5.setBackgroundColor(0xff4485c9);
        pollActivity_imageView_choice_5.setBackgroundColor(0xff4485c9);
    }


    public void checking_img_5_rt() {
        pickCandidate = 0;
        pollActivity_imageView_around_5.setBackgroundColor(0xfff2f2f2);
        pollActivity_imageView_choice_5.setBackgroundColor(0xfff2f2f2);
    }



    public void checking_img_6() {
        pickCandidate = 6;
        pollActivity_imageView_around_6.setBackgroundColor(0xff4485c9);
        pollActivity_imageView_choice_6.setBackgroundColor(0xff4485c9);
    }


    public void checking_img_6_rt() {
        pickCandidate = 0;
        pollActivity_imageView_around_6.setBackgroundColor(0xfff2f2f2);
        pollActivity_imageView_choice_6.setBackgroundColor(0xfff2f2f2);
    }



    public void checking_img_7() {
        pickCandidate = 7;
        pollActivity_imageView_around_7.setBackgroundColor(0xff4485c9);
        pollActivity_imageView_choice_7.setBackgroundColor(0xff4485c9);
    }


    public void checking_img_7_rt() {
        pickCandidate = 0;
        pollActivity_imageView_around_7.setBackgroundColor(0xfff2f2f2);
        pollActivity_imageView_choice_7.setBackgroundColor(0xfff2f2f2);
    }



    public void checking_img_8() {
        pickCandidate = 8;
        pollActivity_imageView_around_8.setBackgroundColor(0xff4485c9);
        pollActivity_imageView_choice_8.setBackgroundColor(0xff4485c9);
    }


    public void checking_img_8_rt() {
        pickCandidate = 0;
        pollActivity_imageView_around_8.setBackgroundColor(0xfff2f2f2);
        pollActivity_imageView_choice_8.setBackgroundColor(0xfff2f2f2);
    }



    public void checking_img_9() {
        pickCandidate = 9;
        pollActivity_imageView_around_9.setBackgroundColor(0xff4485c9);
        pollActivity_imageView_choice_9.setBackgroundColor(0xff4485c9);
    }


    public void checking_img_9_rt() {
        pickCandidate = 0;
        pollActivity_imageView_around_9.setBackgroundColor(0xfff2f2f2);
        pollActivity_imageView_choice_9.setBackgroundColor(0xfff2f2f2);
    }



    public void checking_img_10() {
        pickCandidate = 10;
        pollActivity_imageView_around_10.setBackgroundColor(0xff4485c9);
        pollActivity_imageView_choice_10.setBackgroundColor(0xff4485c9);
    }


    public void checking_img_10_rt() {
        pickCandidate = 0;
        pollActivity_imageView_around_10.setBackgroundColor(0xfff2f2f2);
        pollActivity_imageView_choice_10.setBackgroundColor(0xfff2f2f2);
    }






    //이미지 선택시 체크하기
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pollActivity_imageView_userAddContent_1:
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();

                        String url = contentInfo.get("imageUrl_0").toString();

                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
                        intent.putExtra("imgUrl", url);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                break;
            case R.id.pollActivity_imageView_userAddContent_2:
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();

                        String url = contentInfo.get("imageUrl_1").toString();

                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
                        intent.putExtra("imgUrl", url);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                break;
            case R.id.pollActivity_imageView_userAddContent_3:
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();

                        String url = contentInfo.get("imageUrl_2").toString();

                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
                        intent.putExtra("imgUrl", url);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                break;
            case R.id.pollActivity_imageView_userAddContent_4:
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();

                        String url = contentInfo.get("imageUrl_3").toString();

                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
                        intent.putExtra("imgUrl", url);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                break;
            case R.id.pollActivity_imageView_userAddContent_5:
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();

                        String url = contentInfo.get("imageUrl_4").toString();

                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
                        intent.putExtra("imgUrl", url);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                break;
            case R.id.pollActivity_imageView_userAddContent_6:
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();

                        String url = contentInfo.get("imageUrl_5").toString();

                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
                        intent.putExtra("imgUrl", url);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                break;
            case R.id.pollActivity_imageView_userAddContent_7:
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();

                        String url = contentInfo.get("imageUrl_6").toString();

                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
                        intent.putExtra("imgUrl", url);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                break;
            case R.id.pollActivity_imageView_userAddContent_8:
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();

                        String url = contentInfo.get("imageUrl_7").toString();

                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
                        intent.putExtra("imgUrl", url);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                break;
            case R.id.pollActivity_imageView_userAddContent_9:
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();

                        String url = contentInfo.get("imageUrl_8").toString();

                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
                        intent.putExtra("imgUrl", url);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                break;
            case R.id.pollActivity_imageView_userAddContent_10:
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();

                        String url = contentInfo.get("imageUrl_9").toString();

                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
                        intent.putExtra("imgUrl", url);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                break;

            case R.id.pollActivity_textView_check_1:
                if (((ColorDrawable) pollActivity_imageView_choice_1.getBackground()).getColor() == 0xff4485c9) {
                    checking_img_1_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_1.getBackground()).getColor() == 0xfff2f2f2) {
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

            case R.id.pollActivity_textView_check_2:
                if (((ColorDrawable) pollActivity_imageView_choice_2.getBackground()).getColor() == 0xff4485c9) {
                    checking_img_2_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_2.getBackground()).getColor() == 0xfff2f2f2) {
                    checking_img_2();
                    checking_img_1_rt();
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

            case R.id.pollActivity_textView_check_3:
                if (((ColorDrawable) pollActivity_imageView_choice_3.getBackground()).getColor() == 0xff4485c9) {
                    checking_img_3_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_3.getBackground()).getColor() == 0xfff2f2f2) {
                    checking_img_3();
                    checking_img_1_rt();
                    checking_img_2_rt();
                    checking_img_4_rt();
                    checking_img_5_rt();
                    checking_img_6_rt();
                    checking_img_7_rt();
                    checking_img_8_rt();
                    checking_img_9_rt();
                    checking_img_10_rt();
                }
                break;

            case R.id.pollActivity_textView_check_4:
                if (((ColorDrawable) pollActivity_imageView_choice_4.getBackground()).getColor() == 0xff4485c9) {
                    checking_img_4_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_4.getBackground()).getColor() == 0xfff2f2f2) {
                    checking_img_4();
                    checking_img_1_rt();
                    checking_img_2_rt();
                    checking_img_3_rt();
                    checking_img_5_rt();
                    checking_img_6_rt();
                    checking_img_7_rt();
                    checking_img_8_rt();
                    checking_img_9_rt();
                    checking_img_10_rt();
                }
                break;

            case R.id.pollActivity_textView_check_5:
                if (((ColorDrawable) pollActivity_imageView_choice_5.getBackground()).getColor() == 0xff4485c9) {
                    checking_img_5_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_5.getBackground()).getColor() == 0xfff2f2f2) {
                    checking_img_5();
                    checking_img_1_rt();
                    checking_img_2_rt();
                    checking_img_3_rt();
                    checking_img_4_rt();
                    checking_img_6_rt();
                    checking_img_7_rt();
                    checking_img_8_rt();
                    checking_img_9_rt();
                    checking_img_10_rt();
                }
                break;

            case R.id.pollActivity_textView_check_6:
                if (((ColorDrawable) pollActivity_imageView_choice_6.getBackground()).getColor() == 0xff4485c9) {
                    checking_img_6_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_6.getBackground()).getColor() == 0xfff2f2f2) {
                    checking_img_6();
                    checking_img_1_rt();
                    checking_img_2_rt();
                    checking_img_3_rt();
                    checking_img_4_rt();
                    checking_img_5_rt();
                    checking_img_7_rt();
                    checking_img_8_rt();
                    checking_img_9_rt();
                    checking_img_10_rt();
                }
                break;

            case R.id.pollActivity_textView_check_7:
                if (((ColorDrawable) pollActivity_imageView_choice_7.getBackground()).getColor() == 0xff4485c9) {
                    checking_img_7_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_7.getBackground()).getColor() == 0xfff2f2f2) {
                    checking_img_7();
                    checking_img_1_rt();
                    checking_img_2_rt();
                    checking_img_3_rt();
                    checking_img_4_rt();
                    checking_img_5_rt();
                    checking_img_6_rt();
                    checking_img_8_rt();
                    checking_img_9_rt();
                    checking_img_10_rt();
                }
                break;

            case R.id.pollActivity_textView_check_8:
                if (((ColorDrawable) pollActivity_imageView_choice_8.getBackground()).getColor() == 0xff4485c9) {
                    checking_img_8_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_8.getBackground()).getColor() == 0xfff2f2f2) {
                    checking_img_8();
                    checking_img_1_rt();
                    checking_img_2_rt();
                    checking_img_3_rt();
                    checking_img_4_rt();
                    checking_img_5_rt();
                    checking_img_6_rt();
                    checking_img_7_rt();
                    checking_img_9_rt();
                    checking_img_10_rt();
                }
                break;

            case R.id.pollActivity_textView_check_9:
                if (((ColorDrawable) pollActivity_imageView_choice_9.getBackground()).getColor() == 0xff4485c9) {
                    checking_img_9_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_9.getBackground()).getColor() == 0xfff2f2f2) {
                    checking_img_9();
                    checking_img_1_rt();
                    checking_img_2_rt();
                    checking_img_3_rt();
                    checking_img_4_rt();
                    checking_img_5_rt();
                    checking_img_6_rt();
                    checking_img_7_rt();
                    checking_img_8_rt();
                    checking_img_10_rt();
                }
                break;

            case R.id.pollActivity_textView_check_10:
                if (((ColorDrawable) pollActivity_imageView_choice_10.getBackground()).getColor() == 0xff4485c9) {
                    checking_img_10_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_10.getBackground()).getColor() == 0xfff2f2f2) {
                    checking_img_10();
                    checking_img_1_rt();
                    checking_img_2_rt();
                    checking_img_3_rt();
                    checking_img_4_rt();
                    checking_img_5_rt();
                    checking_img_6_rt();
                    checking_img_7_rt();
                    checking_img_8_rt();
                    checking_img_9_rt();
                }
                break;



//
//            case R.id.pollActivity_imageView_choice_1:
//                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();
//
//                        String url = contentInfo.get("imageUrl_0").toString();
//
//                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
//                        intent.putExtra("imgUrl", url);
//                        startActivity(intent);
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//                break;
//
//            case R.id.pollActivity_imageView_choice_2:
//                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();
//
//                        String url = contentInfo.get("imageUrl_1").toString();
//
//                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
//                        intent.putExtra("imgUrl", url);
//                        startActivity(intent);
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//                break;
//
//            case R.id.pollActivity_imageView_choice_3:
//                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();
//
//                        String url = contentInfo.get("imageUrl_2").toString();
//
//                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
//                        intent.putExtra("imgUrl", url);
//                        startActivity(intent);
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//                break;
//
//            case R.id.pollActivity_imageView_choice_4:
//                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();
//
//                        String url = contentInfo.get("imageUrl_3").toString();
//
//                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
//                        intent.putExtra("imgUrl", url);
//                        startActivity(intent);
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//                break;
//
//            case R.id.pollActivity_imageView_choice_5:
//                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();
//
//                        String url = contentInfo.get("imageUrl_4").toString();
//
//                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
//                        intent.putExtra("imgUrl", url);
//                        startActivity(intent);
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//                break;
//
//            case R.id.pollActivity_imageView_choice_6:
//                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();
//
//                        String url = contentInfo.get("imageUrl_5").toString();
//
//                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
//                        intent.putExtra("imgUrl", url);
//                        startActivity(intent);
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//                break;
//
//            case R.id.pollActivity_imageView_choice_7:
//                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();
//
//                        String url = contentInfo.get("imageUrl_6").toString();
//
//                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
//                        intent.putExtra("imgUrl", url);
//                        startActivity(intent);
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//                break;
//
//            case R.id.pollActivity_imageView_choice_8:
//                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();
//
//                        String url = contentInfo.get("imageUrl_7").toString();
//
//                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
//                        intent.putExtra("imgUrl", url);
//                        startActivity(intent);
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//                break;
//
//            case R.id.pollActivity_imageView_choice_9:
//                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();
//
//                        String url = contentInfo.get("imageUrl_8").toString();
//
//                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
//                        intent.putExtra("imgUrl", url);
//                        startActivity(intent);
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//                break;
//
//            case R.id.pollActivity_imageView_choice_10:
//                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();
//
//                        String url = contentInfo.get("imageUrl_9").toString();
//
//                        Intent intent = new Intent(PollSingleActivity.this, TestActivity.class);
//                        intent.putExtra("imgUrl", url);
//                        startActivity(intent);
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//                break;
        }
    }


    public class ResultValueFormatter implements IValueFormatter {
        private DecimalFormat mFormat;

        public ResultValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0");
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value) + "표";
        }
    }

    public class CategoryBarChartXaxisFormatter implements IAxisValueFormatter {
        ArrayList<String> mValues;

        public CategoryBarChartXaxisFormatter(ArrayList<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {

            int val = (int) value;
            String label = "";
            if (val >= 0 && val < mValues.size()) {
                label = mValues.get(val);
            } else {
                label = "";
            }

            return label;
        }
    }

}
