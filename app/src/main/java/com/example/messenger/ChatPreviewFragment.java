package com.example.messenger;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatPreviewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatPreviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatPreviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatPreviewFragment newInstance(String param1, String param2) {
        ChatPreviewFragment fragment = new ChatPreviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_preview, container, false);
    }

    //Конструктор для получения нужных данных о чате
    public ChatPreviewFragment(int chatLogoId, String chatNameText){
        this.chatLogoId = chatLogoId;
        this.chatNameText = chatNameText;
    }

    //Переменные для хранения нужных данных о чате
    private int chatLogoId;
    private String chatNameText;

    //Создание переменных для эл. разметки
    protected ImageView chatLogo;
    protected TextView chatName;
    protected ConstraintLayout layout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Инициализация переменных для эл. разметки
        chatLogo = view.findViewById(R.id.chat_logo_iv);
        chatName = view.findViewById(R.id.chat_name_tv);
        layout = view.findViewById(R.id.chat_preview_fragment_layout);

        //Передача данных в разметку для отображения
        chatLogo.setImageResource(chatLogoId);
        chatName.setText(chatNameText);

        //Изменение цвета при нажатии
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackgroundColor(Color.parseColor("#77616161"));
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        //Возвращение цвета при выходе из чата
        layout.setBackgroundColor(Color.parseColor("#00FFFFFF"));
    }
}