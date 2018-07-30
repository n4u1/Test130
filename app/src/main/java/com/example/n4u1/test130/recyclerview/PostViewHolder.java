package com.example.n4u1.test130.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.n4u1.test130.R;


//adapter에 viewHolder가 들어갈것임
public class PostViewHolder extends RecyclerView.ViewHolder{

    public ImageView imageView_postImg, imageView_like, imageView_share;
    public TextView textView_likeCount, textView_userName, textView_title, textView_contentType;


    public PostViewHolder(View itemView) {
        super(itemView);
        imageView_postImg = itemView.findViewById(R.id.imageView_postImg);
        imageView_like = itemView.findViewById(R.id.imageView_like);
        imageView_share = itemView.findViewById(R.id.imageView_share);
        textView_likeCount = itemView.findViewById(R.id.textView_likeCount);
        textView_userName = itemView.findViewById(R.id.textView_userName);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_contentType = itemView.findViewById(R.id.textView_contentType);

    }
}
