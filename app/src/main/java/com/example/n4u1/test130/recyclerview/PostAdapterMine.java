package com.example.n4u1.test130.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ContentDTO;
import com.example.n4u1.test130.util.GlideApp;
import com.example.n4u1.test130.views.HomeActivity;
import com.example.n4u1.test130.views.PollRankingActivity;
import com.example.n4u1.test130.views.PollSingleActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PostAdapterMine extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<ContentDTO> contentDTOS;
    private static final int ITEM_VIEW_TYPE_1 = 1;
    private static final int ITEM_VIEW_TYPE_2 = 2;
    private static final int ITEM_VIEW_TYPE_3 = 3;
    private static final int ITEM_VIEW_TYPE_0 = 0;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;



    public PostAdapterMine(Context context, ArrayList<ContentDTO> listItem) {
        this.mContext = context;
        this.contentDTOS = listItem;
    }


    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        auth = FirebaseAuth.getInstance();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_my_like, parent, false);
        return new PostViewHolderMine(view);
    }


    @Override
    public int getItemViewType(int position) {
        if (contentDTOS.get(position).getItemViewType() == 1) {
            return ITEM_VIEW_TYPE_0;
        } else if (contentDTOS.get(position).getItemViewType() == 2) {
            return ITEM_VIEW_TYPE_1;
        } else if (contentDTOS.get(position).getItemViewType() == 3) {
            return ITEM_VIEW_TYPE_2;
        } else if (contentDTOS.get(position).getItemViewType() == 4) {
            return ITEM_VIEW_TYPE_3;
        } else if (contentDTOS.get(position).getItemViewType() == 5) {
            return ITEM_VIEW_TYPE_3;
        } else if (contentDTOS.get(position).getItemViewType() == 5) {
            return ITEM_VIEW_TYPE_3;
        } else if (contentDTOS.get(position).getItemViewType() == 6) {
            return ITEM_VIEW_TYPE_3;
        } else if (contentDTOS.get(position).getItemViewType() == 7) {
            return ITEM_VIEW_TYPE_3;
        } else if (contentDTOS.get(position).getItemViewType() == 8) {
            return ITEM_VIEW_TYPE_3;
        } else if (contentDTOS.get(position).getItemViewType() == 9) {
            return ITEM_VIEW_TYPE_3;
        } else if (contentDTOS.get(position).getItemViewType() == 10) {
            return ITEM_VIEW_TYPE_3;
        } else {
            return ITEM_VIEW_TYPE_1;
        }
    }

    //contentDTO 내용을 아이템셋에 배치
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final List<String> uidLists = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();

        //좋아요 클릭을위해서 참조해서 키값 받아옴
        firebaseDatabase.getReference().child("user_contents").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uidLists.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uidKey = snapshot.getKey();
                    uidLists.add(uidKey);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //아이템 바인딩
        ((PostViewHolderMine) holder).textView_title.setText(contentDTOS.get(position).title);
        ((PostViewHolderMine) holder).textView_userName.setText(contentDTOS.get(position).userID);
        ((PostViewHolderMine) holder).textView_contentType.setText(contentDTOS.get(position).contentType);
        ((PostViewHolderMine) holder).textView_likeCount.setText(String.valueOf(contentDTOS.get(position).likeCount));
        ((PostViewHolderMine) holder).textView_hitCount.setText(String.valueOf(contentDTOS.get(position).contentHit));


        //제목 클릭시 컨텐트 디테일(PollActivity) 로 넘어감
        ((PostViewHolderMine) holder).textView_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movePoll(position);
            }
        });

        //좋아요 클릭시 버튼색 변경
        ((PostViewHolderMine) holder).imageView_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLikeClicked(firebaseDatabase.getReference().child("user_contents").child(uidLists.get(position)));

            }
        });


        if (contentDTOS.get(position).likes.containsKey(auth.getCurrentUser().getUid())) {
            ((PostViewHolderMine) holder).imageView_like.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            ((PostViewHolderMine) holder).imageView_like.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }


    }

    private void movePoll(int position) {
        String string = contentDTOS.get(position).contentKey;

        if (contentDTOS.get(position).getPollMode().equals("순위 투표")) {

            Intent intent = new Intent(mContext, PollRankingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("contentKey", string);
            bundle.putInt("itemViewType", contentDTOS.get(position).itemViewType);
            bundle.putInt("contentHit", contentDTOS.get(position).contentHit);
            intent.putExtras(bundle);
            mContext.startActivity(intent);

        }
        if (contentDTOS.get(position).getPollMode().equals("단일 투표")) {

            Intent intent = new Intent(mContext, PollSingleActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("contentKey", string);
            bundle.putInt("itemViewType", contentDTOS.get(position).itemViewType);
            bundle.putInt("contentHit", contentDTOS.get(position).contentHit);
            intent.putExtras(bundle);
            mContext.startActivity(intent);

        }
    }

    @Override
    public int getItemCount() {
        return contentDTOS.size();
    }



    private void onLikeClicked(final DatabaseReference postRef) {

        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                ContentDTO contentDTO = mutableData.getValue(ContentDTO.class);
                if (contentDTO == null) {
                    return Transaction.success(mutableData);
                }

                if (contentDTO.likes.containsKey(auth.getCurrentUser().getUid())) {
                    // Unstar the post and remove self from stars
                    // 좋아요카운트 -1 하고 리스트에서 삭제
                    contentDTO.likeCount = contentDTO.likeCount - 1;
                    contentDTO.likes.remove(auth.getCurrentUser().getUid());
                    // users/내uid/컨텐트key/false      : 좋아요 누른 컨텐츠 리스트 false
                    firebaseDatabase.getReference()
                            .child("users")
                            .child(auth.getCurrentUser().getUid())
                            .child("likeContent")
                            .child(contentDTO.getContentKey())
                            .setValue("false");

                } else {
                    // Star the post and add self to stars
                    contentDTO.likeCount = contentDTO.likeCount + 1;
                    contentDTO.likes.put(auth.getCurrentUser().getUid(), true);
                    // users/내uid/컨텐트key/true       : 좋아요 누른 컨텐츠 리스트 true
                    firebaseDatabase.getReference()
                            .child("users")
                            .child(auth.getCurrentUser().getUid())
                            .child("likeContent")
                            .child(contentDTO.getContentKey())
                            .setValue("true");
//                    User user = new User();
//                    Map<String, Object> userValues = user.toMap();
//                    Map<String, Object> childUpdates = new HashMap<>();
//                    childUpdates.put("/users/", userValues);
//                    databaseReference.updateChildren(childUpdates);

                }

                // Set value and report transaction success
                mutableData.setValue(contentDTO);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d("lkjlkj", "postTransaction:onComplete:" + databaseError);


            }
        });
    }
}
