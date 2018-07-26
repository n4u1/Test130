package com.example.n4u1.test130.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n4u1.test130.R;

import com.example.n4u1.test130.dialog.ContentChoiceDialog;
import com.example.n4u1.test130.dialog.ContentKindsChoiceDialog;

import java.util.ArrayList;

public class UserContentsUploadActivity extends AppCompatActivity implements ContentChoiceDialog.ContentChoiceDialogListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_contents_upload);

        //카테고리 선택
        EditText editText = findViewById(R.id.editText_addCategory);
        editText.setFocusable(false);
        editText.setClickable(false);
        editText.setOnClickListener(new View.OnClickListener() {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    public void callContentChoice(String string) {
        Toast.makeText(getApplicationContext(), "string ???????? : " + string, Toast.LENGTH_SHORT).show();

    }
}
