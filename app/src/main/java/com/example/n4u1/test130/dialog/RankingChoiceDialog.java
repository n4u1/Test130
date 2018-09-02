package com.example.n4u1.test130.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.n4u1.test130.R;

import java.util.ArrayList;

public class RankingChoiceDialog extends DialogFragment {

    public RankingChoiceDialog() {}


    public interface ContentChoiceDialogListener {
        public void onDialogPositiveClick(ArrayList arrayList);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    ContentChoiceDialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ContentChoiceDialogListener) getActivity();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final ArrayList mSelectedItem = new ArrayList();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("순위를 정해주세요.")
                .setSingleChoiceItems(R.array.ranking, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            mSelectedItem.clear();
                            mSelectedItem.add(i);
                        } else if (i == 1) {
                            mSelectedItem.clear();
                            mSelectedItem.add(i);
                        }
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (mSelectedItem.contains(0)) {
                            mSelectedItem.clear();
                            mSelectedItem.add("사진");
                        } else {
                            mSelectedItem.clear();
                            mSelectedItem.add("동영상");
                        }
//                            Bundle args = new Bundle();
//                            args.putString("test", String.valueOf(mSelectedItem));
//                            myListener.myCallback(args);


//                        Toast.makeText(getContext(), "arrayList : " + arrayList, Toast.LENGTH_SHORT).show();

//                        myListener.myCallback(savedInstanceState);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
