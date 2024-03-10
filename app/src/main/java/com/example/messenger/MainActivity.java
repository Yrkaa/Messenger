package com.example.messenger;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Переменные для хранения информации о чате
    ArrayList<String> chatNames = new ArrayList<>();
    ArrayList<Integer> chatLogoIds = new ArrayList<>();

    //Создание переменных для элементов разметки
    RecyclerView chatsList;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Переменная для доступа к памяти
        SharedPreferences preferences = this.getSharedPreferences("com.example.messenger", MODE_PRIVATE);

        //Инициализация переменных для элементов разметки
        chatsList = findViewById(R.id.chats_list_rv);
        logo = findViewById(R.id.main_user_logo);

        //Проверка на то, зарегвн пользователь или нет
        if(preferences.getBoolean("isReg",false)==false){
            Intent toReg = new Intent(MainActivity.this, RegistrationActivity.class);
            startActivity(toReg);
        }

        //Добавление иконки пользователя
        Glide.with(this).load(Uri.parse(preferences.getString("userLogo",""))).into(logo);

        //Заполнение списков для хранения информации о чате
        chatNames.add("Избранное");
        chatLogoIds.add(R.drawable.favourites);

        //Заполнение списка чатов
        ChatsListAdapter adapter = new ChatsListAdapter(this, chatNames, chatLogoIds);
        chatsList.setAdapter(adapter);

    }
}