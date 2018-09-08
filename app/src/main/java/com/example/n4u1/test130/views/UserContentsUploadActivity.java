package com.example.n4u1.test130.views;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n4u1.test130.R;

import com.example.n4u1.test130.dialog.ContentChoiceDialog;
import com.example.n4u1.test130.dialog.ContentTypeDialog;
import com.example.n4u1.test130.models.ContentDTO;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

public class UserContentsUploadActivity extends AppCompatActivity implements ContentChoiceDialog.ContentChoiceDialogListener
        , ContentTypeDialog.ContentTypeDialogListener, AddContentFragment.AddContentFragmentListener {


    private static final int GALLEY_CODE = 100;
    private static final int VIDEO_CODE = 200;
    private String imgPath;

    ImageView imageView_userAddContent_1;
    ImageView imageView_userAddContent_2;
    ImageView imageView_userAddContent_3;

    TextView textView;

    EditText editText_title;
    EditText editText_description;
    EditText editText_pollMode;
    EditText editText_addCategory;

    RadioGroup radioGroup_rs;
    String pollMode;




    private FirebaseStorage storage;
    private FirebaseAuth auth;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_contents_upload);


        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(" ");
        }
        getSupportActionBar().setIcon(R.mipmap.ic_q_custom);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
//
//
//        imageView_userAddContent_1 = findViewById(R.id.imageView_userAddContent_1);
//        imageView_userAddContent_2 = findViewById(R.id.imageView_userAddContent_2);
//        imageView_userAddContent_3 = findViewById(R.id.imageView_userAddContent_3);

//        radioGroup_rs = findViewById(R.id.radioGroup_rs);
        pollMode = "";


        editText_title = findViewById(R.id.editText_title);
        editText_description = findViewById(R.id.editText_description);


        //카테고리 선택
        editText_addCategory = findViewById(R.id.editText_addCategory);
        editText_addCategory.setFocusable(false);
        editText_addCategory.setClickable(false);
        editText_addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentChoiceDialog contentChoiceDialog = new ContentChoiceDialog();
                

                contentChoiceDialog.show(getSupportFragmentManager(), "contentChoiceDialog");
            }
        });

        //투표모드 선택
        editText_pollMode = findViewById(R.id.editText_pollMode);
        editText_pollMode.setFocusable(false);
        editText_pollMode.setClickable(false);
        editText_pollMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentTypeDialog contentTypeDialog = new ContentTypeDialog();
                contentTypeDialog.show(getSupportFragmentManager(), "pollModeDialog");
            }
        });
    }


    // + 버튼클릭 > 사진 or 동영상 추가 팝업 > 사진 선택
    public void addUserContentImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLEY_CODE);
    }

    // + 버튼클릭 > 사진 or 동영상 추가 팝업 > 동영상 선택
    private void addUserContentVideo() {
    }

    //현재 업로드된 사진 count체크 > n개올라가있으면 n+1번째뷰에 올리기위함
    private int imageViewCheck() {
        int count = 0;

        if (imageView_userAddContent_1.getVisibility() == View.VISIBLE) {
            count++;
        }
        if (imageView_userAddContent_2.getVisibility() == View.VISIBLE) {
            count++;
        }
        if (imageView_userAddContent_3.getVisibility() == View.VISIBLE) {
            count++;
        }
        return count;
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
//                        contentDTO.imageUrl = uri.toString();
                        contentDTO.title = editText_title.getText().toString();
                        contentDTO.description = editText_description.getText().toString();
                        contentDTO.uid = auth.getCurrentUser().getUid();
                        contentDTO.userID = auth.getCurrentUser().getEmail();
                        contentDTO.pollMode = pollMode;
                        contentDTO.contentType = textView.getText().toString(); //아침, 패션, 정치 등..
                        database.getReference().child("user_contents").push().setValue(contentDTO);
                        //contentDTO에 담아서 setValue로 디비에 저장
                    }
                });
//
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.usercontentupload_menu, menu);
        return true;
    }

    // 상단>버튼 클릭시 이미지or동영상 선택
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            case R.id.menu_next:
                if (editText_title.getText().toString().equals("") | editText_addCategory.getText().toString().equals("") | editText_pollMode.getText().toString().equals("") | editText_description.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "빈 칸이 있어요!", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<String> userInputContents = new ArrayList<>();
                    userInputContents.add(editText_title.getText().toString());
                    userInputContents.add(editText_addCategory.getText().toString());
                    userInputContents.add(editText_pollMode.getText().toString());
                    userInputContents.add(editText_description.getText().toString());
                    Intent intent = new Intent(UserContentsUploadActivity.this, FileChoiceActivity.class);
                    intent.putStringArrayListExtra("userInputContents", userInputContents);
                    startActivity(intent);
                }
                break;
            case R.id.menu_home:

                Intent intent = new Intent(UserContentsUploadActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    //ContentChoiceDialog 확인
    @Override
    public void onDialogPositiveClick(ArrayList arrayList) {
        textView = findViewById(R.id.editText_addCategory);
        String string = arrayList.toString();
        if (arrayList.size() < 6) {
            String resultString = string.replace("[", "").replace("]", "");
            textView.setText(resultString);
        } else {
            textView.setText("");
            Toast.makeText(getApplicationContext(), "5개 이하로 선택해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
    //ContentChoiceDialog 취소
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    //ContentTypeDialog choiceItemCallback
    @Override
    public void choiceItemCallback(String string) {
        pollMode = string;
        editText_pollMode.setText(pollMode);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //AddContentFragment 에서 받은 "[사진] or [동영상]" 값을 받아서 처리
    @Override
    public void callbackMethod(String string) {
        if (string.equals("[사진]")) {
            addUserContentImage();

        } else if (string.equals("[동영상]")) {
            addUserContentVideo();

        } else {
            Toast.makeText(getApplicationContext(), "No Image/No Video...What the...?", Toast.LENGTH_SHORT).show();
        }

    }

}
