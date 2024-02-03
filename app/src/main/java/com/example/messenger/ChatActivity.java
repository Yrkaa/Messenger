package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class ChatActivity extends AppCompatActivity {

    //Создание переменных для эл. разметки
    FrameLayout chatHeaderContainer;
    LinearLayout messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Получение доступа к памяти
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.messenger", MODE_PRIVATE);

        //Инициализация переменных для эл. разметки
        chatHeaderContainer = findViewById(R.id.chat_header_container);
        messagesList = findViewById(R.id.messages_list);

        //Получение названия и иконки чата
        String chatName = getIntent().getStringExtra("chat_name");
        int chatLogoId = getIntent().getIntExtra("chat_logo_id", 0);

        //Создание заголовка чата
        ChatHeaderFragment header = new ChatHeaderFragment(chatLogoId, chatName);

        //Инициализация транзакции для вставки фрагментов в активити
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        //Помещение заголовка в контейнер
        fragmentTransaction.add(chatHeaderContainer.getId(), header);

        //Заполнение уже существующими сообщениями
        for(int i = 0; i < sharedPreferences.getInt(chatName+"-items", 0); i++){
            MessageFragment msg = new MessageFragment(sharedPreferences.getString(chatName+"msg"+i, null));
            if(msg!=null)
                fragmentTransaction.add(messagesList.getId(),msg);
        }

        //Коммит всех изменений
        fragmentTransaction.commit();
    }
}