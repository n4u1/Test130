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
        , AddContentFragment.AddContentFragmentListener {


    private static final int GALLEY_CODE = 100;
    private static final int VIDEO_CODE = 200;
    private String imgPath;


    ImageView imageView_userAddContent_1;
    ImageView imageView_userAddContent_2;
    ImageView imageView_userAddContent_3;

    TextView textView;

    EditText editText_title;
    EditText editText_description;

    RadioGroup radioGroup_rs;
    String userContentType;

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
            getSupportActionBar().setTitle("  AQA");
        }
        getSupportActionBar().setIcon(R.drawable.ic_do_not_disturb_black_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        imageView_userAddContent_1 = findViewById(R.id.imageView_userAddContent_1);
        imageView_userAddContent_2 = findViewById(R.id.imageView_userAddContent_2);
        imageView_userAddContent_3 = findViewById(R.id.imageView_userAddContent_3);

        radioGroup_rs = findViewById(R.id.radioGroup_rs);
        userContentType = "";


        editText_title = findViewById(R.id.editText_title);
        editText_description = findViewById(R.id.editText_description);


        imageView_userAddContent_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popup = new PopupMenu(UserContentsUploadActivity.this, imageView_userAddContent_1);
                popup.getMenuInflater().inflate(R.menu.imagelongclick_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(UserContentsUploadActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popup.show();
                return false;
            }
        });

        imageView_userAddContent_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(UserContentsUploadActivity.this, imageView_userAddContent_2);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.imagelongclick_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(UserContentsUploadActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popup.show();//showing popup menu
                return false;
            }
        });

        imageView_userAddContent_3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(UserContentsUploadActivity.this, imageView_userAddContent_3);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.imagelongclick_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(UserContentsUploadActivity.this, "You Selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popup.show();//showing popup menu
                return false;
            }
        });

        //싱글튜표 or 순위투표 선택
        radioGroup_rs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButton_ranking:
                        userContentType = "ranking";
                        break;
                    case R.id.radioButton_single:
                        userContentType = "single";
                        break;
                }
            }
        });

        //카테고리 선택
        EditText editText_addCategory = findViewById(R.id.editText_addCategory);
        //텍스트입력 안되게하고, 다이얼로그만 띄움
        editText_addCategory.setFocusable(false);
        editText_addCategory.setClickable(false);
        editText_addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentChoiceDialog contentChoiceDialog = new ContentChoiceDialog();
                contentChoiceDialog.show(getSupportFragmentManager(), "contentChoiceDialog");
            }
        });
        //사진 or 동영상 추가
        AddContentFragment addContentFragment = new AddContentFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_addContent, addContentFragment).commit();
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
                        contentDTO.imageUrl = uri.toString();
                        contentDTO.title = editText_title.getText().toString();
                        contentDTO.description = editText_description.getText().toString();
                        contentDTO.uid = auth.getCurrentUser().getUid();
                        contentDTO.userID = auth.getCurrentUser().getEmail();
                        contentDTO.pollMode = userContentType;
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


    //디바이스 이미지 경로 가져오기
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

    // 상단V버튼 클릭시 업로드 실행
    //분기처리 해줘야할듯
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            case R.id.menu_upload:
                upload(imgPath);
                break;

        }

        onBackPressed();
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int checkCount = imageViewCheck();
        if (requestCode == GALLEY_CODE) {
            if (checkCount == 0) {
                imageView_userAddContent_1.setVisibility(View.VISIBLE);
                imgPath = getPath(data.getData());
                System.out.println(data.getData());
                System.out.println(getPath(data.getData()));
                File f = new File(imgPath);
                imageView_userAddContent_1.setImageURI(Uri.fromFile(f));

            }
            if (checkCount == 1) {
                imageView_userAddContent_2.setVisibility(View.VISIBLE);
                imgPath = getPath(data.getData());
                System.out.println(data.getData());
                System.out.println(getPath(data.getData()));
                File f = new File(imgPath);
                imageView_userAddContent_2.setImageURI(Uri.fromFile(f));

            }
            if (checkCount == 2) {
                imageView_userAddContent_3.setVisibility(View.VISIBLE);
                imgPath = getPath(data.getData());
                System.out.println(data.getData());
                System.out.println(getPath(data.getData()));
                File f = new File(imgPath);
                imageView_userAddContent_3.setImageURI(Uri.fromFile(f));
            }
        } else {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onDialogPositiveClick(ArrayList arrayList) {
        textView = findViewById(R.id.editText_addCategory);
        String string = arrayList.toString();
        if (arrayList.size() < 5) {
            String resultString = string.replace("[", "").replace("]", "");
            textView.setText(resultString);
        } else {
            textView.setText("");
            Toast.makeText(getApplicationContext(), "5개 이하로 선택해주세요.", Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

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
