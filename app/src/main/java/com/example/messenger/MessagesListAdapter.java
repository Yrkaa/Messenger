package com.example.messenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.ViewHolder> {

    Context c;
    List<String> data;
    LayoutInflater inflater;

    public void addMessage(String d){
        data.add(d);
        notifyItemInserted(data.size()-1);
    }

    @NonNull
    @Override
    public MessagesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.messages_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesListAdapter.ViewHolder holder, int position) {
        String msg = data.get(position);

        if(msg.split("-")[0].equals("text")){
            holder.text.setText(msg.split("-")[1]);
            holder.text.setVisibility(View.VISIBLE);
            holder.sticker.setVisibility(View.GONE);
        }
        else if(msg.split("-")[0].equals("sticker")){
            Glide.with(holder.v).load(Integer.parseInt(msg.split("-")[1])).into(holder.sticker);
            holder.sticker.setVisibility(View.VISIBLE);
            holder.text.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final ImageView sticker;
        public final TextView text;
        public final View v;
        public ViewHolder(View itemView){
            super(itemView);
            sticker = itemView.findViewById(R.id.message_list_sticker);
            text = itemView.findViewById(R.id.message_list_text);
            v = itemView;
        }
    }

    public MessagesListAdapter(Context c, List<String> data){
        this.c = c;
        this.inflater = LayoutInflater.from(c);
        this.data = data;
    }
}
