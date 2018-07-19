package com.example.n4u1.test130.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.n4u1.test130.R;


public class AddCategoryDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
        mBuilder.setView(mLayoutInflater.inflate(R.layout.activity_add_category_dialog, null));
        mBuilder.setTitle("카테고리 선택");

        return mBuilder.create();
    }
}



