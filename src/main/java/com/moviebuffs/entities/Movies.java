package com.moviebuffs.entities;

public class Movies {
    private int id;
    private String image;
    private String name;
    private String description;
    private String price;
    private String movie_duration;
    private String genres;
    private int total_tickets;
    private int left_tickets;
    private String rating;
    private String year_country;

    public Movies(String image, String name, String description, String price, String movie_duration, String genres, int total_tickets, String rating, String year_country) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.price = price;
        this.movie_duration = movie_duration;
        this.genres = genres;
        this.total_tickets = total_tickets;
        this.rating = rating;
        this.year_country = year_country;
    }
    public Movies(String image, String name, String description, String price, String movie_duration, String genres, int total_tickets, int left_tickets, String rating, String year_country) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.price = price;
        this.movie_duration = movie_duration;
        this.genres = genres;
        this.total_tickets = total_tickets;
        this.left_tickets = left_tickets;
        this.rating = rating;
        this.year_country = year_country;
    }

    public Movies(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public Movies(int left_tickets) {
        this.left_tickets = left_tickets;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDuration() {
        return movie_duration;
    }

    public void setDuration(String movie_duration) {
        this.movie_duration = movie_duration;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public int getTotalTickets() {
        return total_tickets;
    }

    public void setTotalTickets(int total_tickets) {
        this.total_tickets = total_tickets;
    }

    public int getLeftTickets() {
        return left_tickets;
    }

    public void setLeftTickets(int left_tickets) {
        this.left_tickets = left_tickets;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getYearCountry() {
        return year_country;
    }

    public void setYearCountry(String year_country) {
        this.year_country = year_country;
    }

}