package com.example.messenger;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StickerInListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StickerInListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StickerInListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StickerInListFragment newInstance(String param1, String param2) {
        StickerInListFragment fragment = new StickerInListFragment();
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
        return inflater.inflate(R.layout.fragment_sticker_in_list, container, false);
    }

    //Переменные для хранения данных
    int id;
    String chatName;

    //Переменная для эл. разметки
    ImageView stickerIV;

    //Конструктор для получения id стикера
    public StickerInListFragment(int id, String chatName){
        this.id = id;
        this.chatName = chatName;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Инициализация переменной для эл. разметки
        stickerIV = view.findViewById(R.id.sticker_iv);

        //Загрузка стикера
        Glide.with(getContext()).load(id).into(stickerIV);

        //Отправка стикера при нажатии на него
        stickerIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction stickerChatTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                stickerChatTransaction.add(R.id.messages_list, new StickerInChatFragment(id, -1, chatName));
                stickerChatTransaction.commit();
            }
        });
    }
}