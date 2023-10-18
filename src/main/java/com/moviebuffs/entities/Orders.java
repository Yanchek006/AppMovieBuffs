package com.moviebuffs.entities;

public class Orders {

    private int order_id;
    private int user_id ;
    private int movie_id;

    private String cinema_name;
    private String movie_name;
    private String seans_date;
    private String seans_time;
    private String price;
    private int quantity;

    public Orders(int orderId, int userId, int movie_id, String cinemaName, String movieName, String seansDate, String seansTime, int quantity, String price) {
        this.order_id = orderId;
        this.user_id = userId;
        this.movie_id = movie_id;
        this.cinema_name = cinemaName;
        this.movie_name = movieName;
        this.seans_date = seansDate;
        this.seans_time = seansTime;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMovie_id() {
        return movie_id;
    }
    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_name() {
        return movie_name;
    }
    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getCinema_name() {
        return cinema_name;
    }
    public void setCinema_name(String cinema_name) {
        this.movie_name = cinema_name;
    }

    public String getSeans_date() {
        return seans_date;
    }
    public void setSeans_date(String seans_date) {
        this.seans_date = seans_date;
    }

    public String getSeans_time() {
        return seans_time;
    }
    public void setSeans_time(String seans_time) {
        this.seans_time = seans_time;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
