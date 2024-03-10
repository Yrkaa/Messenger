package com.example.messenger;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class RegistrationActivity extends AppCompatActivity {

    //Переменные с необходимой информацией
    String logoData = null;

    //Создание переменных для элементов разметки
    Button reg;
    EditText name;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Переменная для доступа к памяти
        SharedPreferences preferences = this.getSharedPreferences("com.example.messenger", MODE_PRIVATE);

        //Инициализация переменных для элементов разметки
        reg = findViewById(R.id.reg_button);
        name = findViewById(R.id.reg_user_name);
        logo = findViewById(R.id.reg_user_logo);

        //Получение лого пользователя
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getImage = new Intent(Intent.ACTION_PICK);
                getImage.setType("image/*");
                startActivityForResult(getImage, 1);
            }
        });

        //Регистрирование пользователя
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(logoData == null){
                    Toast.makeText(RegistrationActivity.this, "Выберите иконку", Toast.LENGTH_LONG).show();
                }
                else if(name.getText().toString().length() == 0){
                    Toast.makeText(RegistrationActivity.this, "Введите свой ник", Toast.LENGTH_LONG).show();
                }
                else{
                    preferences.edit().putBoolean("isReg", true).apply();
                    preferences.edit().putString("userName", name.getText().toString()).apply();
                    preferences.edit().putString("userLogo", logoData).apply();
                    Intent toMain = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(toMain);
                }
            }
        });
    }

    //Функция для обработки полученной фотки (лого пользователя)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        logoData = data.getData().toString();
        logo.setImageURI(Uri.parse(logoData));
    }
}