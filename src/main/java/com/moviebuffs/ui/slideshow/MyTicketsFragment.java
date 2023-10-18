package com.moviebuffs.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moviebuffs.R;
import com.moviebuffs.adapters.TicketAdapter;
import com.moviebuffs.entities.Orders;
import com.moviebuffs.interfaces.UserID_Interface;
import com.moviebuffs.repository.MyDBHandler;

import java.util.ArrayList;

public class MyTicketsFragment extends Fragment {

    private RecyclerView recycle_view;
    MyDBHandler myDbHandler;
    TicketAdapter ticketAdapter;
    ArrayList<Orders> ticketArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tickets, container, false);

        myDbHandler = new MyDBHandler(getContext(),"MovieBuffs.db",null,2);

        // Получаем текущий id юзера и берем все его ордеры(билеты) из базы
        UserID_Interface userProvider = (UserID_Interface) getActivity();
        int currentUserId = userProvider.getCurrentUserId();
        ticketArrayList = myDbHandler.getUserOrders(currentUserId);
//        Orders order1 = new Orders(1, 1, 3,"Movie name1", "25 мая 2023","10:30", "280", "1");
//        ticketArrayList.add(order1);
//        Orders order2 = new Orders(2, 2, 5,"Movie name2", "25 мая 2023","10:30", "210", "1");
//        ticketArrayList.add(order2);

        for (Orders order : ticketArrayList)
        {
            Log.d("TICKET FRAGMENT", "\n");
            Log.d("TICKET FRAGMENT", "Movie name:" + order.getMovie_name());
            Log.d("TICKET FRAGMENT", "Movie id:" + order.getMovie_id());
            Log.d("TICKET FRAGMENT", "Movie price:" + order.getPrice());
            Log.d("TICKET FRAGMENT", "Movie date:" + order.getSeans_date());
            Log.d("TICKET FRAGMENT", "Movie date:" + order.getSeans_time());
            Log.d("TICKET FRAGMENT", "\n");
        }


        ticketAdapter = new TicketAdapter(getContext(), ticketArrayList, getParentFragmentManager());

        recycle_view = root.findViewById(R.id.tickets_recyclerView);
        recycle_view.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recycle_view.setHasFixedSize(true);

        recycle_view.setAdapter(ticketAdapter);


//        TicketAdapter ticketAdapter = new TicketAdapter(getContext(), ticketArrayList, getParentFragmentManager(), new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = (int) v.getTag();
//                // Удаление элемента из базы данных
//                myDbHandler.deleteOrder(ticketArrayList.get(position).getOrder_id());
//
//                // Удаление элемента из списка
//                ticketArrayList.remove(position);
//                notifyItemRemoved(position);
//            }
//        });
//        recycle_view.setAdapter(ticketAdapter);

        return root;
    }


}