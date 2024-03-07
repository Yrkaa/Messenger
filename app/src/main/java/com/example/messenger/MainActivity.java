package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Переменные для хранения информации о чате
    ArrayList<String> chatNames = new ArrayList<>();
    ArrayList<Integer> chatLogoIds = new ArrayList<>();

    //Создание переменных для элементов разметки
    RecyclerView chatsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Инициализация переменных для элементов разметки
        chatsList = findViewById(R.id.chats_list_rv);

        //Заполнение списков для хранения информации о чате
        chatNames.add("Избранное");
        chatLogoIds.add(R.drawable.favourites);

        //Заполнение списка чатов
        ChatsListAdapter adapter = new ChatsListAdapter(this, chatNames, chatLogoIds);
        chatsList.setAdapter(adapter);

    }
}