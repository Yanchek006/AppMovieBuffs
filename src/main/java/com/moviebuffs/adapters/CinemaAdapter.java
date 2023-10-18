package com.moviebuffs.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.moviebuffs.R;
import com.moviebuffs.entities.Cinema;
import com.moviebuffs.interfaces.UserID_Interface;
import com.moviebuffs.repository.MyDBHandler;

import java.util.ArrayList;
import java.util.List;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.CinemaHolder>{

    Context context;
    List<Cinema> cinemasArrayList = new ArrayList<>();
    Cinema cinema;
    MyDBHandler myDBHandler;

    List<String> timeSlotsList;

    private NavController navController;



    public CinemaAdapter(Context context, List<Cinema> cinemasArrayList) {
        this.context = context;
        this.cinemasArrayList = cinemasArrayList;
        this.timeSlotsList = timeSlotsList;
    }


    @NonNull
    @Override
    public CinemaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_cinema_item, parent, false);

        CinemaHolder cinemaHolder = new CinemaHolder(v);
        myDBHandler = new MyDBHandler(context, "MovieBuffs.db", null, 2);

        return cinemaHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CinemaHolder holder, int position) {
        cinema = cinemasArrayList.get(position);

        if (cinema!=null){
            holder.nameView.setText(cinema.getCinema_name());
            holder.addressView.setText(cinema.getCinema_address());
            holder.ticketLeftView.setText(String.valueOf(cinema.getTicketsLeft()));
            holder.priceView.setText(cinema.getCinema_ticket_price());

//        Random random = new Random();
//        int randomIndex = random.nextInt(cinemasArrayList.size());
//        String randomSeansTime = cinemasArrayList.get(randomIndex).getCinema_time_slots();
            List<String> times = new ArrayList<>();
            times.add(cinema.getCinema_time_slots());
            Log.d("CinemaAdapter", "cinema_time_slots: "+ cinema.getCinema_time_slots());

            // заполняем recycle view с временем сеансов
            SeansTimeAdapter timeSlotAdapter = new SeansTimeAdapter(context, times);
            holder.timeSlotsRecycleView.setAdapter(timeSlotAdapter);

            UserID_Interface userProvider = (UserID_Interface) context;
            int currentUserId = userProvider.getCurrentUserId();
//        int movie_id = myDBHandler.getC(cinema.getName());

//        SeansTimeAdapter seansTimeAdapter = new SeansTimeAdapter(context, timeSlotsList);
//        holder.timeSlotsRecycleView.setAdapter(seansTimeAdapter);

            // обработка клика по элементу recycleView
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                TicketRemoveDialogFragment dialog = new TicketRemoveDialogFragment(this, position);
//                dialog.show(mFragmentManager, "dialog");

                    UserID_Interface userProvider = (UserID_Interface) context;
                    int currentUserId = userProvider.getCurrentUserId();
                    int movie_id = myDBHandler.getMovieId(cinema.getMovieName());

                    List<Integer> left_tickets =  myDBHandler.getLeftTickets(cinema.getMovieName(), cinema.getSessionDate());
                    for (Integer tickets : left_tickets)
                    {
                        Log.d("DB DEBUG", "ДАТА:" + cinema.getSessionDate());
                        Log.d("DB DEBUG", "ТИКЕТОВ БЫЛО:" + tickets);
                    }


                    if (left_tickets.get(holder.getAdapterPosition()) > 0)
                    {
                        // TODO: сделать выбор количества покупаемых билетов
                        myDBHandler.addOrder(currentUserId, movie_id, cinema.getCinema_name(), cinema.getMovieName(), cinema.getSessionDate(),cinema.getCinema_time_slots(), 1, cinema.getCinema_ticket_price(), cinema.getTicketsLeft());

                        Integer new_tickets = left_tickets.get(holder.getAdapterPosition())-1;
                        left_tickets.set(holder.getAdapterPosition(), new_tickets);
                        myDBHandler.setLeftTickets(cinema.getMovieName(), cinema.getSessionDate(), left_tickets);

                        left_tickets =  myDBHandler.getLeftTickets(cinema.getMovieName(), cinema.getSessionDate());
                        for (Integer tickets : left_tickets)
                        {
                            Log.d("DB DEBUG", "ДАТА:" + cinema.getSessionDate());
                            Log.d("DB DEBUG", "ТИКЕТОВ СТАЛО:" + tickets);
                        }

                        // String left_tickets_string = String.valueOf(Integer.parseInt(String.valueOf(holder.ticketLeftView.getText())) - 1);
                        holder.ticketLeftView.setText(String.valueOf(left_tickets.get(holder.getAdapterPosition()))); // TODO: исправить тикеты
                        Toast.makeText(context, "Билет куплен! Ищите свои билеты в разделе Мои билеты", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(context, "Все билеты раскуплены", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

    public void setData(List<Cinema> cinemaList) {
        this.cinemasArrayList = cinemaList;
    }

    @Override
    public int getItemCount() {
        return cinemasArrayList.size();
    }

    public static class CinemaHolder extends RecyclerView.ViewHolder{
        TextView nameView;
        TextView addressView;
        TextView ticketLeftView;
        TextView priceView;
        RecyclerView timeSlotsRecycleView;

        public CinemaHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.cinema_name_text);
            addressView = itemView.findViewById(R.id.cinema_address_text);
            ticketLeftView = itemView.findViewById(R.id.cinema_ticket_left_text);
            priceView = itemView.findViewById(R.id.cinema_ticket_price_text);
            timeSlotsRecycleView = itemView.findViewById(R.id.timeSlots_recycleView);
        }
    }


}
