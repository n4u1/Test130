package com.example.n4u1.test130.views;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ImageDTO;
import com.example.n4u1.test130.models.PostItem;
import com.example.n4u1.test130.recyclerview.PostAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UploadTestActivity extends AppCompatActivity {

    ArrayList<String> list;
    ArrayAdapter<String> adapter;
//    ListView lv;
    InputMethodManager imm;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    private ImageView imageView_img;
    private Button button_upload;
    private EditText editText_content;
    private EditText editText_title;
    private static final int GALLEY_CODE = 10;
    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_test);


        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        final EditText editText_addItem = findViewById(R.id.edittext_addItem);
        editText_content = findViewById(R.id.edittext_content);
        editText_title = findViewById(R.id.edittext_title);
        button_upload = findViewById(R.id.button_upload);
        imageView_img = findViewById(R.id.imageView_img);


        imageView_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, GALLEY_CODE);
            }
        });

        button_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload(imgPath);
            }
        });


        list = new ArrayList<String>();

        list.add("01");
        list.add("02");
        list.add("03");
        list.add("04");
        list.add("05");
        list.add("06");
        list.add("07");
        list.add("08");
        list.add("09");
        list.add("10");
        list.add("11");

//
//
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list);
//        lv = findViewById(R.id.lv);
//        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//        lv.setAdapter(adapter);
//        setListViewHeightBasedOnChildren(lv);


        findViewById(R.id.button_addItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_addItem.getText().length() != 0) {
                    list.add(editText_addItem.getText().toString());
                    editText_addItem.setText("");
                    // 갱신되었음을 어댑터에 통보한다.
                    adapter.notifyDataSetChanged();
                    imm.hideSoftInputFromWindow(editText_addItem.getWindowToken(), 0);
                }
            }
        });


        //RecyclerView 에 아이템 붙이기
        ArrayList<PostItem> listItem = new ArrayList<>();
        RecyclerView recyclerView_uploadTest = findViewById(R.id.recyclerView_uploadTest);

        for (int i = 0; i < 5; i++) {
            PostItem item = new PostItem(true, 122, "irene", "http://image.hankookilbo.com/i.aspx?Guid=2017\\11\\18\\02dcbfbf70ec42f0bf812ebb337949a2&Month=Entertainment&size=640", "wowffffffffff");
            listItem.add(i, item);
        }

        PostAdapter postAdapter = new PostAdapter(this, listItem);
        recyclerView_uploadTest.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        recyclerView_uploadTest.setAdapter(postAdapter);
        //recyclerViewList



    }
    //listView 스크롤 제대로 나오게 하는 메서드
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    //FireBase 에 제목, 내용, 사진, uid, userID 올리기
    public void upload(String uri) {

        StorageReference storageRef = storage.getReferenceFromUrl("gs://test130-1068f.appspot.com");

        Uri file = Uri.fromFile(new File(uri));
        StorageReference riversRef = storageRef.child("imagessss/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                ImageDTO imageDTO = new ImageDTO();
                imageDTO.title = editText_title.getText().toString();
                imageDTO.description = editText_content.getText().toString();
                imageDTO.uid = auth.getCurrentUser().getUid();
                imageDTO.userID = auth.getCurrentUser().getEmail();
                database.getReference().child("images").push().setValue(imageDTO);

            }
        });


    }


    //디바이스 이미지 경로 가져오기
    private String getPath(Uri uri) {
        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if(requestCode == GALLEY_CODE){

                imgPath = getPath(data.getData());
                System.out.println(data.getData());
                System.out.println(getPath(data.getData()));
                Log.d("LKJ", "??? : " + imgPath);

                File f = new File(imgPath);
                imageView_img.setImageURI(Uri.fromFile(f));

            } else {return;}

        super.onActivityResult(requestCode, resultCode, data);
    }




}
