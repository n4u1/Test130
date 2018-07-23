package com.example.n4u1.test130.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.dialog.AddCategoryDialog;
import com.example.n4u1.test130.dialog.ContentChoiceDialog;

import java.util.ArrayList;

public class UserContentsUploadActivity extends AppCompatActivity implements ContentChoiceDialog.ContentChoiceDialogListener {

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
                ContentChoiceDialog contentChoiceDialog = new ContentChoiceDialog();
                contentChoiceDialog.show(getSupportFragmentManager(), "Category Choice");

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 3959) {
            TextView textView = findViewById(R.id.textView_choiceCategory);
            Intent intent = getIntent();
//            Bundle mArgs = new Bundle();
//
            String stringResult = "";
            String[] myStrings = intent.getStringArrayExtra("Strings");
            for (int i = 0; i < 12; i++) {
                if (!myStrings[i].equals("")) {
                    stringResult = stringResult + myStrings[i];
                }
            }
            textView.setText(stringResult);

        }
    }


    @Override
    public void onDialogPositiveClick(ArrayList arrayList) {
        TextView textView = findViewById(R.id.textView_choiceCategory);
        String string = arrayList.toString();
        String resultString = string.replace("[", "").replace("]","");

        textView.setText(resultString);


    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
