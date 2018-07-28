package com.example.n4u1.test130.setting;

import android.support.v7.app.ActionBar;
import android.text.Html;

public class SetActionBarTitle {


    public static void SetActionBarTitle(ActionBar actionBar, String title) {

        if (title != null) {
            actionBar.setTitle(Html.fromHtml("<font color='#4485c9'>" + title + "</font>"));
        }
    }
}
