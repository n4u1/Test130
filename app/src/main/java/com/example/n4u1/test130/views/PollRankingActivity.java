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
import android.support.design.widget.FloatingActionButton;
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
import com.example.n4u1.test130.dialog.RankingChoiceActivity;
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

public class PollRankingActivity extends AppCompatActivity implements View.OnClickListener {

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
    int contentHit;

    private GestureDetector mGestureDetector;

    FloatingActionButton pollActivity_fab_result;
    TextView pollActivity_textView_title, pollActivity_textView_description,
            pollActivity_textView_pollMode, pollActivity_textView_contentType, pollActivity_textView_date;
    ImageView pollActivity_imageView_userAddContent_1, pollActivity_imageView_userAddContent_2,
            pollActivity_imageView_userAddContent_3, pollActivity_imageView_userAddContent_4,
            pollActivity_imageView_userAddContent_5, pollActivity_imageView_userAddContent_6,
            pollActivity_imageView_userAddContent_7, pollActivity_imageView_userAddContent_8,
            pollActivity_imageView_userAddContent_9, pollActivity_imageView_userAddContent_10;

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

    ImageView pollActivity_imageView_reply_upButton, pollActivity_imageView_reply_downButton;
    //    Button pollActivity_button_statistic;
    ImageView pollActivity_button_replySend;
    EditText pollActivity_editText_reply;
    RecyclerView pollActivity_recyclerView_reply;
    RelativeLayout pollActivity_relativeLayout_reply;
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
        getSupportActionBar().setIcon(R.drawable.q);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        final String contentKey = getIntent().getStringExtra("contentKey");
        contentHit = getIntent().getIntExtra("contentHit", 999999);


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


        pollActivity_fab_result = findViewById(R.id.pollActivity_fab_result);
        pollActivity_textView_title = findViewById(R.id.pollActivity_textView_title);
        pollActivity_textView_description = findViewById(R.id.pollActivity_textView_description);
        pollActivity_textView_contentType = findViewById(R.id.pollActivity_textView_contentType);
        pollActivity_textView_pollMode = findViewById(R.id.pollActivity_textView_pollMode);
        pollActivity_textView_date = findViewById(R.id.pollActivity_textView_date);
        pollActivity_textView_reply = findViewById(R.id.pollActivity_textView_reply);


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
        pollActivity_relativeLayout_reply = findViewById(R.id.pollActivity_relativeLayout_reply);
        pollActivity_imageView_reply_downButton = findViewById(R.id.pollActivity_imageView_reply_downButton);
        pollActivity_imageView_reply_upButton = findViewById(R.id.pollActivity_imageView_reply_upButton);
        pollActivity_recyclerView_reply = findViewById(R.id.pollActivity_recyclerView_reply);
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

        //투표하고 결과보기
        pollActivity_fab_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rankingChecking() == 0) {
                    Toast.makeText(getApplicationContext(), "순위투표를 먼저 해주세요~!", Toast.LENGTH_SHORT).show();
                }
                if (rankingChecking() == 10) {
                    onResultClicked(firebaseDatabase.getReference().child("user_contents").child(contentKey), currentPick());
                }
                if (rankingChecking() == 1 || rankingChecking() == 2 || rankingChecking() == 3 ||
                        rankingChecking() == 4 || rankingChecking() == 5 || rankingChecking() == 6 ||
                        rankingChecking() == 7 || rankingChecking() == 8 || rankingChecking() == 9 ) {
                    Toast.makeText(getApplicationContext(), "순위투표입니다. 순위를 모두 정해주세요!", Toast.LENGTH_SHORT).show();
                }

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
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_1).getView();
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
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_2).getView();
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
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_2).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_3).getView();
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
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_2).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_3).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_4).getView();
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
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_2).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_3).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_4).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_5).getView();
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
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_2).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_3).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_4).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_5).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_5()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_6).getView();
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
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_2).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_3).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_4).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_5).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_5()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_6).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_6()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_7).getView();
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
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_2).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_3).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_4).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_5).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_5()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_6).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_6()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_7).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_7()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_8).getView();
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
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_2).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_3).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_4).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_5).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_5()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_6).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_6()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_7).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_7()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_8).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_8()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_9).getView();
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
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_0()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_1).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_1()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_2).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_2()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_3).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_3()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_4).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_4()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_5).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_5()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_6).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_6()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_7).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_7()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_8).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_8()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_9).getView();
                        GlideApp.with(getApplicationContext()).load(contentDTO.getImageUrl_9()).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.loadingicon)).into(pollActivity_imageView_userAddContent_10).getView();
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
                    pollActivity_fab_result.setVisibility(View.GONE);
                    ACTIVITY_REPLY_FLAG = true;
                } else {
                    pollActivity_imageView_reply_downButton.setVisibility(View.VISIBLE);
                    pollActivity_imageView_reply_upButton.setVisibility(View.GONE);
                    pollActivity_textView_reply.setText("댓글 펼치기 (" + replyCount + ")");
                    pollActivity_recyclerView_reply.setVisibility(View.GONE);
                    pollActivity_editText_reply.setVisibility(View.GONE);
                    pollActivity_button_replySend.setVisibility(View.GONE);
                    pollActivity_fab_result.setVisibility(View.VISIBLE);
                    ACTIVITY_REPLY_FLAG = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    //댓글수 추가 (replyCount)
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
                final ContentDTO contentDTO = mutableData.getValue(ContentDTO.class);
                if (contentDTO == null) {
                    return Transaction.success(mutableData);
                }
                if (contentDTO.contentPicker.containsKey(auth.getCurrentUser().getUid())) {
                    //투표가 되어있으면 PollResultDialog
                    PollResultDialog pollResultDialog = new PollResultDialog();
                    Bundle bundle = new Bundle();

                    bundle.putInt("imagePick", currentPick());
                    bundle.putInt("imageN", getIntent().getIntExtra("itemViewType", 100));
                    bundle.putInt("contentHits", contentHit);
                    bundle.putString("currentContent", getIntent().getStringExtra("contentKey"));
                    bundle.putString("statisticsCode", contentDTO.statistics_code);

                    pollResultDialog.setArguments(bundle);
                    pollResultDialog.show(getSupportFragmentManager(), "pollResultDialog");

                    //투표가 안되어있으면 투표하고 PollResultDialog
                } else {
                    //투표 선택 되있으면 true, 안되있으면 false
                    if (pollChecking()) {
                        //true면 투표수 추가하고 PollResultDialog
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


//                        tmp.add(Integer.parseInt(object0.toString()));
                        mDatabaseReferencePicker.child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Map<String, Object> user = (Map<String, Object>) dataSnapshot.getValue();
                                Object object = user.get("age");
                                int currentAge = Integer.parseInt(object.toString());
                                String currentGender = user.get("sex").toString();
                                String statisticsCodeTmp = addStatistics(contentDTO.statistics_code, currentPick(), currentGender, currentAge);
                                mDatabaseReference.child("statistics_code").setValue(statisticsCodeTmp);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        contentDTO.contentPicker.put(auth.getCurrentUser().getUid(), currentPick());
                        String key = getIntent().getStringExtra("contentKey");
                        firebaseDatabase.getReference()
                                .child("users")
                                .child(auth.getCurrentUser().getUid())
                                .child("pickContent")
                                .child(key)
                                .setValue(currentPick());

                        PollResultDialog pollResultDialog = new PollResultDialog();
                        Bundle bundle = new Bundle();
                        bundle.putInt("imagePick", currentPick());
                        bundle.putInt("imageN", getIntent().getIntExtra("itemViewType", 100));
                        bundle.putString("currentContent", getIntent().getStringExtra("contentKey"));
                        bundle.putString("statisticsCode", contentDTO.statistics_code);
                        pollResultDialog.setArguments(bundle);
                        pollResultDialog.show(getSupportFragmentManager(), "pollResultDialog");

                    } else { //투표 선택 안되있으면
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
        SimpleDateFormat df = new SimpleDateFormat("yyMMdd-HHmm", Locale.KOREAN);
        df.setTimeZone(timeZone);
        String currentDate = df.format(date);
        return currentDate;
    }


    //순위투표 여부 체크
    private int rankingChecking() {
        int rCheck = 0;
        if (((ColorDrawable) pollActivity_imageView_choice_1.getBackground()).getColor() == 0xff4485c9) {
            rCheck++;
        }
        if (((ColorDrawable) pollActivity_imageView_choice_2.getBackground()).getColor() == 0xff4485c9) {
            rCheck++;
        }
        if (((ColorDrawable) pollActivity_imageView_choice_3.getBackground()).getColor() == 0xff4485c9) {
            rCheck++;
        }
        if (((ColorDrawable) pollActivity_imageView_choice_4.getBackground()).getColor() == 0xff4485c9) {
            rCheck++;
        }
        if (((ColorDrawable) pollActivity_imageView_choice_5.getBackground()).getColor() == 0xff4485c9) {
            rCheck++;
        }
        if (((ColorDrawable) pollActivity_imageView_choice_6.getBackground()).getColor() == 0xff4485c9) {
            rCheck++;
        }
        if (((ColorDrawable) pollActivity_imageView_choice_7.getBackground()).getColor() == 0xff4485c9) {
            rCheck++;
        }
        if (((ColorDrawable) pollActivity_imageView_choice_8.getBackground()).getColor() == 0xff4485c9) {
            rCheck++;
        }
        if (((ColorDrawable) pollActivity_imageView_choice_9.getBackground()).getColor() == 0xff4485c9) {
            rCheck++;
        }
        if (((ColorDrawable) pollActivity_imageView_choice_10.getBackground()).getColor() == 0xff4485c9) {
            rCheck++;
        }
        return rCheck;
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


    //이미지 선택시 전체화면으로 보여주기
    //N선택 선택시 파란색으로 바뀌면서 체크
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pollActivity_imageView_userAddContent_1:
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Object> contentInfo = (Map<String, Object>) dataSnapshot.getValue();

                        String url = contentInfo.get("imageUrl_0").toString();

                        Intent intent = new Intent(PollRankingActivity.this, TestActivity.class);
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

                        Intent intent = new Intent(PollRankingActivity.this, TestActivity.class);
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

                        Intent intent = new Intent(PollRankingActivity.this, TestActivity.class);
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

                        Intent intent = new Intent(PollRankingActivity.this, TestActivity.class);
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

                        Intent intent = new Intent(PollRankingActivity.this, TestActivity.class);
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

                        Intent intent = new Intent(PollRankingActivity.this, TestActivity.class);
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

                        Intent intent = new Intent(PollRankingActivity.this, TestActivity.class);
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

                        Intent intent = new Intent(PollRankingActivity.this, TestActivity.class);
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

                        Intent intent = new Intent(PollRankingActivity.this, TestActivity.class);
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

                        Intent intent = new Intent(PollRankingActivity.this, TestActivity.class);
                        intent.putExtra("imgUrl", url);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                break;

            case R.id.pollActivity_textView_check_1:

                Intent intent = new Intent(PollRankingActivity.this, RankingChoiceActivity.class);
                startActivity(intent);
//                ContentChoiceDialog contentChoiceDialog = new ContentChoiceDialog();
//                contentChoiceDialog.show(getSupportFragmentManager(), "contentChoiceDialog");
                if (((ColorDrawable) pollActivity_imageView_choice_1.getBackground()).getColor() == 0xff4485c9) {
                    pollActivity_textView_check_1.setText("1 선택");
                    checking_img_1_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_1.getBackground()).getColor() == 0xfff2f2f2) {
                    pollActivity_textView_check_1.setText(rankingChecking() + 1 + " 위");
                    checking_img_1();
                }
                break;

            case R.id.pollActivity_textView_check_2:
                if (((ColorDrawable) pollActivity_imageView_choice_2.getBackground()).getColor() == 0xff4485c9) {
                    pollActivity_textView_check_2.setText("2 선택");
                    checking_img_2_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_2.getBackground()).getColor() == 0xfff2f2f2) {
                    pollActivity_textView_check_2.setText(rankingChecking() + 1 + " 위");
                    checking_img_2();
                }
                break;

            case R.id.pollActivity_textView_check_3:
                if (((ColorDrawable) pollActivity_imageView_choice_3.getBackground()).getColor() == 0xff4485c9) {
                    pollActivity_textView_check_3.setText("3 선택");
                    checking_img_3_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_3.getBackground()).getColor() == 0xfff2f2f2) {
                    pollActivity_textView_check_3.setText(rankingChecking() + 1 + " 위");
                    checking_img_3();
                }
                break;

            case R.id.pollActivity_textView_check_4:
                if (((ColorDrawable) pollActivity_imageView_choice_4.getBackground()).getColor() == 0xff4485c9) {
                    pollActivity_textView_check_4.setText("4 선택");
                    checking_img_4_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_4.getBackground()).getColor() == 0xfff2f2f2) {
                    pollActivity_textView_check_4.setText(rankingChecking() + 1 + " 위");
                    checking_img_4();
                }
                break;

            case R.id.pollActivity_textView_check_5:
                if (((ColorDrawable) pollActivity_imageView_choice_5.getBackground()).getColor() == 0xff4485c9) {
                    pollActivity_textView_check_5.setText("5 선택");
                    checking_img_5_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_5.getBackground()).getColor() == 0xfff2f2f2) {
                    pollActivity_textView_check_5.setText(rankingChecking() + 1 + " 위");
                    checking_img_5();
                }
                break;

            case R.id.pollActivity_textView_check_6:
                if (((ColorDrawable) pollActivity_imageView_choice_6.getBackground()).getColor() == 0xff4485c9) {
                    pollActivity_textView_check_6.setText("6 선택");
                    checking_img_6_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_6.getBackground()).getColor() == 0xfff2f2f2) {
                    pollActivity_textView_check_6.setText(rankingChecking() + 1 + " 위");
                    checking_img_6();
                }
                break;

            case R.id.pollActivity_textView_check_7:
                if (((ColorDrawable) pollActivity_imageView_choice_7.getBackground()).getColor() == 0xff4485c9) {
                    pollActivity_textView_check_7.setText("7 선택");
                    checking_img_7_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_7.getBackground()).getColor() == 0xfff2f2f2) {
                    pollActivity_textView_check_7.setText(rankingChecking() + 1 + " 위");
                    checking_img_7();
                }
                break;

            case R.id.pollActivity_textView_check_8:
                if (((ColorDrawable) pollActivity_imageView_choice_8.getBackground()).getColor() == 0xff4485c9) {
                    pollActivity_textView_check_1.setText("8 선택");
                    checking_img_8_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_8.getBackground()).getColor() == 0xfff2f2f2) {
                    pollActivity_textView_check_8.setText(rankingChecking() + 1 + " 위");
                    checking_img_8();
                }
                break;

            case R.id.pollActivity_textView_check_9:
                if (((ColorDrawable) pollActivity_imageView_choice_9.getBackground()).getColor() == 0xff4485c9) {
                    pollActivity_textView_check_1.setText("9 선택");
                    checking_img_9_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_9.getBackground()).getColor() == 0xfff2f2f2) {
                    pollActivity_textView_check_9.setText(rankingChecking() + 1 + " 위");
                    checking_img_9();
                }
                break;

            case R.id.pollActivity_textView_check_10:
                if (((ColorDrawable) pollActivity_imageView_choice_10.getBackground()).getColor() == 0xff4485c9) {
                    pollActivity_textView_check_10.setText("10 선택");
                    checking_img_10_rt();
                } else if (((ColorDrawable) pollActivity_imageView_choice_10.getBackground()).getColor() == 0xfff2f2f2) {
                    pollActivity_textView_check_10.setText(rankingChecking() + 1 + " 위");
                    checking_img_10();
                }
                break;

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


    //picker의 현재픽,성별,나이 가져와서 통계항목에 +1
    private String addStatistics(String statistics_code, int currentPick, String gender, int age) {
        String[] stringArray = null;
        stringArray = statistics_code.split(":");
        int[] tmpStatistics = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            tmpStatistics[i] = Integer.parseInt(stringArray[i]);
        }

        if (gender.equals("여")) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[0]++;
                    break;
                case 1:
                    tmpStatistics[1]++;
                    break;
                case 2:
                    tmpStatistics[2]++;
                    break;
                case 3:
                    tmpStatistics[3]++;
                    break;
                case 4:
                    tmpStatistics[4]++;
                    break;
                case 5:
                    tmpStatistics[5]++;
                    break;
                case 6:
                    tmpStatistics[6]++;
                    break;
                case 7:
                    tmpStatistics[7]++;
                    break;
                case 8:
                    tmpStatistics[8]++;
                    break;
                case 9:
                    tmpStatistics[9]++;
                    break;
            }
        }
        if (gender.equals("남")) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[10]++;
                    break;
                case 1:
                    tmpStatistics[11]++;
                    break;
                case 2:
                    tmpStatistics[12]++;
                    break;
                case 3:
                    tmpStatistics[13]++;
                    break;
                case 4:
                    tmpStatistics[14]++;
                    break;
                case 5:
                    tmpStatistics[15]++;
                    break;
                case 6:
                    tmpStatistics[16]++;
                    break;
                case 7:
                    tmpStatistics[17]++;
                    break;
                case 8:
                    tmpStatistics[18]++;
                    break;
                case 9:
                    tmpStatistics[19]++;
                    break;
            }
        }
        if (age==10 || age==11 || age==12) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[20]++;
                    break;
                case 1:
                    tmpStatistics[21]++;
                    break;
                case 2:
                    tmpStatistics[22]++;
                    break;
                case 3:
                    tmpStatistics[23]++;
                    break;
                case 4:
                    tmpStatistics[24]++;
                    break;
                case 5:
                    tmpStatistics[25]++;
                    break;
                case 6:
                    tmpStatistics[26]++;
                    break;
                case 7:
                    tmpStatistics[27]++;
                    break;
                case 8:
                    tmpStatistics[28]++;
                    break;
                case 9:
                    tmpStatistics[29]++;
                    break;
            }
        }
        if (age==13 || age==14 || age==15 || age==16) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[30]++;
                    break;
                case 1:
                    tmpStatistics[31]++;
                    break;
                case 2:
                    tmpStatistics[32]++;
                    break;
                case 3:
                    tmpStatistics[33]++;
                    break;
                case 4:
                    tmpStatistics[34]++;
                    break;
                case 5:
                    tmpStatistics[35]++;
                    break;
                case 6:
                    tmpStatistics[36]++;
                    break;
                case 7:
                    tmpStatistics[37]++;
                    break;
                case 8:
                    tmpStatistics[38]++;
                    break;
                case 9:
                    tmpStatistics[39]++;
                    break;
            }
        }
        if (age==17 || age==18 || age==19) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[40]++;
                    break;
                case 1:
                    tmpStatistics[41]++;
                    break;
                case 2:
                    tmpStatistics[42]++;
                    break;
                case 3:
                    tmpStatistics[43]++;
                    break;
                case 4:
                    tmpStatistics[44]++;
                    break;
                case 5:
                    tmpStatistics[45]++;
                    break;
                case 6:
                    tmpStatistics[46]++;
                    break;
                case 7:
                    tmpStatistics[47]++;
                    break;
                case 8:
                    tmpStatistics[48]++;
                    break;
                case 9:
                    tmpStatistics[49]++;
                    break;
            }
        }
        if (age==20 || age==21 || age==22) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[50]++;
                    break;
                case 1:
                    tmpStatistics[51]++;
                    break;
                case 2:
                    tmpStatistics[52]++;
                    break;
                case 3:
                    tmpStatistics[53]++;
                    break;
                case 4:
                    tmpStatistics[54]++;
                    break;
                case 5:
                    tmpStatistics[55]++;
                    break;
                case 6:
                    tmpStatistics[56]++;
                    break;
                case 7:
                    tmpStatistics[57]++;
                    break;
                case 8:
                    tmpStatistics[58]++;
                    break;
                case 9:
                    tmpStatistics[59]++;
                    break;
            }
        }
        if (age==23 || age==24 || age==25 || age==26) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[60]++;
                    break;
                case 1:
                    tmpStatistics[61]++;
                    break;
                case 2:
                    tmpStatistics[62]++;
                    break;
                case 3:
                    tmpStatistics[63]++;
                    break;
                case 4:
                    tmpStatistics[64]++;
                    break;
                case 5:
                    tmpStatistics[65]++;
                    break;
                case 6:
                    tmpStatistics[66]++;
                    break;
                case 7:
                    tmpStatistics[67]++;
                    break;
                case 8:
                    tmpStatistics[68]++;
                    break;
                case 9:
                    tmpStatistics[69]++;
                    break;
            }
        }
        if (age==27 || age==28 || age==29) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[70]++;
                    break;
                case 1:
                    tmpStatistics[71]++;
                    break;
                case 2:
                    tmpStatistics[72]++;
                    break;
                case 3:
                    tmpStatistics[73]++;
                    break;
                case 4:
                    tmpStatistics[74]++;
                    break;
                case 5:
                    tmpStatistics[75]++;
                    break;
                case 6:
                    tmpStatistics[76]++;
                    break;
                case 7:
                    tmpStatistics[77]++;
                    break;
                case 8:
                    tmpStatistics[78]++;
                    break;
                case 9:
                    tmpStatistics[79]++;
                    break;
            }
        }
        if (age==30 || age==31 || age==32) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[80]++;
                    break;
                case 1:
                    tmpStatistics[81]++;
                    break;
                case 2:
                    tmpStatistics[82]++;
                    break;
                case 3:
                    tmpStatistics[83]++;
                    break;
                case 4:
                    tmpStatistics[84]++;
                    break;
                case 5:
                    tmpStatistics[85]++;
                    break;
                case 6:
                    tmpStatistics[86]++;
                    break;
                case 7:
                    tmpStatistics[87]++;
                    break;
                case 8:
                    tmpStatistics[88]++;
                    break;
                case 9:
                    tmpStatistics[89]++;
                    break;
            }
        }
        if (age==33 || age==34 || age==35 || age==36) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[90]++;
                    break;
                case 1:
                    tmpStatistics[91]++;
                    break;
                case 2:
                    tmpStatistics[92]++;
                    break;
                case 3:
                    tmpStatistics[93]++;
                    break;
                case 4:
                    tmpStatistics[94]++;
                    break;
                case 5:
                    tmpStatistics[95]++;
                    break;
                case 6:
                    tmpStatistics[96]++;
                    break;
                case 7:
                    tmpStatistics[97]++;
                    break;
                case 8:
                    tmpStatistics[98]++;
                    break;
                case 9:
                    tmpStatistics[99]++;
                    break;
            }
        }
        if (age==37 || age==38 || age==39) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[100]++;
                    break;
                case 1:
                    tmpStatistics[101]++;
                    break;
                case 2:
                    tmpStatistics[102]++;
                    break;
                case 3:
                    tmpStatistics[103]++;
                    break;
                case 4:
                    tmpStatistics[104]++;
                    break;
                case 5:
                    tmpStatistics[105]++;
                    break;
                case 6:
                    tmpStatistics[106]++;
                    break;
                case 7:
                    tmpStatistics[107]++;
                    break;
                case 8:
                    tmpStatistics[108]++;
                    break;
                case 9:
                    tmpStatistics[109]++;
                    break;
            }
        }
        if (age==40 || age==41 || age==42) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[110]++;
                    break;
                case 1:
                    tmpStatistics[111]++;
                    break;
                case 2:
                    tmpStatistics[112]++;
                    break;
                case 3:
                    tmpStatistics[113]++;
                    break;
                case 4:
                    tmpStatistics[114]++;
                    break;
                case 5:
                    tmpStatistics[115]++;
                    break;
                case 6:
                    tmpStatistics[116]++;
                    break;
                case 7:
                    tmpStatistics[117]++;
                    break;
                case 8:
                    tmpStatistics[118]++;
                    break;
                case 9:
                    tmpStatistics[119]++;
                    break;
            }
        }
        if (age==43 || age==44 || age==45 || age==46) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[120]++;
                    break;
                case 1:
                    tmpStatistics[121]++;
                    break;
                case 2:
                    tmpStatistics[122]++;
                    break;
                case 3:
                    tmpStatistics[123]++;
                    break;
                case 4:
                    tmpStatistics[124]++;
                    break;
                case 5:
                    tmpStatistics[125]++;
                    break;
                case 6:
                    tmpStatistics[126]++;
                    break;
                case 7:
                    tmpStatistics[127]++;
                    break;
                case 8:
                    tmpStatistics[128]++;
                    break;
                case 9:
                    tmpStatistics[129]++;
                    break;
            }
        }
        if (age==47 || age==48 || age==49) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[130]++;
                    break;
                case 1:
                    tmpStatistics[131]++;
                    break;
                case 2:
                    tmpStatistics[132]++;
                    break;
                case 3:
                    tmpStatistics[133]++;
                    break;
                case 4:
                    tmpStatistics[134]++;
                    break;
                case 5:
                    tmpStatistics[135]++;
                    break;
                case 6:
                    tmpStatistics[136]++;
                    break;
                case 7:
                    tmpStatistics[137]++;
                    break;
                case 8:
                    tmpStatistics[138]++;
                    break;
                case 9:
                    tmpStatistics[139]++;
                    break;
            }
        }
        if (age==50 || age==51 || age==52) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[140]++;
                    break;
                case 1:
                    tmpStatistics[141]++;
                    break;
                case 2:
                    tmpStatistics[142]++;
                    break;
                case 3:
                    tmpStatistics[143]++;
                    break;
                case 4:
                    tmpStatistics[144]++;
                    break;
                case 5:
                    tmpStatistics[145]++;
                    break;
                case 6:
                    tmpStatistics[146]++;
                    break;
                case 7:
                    tmpStatistics[147]++;
                    break;
                case 8:
                    tmpStatistics[148]++;
                    break;
                case 9:
                    tmpStatistics[149]++;
                    break;
            }
        }
        if (age==53 || age==54 || age==55 || age==56) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[150]++;
                    break;
                case 1:
                    tmpStatistics[151]++;
                    break;
                case 2:
                    tmpStatistics[152]++;
                    break;
                case 3:
                    tmpStatistics[153]++;
                    break;
                case 4:
                    tmpStatistics[154]++;
                    break;
                case 5:
                    tmpStatistics[155]++;
                    break;
                case 6:
                    tmpStatistics[156]++;
                    break;
                case 7:
                    tmpStatistics[157]++;
                    break;
                case 8:
                    tmpStatistics[158]++;
                    break;
                case 9:
                    tmpStatistics[159]++;
                    break;
            }
        }
        if (age==57 || age==58 || age==59) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[160]++;
                    break;
                case 1:
                    tmpStatistics[161]++;
                    break;
                case 2:
                    tmpStatistics[162]++;
                    break;
                case 3:
                    tmpStatistics[163]++;
                    break;
                case 4:
                    tmpStatistics[164]++;
                    break;
                case 5:
                    tmpStatistics[165]++;
                    break;
                case 6:
                    tmpStatistics[166]++;
                    break;
                case 7:
                    tmpStatistics[167]++;
                    break;
                case 8:
                    tmpStatistics[168]++;
                    break;
                case 9:
                    tmpStatistics[169]++;
                    break;
            }
        }
        if (age==60 || age==61 || age==62) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[170]++;
                    break;
                case 1:
                    tmpStatistics[171]++;
                    break;
                case 2:
                    tmpStatistics[172]++;
                    break;
                case 3:
                    tmpStatistics[173]++;
                    break;
                case 4:
                    tmpStatistics[174]++;
                    break;
                case 5:
                    tmpStatistics[175]++;
                    break;
                case 6:
                    tmpStatistics[176]++;
                    break;
                case 7:
                    tmpStatistics[177]++;
                    break;
                case 8:
                    tmpStatistics[178]++;
                    break;
                case 9:
                    tmpStatistics[179]++;
                    break;
            }
        }
        if (age==63 || age==64 || age==65 || age==66) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[180]++;
                    break;
                case 1:
                    tmpStatistics[181]++;
                    break;
                case 2:
                    tmpStatistics[182]++;
                    break;
                case 3:
                    tmpStatistics[183]++;
                    break;
                case 4:
                    tmpStatistics[184]++;
                    break;
                case 5:
                    tmpStatistics[185]++;
                    break;
                case 6:
                    tmpStatistics[186]++;
                    break;
                case 7:
                    tmpStatistics[187]++;
                    break;
                case 8:
                    tmpStatistics[188]++;
                    break;
                case 9:
                    tmpStatistics[189]++;
                    break;
            }
        }
        if (age==67 || age==68 || age==69) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[190]++;
                    break;
                case 1:
                    tmpStatistics[191]++;
                    break;
                case 2:
                    tmpStatistics[192]++;
                    break;
                case 3:
                    tmpStatistics[193]++;
                    break;
                case 4:
                    tmpStatistics[194]++;
                    break;
                case 5:
                    tmpStatistics[195]++;
                    break;
                case 6:
                    tmpStatistics[196]++;
                    break;
                case 7:
                    tmpStatistics[197]++;
                    break;
                case 8:
                    tmpStatistics[198]++;
                    break;
                case 9:
                    tmpStatistics[199]++;
                    break;
            }
        }
        if (age==70 || age==71 || age==72) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[200]++;
                    break;
                case 1:
                    tmpStatistics[201]++;
                    break;
                case 2:
                    tmpStatistics[202]++;
                    break;
                case 3:
                    tmpStatistics[203]++;
                    break;
                case 4:
                    tmpStatistics[204]++;
                    break;
                case 5:
                    tmpStatistics[205]++;
                    break;
                case 6:
                    tmpStatistics[206]++;
                    break;
                case 7:
                    tmpStatistics[207]++;
                    break;
                case 8:
                    tmpStatistics[208]++;
                    break;
                case 9:
                    tmpStatistics[209]++;
                    break;
            }
        }
        if (age==73 || age==74 || age==75 || age==76) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[210]++;
                    break;
                case 1:
                    tmpStatistics[211]++;
                    break;
                case 2:
                    tmpStatistics[212]++;
                    break;
                case 3:
                    tmpStatistics[213]++;
                    break;
                case 4:
                    tmpStatistics[214]++;
                    break;
                case 5:
                    tmpStatistics[215]++;
                    break;
                case 6:
                    tmpStatistics[216]++;
                    break;
                case 7:
                    tmpStatistics[217]++;
                    break;
                case 8:
                    tmpStatistics[218]++;
                    break;
                case 9:
                    tmpStatistics[219]++;
                    break;
            }
        }
        if (age==77 || age==78 || age==79) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[220]++;
                    break;
                case 1:
                    tmpStatistics[221]++;
                    break;
                case 2:
                    tmpStatistics[222]++;
                    break;
                case 3:
                    tmpStatistics[223]++;
                    break;
                case 4:
                    tmpStatistics[224]++;
                    break;
                case 5:
                    tmpStatistics[225]++;
                    break;
                case 6:
                    tmpStatistics[226]++;
                    break;
                case 7:
                    tmpStatistics[227]++;
                    break;
                case 8:
                    tmpStatistics[228]++;
                    break;
                case 9:
                    tmpStatistics[229]++;
                    break;
            }
        }
        if (age==80 || age==81 || age==82) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[230]++;
                    break;
                case 1:
                    tmpStatistics[231]++;
                    break;
                case 2:
                    tmpStatistics[232]++;
                    break;
                case 3:
                    tmpStatistics[233]++;
                    break;
                case 4:
                    tmpStatistics[234]++;
                    break;
                case 5:
                    tmpStatistics[235]++;
                    break;
                case 6:
                    tmpStatistics[236]++;
                    break;
                case 7:
                    tmpStatistics[237]++;
                    break;
                case 8:
                    tmpStatistics[238]++;
                    break;
                case 9:
                    tmpStatistics[239]++;
                    break;
            }
        }
        if (age==83 || age==84 || age==85 || age==86) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[240]++;
                    break;
                case 1:
                    tmpStatistics[241]++;
                    break;
                case 2:
                    tmpStatistics[242]++;
                    break;
                case 3:
                    tmpStatistics[243]++;
                    break;
                case 4:
                    tmpStatistics[244]++;
                    break;
                case 5:
                    tmpStatistics[245]++;
                    break;
                case 6:
                    tmpStatistics[246]++;
                    break;
                case 7:
                    tmpStatistics[247]++;
                    break;
                case 8:
                    tmpStatistics[248]++;
                    break;
                case 9:
                    tmpStatistics[249]++;
                    break;
            }
        }
        if (age==87 || age==88 || age==89) {
            switch (currentPick) {
                case 0:
                    tmpStatistics[250]++;
                    break;
                case 1:
                    tmpStatistics[251]++;
                    break;
                case 2:
                    tmpStatistics[252]++;
                    break;
                case 3:
                    tmpStatistics[253]++;
                    break;
                case 4:
                    tmpStatistics[254]++;
                    break;
                case 5:
                    tmpStatistics[255]++;
                    break;
                case 6:
                    tmpStatistics[256]++;
                    break;
                case 7:
                    tmpStatistics[257]++;
                    break;
                case 8:
                    tmpStatistics[258]++;
                    break;
                case 9:
                    tmpStatistics[259]++;
                    break;
            }
        }

        String callbackStatistics = java.util.Arrays.toString(tmpStatistics);
        callbackStatistics = callbackStatistics.replace(", ",":");
        callbackStatistics = callbackStatistics.replace("[","");
        callbackStatistics = callbackStatistics.replace("]","");

        return callbackStatistics;
    }
}


