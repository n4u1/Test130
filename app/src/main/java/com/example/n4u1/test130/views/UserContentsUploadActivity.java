package com.example.n4u1.test130.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.dialog.AddCategoryDialog;

public class UserContentsUploadActivity extends AppCompatActivity {

    private AddCategoryDialog mAddCategoryDialog;
    private Button mRightButton;
    private Button mLeftButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_contents_upload);

        CheckBox checkBox0 = findViewById(R.id.checkBox_0);


        findViewById(R.id.button_addCategory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCategoryDialog addCategoryDialog = new AddCategoryDialog();
                addCategoryDialog.show(getSupportFragmentManager(),"sdf");


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
