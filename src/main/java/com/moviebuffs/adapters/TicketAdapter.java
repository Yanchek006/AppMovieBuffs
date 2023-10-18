package com.moviebuffs.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moviebuffs.R;
import com.moviebuffs.dialogs.TicketRemoveDialogFragment;
import com.moviebuffs.entities.Orders;
import com.moviebuffs.interfaces.UserID_Interface;
import com.moviebuffs.repository.MyDBHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketHolder>{

    Context context;
    Orders ticket;
    ArrayList<Orders> ticketsArrayList;
    MyDBHandler myDbHandler;
    private FragmentManager mFragmentManager;

    public TicketAdapter(Context context, ArrayList<Orders> ticketsArrayList, FragmentManager fragmentManager) {
        this.context = context;
        this.ticketsArrayList = ticketsArrayList;
        this.mFragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public TicketAdapter.TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_tiket_item, parent, false);
        myDbHandler = new MyDBHandler(v.getContext(), "MovieBuffs.db",null,2);

        TicketHolder ticketHolder = new TicketHolder(v);
        return ticketHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.TicketHolder holder, int position) {

        ticket = ticketsArrayList.get(position);
        String movieImage = myDbHandler.getMovieImageById(ticket.getMovie_id());

//        Log.d("TICKET ADAPTER", "\n");
//        Log.d("TICKET ADAPTER", "Movie name:" + ticket.getMovie_name());
//        Log.d("TICKET ADAPTER", "Movie ID:" + ticket.getMovie_id());
//        Log.d("TICKET ADAPTER", "Movie image:" + movieImage);
//        Log.d("TICKET ADAPTER", "Movie price:" + ticket.getPrice());
//        Log.d("TICKET ADAPTER", "Movie datetime:" + ticket.getSeans_date());
//        Log.d("TICKET ADAPTER", "\n");

        Calendar calendar = Calendar.getInstance();

        Picasso.get().load(movieImage).into(holder.movie_image);
        holder.ticket_date.setText(ticket.getSeans_date()+ "." + calendar.get(Calendar.MONTH)+ "." + calendar.get(Calendar.YEAR) + ", " + ticket.getSeans_time()); // TODO: сделать полную дату и добавить время
        holder.movie_name.setText(ticket.getMovie_name());

//        String price_number = ticket.getPrice().replace("₽", "");
//        price_number = price_number.replace(" ", "");
//        price_number.substring(0);
//        String ticketPrice = price_number+".00"+" ₽"; // костыль
        String ticketPrice = ticket.getPrice().substring(3);
        holder.ticket_price.setText(ticketPrice);

        // обработка клика по кнопке удаления билета
        holder.btn_remove_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Log.d("TicketAdapter", "ON ITEM CLICK POSITION: " + position);
                onTicketRemoveClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ticketsArrayList.size();
    }

    public void onTicketRemoveClick(View view, int position) {
        // выполнить нужные действия при нажатии на кнопку
        TicketRemoveDialogFragment dialog = new TicketRemoveDialogFragment(this, position);
        dialog.show(mFragmentManager, "dialog");
    }
    public void updateTicketList(View view, int position) {
        UserID_Interface userProvider = (UserID_Interface) context;
        int currentUserId = userProvider.getCurrentUserId();

        Log.d("TicketAdapter", "\n");
        Log.d("TicketAdapter", "ADAPTER POSITION: " + position);
        Log.d("TicketAdapter", "USER ID: " + currentUserId);
        Log.d("TicketAdapter", "TICKET ORDER ID: " + ticketsArrayList.get(position).getOrder_id());
        Log.d("TicketAdapter", "TICKET ARRAY ORDER ID: " + ticketsArrayList.get(position).getOrder_id());
        Log.d("TicketAdapter", "CINEMA NAME: " + ticketsArrayList.get(position).getCinema_name());
        Log.d("TicketAdapter", "MOVIE NAME: " + ticketsArrayList.get(position).getMovie_name());
        Log.d("TicketAdapter", "\n");

        myDbHandler.deleteOrder(ticketsArrayList.get(position).getOrder_id());
        ticketsArrayList.remove(position);
        this.notifyItemRemoved(position);
    }


    public static class TicketHolder extends RecyclerView.ViewHolder{

        ImageView movie_image;
        TextView ticket_date;
        TextView movie_name;
        TextView ticket_price;
        ImageButton btn_remove_ticket;

        public TicketHolder(@NonNull View itemView) {
            super(itemView);
            btn_remove_ticket = itemView.findViewById(R.id.btn_ticket_remove);
            movie_image = itemView.findViewById(R.id.ticket_movie_image);
            ticket_date = itemView.findViewById(R.id.cinema_address_text);
            movie_name = itemView.findViewById(R.id.cinema_name_text);
            ticket_price = itemView.findViewById(R.id.ticket_price_text);
        }
    }
}
