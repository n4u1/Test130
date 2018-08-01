package com.example.n4u1.test130.setting;

import android.support.v7.app.ActionBar;
import android.text.Html;

import com.example.n4u1.test130.R;

public class SetActionBarTitle {


    public static void SetActionBarTitle(ActionBar actionBar, String title) {

        if (title != null) {
            actionBar.setLogo(R.drawable.logo_a);
            actionBar.setTitle(Html.fromHtml("<font color='#4485c9'>" + title + "</font>"));

//            actionBar.setIcon(R.drawable.logo_a);
//            actionBar.setDisplayShowHomeEnabled(true);



//        getSupportActionBar().setIcon(R.drawable.logo_a);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
    }
}
