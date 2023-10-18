package com.moviebuffs.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.moviebuffs.R;
import com.moviebuffs.adapters.CinemaAdapter;
import com.moviebuffs.adapters.SeansDayAdapter;
import com.moviebuffs.adapters.SeansTimeAdapter;
import com.moviebuffs.entities.Cinema;
import com.moviebuffs.entities.Movies;
import com.moviebuffs.repository.MyDBHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MovieTicketBuild extends Fragment {

    MyDBHandler myDbHandler;
    SeansDayAdapter seansDayAdapter;
    SeansTimeAdapter seansTimeAdapter;

    CinemaAdapter cinemaAdapter;

    private RecyclerView seansDay_recyclerView;
    private RecyclerView seansTime_recyclerView;
    private RecyclerView cinemas_recyclerView;

    Movies movie;

    List<Cinema> cinemaArrayList = new ArrayList<>();

    String[][] daysAndNumbersOfWeek;

    Bundle bundle;

//    public MovieTicketBuild(Context context
//            , Movies changed_movie
//            , ArrayList<Integer> week_date_list
//            , ArrayList<String> cinema_list
//            , ArrayList<String> cinema_address_list
//            , ArrayList<String> time_list) {
//        this.context = context;
//        this.movie = changed_movie;
//        this.week_date_list = week_date_list;
//        this.cinema_list = cinema_list;
//        this.cinema_address_list = cinema_address_list;
//        this.time_list = time_list;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // инициализация элементов пользовательского интерфейса
        View view = inflater.inflate(R.layout.fragment_build_tickets, container, false);

        myDbHandler = new MyDBHandler(getContext(),"MovieBuffs.db",null,2);

        // генерим список дат сеансов
        daysAndNumbersOfWeek = getDaysAndNumbersOfWeek(7);
////        for (String days, String numbers : daysAndNumbersOfWeek){
////            Log.d("SeansDayAdapter", days+numbers);
////        }
//        for (int i = 0; i < daysAndNumbersOfWeek.length; i++) {
//            for (int j = 0; j < daysAndNumbersOfWeek[i].length; j++) {
//                Log.d("SeansDayAdapter", daysAndNumbersOfWeek[i][j]);
//            }
//        }

        // генерим список времени сеансов
//        List<String> seansTimesList = Arrays.asList("9:00", "10:30", "11:30","12:30", "14:30", "15:45", "17:30", "18:45");

        bundle = getArguments();

        // генерим список кинотеатров
//        List<Cinema> cinemasList = new ArrayList<>();
//        List<String> seansDateList = Arrays.asList("30", "31", "1", "2", "3", "4", "5");
//        List<String> cinemasNameList = Arrays.asList("MovieBufs на вернадке", "MovieBufs на мира", "MovieBufs на ордынке", "MovieBufs на тверской", "MovieBufs на лубянке", "MovieBufs на арбате", "MovieBufs на ленинском");
//        List<String> cinemasAddressList = Arrays.asList("пр. Вернадского 17", "пр. Мира 10", "ул. Большая ордынка 36", "ул. Тверская 23", "ул. Большая Лубянка 18А", "ул. Новый Арбат 41", "пр. Ленинский 4");
//        List<String> cinemasPriceList = Arrays.asList("150 ₽", "200 ₽", "250 ₽", "300 ₽", "350 ₽", "400 ₽", "450 ₽");
//        List<String> seansTimesList = Arrays.asList("9:00", "10:30", "11:30","12:30", "14:30", "15:45", "17:30", "18:45");
//        List<String> seansTicketList = Arrays.asList("50", "45", "35","26", "37", "21", "13", "6");
//
//        for (int i = 0; i < cinemasNameList.size(); i++)
//        {
//            String name = cinemasNameList.get(i);
//            String address = cinemasAddressList.get(i);
//            String left_tickets = seansTicketList.get(i);
//            String price = cinemasPriceList.get(i);
//
//            String time_slot = seansTimesList.get(i);
//            cinemasList.add(new Cinema(bundle.getString("movie_name"), "30", name, address, time_slot, price, Integer.parseInt(left_tickets)));
//        }
//        myDBHandler.allSeanses()
//        cinemaArrayList.clear();
//        cinemaArrayList.add(myDbHandler.getSeansByMovieAndDate(bundle.getString("movie_name"), daysAndNumbersOfWeek[1][0]));

//        cinemaArrayList.add() myDbHandler.getSeansByMovieAndDate(bundle.getString("movie_name"), daysAndNumbersOfWeek[1][0]));
        cinemaArrayList = myDbHandler.getSeansListByMovieAndDate(bundle.getString("movie_name"), daysAndNumbersOfWeek[1][0]);
        cinemaAdapter = new CinemaAdapter(getContext(), cinemaArrayList);

        Log.d("MovieTicketBuild", "Текущая дата:" + daysAndNumbersOfWeek[1][0]);
        //UpdateCinemaList(String.valueOf(daysAndNumbersOfWeek[1][0]));

        seansDayAdapter = new SeansDayAdapter(getContext(), this, daysAndNumbersOfWeek);
        seansDay_recyclerView = view.findViewById(R.id.changedate_recycleView);
        seansDay_recyclerView.setAdapter(seansDayAdapter);

        cinemas_recyclerView = view.findViewById(R.id.cinemas_recyclerView);
        cinemas_recyclerView.setAdapter(cinemaAdapter);

        return view;
    }

    public void UpdateCinemaList(String seans_date){
        cinemaArrayList.clear();
        cinemaArrayList = myDbHandler.getSeansListByMovieAndDate(bundle.getString("movie_name"), seans_date);

        for (Cinema cin : cinemaArrayList)
        {
            Log.d("DB", "\n");
            Log.d("DB", "DB Seans date:" + seans_date);
            Log.d("DB", "DB Cinema name:" + cin.getCinema_name());
            Log.d("DB", "DB Cinema address:" + cin.getCinema_address());
            Log.d("DB", "DB Cinema time slots:" + cin.getCinema_time_slots());
            Log.d("DB", "DB Cinema ticket prices:" + cin.getCinema_ticket_price());
            Log.d("DB", "\n");
        }

        // обновляем данные в адаптере и вызываем метод notifyDataSetChanged()
        cinemaAdapter.setData(cinemaArrayList);
        cinemaAdapter.notifyDataSetChanged();
    }



    public static String[][] getDaysAndNumbersOfWeek(Integer number_range) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String[] daysOfWeek = new String[number_range];
        Integer[] numbersOfWeek = new Integer[number_range];
        for (int i = 0; i < number_range; i++) {
            String dayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, new Locale("ru", "RU"));
            daysOfWeek[i] = dayName;
            numbersOfWeek[i] = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        String[][] daysAndNumbersOfWeek = new String[2][number_range];
        for (int i = 0; i < number_range; i++) {
            daysAndNumbersOfWeek[0][i] = daysOfWeek[i];
            daysAndNumbersOfWeek[1][i] = String.valueOf(numbersOfWeek[i]);
        }
        return daysAndNumbersOfWeek;
    }

}
