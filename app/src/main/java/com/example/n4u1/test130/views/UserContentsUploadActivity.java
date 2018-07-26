package com.example.n4u1.test130.views;

import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n4u1.test130.R;

import com.example.n4u1.test130.dialog.ContentChoiceDialog;
import com.example.n4u1.test130.dialog.ContentKindsChoiceDialog;

import java.io.File;
import java.util.ArrayList;

public class UserContentsUploadActivity extends AppCompatActivity implements ContentChoiceDialog.ContentChoiceDialogListener
                , AddContentFragment.AddContentFragmentListener{


    private static final int GALLEY_CODE = 100;
    private static final int VIDEO_CODE = 200;
    private String imgPath;


    ImageView imageView_userAddContent_1;
    ImageView imageView_userAddContent_2;
    ImageView imageView_userAddContent_3;
    ImageView imageView_userAddContent_4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_contents_upload);


        imageView_userAddContent_1 = findViewById(R.id.imageView_userAddContent_1);
        imageView_userAddContent_2 = findViewById(R.id.imageView_userAddContent_2);
        imageView_userAddContent_3 = findViewById(R.id.imageView_userAddContent_3);


        //카테고리 선택
        EditText editText = findViewById(R.id.editText_addCategory);

        //텍스트입력 안되게하고, 다이얼로그만 띄움
        editText.setFocusable(false);
        editText.setClickable(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentChoiceDialog contentChoiceDialog = new ContentChoiceDialog();
                contentChoiceDialog.show(getSupportFragmentManager(), "contentChoiceDialog");

            }
        });

        //현재 업로드된 사진 체크






        //사진 or 동영상 추가
        AddContentFragment addContentFragment = new AddContentFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_addContent, addContentFragment).commit();



    }



    public void addUserContentImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLEY_CODE);
    }


    private void addUserContentVideo() {
    }

    private int imageViewCheck () {
        int count = 0;

            if (imageView_userAddContent_1.getVisibility() == View.VISIBLE) {
                count ++;
            } if (imageView_userAddContent_2.getVisibility() == View.VISIBLE) {
                count ++;
            } if (imageView_userAddContent_3.getVisibility() == View.VISIBLE) {
                count ++;
            }
            return count;
    }




    //디바이스 이미지 경로 가져오기
    private String getPath(Uri uri) {
        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj,null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int checkCount = imageViewCheck();

        if(requestCode == GALLEY_CODE){
//            imageView_userAddContent_1.setVisibility(imageView_userAddContent_1.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

            if (checkCount == 0) {
                imageView_userAddContent_1.setVisibility(View.VISIBLE);
                imgPath = getPath(data.getData());
                System.out.println(data.getData());
                System.out.println(getPath(data.getData()));
                Log.d("LKJ", "check count 0000000 ");
                File f = new File(imgPath);
                imageView_userAddContent_1.setImageURI(Uri.fromFile(f));

            } if (checkCount == 1) {
                imageView_userAddContent_2.setVisibility(View.VISIBLE);
                imgPath = getPath(data.getData());
                System.out.println(data.getData());
                System.out.println(getPath(data.getData()));
                Log.d("LKJ", "check count 111111111 ");
                File f = new File(imgPath);
                imageView_userAddContent_2.setImageURI(Uri.fromFile(f));

            } if (checkCount == 2) {
                imageView_userAddContent_3.setVisibility(View.VISIBLE);
                imgPath = getPath(data.getData());
                System.out.println(data.getData());
                System.out.println(getPath(data.getData()));
                Log.d("LKJ", "check count 222222222 ");
                File f = new File(imgPath);
                imageView_userAddContent_3.setImageURI(Uri.fromFile(f));
            }


        } else {return;}

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onDialogPositiveClick(ArrayList arrayList) {
        TextView textView = findViewById(R.id.editText_addCategory);
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

        } else if(string.equals("[동영상]")) {
            addUserContentVideo();

        } else {
            Toast.makeText(getApplicationContext(), "No Image/No Video...What the...?", Toast.LENGTH_SHORT).show();
        }

    }

}
