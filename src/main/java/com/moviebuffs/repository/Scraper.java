package com.moviebuffs.repository;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviebuffs.interfaces.OnScrapeListener;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Scraper extends Thread {
    private OnScrapeListener listener;

    public Scraper(OnScrapeListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        List<Map<String, String>> result = new ArrayList<>();
        String url = "https://msk.kinoafisha.info/";
        int retries = 3;

        while (retries > 0) {
            try {
                Connection.Response response = Jsoup.connect(url).execute();

                if (response.statusCode() == 200) {
                    Document doc = response.parse();
                    // остальной код
//                    Document doc = Jsoup.connect(url).get();
                    for (Element movieItem : doc.select("div.movieList_item")) {
                        if (movieItem.text()!=null)
                        {
                            String title = Objects.requireNonNull(movieItem.selectFirst("a.movieItem_title")).text().trim();
                            String imageUrl = movieItem.selectFirst("picture.movieItem_poster img.picture_image").attr("src");
                            String description = movieItem.selectFirst("span.movieItem_genres").text().trim();
                            String[] yearCountry = movieItem.selectFirst("span.movieItem_year").text().trim().split(",");
                            String year = yearCountry[0].trim();
                            String country = yearCountry[1].trim();

                            Map<String, String> movieDict = new HashMap<>();

                            movieDict.put("title", title);
                            movieDict.put("image_url", imageUrl);
                            movieDict.put("description", description);
                            movieDict.put("year", year);
                            movieDict.put("country", country);
                            result.add(movieDict);
                        }
                        else {
                            Log.d("Scraper", "Элемент не найден на сайте");
                        }
                    }
                    if (listener != null) {
                        if (result != null) {
                            try {
                                String jsonResult = new ObjectMapper().writeValueAsString(result);
                                listener.onSuccess(jsonResult);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                                listener.onError(e.getMessage());
                            }
                        } else {
                            listener.onError(" ! ---------------- Failed to scrape data --------------- !");
                        }
                    }
                    return;
                } else {
                    // обработка ошибки
                    Log.e("Scraper", "Failed to connect to " + url);
                }
//
            } catch (IOException e) {
                retries--;
                if (retries == 0) {
                    e.printStackTrace();
                }
            }
        }
    }
}