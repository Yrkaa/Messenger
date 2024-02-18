package com.example.messenger;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StickersListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StickersListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StickersListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StickersListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StickersListFragment newInstance(String param1, String param2) {
        StickersListFragment fragment = new StickersListFragment();
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
        return inflater.inflate(R.layout.fragment_stickers_list, container, false);
    }

    //Создание переменных для эл. разметки
    LinearLayout list;

    //Переменные для хранения данных
    String chatName;

    public StickersListFragment(String chatName){
        this.chatName = chatName;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Инициализация переменных для эл. разметки
        list = view.findViewById(R.id.stickers_list);

        for(int i = 0; i <= /*Это количество стикеров*/ 4; i++){
            int stickerId = getResources().getIdentifier("sticker"+i, "raw", "com.example.messenger");
            FragmentTransaction stickerListTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            stickerListTransaction.add(list.getId(), new StickerInListFragment(stickerId, chatName));
            stickerListTransaction.commit();
        }

    }
}