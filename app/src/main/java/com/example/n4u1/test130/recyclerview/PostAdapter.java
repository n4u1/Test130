package com.example.n4u1.test130.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.sip.SipSession;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ContentDTO;
import com.example.n4u1.test130.models.User;
import com.example.n4u1.test130.util.GlideApp;
import com.example.n4u1.test130.views.HomeActivity;
import com.example.n4u1.test130.views.PollActivity;
import com.example.n4u1.test130.views.PollRankingActivity;
import com.example.n4u1.test130.views.PollSingleActivity;
import com.example.n4u1.test130.views.TestActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private ArrayList strings;
    private Context mContext;
    private ArrayList<ContentDTO> contentDTOS;
    private static final int ITEM_VIEW_TYPE_1 = 1;
    private static final int ITEM_VIEW_TYPE_2 = 2;
    private static final int ITEM_VIEW_TYPE_3 = 3;
    private static final int ITEM_VIEW_TYPE_0 = 0;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;



    public PostAdapter(Context context, ArrayList<ContentDTO> listItem) {
        this.mContext = context;
        this.contentDTOS = listItem;
    }





    //아이템 클릭시 실행 함수
    private ItemClick itemClick;



    public interface ItemClick {
        public void onClick(View view,int position);
    }


    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }



    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        auth = FirebaseAuth.getInstance();
        if (viewType == ITEM_VIEW_TYPE_0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_home_0_img, parent, false);
            return new PostViewHolder(view);
        } else if (viewType == ITEM_VIEW_TYPE_1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_home_1_img, parent, false);
            return new PostViewHolder1(view);
        } else if (viewType == ITEM_VIEW_TYPE_2){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_home_2_img, parent, false);
            return new PostViewHolder2(view);
        } else if (viewType == ITEM_VIEW_TYPE_3) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_home_3_img, parent, false);
            return new PostViewHolder3(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_home_0_img, parent, false);
            return new PostViewHolder(view);
        }
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

        switch (holder.getItemViewType()) {



            case ITEM_VIEW_TYPE_0 :
                //이미지 클릭시 컨텐트 디테일(PollActivity) 로 넘어감
                ((PostViewHolder)holder).imageView_postImg_0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        movePoll(position);
//                        String string = contentDTOS.get(position).contentKey;
//                        if (contentDTOS.get(position).getPollMode().equals("순위 투표")) {
//                            movePoll(position);
//
//                        } if (contentDTOS.get(position).getPollMode().equals("단일 투표")) {
//                            movePoll(position);
//                        }
                    }
                });
                //아이템 바인딩
                ((PostViewHolder)holder).textView_title.setText(contentDTOS.get(position).title);
                ((PostViewHolder)holder).textView_userName.setText(contentDTOS.get(position).userID);
                ((PostViewHolder)holder).textView_contentType.setText(contentDTOS.get(position).contentType);
                ((PostViewHolder)holder).textView_likeCount.setText(String.valueOf(contentDTOS.get(position).likeCount));
                ((PostViewHolder)holder).textView_hitCount.setText(String.valueOf(contentDTOS.get(position).contentHit));


                GlideApp
                        .with(holder.itemView.getContext())
                        .load(contentDTOS.get(position).imageUrl_0)
                        .centerCrop()
                        .thumbnail(Glide.with(holder.itemView.getContext()).load(R.drawable.loadingicon))
                        .into(((PostViewHolder)holder).imageView_postImg_0);




                ((PostViewHolder)holder).imageView_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLikeClicked(firebaseDatabase.getReference().child("user_contents").child(uidLists.get(position)));
                        resetHomeActivity();


                    }
                });
                if (contentDTOS.get(position).likes.containsKey(auth.getCurrentUser().getUid())) {
                    ((PostViewHolder)holder).imageView_like.setImageResource(R.drawable.ic_favorite_black_24dp);
                } else {
                    ((PostViewHolder)holder).imageView_like.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }
                break;

            case ITEM_VIEW_TYPE_1 :
                ((PostViewHolder1)holder).imageView_postImg_0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        movePoll(position);
                    }
                });
                ((PostViewHolder1)holder).imageView_postImg_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        movePoll(position);
                    }
                });

                ((PostViewHolder1)holder).imageView_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLikeClicked(firebaseDatabase.getReference().child("user_contents").child(uidLists.get(position)));
                        resetHomeActivity();
                    }
                });
                if (contentDTOS.get(position).likes.containsKey(auth.getCurrentUser().getUid())) {
                    ((PostViewHolder1)holder).imageView_like.setImageResource(R.drawable.ic_favorite_black_24dp);
                } else {
                    ((PostViewHolder1)holder).imageView_like.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }
                ((PostViewHolder1)holder).textView_title.setText(contentDTOS.get(position).title);
                ((PostViewHolder1)holder).textView_userName.setText(contentDTOS.get(position).userID);
                ((PostViewHolder1)holder).textView_contentType.setText(contentDTOS.get(position).contentType);
                ((PostViewHolder1)holder).textView_likeCount.setText(String.valueOf(contentDTOS.get(position).likeCount));
                ((PostViewHolder1)holder).textView_hitCount.setText(String.valueOf(contentDTOS.get(position).contentHit));
                
                GlideApp.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_0).centerCrop().thumbnail(Glide.with(holder.itemView.getContext()).load(R.drawable.loadingicon)).into(((PostViewHolder1)holder).imageView_postImg_0);
                GlideApp.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_1).centerCrop().thumbnail(Glide.with(holder.itemView.getContext()).load(R.drawable.loadingicon)).into(((PostViewHolder1)holder).imageView_postImg_1);



                break;

            case ITEM_VIEW_TYPE_2 :
                ((PostViewHolder2)holder).imageView_postImg_0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        movePoll(position);
                    }
                });
                ((PostViewHolder2)holder).imageView_postImg_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        movePoll(position);
                    }
                });
                ((PostViewHolder2)holder).imageView_postImg_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        movePoll(position);
                    }
                });
                ((PostViewHolder2)holder).imageView_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLikeClicked(firebaseDatabase.getReference().child("user_contents").child(uidLists.get(position)));
                        resetHomeActivity();

                    }
                });
                if (contentDTOS.get(position).likes.containsKey(auth.getCurrentUser().getUid())) {
                    ((PostViewHolder2)holder).imageView_like.setImageResource(R.drawable.ic_favorite_black_24dp);
                } else {
                    ((PostViewHolder2)holder).imageView_like.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }
                ((PostViewHolder2)holder).textView_title.setText(contentDTOS.get(position).title);
                ((PostViewHolder2)holder).textView_userName.setText(contentDTOS.get(position).userID);
                ((PostViewHolder2)holder).textView_contentType.setText(contentDTOS.get(position).contentType);
                ((PostViewHolder2)holder).textView_likeCount.setText(String.valueOf(contentDTOS.get(position).likeCount));
                ((PostViewHolder2)holder).textView_hitCount.setText(String.valueOf(contentDTOS.get(position).contentHit));
                GlideApp.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_0).centerCrop().thumbnail(Glide.with(holder.itemView.getContext()).load(R.drawable.loadingicon)).into(((PostViewHolder2)holder).imageView_postImg_0);
                GlideApp.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_1).centerCrop().thumbnail(Glide.with(holder.itemView.getContext()).load(R.drawable.loadingicon)).into(((PostViewHolder2)holder).imageView_postImg_1);
                GlideApp.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_2).centerCrop().thumbnail(Glide.with(holder.itemView.getContext()).load(R.drawable.loadingicon)).into(((PostViewHolder2)holder).imageView_postImg_2);

                break;

            case ITEM_VIEW_TYPE_3 :
                ((PostViewHolder3)holder).imageView_postImg_0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        movePoll(position);
                    }
                });
                ((PostViewHolder3)holder).imageView_postImg_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        movePoll(position);
                    }
                });
                ((PostViewHolder3)holder).imageView_postImg_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        movePoll(position);
                    }
                });
                ((PostViewHolder3)holder).imageView_postImg_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        movePoll(position);
                    }
                });
                ((PostViewHolder3)holder).imageView_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLikeClicked(firebaseDatabase.getReference().child("user_contents").child(uidLists.get(position)));
                        resetHomeActivity();

                    }
                });
                if (contentDTOS.get(position).likes.containsKey(auth.getCurrentUser().getUid())) {
                    ((PostViewHolder3)holder).imageView_like.setImageResource(R.drawable.ic_favorite_black_24dp);
                } else {
                    ((PostViewHolder3)holder).imageView_like.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }
                ((PostViewHolder3)holder).textView_title.setText(contentDTOS.get(position).title);
                ((PostViewHolder3)holder).textView_userName.setText(contentDTOS.get(position).userID);
                ((PostViewHolder3)holder).textView_contentType.setText(contentDTOS.get(position).contentType);
                ((PostViewHolder3)holder).textView_hitCount.setText(String.valueOf(contentDTOS.get(position).contentHit));
                ((PostViewHolder3)holder).textView_likeCount.setText(String.valueOf(contentDTOS.get(position).likeCount));

                GlideApp.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_0).centerCrop().thumbnail(Glide.with(holder.itemView.getContext()).load(R.drawable.loadingicon)).into(((PostViewHolder3)holder).imageView_postImg_0);
                GlideApp.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_1).centerCrop().thumbnail(Glide.with(holder.itemView.getContext()).load(R.drawable.loadingicon)).into(((PostViewHolder3)holder).imageView_postImg_1);
                GlideApp.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_2).centerCrop().thumbnail(Glide.with(holder.itemView.getContext()).load(R.drawable.loadingicon)).into(((PostViewHolder3)holder).imageView_postImg_2);
                GlideApp.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_3).centerCrop().thumbnail(Glide.with(holder.itemView.getContext()).load(R.drawable.loadingicon)).into(((PostViewHolder3)holder).imageView_postImg_3);


                break;
            default: break;
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

        } if (contentDTOS.get(position).getPollMode().equals("단일 투표")) {

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

    private void resetHomeActivity() {
        if (mContext instanceof HomeActivity) {
            ((HomeActivity)mContext).resetActivity();
        }
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
