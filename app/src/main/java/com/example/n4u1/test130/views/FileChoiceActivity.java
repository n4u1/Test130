package com.example.n4u1.test130.views;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.fragments.CameraFragment;
import com.example.n4u1.test130.fragments.ImageFragment;
import com.example.n4u1.test130.fragments.VideoFragment;
import com.example.n4u1.test130.models.ContentDTO;
import com.example.n4u1.test130.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

public class FileChoiceActivity extends AppCompatActivity
        implements ImageFragment.OnFragmentInteractionListener,
        VideoFragment.OnFragmentInteractionListener,
        CameraFragment.OnFragmentInteractionListener {

    private String[] imgStrings;
    private ArrayList<String> userInputContents;
    private UploadTask uploadTask;
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    private FirebaseDatabase mdatabase;
    private StorageReference riversRef;
    private StorageReference storageRef;
    private DatabaseReference mdatabaseRef;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_choice);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
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

        userInputContents = new ArrayList<>();
        userInputContents = getIntent().getStringArrayListExtra("userInputContents");

        imgStrings = new String[10];

        mdatabaseRef = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        mdatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        final int[] i = new int[1];
        i[0] = 0;


        //현재page를 position으로 확인
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("onPage_position", String.valueOf(position));
                i[0] = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }//super.onCreate(savedInstanceState);

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
                case 0:
                    return "사진";
                case 1:
                    return "동영상";
                case 2:
                    return "카메라";
                default:
                    return "";
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filechoice_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            case R.id.menu_confirm:
                Toast toast = Toast.makeText(getApplicationContext(), "투표가 시작 되었습니다!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
                upload(imgStrings);
                break;
        }

        Intent intent = new Intent(FileChoiceActivity.this, HomeActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    //upload할 파일의 경로를 리스너로 받음
    @Override
    public void onFragmentInteraction(String[] strings) {
        for (int i = 0; i < 10; i++) {
            if (strings[i] == null) {
                return;
            } else {
                imgStrings[i] = strings[i];
            }

        }

    }

    //upload할 파일의 경로를 리스너로 받음 → 받아서 FireBase 에 업로드
    public void upload(final String[] uri) {

        int itemViewTypeCount = 1;
        Log.d("lkjtest", String.valueOf(itemViewTypeCount));

        mdatabaseRef = FirebaseDatabase.getInstance().getReference();
        key = mdatabaseRef.child("user_contents").push().getKey();

        //content input start into fireBase "user_contents"
        ContentDTO contentDTO = new ContentDTO();
        contentDTO.title = userInputContents.get(0);
        contentDTO.contentType = userInputContents.get(1);
        contentDTO.pollMode = userInputContents.get(2);
        contentDTO.description = userInputContents.get(3);
        contentDTO.uid = auth.getCurrentUser().getUid();
        contentDTO.userID = auth.getCurrentUser().getEmail();
        mdatabaseRef.child("user_contents").child(key).setValue(contentDTO);
//
        if (uri[0].length() != 0) {
            Uri file_0 = Uri.fromFile(new File(uri[0]));
            storageRef = storage.getReferenceFromUrl("gs://test130-1068f.appspot.com");
            riversRef = storageRef.child("images/" + file_0.getLastPathSegment());
            uploadTask = riversRef.putFile(file_0);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            mdatabaseRef.child("user_contents").child(key).child("imageUrl_0").setValue(uri.toString());
                        }
                    });
                }
            });
        }

        if (uri[1].length() != 0) {
            itemViewTypeCount++;
            Uri file_1 = Uri.fromFile(new File(uri[1]));
            StorageReference storageRef = storage.getReferenceFromUrl("gs://test130-1068f.appspot.com");
            final StorageReference riversRef = storageRef.child("images/" + file_1.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(file_1);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            mdatabaseRef.child("user_contents").child(key).child("imageUrl_1").setValue(uri.toString());
                        }
                    });
                }
            });
        }

        if (uri[2].length() != 0) {
            itemViewTypeCount++;
            Uri file_2 = Uri.fromFile(new File(uri[2]));
            StorageReference storageRef = storage.getReferenceFromUrl("gs://test130-1068f.appspot.com");
            final StorageReference riversRef = storageRef.child("images/" + file_2.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(file_2);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            mdatabaseRef.child("user_contents").child(key).child("imageUrl_2").setValue(uri.toString());
                        }
                    });
                }
            });
        }

        if (uri[3].length() != 0) {
            itemViewTypeCount++;
            Uri file_3 = Uri.fromFile(new File(uri[3]));
            StorageReference storageRef = storage.getReferenceFromUrl("gs://test130-1068f.appspot.com");
            final StorageReference riversRef = storageRef.child("images/" + file_3.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(file_3);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {//
                            mdatabaseRef.child("user_contents").child(key).child("imageUrl_3").setValue(uri.toString());
                        }
                    });
                }
            });
        }

        if (uri[4].length() != 0) {
            itemViewTypeCount++;
            Uri file_4 = Uri.fromFile(new File(uri[4]));
            StorageReference storageRef = storage.getReferenceFromUrl("gs://test130-1068f.appspot.com");
            final StorageReference riversRef = storageRef.child("images/" + file_4.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(file_4);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            mdatabaseRef.child("user_contents").child(key).child("imageUrl_4").setValue(uri.toString());
                        }
                    });
                }
            });
        }

        if (uri[5].length() != 0) {
            itemViewTypeCount++;
            Uri file_5 = Uri.fromFile(new File(uri[5]));
            StorageReference storageRef = storage.getReferenceFromUrl("gs://test130-1068f.appspot.com");
            final StorageReference riversRef = storageRef.child("images/" + file_5.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(file_5);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            mdatabaseRef.child("user_contents").child(key).child("imageUrl_5").setValue(uri.toString());
                        }
                    });
                }
            });
        }

        if (uri[6].length() != 0) {
            itemViewTypeCount++;
            Uri file_6 = Uri.fromFile(new File(uri[6]));
            StorageReference storageRef = storage.getReferenceFromUrl("gs://test130-1068f.appspot.com");
            final StorageReference riversRef = storageRef.child("images/" + file_6.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(file_6);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            mdatabaseRef.child("user_contents").child(key).child("imageUrl_6").setValue(uri.toString());
                        }
                    });
                }
            });
        }

        if (uri[7].length() != 0) {
            itemViewTypeCount++;
            Uri file_7 = Uri.fromFile(new File(uri[7]));
            StorageReference storageRef = storage.getReferenceFromUrl("gs://test130-1068f.appspot.com");
            final StorageReference riversRef = storageRef.child("images/" + file_7.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(file_7);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            mdatabaseRef.child("user_contents").child(key).child("imageUrl_7").setValue(uri.toString());
                        }
                    });
                }
            });
        }

        if (uri[8].length() != 0) {
            itemViewTypeCount++;
            Uri file_8 = Uri.fromFile(new File(uri[8]));
            StorageReference storageRef = storage.getReferenceFromUrl("gs://test130-1068f.appspot.com");
            final StorageReference riversRef = storageRef.child("images/" + file_8.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(file_8);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            mdatabaseRef.child("user_contents").child(key).child("imageUrl_8").setValue(uri.toString());
                        }
                    });
                }
            });
        }

        if (uri[9].length() != 0) {
            itemViewTypeCount++;
            Uri file_9 = Uri.fromFile(new File(uri[9]));
            StorageReference storageRef = storage.getReferenceFromUrl("gs://test130-1068f.appspot.com");
            final StorageReference riversRef = storageRef.child("images/" + file_9.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(file_9);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            mdatabaseRef.child("user_contents").child(key).child("imageUrl_9").setValue(uri.toString());
                        }
                    });
                }
            });
        }
        mdatabaseRef.child("user_contents").child(key).child("itemViewType").setValue(itemViewTypeCount);


    }


    //디바이스 경로 가져오기
    private String getPath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }

}
