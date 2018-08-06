package com.example.n4u1.test130.views;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.fragments.CameraFragment;
import com.example.n4u1.test130.fragments.ImageFragment;
import com.example.n4u1.test130.fragments.VideoFragment;
import com.example.n4u1.test130.models.ContentDTO;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

public class FileChoiceActivity extends AppCompatActivity
        implements ImageFragment.OnFragmentInteractionListener,
        VideoFragment.OnFragmentInteractionListener,
        CameraFragment.OnFragmentInteractionListener{

    private String imgPath;

    private FirebaseStorage storage;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private ArrayList<String> stringArrayList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_choice);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("  AQA");
        }
        getSupportActionBar().setIcon(R.drawable.ic_do_not_disturb_black_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        ViewPager viewPager = findViewById(R.id.viewPager);
        Fragment[] arrFragments = new Fragment[3];
        arrFragments[0] = new ImageFragment();
        arrFragments[1] = new VideoFragment();
        arrFragments[2] = new CameraFragment();
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), arrFragments);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = new Intent();
        stringArrayList = getIntent().getStringArrayListExtra("stringArrayList");

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        final int[] i = new int[1];
        i[0] = 0;

        //현재page를 position으로 확인
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("onPage_position", String.valueOf(position));
                i[0] = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        Button button = findViewById(R.id.bttest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "????", Toast.LENGTH_SHORT).show();
                Log.d("onPage_position???", String.valueOf(i[0]));

            }
        });




    }//super.onCreate(savedInstanceState);




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filechoice_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            case R.id.menu_confirm:
                Toast toast = Toast.makeText(getApplicationContext(), "투표가 시작 되었습니다!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
//                upload(imgPath);
                break;
        }
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onFragmentInteraction(String[] strings) {
        Log.d("uriArrayList_0", strings[0]);
        Log.d("uriArrayList_1", strings[1]);
        Log.d("uriArrayList_2", strings[2]);
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private Fragment[] arrFragments;


        public PagerAdapter(FragmentManager fm, Fragment[] arrFragments) {
            super(fm);
            this.arrFragments = arrFragments;
        }

        @Override
        public Fragment getItem(int position) {
            return arrFragments[position];
        }

        @Override
        public int getCount() {
            return arrFragments.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0 : return "사진";
                case 1 : return "동영상";
                case 2 : return "카메라";
                default: return "";
            }
        }
    }

    //FireBase 에 업로드
    public void upload(final String uri) {
        Uri file = Uri.fromFile(new File(uri));
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://test130-1068f.appspot.com");
        final StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ContentDTO contentDTO = new ContentDTO();
                        contentDTO.imageUrl = uri.toString();
//                        contentDTO.title = editText_title.getText().toString();
//                        contentDTO.description = editText_description.getText().toString();
//                        contentDTO.uid = auth.getCurrentUser().getUid();
//                        contentDTO.userID = auth.getCurrentUser().getEmail();
//                        contentDTO.pollMode = userContentType;
//                        contentDTO.contentType = textView.getText().toString(); //아침, 패션, 정치 등..
                        database.getReference().child("user_contents").push().setValue(contentDTO);
                        //contentDTO에 담아서 setValue로 디비에 저장
                    }
                });
//                String result =
//                riversRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task) {
//                    }
//                }).getResult().toString();
//                Log.d("★★★★★★", result);

                Toast toast = Toast.makeText(getApplicationContext(), "투표가 시작 되었습니다!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();

            }
        });
    }


    //디바이스 경로 가져오기
    private String getPath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }

}
