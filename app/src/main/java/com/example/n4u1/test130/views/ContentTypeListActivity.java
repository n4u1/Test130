package com.example.n4u1.test130.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.n4u1.test130.R;

import static com.example.n4u1.test130.setting.SetActionBarTitle.SetActionBarTitle;


public class ContentTypeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetActionBarTitle(getSupportActionBar(), "AQA");
        setContentView(R.layout.activity_content_type_list);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contenttypelist_menu, menu);
        return true;
    }
}
