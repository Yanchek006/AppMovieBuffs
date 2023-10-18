package com.moviebuffs.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moviebuffs.R;
import com.moviebuffs.adapters.MovieAdapter;
import com.moviebuffs.entities.Cinema;
import com.moviebuffs.entities.Movies;
import com.moviebuffs.repository.MyDBHandler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MovieListFragment extends Fragment{

    private NavController navController;
    private SearchView searchView;

    private RecyclerView recycleview;
    MyDBHandler myDbHandler;
    MovieAdapter movieAdapter;
    ArrayList<Movies> moviesArrayList = new ArrayList<>();

    private List<Movies> movies;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);

        movies = new ArrayList<>();
        myDbHandler = new MyDBHandler(getContext(),"MovieBuffs.db",null,2);

        if (myDbHandler.allMovies().isEmpty()) {
            init();
        }
        else {
            moviesArrayList = myDbHandler.allMovies();
        }


        movieAdapter = new MovieAdapter(getContext(), moviesArrayList, new OnMovieItemClickListener() {
            @Override
            public void onMovieItemClick(int position) {
                // обработка клика по элементам списка
                // создание объекта Bundle и добавление в него данных
                Bundle bundle = new Bundle();
                bundle.putString("name", moviesArrayList.get(position).getName());
                bundle.putString("image", moviesArrayList.get(position).getImage());
                bundle.putString("price", moviesArrayList.get(position).getPrice());
                bundle.putString("movie_duration", moviesArrayList.get(position).getDuration());
                bundle.putString("description", moviesArrayList.get(position).getDescription());
                bundle.putString("genres", moviesArrayList.get(position).getGenres());
                bundle.putInt("total_tickets", moviesArrayList.get(position).getTotalTickets());
                bundle.putInt("left_tickets", moviesArrayList.get(position).getLeftTickets());
                bundle.putString("rating", moviesArrayList.get(position).getRating());
                bundle.putString("year_country", moviesArrayList.get(position).getYearCountry());


                NavHostFragment navHostFragment =
                        (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_menu_movies_item_to_movieDetailsFragment2, bundle);

            }
        });


        recycleview = view.findViewById(R.id.timeSlots_recycleView);
        recycleview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recycleview.setHasFixedSize(true);
        recycleview.setAdapter(movieAdapter);

        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

        });
        recycleview.setAdapter(movieAdapter);
        return view;

    }

    private void filterList(String text) {
        ArrayList<Movies> filteredList = new ArrayList<>();
        for (Movies movie : moviesArrayList){
            if (movie.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(movie);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(getContext(), "Ничего не найдено", Toast.LENGTH_SHORT).show();
        }else{
            movieAdapter.setFilteredList(filteredList);
        }
    }


    public void init(){
        // Запускаем парсер в отдельном потоке
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getHTML("https://msk.kinoafisha.info/movies/");
            }
        };
        Thread secThread = new Thread(runnable);
        secThread.start();
    }
    public void getHTML(String url)
    {
        try {
            Document doc = Jsoup.connect(url).get();
            Log.d("Scraper", "Title:" + doc.title());
            Log.d("Scraper", "Movies count:" + doc.getElementsByClass("movieList_item").size());

            for (int i = 0; i < 6; i++) {
                Element movieItem = doc.getElementsByClass("movieList_item").get(i);
                if (movieItem != null && movieItem.text() != null && !movieItem.text().isEmpty())
                {
                    String movie_name = movieItem.selectFirst("div.movieItem_info a.movieItem_title").text().trim();
                    Element source = movieItem.select("source[type='image/jpeg']").first();
                    String movie_imageUrl = source.attr("srcset");
                    String genres = movieItem.selectFirst("span.movieItem_genres").text().trim();
                    String yearCountry = movieItem.selectFirst("span.movieItem_year").text().trim();

                    // Парсим описание и тд. со страницы фильма
                    String cinema_details_ref = movieItem.selectFirst("a").attr("href");
                    Document cinema_doc = Jsoup.connect(cinema_details_ref).get();

                    String description = "";
                    String price = "";
                    String rating = "";
                    String movie_duration = "";


                    try {
                        description = cinema_doc.selectFirst("div.filmInfo div.tabs_content p").text();
                        price = cinema_doc.selectFirst("div.schedule_showtimes div.showtimes_cell span.session_price").text();
                        movie_duration = cinema_doc.selectFirst("div.filmInfo_infoItem span.filmInfo_infoData").text();
                        rating = cinema_doc.selectFirst("div.rating_inner span.rating_num").text();


                        String cinema_name = "";
                        String cinema_address = "";
                        String cinema_time_slots = "";
                        String cinema_ticket_price = "";

                        String date_ref = "";
                        String date_number = "";


                        // переходим на страницу с расписаниями сеансов
                        Element seans_more_btn = cinema_doc.selectFirst("div.schedule_more");
                        String morebtn_ref = seans_more_btn.selectFirst("a").attr("href");
                        Document doc_moreSeanses = Jsoup.connect(morebtn_ref).get();

                        // получаем даты и ссылки
                        Element date_bar = doc_moreSeanses.selectFirst("div.scheduleFilter_calendar.week.swipe.outer-mobile.inner-mobile");
                        Elements dateItem = date_bar.select("a.week_day");
                        Log.d("Scraper|Cinema details", "Date list:" + date_bar.text());

//
                        for (Element date : dateItem) // перебор дат
                        {
                            date_ref = date.attr("href");
                            date_number = date.selectFirst("span.week_num").text();

                            Log.d("Scraper|Cinema details", "Date ref:" + date_ref);
                            Log.d("Scraper|Cinema details", "Date number:" + date_number);

                            // переходим по ссылкам в датах и собираем данные для каждой даты
                            Document doc_dateItem = Jsoup.connect(date_ref).get();
                            getSeansData(movie_name, doc_dateItem, date_number);
                        }

                    }
                    catch (Exception x) {
                        Log.d("Scraper|Cinema details", "Exception:" + x);
                        x.printStackTrace();
                    }

                    // Дебажим ответ парсера
                    Log.d("Scraper|Movie", "\n");
                    Log.d("Scraper|Movie", "Parsed Title:" + movie_name);
                    Log.d("Scraper", "Parsed Image:" + movie_imageUrl);
                    Log.d("Scraper", "Parsed description:" + description);
                    Log.d("Scraper", "Parsed price:" + price);
                    Log.d("Scraper", "Parsed duration:" + movie_duration);
                    Log.d("Scraper", "Parsed genres:" + genres);
                    Log.d("Scraper", "Parsed rating:" + rating);
                    Log.d("Scraper", "Parsed yearCountry:" + yearCountry);
                    Log.d("Scraper|Movie", "\n");

                    Movies movie_item = new Movies(
                            movie_imageUrl,
                            movie_name,
                            description,
                            price,
                            movie_duration,
                            genres,
                            50,
                            50,
                            rating,
                            yearCountry);

                    // Проверяем, существует ли такой фильм в базе и добавляем, если нет
                    if (!myDbHandler.checkMovieExist(movie_item)){
                        myDbHandler.addMovie(movie_item);
                        moviesArrayList.add(movie_item);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                movieAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    else {
                        myDbHandler.updateMovie(movie_item);
//                        if(!moviesArrayList.isEmpty()) moviesArrayList.set(i, movie_item);
//                        moviesArrayList = myDbHandler.allMovies();
                        moviesArrayList.add(movie_item);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                movieAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
                else {
                    Log.d("Scraper", "Элемент не найден на сайте");
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void getSeansData(String movie_name, Document current_doc, String seans_date) {
        String cinema_time_slots;
        String cinema_name;
        String cinema_ticket_price;
        String cinema_address;
        Element seans_table = current_doc.selectFirst("div.showtimes.showtimes-movie.outer-mobile.coating-mobile-main"); //.subList(0, 5);

        Elements allElements = null;
        if (seans_table != null) {
            allElements = seans_table.getElementsByClass("showtimes_item");
            allElements.remove(1);
        }
        else {
            Log.d("Scraper", "Seans table is NULL: "+seans_table.select("div.showtimes_item").text());
        }
        List<Element> firstThreeElements = allElements.subList(2,8);

        for (Element row : firstThreeElements)
        {
            if(!row.text().isEmpty())
            {
                cinema_name = row.selectFirst("span.showtimesCinema_name").text();
                cinema_address = row.selectFirst("span.showtimesCinema_addr").text();
                cinema_time_slots = row.selectFirst("div.showtimes_sessions span.session_time").text(); // TODO: сделать парсинг всех тайм-слотов
                cinema_ticket_price = row.selectFirst("div.showtimes_sessions span.session_price").text();

                Log.d("Scraper|Cinema", "\n");
                Log.d("Scraper|Cinema", "Parsed Seans date:" + seans_date);
                Log.d("Scraper|Cinema", "Parsed Cinema name:" + cinema_name);
                Log.d("Scraper|Cinema", "Parsed Cinema address:" + cinema_address);
                Log.d("Scraper|Cinema", "Parsed Cinema time slots:" + cinema_time_slots);
                Log.d("Scraper|Cinema", "Parsed Cinema ticket prices:" + cinema_ticket_price);
                Log.d("Scraper|Cinema", "\n");

                // генерим рандомное число билетов для сеанса в кинотеатре
                Random random = new Random();
                int randomTickets = random.nextInt(50);
                // TODO: сделать для каждого сеанса свое число билетом
                // TODO: сделать для каждого сеанса свою цену

                Cinema cinemaObjectItem = null;
                if (!cinema_name.isEmpty() && !cinema_address.isEmpty() && !cinema_time_slots.isEmpty() && !cinema_ticket_price.isEmpty()){
                    // создаем сеанс и сохраняем в базу
                    cinemaObjectItem = new Cinema(movie_name, seans_date, cinema_name, cinema_address, cinema_time_slots, cinema_ticket_price, randomTickets);
                    if(!myDbHandler.checkSeansExist(cinemaObjectItem)) myDbHandler.addSeans(cinemaObjectItem);
                }

            }
        }
    }

}