package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    //Создание переменных для элементов разметки
    LinearLayout chatsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Инициализация переменных для элементов разметки
        chatsList = findViewById(R.id.chats_list_layout);

        //Заполнение списка чатов(пока что тут только избранное)
        ChatPreviewFragment favourites = new ChatPreviewFragment(R.drawable.favourites, "Избранное");
        FragmentTransaction chatsListFragmentTransaction = getSupportFragmentManager().beginTransaction();
        chatsListFragmentTransaction.add(chatsList.getId(), favourites);
        chatsListFragmentTransaction.commit();

    }
}