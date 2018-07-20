package com.example.n4u1.test130.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n4u1.test130.R;


public class AddCategoryDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
        View view = mLayoutInflater.inflate(R.layout.activity_add_category_dialog, null);
        mBuilder.setView(mLayoutInflater.inflate(R.layout.activity_add_category_dialog, null));

        final CheckBox checkBox0 = view.getRootView().findViewById(R.id.checkBox_0);
        final CheckBox checkBox1 = view.getRootView().findViewById(R.id.checkBox_1);
        final CheckBox checkBox2 = view.getRootView().findViewById(R.id.checkBox_2);
        final CheckBox checkBox3 = view.getRootView().findViewById(R.id.checkBox_3);
        final CheckBox checkBox4 = view.getRootView().findViewById(R.id.checkBox_4);
        final CheckBox checkBox5 = view.getRootView().findViewById(R.id.checkBox_5);
        final CheckBox checkBox6 = view.getRootView().findViewById(R.id.checkBox_6);
        final CheckBox checkBox7 = view.getRootView().findViewById(R.id.checkBox_7);
        final CheckBox checkBox8 = view.getRootView().findViewById(R.id.checkBox_8);
        final CheckBox checkBox9 = view.getRootView().findViewById(R.id.checkBox_9);
        final CheckBox checkBox10 = view.getRootView().findViewById(R.id.checkBox_10);
        final CheckBox checkBox11 = view.getRootView().findViewById(R.id.checkBox_11);



        mBuilder.setTitle("카테고리 선택aa");
        mBuilder.setView(view)
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                for (int i = 0; i < 13; i++) {


                                }


                            }
                        }).setNegativeButton("취소", null);




        return mBuilder.create();
    }




}



