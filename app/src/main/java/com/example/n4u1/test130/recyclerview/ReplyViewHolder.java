package com.example.n4u1.test130.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.n4u1.test130.R;


//adapter에 viewHolder가 들어갈것임
public class ReplyViewHolder extends RecyclerView.ViewHolder{

    public TextView textView_id, textView_reply;




    public ReplyViewHolder(View itemView) {
        super(itemView);
        textView_reply = itemView.findViewById(R.id.textView_reply);
        textView_id = itemView.findViewById(R.id.textView_id);

    }
}
