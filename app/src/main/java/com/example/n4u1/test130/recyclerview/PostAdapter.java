package com.example.n4u1.test130.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ContentDTO;
import com.example.n4u1.test130.views.HomeActivity;
import com.example.n4u1.test130.views.PollActivity;
import com.example.n4u1.test130.views.TestActivity;

import junit.framework.Test;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList strings;
    private Context mContext;
    private ArrayList<ContentDTO> contentDTOS;
    private static final int ITEM_VIEW_TYPE_1 = 1;
    private static final int ITEM_VIEW_TYPE_2 = 2;
    private static final int ITEM_VIEW_TYPE_3 = 3;
    private static final int ITEM_VIEW_TYPE_0 = 0;



    public PostAdapter(Context context, ArrayList<ContentDTO> listItem) {
        mContext = context;
        contentDTOS = listItem;
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
        } else {
            return ITEM_VIEW_TYPE_1;
        }
    }

    //contentDTO 내용을 아이템셋에 배치
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case ITEM_VIEW_TYPE_0 :
                //이미지 클릭시 컨텐트 디테일(PollActivity) 로 넘어감
                ((PostViewHolder)holder).imageView_postImg_0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String string = contentDTOS.get(position).contentKey;
                        Log.d("lkjlkj", contentDTOS.get(position).getPollMode());
                        if (contentDTOS.get(position).getPollMode().equals("순위 투표")) {

                        } if (contentDTOS.get(position).getPollMode().equals("단일 투표")) {

                        } if (contentDTOS.get(position).getPollMode().equals("다중 투표")) {

                        }

                        Intent intent = new Intent(mContext, PollActivity.class);
                        intent.putExtra("contentKey", string);
                        mContext.startActivity(intent);
                    }
                });

                //아이템 바인딩
                ((PostViewHolder)holder).textView_title.setText(contentDTOS.get(position).title);
                ((PostViewHolder)holder).textView_userName.setText(contentDTOS.get(position).userID);
                ((PostViewHolder)holder).textView_contentType.setText(contentDTOS.get(position).contentType);
                Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_0).into(((PostViewHolder)holder).imageView_postImg_0);
                break;

            case ITEM_VIEW_TYPE_1 :
                ((PostViewHolder1)holder).imageView_postImg_0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                ((PostViewHolder1)holder).imageView_postImg_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                ((PostViewHolder1)holder).textView_title.setText(contentDTOS.get(position).title);
                ((PostViewHolder1)holder).textView_userName.setText(contentDTOS.get(position).userID);
                ((PostViewHolder1)holder).textView_contentType.setText(contentDTOS.get(position).contentType);
                Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_0).into(((PostViewHolder1)holder).imageView_postImg_0);
                Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_1).into(((PostViewHolder1)holder).imageView_postImg_1);
                break;

            case ITEM_VIEW_TYPE_2 :
                ((PostViewHolder2)holder).imageView_postImg_0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                ((PostViewHolder2)holder).imageView_postImg_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                ((PostViewHolder2)holder).imageView_postImg_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                ((PostViewHolder2)holder).textView_title.setText(contentDTOS.get(position).title);
                ((PostViewHolder2)holder).textView_userName.setText(contentDTOS.get(position).userID);
                ((PostViewHolder2)holder).textView_contentType.setText(contentDTOS.get(position).contentType);
                Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_0).into(((PostViewHolder2)holder).imageView_postImg_0);
                Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_1).into(((PostViewHolder2)holder).imageView_postImg_1);
                Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_2).into(((PostViewHolder2)holder).imageView_postImg_2);
                break;

            case ITEM_VIEW_TYPE_3 :
                ((PostViewHolder3)holder).imageView_postImg_0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                ((PostViewHolder3)holder).imageView_postImg_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                ((PostViewHolder3)holder).imageView_postImg_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                ((PostViewHolder3)holder).imageView_postImg_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                ((PostViewHolder3)holder).textView_title.setText(contentDTOS.get(position).title);
                ((PostViewHolder3)holder).textView_userName.setText(contentDTOS.get(position).userID);
                ((PostViewHolder3)holder).textView_contentType.setText(contentDTOS.get(position).contentType);
                Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_0).into(((PostViewHolder3)holder).imageView_postImg_0);
                Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_1).into(((PostViewHolder3)holder).imageView_postImg_1);
                Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_2).into(((PostViewHolder3)holder).imageView_postImg_2);
                Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_3).into(((PostViewHolder3)holder).imageView_postImg_3);
                break;
                default: break;
        }




    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
//        //ContentDTO item = contentDTOS.get(position);
//        holder.textView_userName.setText(contentDTOS.get(position).userID);
//
//        holder.textView_title.setText(contentDTOS.get(position).title);
////        holder.textView_title.setText(item.getPostText());
////        holder.textView_likeCount.setText(String.valueOf(item.getPostLikeCount()));
//
//    }

    @Override
    public int getItemCount() {
        return contentDTOS.size();
    }
}
