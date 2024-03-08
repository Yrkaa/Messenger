package com.example.messenger;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.ViewHolder> {

    SharedPreferences preferences;
    Context c;
    List<String> data;
    List<Integer> idData;
    String chatName;
    LayoutInflater inflater;

    public MessagesListAdapter(Context c, List<String> data, List<Integer> idData, SharedPreferences preferences, String chatName) {
        this.c = c;
        this.inflater = LayoutInflater.from(c);
        this.data = data;
        this.idData = idData;
        this.preferences = preferences;
        this.chatName = chatName;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView sticker;
        public final TextView text;
        public final TextView date;
        public final View v;

        public ViewHolder(View itemView) {
            super(itemView);
            sticker = itemView.findViewById(R.id.message_list_sticker);
            text = itemView.findViewById(R.id.message_list_text);
            date = itemView.findViewById(R.id.message_date);
            v = itemView;
        }
    }

    public void addMessage(String d) {
        data.add(d);
        idData.add(-1);
        notifyItemInserted(data.size() - 1);
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
        int id = idData.get(position);

        holder.date.setText(getTime(id) + ", " + getDate(id));

        if (msg.split("-")[0].equals("text")) {
            holder.text.setText(msg.split("-")[1]);
            holder.text.setVisibility(View.VISIBLE);
            holder.sticker.setVisibility(View.GONE);

            holder.date.setBackgroundColor(Color.BLACK);
            holder.date.setTextColor(Color.WHITE);
        } else if (msg.split("-")[0].equals("sticker")) {
            Glide.with(holder.v).load(Integer.parseInt(msg.split("-")[1])).into(holder.sticker);
            holder.sticker.setVisibility(View.VISIBLE);
            holder.text.setVisibility(View.GONE);

            holder.date.setBackgroundColor(Color.TRANSPARENT);
            holder.date.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private String getDate(int id) {
        LocalDate date = null;
        String day = null, month = null;
        if (id == -1) {
            //Получение текущей даты
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                date = LocalDate.now();
            }

            //Получение и преобразование нужных данных из date
            if (date != null) {
                String[] dateMassive = date.toString().split("-");
                day = dateMassive[2];
                switch (dateMassive[1]) {
                    case "01":
                        month = "Янв";
                        break;
                    case "02":
                        month = "Фев";
                        break;
                    case "03":
                        month = "Мар";
                        break;
                    case "04":
                        month = "Апр";
                        break;
                    case "05":
                        month = "Май";
                        break;
                    case "06":
                        month = "Июнь";
                        break;
                    case "07":
                        month = "Июль";
                        break;
                    case "08":
                        month = "Авг";
                        break;
                    case "09":
                        month = "Сен";
                        break;
                    case "10":
                        month = "Окт";
                        break;
                    case "11":
                        month = "Ноя";
                        break;
                    case "12":
                        month = "Дек";
                        break;
                }

                //Сохранение даты в памяти
                int newId = preferences.getInt(chatName + "-items", 0);
                preferences.edit().putString(chatName + "-" + newId + "-date", month + "-" + day).apply();
            }
        } else {
            String messageDateFromId = preferences.getString(chatName + "-" + (id + 1) + "-date", null);
            if (messageDateFromId != null) {
                month = messageDateFromId.split("-")[0];
                day = messageDateFromId.split("-")[1];
            }
        }

        return day + " " + month;
    }

    private String getTime(int id) {
        String[] timeMassive;
        LocalTime time = null;
        String hour = null, min = null;

        if (id == -1) {
            //Получение текущего времени
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                time = LocalTime.now();
            }

            //Получение и преобразование нужных данных из time
            if (time != null) {
                timeMassive = time.toString().split(":");
                hour = timeMassive[0];
                min = timeMassive[1];

                //Сохранение времени в памяти
                int newId = preferences.getInt(chatName + "-items", 0);
                preferences.edit().putString(chatName + "-" + newId + "-time", hour + "-" + min).apply();
            }
        } else {
            String messageTimeFromId = preferences.getString(chatName + "-" + (id+1) + "-time", null);
            if (messageTimeFromId != null) {
                hour = messageTimeFromId.split("-")[0];
                min = messageTimeFromId.split("-")[1];
            }
        }

        return hour + ":" + min;
    }

}
