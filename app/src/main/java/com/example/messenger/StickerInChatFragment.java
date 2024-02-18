package com.example.messenger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StickerInChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StickerInChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StickerInChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StickerInChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StickerInChatFragment newInstance(String param1, String param2) {
        StickerInChatFragment fragment = new StickerInChatFragment();
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
        return inflater.inflate(R.layout.fragment_sticker_in_chat, container, false);
    }

    //Создание переменных для эл. разметки
    ImageView stickerIV;

    //Переменные для хранения данных
    int imageId, fragmentId;
    String chatName;

    public StickerInChatFragment(int imageId, int fragmentId, String chatName){
        this.imageId = imageId;
        this.fragmentId = fragmentId;
        this.chatName = chatName;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Переменная для доступа к памяти
        SharedPreferences preferences = getContext().getSharedPreferences("com.example.messenger", Context.MODE_PRIVATE);

        //Инициализация переменных для эл. разметки
        stickerIV = view.findViewById(R.id.sticker_in_chat_iv);

        //Загрузка стикера
        Glide.with(getContext()).load(imageId).into(stickerIV);

        //Сохранение в памяти, если не объявлено
        if(fragmentId == -1){
            int item = preferences.getInt(chatName+"-items",0);
            preferences.edit().putString(chatName+"-msg-"+item, "sticker"+"-"+imageId).apply();
            item+=1;
            preferences.edit().putInt(chatName+"-items", item).apply();
        }
    }
}