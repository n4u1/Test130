package com.example.n4u1.test130.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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



        findViewById(R.id.button_addCategory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAddCategoryDialog = new AddCategoryDialog(UserContentsUploadActivity.this, "ad", "ddddd", mLeftButton, mRightButton);
                mAddCategoryDialog.show();

            }
        });
    }
}
