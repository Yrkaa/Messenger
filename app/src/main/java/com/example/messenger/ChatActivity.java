package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

public class ChatActivity extends AppCompatActivity {

    //Создание переменных для эл. разметки
    FrameLayout chatHeaderContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //Инициализация переменных для эл. разметки
        chatHeaderContainer = findViewById(R.id.chat_header_container);

        //Получение названия и иконки чата
        String chatName = getIntent().getStringExtra("chat_name");
        int chatLogoId = getIntent().getIntExtra("chat_logo_id", 0);

        //Создание заголовка чата
        ChatHeaderFragment header = new ChatHeaderFragment(chatLogoId, chatName);
        //Помещение заголовка в контейнер
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(chatHeaderContainer.getId(), header);
        fragmentTransaction.commit();
    }
}