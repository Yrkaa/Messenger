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
import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {

    //Переменные для хранения информации
    ArrayList<Integer> stickersData = new ArrayList<>();
    ArrayList<String> messagesData = new ArrayList<>();
    ArrayList<Integer> messagesIdData = new ArrayList<>();

    //Переменные-адаптеры для чата
    MessagesListAdapter messagesListAdapter;
    StickersListAdapter stickersListAdapter;

    //Создание переменных для эл. разметки
    FrameLayout chatHeaderContainer;
    RecyclerView stickersList;
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
        stickersList = findViewById(R.id.stickers_list_rv);
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
                messagesIdData.add(i);
            }
        }
        //Инициализация переменной-адаптора
        messagesListAdapter = new MessagesListAdapter(this, messagesData, messagesIdData, sharedPreferences, chatName);
        //Заполнение списка уже существующими сообщениями
        messagesList.setAdapter(messagesListAdapter);

        //Заполнение id стикеров
        stickersData.addAll(Arrays.asList(R.raw.sticker0, R.raw.sticker1, R.raw.sticker2, R.raw.sticker3, R.raw.sticker4));
        //Инициализация переменной-адаптора
        stickersListAdapter = new StickersListAdapter(this, stickersData, sharedPreferences, chatName, messagesListAdapter, messagesList);
        //Заполнение списка стикеров
        stickersList.setAdapter(stickersListAdapter);

        //Скрытие/показ списка стикеров
        showStickersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stickersList.getVisibility() == View.INVISIBLE)
                    stickersList.setVisibility(View.VISIBLE);
                else if(stickersList.getVisibility() == View.VISIBLE)
                    stickersList.setVisibility(View.INVISIBLE);
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
                    messagesList.scrollToPosition(messagesListAdapter.data.size()-1);
                    //Сбрасывание пользовательского текста
                    userText.setText("");
                }
            }
        });

    }
}