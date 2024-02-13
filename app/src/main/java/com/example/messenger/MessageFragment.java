package com.example.messenger;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
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
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    //Конструктор для получения нужных данных
    public MessageFragment(String text){
        this.text = text;
    }

    //Переменные для хранения данных о сообщении
    String text;

    //Переменные для эл. разметки
    TextView messageText, messageDate;

    //Переменные для хранения данных о дате
    LocalDate date = null;
    LocalTime time = null;
    String[] dateMassive = null;
    String[] timeMassive = null;
    String day = null;
    String month = null;
    String hour = null;
    String min = null;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Инициализация переменных для эл. разметки
        messageText = view.findViewById(R.id.message_text_tv);
        messageDate = view.findViewById(R.id.message_date_tv);

        //Получение текущей даты и времени
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDate.now();
            time = LocalTime.now();
        }

        //Получение и преобразование нужных данных из date и time
        if (date != null){
            dateMassive = date.toString().split("-");
            day = dateMassive[2];
            switch (dateMassive[1]){
                case "01":
                    month = "Янв";
                    break;
                case "02":
                    month = "Фев";
                    break;
                case "03":
                    month = "Мар";
                    break;
                case "04":
                    month = "Апр";
                    break;
                case "05":
                    month = "Май";
                    break;
                case "06":
                    month = "Июнь";
                    break;
                case "07":
                    month = "Июль";
                    break;
                case "08":
                    month = "Авг";
                    break;
                case "09":
                    month = "Сен";
                    break;
                case "10":
                    month = "Окт";
                    break;
                case "11":
                    month = "Ноя";
                    break;
                case "12":
                    month = "Дек";
                    break;
            }
        }

        if(time != null){
            timeMassive = time.toString().split(":");
            hour = timeMassive[0];
            min = timeMassive[1];
        }


        //Заполнение данных
        messageText.setText(text);
        if(date != null && time != null){
            messageDate.setText(hour+":"+min+","+day+" "+month);
        }


    }
}