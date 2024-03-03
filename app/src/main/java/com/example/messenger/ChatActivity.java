package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    ArrayList<String> messagesData = new ArrayList<>();

    //Переменная-адаптер для чата
    MessagesListAdapter messagesListAdapter;

    //Создание переменных для эл. разметки
    FrameLayout chatHeaderContainer, stickersListPlaceHolder;
    RecyclerView messagesList;
    ImageButton sendMsg;
    EditText userText;
    Button showStickersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Получение доступа к памяти
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.messenger", MODE_PRIVATE);

        //Инициализация переменных для эл. разметки
        chatHeaderContainer = findViewById(R.id.chat_header_container);
        messagesList = findViewById(R.id.messages_list);
        sendMsg = findViewById(R.id.send_message_btn);
        userText = findViewById(R.id.user_text_et);
        stickersListPlaceHolder = findViewById(R.id.stickers_list_placeholder);
        showStickersList = findViewById(R.id.show_stickers_list_btn);

        //Получение названия и иконки чата
        String chatName = getIntent().getStringExtra("chat_name");
        int chatLogoId = getIntent().getIntExtra("chat_logo_id", 0);

        //Создание заголовка чата
        ChatHeaderFragment header = new ChatHeaderFragment(chatLogoId, chatName);

        //Помещение заголовка в контейнер
        FragmentTransaction headTransaction = getSupportFragmentManager().beginTransaction();
        headTransaction.add(chatHeaderContainer.getId(), header);
        headTransaction.commit();

        //Получение массива с уже существующими сообщениями и их id
        for(int i = 0; i < sharedPreferences.getInt(chatName+"-items", 0); i++){
            String msg = sharedPreferences.getString(chatName+"-msg-"+i, null);
            if(msg!=null){
                messagesData.add(msg);
            }
        }
        //Инициализация переменной-адаптора
        messagesListAdapter = new MessagesListAdapter(this, messagesData);
        //Заполнение списка уже существующими сообщениями
        messagesList.setAdapter(messagesListAdapter);

        //Помещение списка стикеров в разметку
        FragmentTransaction stickerListPlaceHolderTransaction = getSupportFragmentManager().beginTransaction();
        stickerListPlaceHolderTransaction.add(stickersListPlaceHolder.getId(), new StickersListFragment(chatName, messagesListAdapter));
        stickerListPlaceHolderTransaction.commit();

        //Скрытие/показ списка стикеров
        showStickersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stickersListPlaceHolder.getVisibility() == View.INVISIBLE)
                    stickersListPlaceHolder.setVisibility(View.VISIBLE);
                else if(stickersListPlaceHolder.getVisibility() == View.VISIBLE)
                    stickersListPlaceHolder.setVisibility(View.INVISIBLE);
            }
        });

        //Создание нового сообщения
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userText.length() > 0) {
                    //Это код, чтобы убрать клаву
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    //Сохранение сообщения в памяти
                    int item = sharedPreferences.getInt(chatName + "-items", 0);
                    sharedPreferences.edit().putString(chatName + "-msg-" + item, "text" + "-" + userText.getText().toString()).apply();
                    item += 1;
                    sharedPreferences.edit().putInt(chatName + "-items", item).apply();
                    //Отображение нового сообщения
                    messagesListAdapter.addMessage("text" + "-" + userText.getText().toString());
                    //Перемещение вниз

                    //Сбрасывание пользовательского текста
                    userText.setText("");
                }

            }
        });

    }
}