package com.moviebuffs.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.moviebuffs.R;
import com.moviebuffs.repository.MyDBHandler;
import com.moviebuffs.ui.gallery.MovieTicketBuild;

import java.util.ArrayList;
import java.util.List;

public class SeansDayAdapter extends RecyclerView.Adapter<SeansDayAdapter.SeansDayHolder>{

    Context context;
    String[][] daysAndNumbersOfWeek;

    String week_day;
    String number_day;
    MyDBHandler myDbHandler;
    List<Boolean> selectedItems = new ArrayList<>();
    List<Integer> selectedDays = new ArrayList<>();

    MovieTicketBuild parent_fragment;

    private int selectedItem = 0;

    public SeansDayAdapter(Context context, MovieTicketBuild parent_fragment, String[][] daysAndNumbersOfWeek) {
        this.context = context;
        this.parent_fragment = parent_fragment;
        this.daysAndNumbersOfWeek = daysAndNumbersOfWeek;
    }

    @NonNull
    @Override
    public SeansDayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.movie_date_item, parent, false);

        SeansDayHolder seansDayHolder = new SeansDayHolder(v);
        return seansDayHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SeansDayHolder holder, int position) {

        // инициализируем массив состояний
//        for (int i = 0; i < daysAndNumbersOfWeek[0].length; i++)
//        {
//            selectedItems.add(i, false);
//        }
//
//        if (position == 0) {
//            holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.movie_date_selected_image));
//            selectedItems.set(0, true);
//        } else {
//            holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.movie_date_image));
//            selectedItems.set(0, false);
//        }

        week_day = daysAndNumbersOfWeek[0][position];
        number_day = daysAndNumbersOfWeek[1][position];

        holder.week_day.setText(week_day);
        holder.number_day.setText(number_day);


        if (selectedItem == position) {
            holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.movie_date_selected_image));
        } else {
            holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.movie_date_image));
        }



        // создаем клик-листенер для элементов
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // обработка клика на кнопке
//                Drawable currentBackground = v.getBackground();
//                Drawable newBackground = context.getResources().getDrawable(R.drawable.movie_date_selected_image);
//                // если текущий ресурс и новый ресурс не равны, то меняем фон
//                if (!currentBackground.equals(newBackground)) {
//                    v.setBackground(newBackground);
//                }

//                Drawable default_bg = v.getResources().getDrawable(R.drawable.movie_date_image);
//                Drawable selected_bg = v.getResources().getDrawable(R.drawable.movie_date_selected_image);
//                Drawable newBackground = null;
//
//                for (int i = 0; i < daysAndNumbersOfWeek[0].length; i++)
//                {
//                    selectedItems.set(i, false);
////                    newBackground = default_bg;
//                    v.setBackground(selected_bg);
//                }
//
//
//
////                if (selectedItems.get(holder.getAdapterPosition()) == false) {
//////                    newBackground = selected_bg;
////                    for (int i = 0; i < daysAndNumbersOfWeek[0].length; i++)
////                    {
////                        selectedItems.set(i, false);
////                        newBackground = default_bg;
//////                        v.setBackground(default_bg);
////                    }
////                    selectedItems.set(holder.getAdapterPosition(), true);
////                    newBackground = selected_bg;
////                    Log.d("SEAS TIME ADAPTER", "TRUE");
////                } else {
//////                    if (selectedItems.get(holder.getAdapterPosition()) == true)
//////                    {
//////
//////                    }
//////                    newBackground = default_bg;
//////                    selectedItems.set(holder.getAdapterPosition(), false);
//////                    Log.d("SEAS TIME ADAPTER", "FALSE");
////                }
//                for (int i = 0; i < daysAndNumbersOfWeek[0].length; i++)
//                {
//                    Log.d("DATE", "\n");
//                    Log.d("DATE", i + ": " + selectedItems.get(i));
//                    Log.d("DATE", "\n");
//                }
//
//                // меняем фон элемента
//                v.setBackground(default_bg);

                if (selectedItem == position) {
                    return; // уже выбран этот элемент
                }

                int previousItem = selectedItem;
                selectedItem = position;
                notifyItemChanged(previousItem);
                notifyItemChanged(selectedItem);

                String itemDay = daysAndNumbersOfWeek[1][holder.getAdapterPosition()];
                Log.d("SEAS DAY ADAPTER", "Получаем дату итема при клике на него:" + itemDay);
                selectedDays.add(Integer.parseInt(itemDay));

                parent_fragment.UpdateCinemaList(itemDay);




//                MovieTicketBuild ticketBuild = new MovieTicketBuild();
//                ticketBuild.filterCinemaList(selectedDays);

            }
        });
    }

    @Override
    public int getItemCount() {
        return daysAndNumbersOfWeek[0].length;
    }

    public void onTicketRemoveClick(View view) {
        // выполнить нужные действия при нажатии на кнопку
//        Log.d("SEAS DAY ADAPTER", "\n");
//        Log.d("SEAS DAY ADAPTER", "ВЫЗОВ ДИАЛОГОВОГО ОКНА ОБ УДАЛЕНИИ ТИКЕТА");
//        Log.d("SEAS DAY ADAPTER", "\n");

    }

    public static class SeansDayHolder extends RecyclerView.ViewHolder{
        TextView week_day;
        TextView number_day;

        public SeansDayHolder(@NonNull View itemView) {
            super(itemView);
            week_day = itemView.findViewById(R.id.week_day);
            number_day = itemView.findViewById(R.id.number_day);
        }
    }
}
