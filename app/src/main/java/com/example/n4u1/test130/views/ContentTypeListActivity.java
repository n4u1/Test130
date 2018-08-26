package com.example.n4u1.test130.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.n4u1.test130.R;


public class ContentTypeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SetActionBarTitle(getSupportActionBar(), "AQA");
        setContentView(R.layout.activity_content_type_list);


        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
//        if (getSupportActionBar() != null){
//            getSupportActionBar().setTitle("  AQA");
//        }
        getSupportActionBar().setIcon(R.drawable.aqa2);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


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
