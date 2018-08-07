package com.example.n4u1.test130.views;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

public class FileChoiceActivity extends AppCompatActivity
        implements ImageFragment.OnFragmentInteractionListener,
        VideoFragment.OnFragmentInteractionListener,
        CameraFragment.OnFragmentInteractionListener {

    private String imgPath;
    private String[] imgStrings;
    private String uriString;
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private ArrayList<String> userInputContents;


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

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        final int[] i = new int[1];
        i[0] = 0;

        Log.d("userInputContents 0", userInputContents.get(0));
        Log.d("userInputContents 1", userInputContents.get(1));
        Log.d("userInputContents 2", userInputContents.get(2));
        Log.d("userInputContents 3", userInputContents.get(3));


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
        onBackPressed();
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

        final ContentDTO contentDTO = new ContentDTO();

        if (uri[0].length() != 0) {
            Uri file_0 = Uri.fromFile(new File(uri[0]));
            StorageReference storageRef = storage.getReferenceFromUrl("gs://test130-1068f.appspot.com");
            final StorageReference riversRef = storageRef.child("images/" + file_0.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(file_0);
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
                            uriString = uri.toString();
                            contentDTO.imageUrl_0 = uri.toString();
                            contentDTO.uid = auth.getCurrentUser().getUid();
                            contentDTO.userID = auth.getCurrentUser().getEmail();
                            contentDTO.title = userInputContents.get(0);
                            contentDTO.description = userInputContents.get(1);
                            contentDTO.pollMode = userInputContents.get(2); // 단일투표 or 순위투표
                            contentDTO.contentType = userInputContents.get(3); //아침, 패션, 정치 등..
                            database.getReference().child("user_contents").push().setValue(contentDTO); //contentDTO에 담아서 setValue로 디비에 저장
                        }
                    });
                }
            });
        }
        if (uri[1].length() != 0) {
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
//                            contentDTO.imageUrl_1 = uri.toString();
//                            database.getReference().child("user_contents").setValue(contentDTO);
//                            String contentKey = database.getReference().child("user_contents").get();
//                            database.getReference().child("user_contents").child(contentKey).updateChildren()
                        }
                    });
                }
            });
        }

        if (uri[2].length() != 0) {
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
                        public void onSuccess(Uri uri) {//
//                            contentDTO.imageUrl_2 = uri.toString();
//                            database.getReference().child("user_contents").setValue(contentDTO);
                        }
                    });
                }
            });
        }

        if (uri[3].length() != 0) {
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
//                            contentDTO.imageUrl_3 = uri.toString();
//                            database.getReference().child("user_contents").setValue(contentDTO);
                        }
                    });
                }
            });
        }

        if (uri[4].length() != 0) {
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
                        public void onSuccess(Uri uri) {//
//                            contentDTO.imageUrl_4 = uri.toString();
//                            database.getReference().child("user_contents").setValue(contentDTO);
                        }
                    });
                }
            });
        }

        if (uri[5].length() != 0) {
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
                        public void onSuccess(Uri uri) {//
//                            contentDTO.imageUrl_5 = uri.toString();
//                            database.getReference().child("user_contents").setValue(contentDTO);
                        }
                    });
                }
            });
        }

        if (uri[6].length() != 0) {
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
                        public void onSuccess(Uri uri) {//
//                            contentDTO.imageUrl_6 = uri.toString();
//                            database.getReference().child("user_contents").setValue(contentDTO);
                        }
                    });
                }
            });
        }

        if (uri[7].length() != 0) {
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
                        public void onSuccess(Uri uri) {//
//                            contentDTO.imageUrl_7 = uri.toString();
//                            database.getReference().child("user_contents").setValue(contentDTO);
                        }
                    });
                }
            });
        }

        if (uri[8].length() != 0) {
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
                        public void onSuccess(Uri uri) {//
//                            contentDTO.imageUrl_8 = uri.toString();
//                            database.getReference().child("user_contents").setValue(contentDTO);
                        }
                    });
                }
            });
        }

        if (uri[9].length() != 0) {
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
                        public void onSuccess(Uri uri) {//
//                            contentDTO.imageUrl_9 = uri.toString();
//                            database.getReference().child("user_contents").setValue(contentDTO);
                        }
                    });
                }
            });
        }
        if (userInputContents.get(0) == null | userInputContents.get(1) == null | userInputContents.get(2) == null | userInputContents.get(3) == null) {
            Toast.makeText(getApplicationContext(), "빈 칸이 있어요!", Toast.LENGTH_SHORT).show();
        }

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
