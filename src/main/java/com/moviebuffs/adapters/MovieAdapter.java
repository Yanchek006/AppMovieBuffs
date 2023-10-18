package com.moviebuffs.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moviebuffs.R;
import com.moviebuffs.entities.Movies;
import com.moviebuffs.ui.gallery.OnMovieItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    Context context;

    ArrayList<Movies> moviesArrayList;
    private OnMovieItemClickListener listener;

    public MovieAdapter(Context context, ArrayList<Movies> moviesArrayList, OnMovieItemClickListener listener) {
        this.context = context;
        this.moviesArrayList = moviesArrayList;
        this.listener = listener;
    }

    public void setFilteredList(ArrayList<Movies> filteredList){
        this.moviesArrayList = filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.product_item,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Movies movie = moviesArrayList.get(position);

        holder.name.setText(movie.getName());
        Picasso.get().load(movie.getImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onMovieItemClick(holder.getAdapterPosition());
                }
                else {
                    Log.e("MovieAdapter", "Объект onMovieItemClick не инициализирован");
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView description;
        TextView duration;
        TextView rating;
        TextView year_country;

        RecyclerView genresRecylerView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.description);
            duration = itemView.findViewById(R.id.movie_duration_text);
            rating = itemView.findViewById(R.id.rating_text);
            year_country = itemView.findViewById(R.id.movie_year_country);
            genresRecylerView = itemView.findViewById(R.id.genres_recycleView);
        }
    }
}
