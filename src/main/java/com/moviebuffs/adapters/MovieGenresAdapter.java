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

public class MovieGenresAdapter extends RecyclerView.Adapter<MovieGenresAdapter.MovieGenreHolder>{

    Context context;
    List<String> genresArrayList;

    String genre;

    public MovieGenresAdapter(Context context, List<String> genresArrayList) {
        this.context = context;
        this.genresArrayList = genresArrayList;
    }

    @NonNull
    @Override
    public MovieGenreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.movie_genre_item, parent, false);

        MovieGenreHolder seansTimeHolder = new MovieGenreHolder(v);

//        Drawable default_bg = context.getResources().getDrawable(R.drawable.movie_time_bg);
//        Drawable selected_bg = context.getResources().getDrawable(R.drawable.seans_time_selected_bg);

//        Drawable default_bg = ContextCompat.getDrawable(context, R.drawable.seans_time_selected_bg);
//        Drawable selected_bg = ContextCompat.getDrawable(context, R.drawable.movie_time_bg);


        return seansTimeHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieGenreHolder holder, int position) {
        genre = genresArrayList.get(position);
        holder.genreView.setText(genre);
    }

    @Override
    public int getItemCount() {
        return genresArrayList.size();
    }


    public static class MovieGenreHolder extends RecyclerView.ViewHolder{
        TextView genreView;

        public MovieGenreHolder(@NonNull View itemView) {
            super(itemView);
            genreView = itemView.findViewById(R.id.movie_genre_text);
        }
    }
}
