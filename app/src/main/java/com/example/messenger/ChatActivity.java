package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    //Создание переменных для эл. разметки
    FrameLayout chatHeaderContainer, stickersListPlaceHolder;
    LinearLayout messagesList;
    Button sendMsg;
    EditText userText;
    ScrollView scrollView;

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
        scrollView  = findViewById(R.id.messages_scroll);

        //Автоматический скролл в конец переписки
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

        //Получение названия и иконки чата
        String chatName = getIntent().getStringExtra("chat_name");
        int chatLogoId = getIntent().getIntExtra("chat_logo_id", 0);

        //Создание заголовка чата
        ChatHeaderFragment header = new ChatHeaderFragment(chatLogoId, chatName);

        //Помещение заголовка в контейнер
        FragmentTransaction headTransaction = getSupportFragmentManager().beginTransaction();
        headTransaction.add(chatHeaderContainer.getId(), header);
        headTransaction.commit();

        //Заполнение уже существующими сообщениями
        for(int i = 0; i < sharedPreferences.getInt(chatName+"-items", 0); i++){
            String msg = sharedPreferences.getString(chatName+"-msg-"+i, null);
            if(msg!=null){
                FragmentTransaction msgTransaction = getSupportFragmentManager().beginTransaction();
                if(msg.split("-")[0].equals("text")){
                    msgTransaction.add(messagesList.getId(), new MessageFragment(msg.split("-")[1],  i, chatName));
                }
                else if(msg.split("-")[0].equals("sticker")){
                    msgTransaction.add(messagesList.getId(), new StickerInChatFragment(Integer.parseInt(msg.split("-")[1]), i, chatName));
                }
                msgTransaction.commit();
            }

        }

        //Помещение списка стикеров в разметку
        FragmentTransaction stickerListPlaceHolderTransaction = getSupportFragmentManager().beginTransaction();
        stickerListPlaceHolderTransaction.add(stickersListPlaceHolder.getId(), new StickersListFragment(chatName));
        stickerListPlaceHolderTransaction.commit();

        //Создание нового сообщения
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userText.length() > 0){
                    //Это код, чтобы убрать клаву
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    //Сохранение сообщения в памяти
                    int item = sharedPreferences.getInt(chatName+"-items", 0);
                    sharedPreferences.edit().putString(chatName+"-msg-"+item, "text"+"-"+userText.getText().toString()).apply();
                    item+=1;
                    sharedPreferences.edit().putInt(chatName+"-items", item).apply();
                    //Отображение нового сообщения
                    MessageFragment newMsg = new MessageFragment(userText.getText().toString(), -1, chatName);
                    FragmentTransaction newMsgTransaction = getSupportFragmentManager().beginTransaction();
                    newMsgTransaction.add(messagesList.getId(), newMsg);
                    newMsgTransaction.commit();
                    //Сбрасывание пользовательского текста
                    userText.setText("");

                }

            }
        });

    }
}