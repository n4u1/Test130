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
import com.example.n4u1.test130.models.PostItem;
import com.example.n4u1.test130.views.HomeActivity;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<ContentDTO> contentDTOS;


    public PostAdapter(Context context, ArrayList<ContentDTO> listItem) {
        mContext = context;
        contentDTOS = listItem;
    }


    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_home_img, parent, false);
        return new PostViewHolder(view);
    }




    //contentDTO 내용을 한개의 아이템셋에 배치
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PostViewHolder)holder).textView_title.setText(contentDTOS.get(position).title);
        ((PostViewHolder)holder).textView_userName.setText(contentDTOS.get(position).userID);
        ((PostViewHolder)holder).textView_contentType.setText(contentDTOS.get(position).contentType);
        Glide.with(holder.itemView.getContext()).load(contentDTOS.get(position).imageUrl).into(((PostViewHolder)holder).imageView_postImg);

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
