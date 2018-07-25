package com.example.n4u1.test130.views;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.dialog.ContentChoiceDialog;
import com.example.n4u1.test130.dialog.ContentKindsChoiceDialog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddContentFragment extends Fragment implements ContentKindsChoiceDialog.ContentKindsChoiceDialogListener{


    public AddContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_content, container, false);
        view.findViewById(R.id.imageView_addContent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentKindsChoiceDialog contentKindsChoiceDialog = new ContentKindsChoiceDialog();
                contentKindsChoiceDialog.show(getFragmentManager(), "contentKindsChoiceDialog");
            }
        });

        return view;

    }


    @Override
    public void onDialogPositiveClickd(String string) {
        Toast.makeText(getContext(), "???????????" + string, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
