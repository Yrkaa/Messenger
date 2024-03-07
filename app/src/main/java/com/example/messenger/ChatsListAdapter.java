package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ChatsListAdapter extends RecyclerView.Adapter<ChatsListAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<String> namesList;
    List<Integer> idList;

    public ChatsListAdapter(Context c, List<String> namesList, List<Integer> idList){
        this.inflater = LayoutInflater.from(c);
        this.namesList = namesList;
        this.idList = idList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final ImageView chatLogo;
        public final TextView chatName;
        public final ConstraintLayout layout;

        public ViewHolder(View v){
            super(v);
            this.chatLogo = v.findViewById(R.id.chat_logo_iv);
            this.chatName = v.findViewById(R.id.chat_name_tv);
            this.layout = v.findViewById(R.id.chats_list_item_layout);
        }

    }

    @NonNull
    @Override
    public ChatsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.chats_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsListAdapter.ViewHolder holder, int position) {
        String chatNameText = namesList.get(position);
        int chatLogoId = idList.get(position);

        holder.chatName.setText(chatNameText);
        holder.chatLogo.setImageResource(chatLogoId);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toChat = new Intent(holder.layout.getContext(), ChatActivity.class);
                toChat.putExtra("chat_name", chatNameText);
                toChat.putExtra("chat_logo_id", chatLogoId);
                holder.layout.getContext().startActivity(toChat);
            }
        });
    }

    @Override
    public int getItemCount() {
        return namesList.size();
    }
}
