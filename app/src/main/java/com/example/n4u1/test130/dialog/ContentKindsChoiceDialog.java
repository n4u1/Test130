package com.example.n4u1.test130.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.views.UserContentsUploadActivity;

import java.util.ArrayList;

public class ContentKindsChoiceDialog extends DialogFragment {


    public ContentKindsChoiceDialog() {}


    public interface ContentKindsChoiceDialogListener {
        public void onDialogPositiveClickd(String string);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    ContentKindsChoiceDialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ContentKindsChoiceDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
//        final String[] mSelectedItems = new String[1];
          // Where we track the selected items

          // Where we track the selected items

        final AlertDialog.Builder[] builder = {new AlertDialog.Builder(getActivity())};
        builder[0].setTitle("추가할 자료의 종류를 선택해주세요.")
                .setSingleChoiceItems(R.array.contentskinds, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mSelectedItem;
//                        mSelectedItems[0] = String.valueOf(i);
                        if (i == 0) {
                            mSelectedItem = "사진";
                        } else if (i == 1) {
                            mSelectedItem = "동영상";
                        } else {
                            return;
                        }
                        Intent intent = new Intent(getDialog().getContext(), UserContentsUploadActivity.class);
                        intent.putExtra("ContentKindsChoice", mSelectedItem);
                        startActivityForResult(intent, 1234);
                    }
                });



        return builder[0].create();
    }
}
