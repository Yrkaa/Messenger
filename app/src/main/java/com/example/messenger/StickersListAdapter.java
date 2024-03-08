package com.example.messenger;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class StickersListAdapter extends RecyclerView.Adapter<StickersListAdapter.ViewHolder> {

    List<Integer> data;
    LayoutInflater inflater;
    SharedPreferences preferences;
    String chatName;
    MessagesListAdapter msgAdapter;
    RecyclerView msgList;


    public StickersListAdapter(Context c, List<Integer> data, SharedPreferences preferences, String chatName, MessagesListAdapter msgAdapter, RecyclerView msgList){
        this.inflater = LayoutInflater.from(c);
        this.data = data;
        this.preferences = preferences;
        this.chatName = chatName;
        this.msgAdapter = msgAdapter;
        this.msgList = msgList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final ImageView sticker;
        public final View itemView;
        public ViewHolder(View v){
            super(v);
            sticker = v.findViewById(R.id.sticker_iv);
            itemView = v;
        }
    }

    @NonNull
    @Override
    public StickersListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.stickers_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StickersListAdapter.ViewHolder holder, int position) {
        int id = data.get(position);

        Glide.with(holder.itemView).load(id).into(holder.sticker);

        holder.sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int item = preferences.getInt(chatName+"-items",0);
                preferences.edit().putString(chatName+"-msg-"+item, "sticker"+"-"+id).apply();
                item+=1;
                preferences.edit().putInt(chatName+"-items", item).apply();
                msgAdapter.addMessage("sticker"+"-"+id);

                msgList.scrollToPosition(msgAdapter.data.size()-1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
