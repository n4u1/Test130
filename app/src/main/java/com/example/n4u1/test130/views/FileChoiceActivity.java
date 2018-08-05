package com.example.n4u1.test130.views;

import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.fragments.CameraFragment;
import com.example.n4u1.test130.fragments.ImageFragment;
import com.example.n4u1.test130.fragments.VideoFragment;

public class FileChoiceActivity extends AppCompatActivity
        implements ImageFragment.OnFragmentInteractionListener,
        VideoFragment.OnFragmentInteractionListener,
        CameraFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_choice);


        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("  AQA");
        }
        getSupportActionBar().setIcon(R.drawable.ic_do_not_disturb_black_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        ViewPager viewPager = findViewById(R.id.viewPager);
        Fragment[] arrFragments = new Fragment[3];
        arrFragments[0] = new ImageFragment();
        arrFragments[1] = new VideoFragment();
        arrFragments[2] = new CameraFragment();
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), arrFragments);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);



    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private Fragment[] arrFragments;


        public PagerAdapter(FragmentManager fm, Fragment[] arrFragments) {
            super(fm);
            this.arrFragments = arrFragments;
        }

        @Override
        public Fragment getItem(int position) {
            return arrFragments[position];
        }

        @Override
        public int getCount() {
            return arrFragments.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0 : return "사진";
                case 1 : return "동영상";
                case 2 : return "카메라";
                default: return "";
            }
        }
    }

}
