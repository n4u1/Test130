package com.example.n4u1.test130.views;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ContentDTO;
import com.example.n4u1.test130.models.Post;
import com.example.n4u1.test130.recyclerview.PostViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class TestActivity extends AppCompatActivity {

    Button B;
    ImageView I,I2;

    Bitmap bitmap;
    Bitmap O;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        B = (Button) findViewById(R.id.button);
        I = (ImageView) findViewById(R.id.imageView_1);
        I2 = findViewById(R.id.imageView_2);

//        Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_0).into(((PostViewHolder)holder).imageView_postImg_0);

        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/test130-1068f.appspot.com/o/images%2F20180202144745_haswtala.jpg?alt=media&token=51dcab09-27f5-41a8-9269-b54c331c08d4")
                .into(I)
                .getView();

//
//        Glide.with(this)
//                .load(R.drawable.common_full_open_on_phone)
//                .into(I)
//                .getView();

//        BitmapDrawable BD =  I.setAlpha(0.5f);
//        bitmap = BD.getBitmap();
    }

    public void change_color(View view){

        if (I2.getVisibility() == View.GONE) {
            I.setAlpha(0.5f);
            I2.setVisibility(View.VISIBLE);
        } else {
            I.setAlpha(1.0f);
            I2.setVisibility(View.GONE);
        }

        /*O = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), bitmap.getConfig());

        for(int i=0; i<bitmap.getWidth(); i++){
            for(int j=0; j<bitmap.getHeight(); j++){
                int p = bitmap.getPixel(i, j);
                int b = Color.blue(p);

                int x =  0;
                int y =  0;
                b =  b+150;
                O.setPixel(i, j, Color.argb(Color.alpha(p), x, y, b));
            }
        }
        I.setImageBitmap(O);
*/
    }
}
