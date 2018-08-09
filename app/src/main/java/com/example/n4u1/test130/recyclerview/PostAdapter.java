package com.example.n4u1.test130.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ContentDTO;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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

    //contentDTO 내용을 한개의 아이템셋에 배치
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case ITEM_VIEW_TYPE_0 :
                ((PostViewHolder)holder).textView_title.setText(contentDTOS.get(position).title);
                ((PostViewHolder)holder).textView_userName.setText(contentDTOS.get(position).userID);
                ((PostViewHolder)holder).textView_contentType.setText(contentDTOS.get(position).contentType);
                Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_0).into(((PostViewHolder)holder).imageView_postImg_0);
                break;
            case ITEM_VIEW_TYPE_1 :
                ((PostViewHolder1)holder).textView_title.setText(contentDTOS.get(position).title);
                ((PostViewHolder1)holder).textView_userName.setText(contentDTOS.get(position).userID);
                ((PostViewHolder1)holder).textView_contentType.setText(contentDTOS.get(position).contentType);
                Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_0).into(((PostViewHolder1)holder).imageView_postImg_0);
                Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_1).into(((PostViewHolder1)holder).imageView_postImg_1);
                break;
            case ITEM_VIEW_TYPE_2 :
                ((PostViewHolder2)holder).textView_title.setText(contentDTOS.get(position).title);
                ((PostViewHolder2)holder).textView_userName.setText(contentDTOS.get(position).userID);
                ((PostViewHolder2)holder).textView_contentType.setText(contentDTOS.get(position).contentType);
                Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_0).into(((PostViewHolder2)holder).imageView_postImg_0);
                Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_1).into(((PostViewHolder2)holder).imageView_postImg_1);
                Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl_2).into(((PostViewHolder2)holder).imageView_postImg_2);
                break;
            case ITEM_VIEW_TYPE_3 :
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
