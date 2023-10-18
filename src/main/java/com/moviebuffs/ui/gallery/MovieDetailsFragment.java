package com.moviebuffs.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.moviebuffs.R;
import com.moviebuffs.adapters.MovieGenresAdapter;
import com.moviebuffs.entities.Movies;
import com.moviebuffs.interfaces.UserID_Interface;
import com.moviebuffs.repository.MyDBHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MovieDetailsFragment extends Fragment {

    private ArrayList<Movies> moviesArrayList;
    MyDBHandler myDBHandler = new MyDBHandler(getContext(), "MovieBuffs.db", null, 2);
    Movies movie;
    private ArrayList<Movies> movies;
    TextView leftTicketsTextView;

    List<String> genres_list;
    private NavController navController;

//    private TextView titleTextView;
//    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        productsInfo = myDbHandler.allProducts();
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        // получение данных из Bundle
        Bundle bundle = getArguments();

        String image = bundle.getString("image");
        String name = bundle.getString("name");
        String price = bundle.getString("price");
        String movie_duration = bundle.getString("movie_duration");
        String description = bundle.getString("description");
        String genres = bundle.getString("genres");
        int total_tickets = bundle.getInt("total_tickets");
        int left_tickets = bundle.getInt("left_tickets");
        String rating = bundle.getString("rating");
        String year_country = bundle.getString("year_country");

        movie = new Movies(image, name, description, price, movie_duration, genres, total_tickets, left_tickets, rating, year_country);

        // инициализация элементов пользовательского интерфейса
        ImageView movie_image = view.findViewById(R.id.product_image);
        TextView nameTextView = view.findViewById(R.id.product_name);
        TextView durationTextView = view.findViewById(R.id.movie_duration_text);
//        TextView genre1TextView = view.findViewById(R.id.genre1);
//        TextView genre2TextView = view.findViewById(R.id.genre2);
//        TextView genre3TextView = view.findViewById(R.id.genre3);
//        TextView leftTicketsTextView = view.findViewById(R.id.left_tickets_textView);
        TextView descriptionTextView = view.findViewById(R.id.description);
        TextView ratingTextView = view.findViewById(R.id.rating_text);
        TextView year_countryTextView = view.findViewById(R.id.movie_year_country);


        genres_list = Arrays.asList(genres.split(","));

//        if (genres_list.length != 0)
//        {
//            if (!genres_list[0].isEmpty()) genre1TextView.setText(genres_list[0]);
//            else genre1TextView.setVisibility(View.GONE);
//
//            if (!genres_list[0].isEmpty()) genre2TextView.setText(genres_list[0]);
//            else genre2TextView.setVisibility(View.GONE);
//
//            if (!genres_list[0].isEmpty()) genre3TextView.setText(genres_list[0]);
//            else genre3TextView.setVisibility(View.GONE);
//        }

        Picasso.get().load(image).into(movie_image);
        nameTextView.setText(name);
        durationTextView.setText(movie_duration);
//        leftTicketsTextView.setText(name);
        descriptionTextView.setText(description);
        ratingTextView.setText(rating);
        year_countryTextView.setText(year_country);

//        UserID_Interface userProvider = (UserID_Interface) getActivity();
//        int currentUserId = userProvider.getCurrentUserId();
//        Log.d("DETAILS", "------------ CURRENT ID ---------- " + currentUserId);
//        Log.d("DETAILS", "------------ ACTIVITY ---------- " + getActivity());

        // возврат View
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movies = new ArrayList<>();
        myDBHandler = new MyDBHandler(getContext(),null,null,2);

//        leftTicketsTextView = view.findViewById(R.id.left_tickets_textView);
//        String left_tickets_string = String.valueOf(myDBHandler.getLeftTickets(movie.getName()));
//        leftTicketsTextView.setText(left_tickets_string);

        RecyclerView genreRecyclerView = view.findViewById(R.id.genres_recycleView);
        MovieGenresAdapter genresAdapter = new MovieGenresAdapter(getContext(), genres_list);
        genreRecyclerView.setAdapter(genresAdapter);


        // Обработка клика на кнопку - Купить билет
        Button buy_button = view.findViewById(R.id.buy_button);

        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnBuyButtonClicked(view);
            }
        });

    }

    public void OnBuyButtonClicked(View v) {
        Log.d("DETAILS", "------------ TICKET BUY BUTTON CLICKED ---------- ");
        UserID_Interface userProvider = (UserID_Interface) getActivity();
        int currentUserId = userProvider.getCurrentUserId();
        int movie_id = myDBHandler.getMovieId(movie.getName());

        Bundle bundle = new Bundle();
        bundle.putString("movie_name", movie.getName());

        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        navController = navHostFragment.getNavController();
        navController.navigate(R.id.action_movieDetailsFragment_to_menu_build_ticket, bundle);

//        Log.d("DETAILS", "\n");
//        Log.d("DETAILS", "CURRENT USER ID:" + currentUserId);
//        Log.d("DETAILS", "MOVIE ID:" + myDBHandler.getMovieId(movie.getName()));
//        Log.d("DETAILS", "MOVIE NAME:" + movie.getName());
//        Log.d("DETAILS", "ORDER PRICE:" + movie.getPrice());
//        Log.d("DETAILS", "Left Tickets:" + myDBHandler.getLeftTickets(cinema.getCinema_name(), cinema.getMovieName(), cinema.getSessionDate()));
//        Log.d("DETAILS", "\n");


    }

}