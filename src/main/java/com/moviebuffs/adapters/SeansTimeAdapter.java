package com.moviebuffs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moviebuffs.R;

import java.util.List;

public class SeansTimeAdapter extends RecyclerView.Adapter<SeansTimeAdapter.SeansTimeHolder>{

    Context context;
    List<String> seansTimeArrayList;

    String seans_time;


    public SeansTimeAdapter(Context context, List<String> seansTimeArray) {
        this.context = context;
        this.seansTimeArrayList = seansTimeArray;
    }

    @NonNull
    @Override
    public SeansTimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.movie_time_item, parent, false);

        SeansTimeHolder seansTimeHolder = new SeansTimeHolder(v);
        return seansTimeHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SeansTimeHolder holder, int position) {
        String time = seansTimeArrayList.get(position);
        holder.timeView.setText(time);

    }

    @Override
    public int getItemCount() {
        return seansTimeArrayList.size();
    }


    public static class SeansTimeHolder extends RecyclerView.ViewHolder{
        TextView timeView;

        public SeansTimeHolder(@NonNull View itemView) {
            super(itemView);
            timeView = itemView.findViewById(R.id.seans_time_text);
        }
    }
}
