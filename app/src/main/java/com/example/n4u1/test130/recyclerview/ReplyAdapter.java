package com.example.n4u1.test130.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ReplyDTO;

import java.util.ArrayList;

public class ReplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<ReplyDTO> replyDTO;

    public ReplyAdapter(Context context, ArrayList<ReplyDTO> listItem) {
        this.mContext = context;
        this.replyDTO = listItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_item_poll, parent, false);
        return new ReplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        ((ReplyViewHolder)holder).textView_id.setText(replyDTO.);
        ((ReplyViewHolder)holder).textView_reply.setText("dddddddddccccccom");

    }

    @Override
    public int getItemCount() {
        return replyDTO.size();
    }
}
