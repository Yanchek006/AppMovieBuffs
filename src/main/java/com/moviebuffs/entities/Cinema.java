package com.moviebuffs.entities;

public class Cinema {
    private int id;
    private String movie_name;
    private String cinema_session_date;
    private String cinema_name;
    private String cinema_address;
    private String cinema_time_slots;
    private String cinema_ticket_price;
    private int cinema_tickets_left;


    public Cinema(String movie_name, String cinema_session_date, String cinema_name, String cinema_address, String cinema_time_slots, String cinema_ticket_price, int cinema_tickets_left) {
        this.movie_name = movie_name;
        this.cinema_session_date = cinema_session_date;
        this.cinema_name = cinema_name;
        this.cinema_address = cinema_address;
        this.cinema_time_slots = cinema_time_slots;
        this.cinema_ticket_price = cinema_ticket_price;
        this.cinema_tickets_left = cinema_tickets_left;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getSessionDate() {
        return cinema_session_date;
    }
    public void setSessionDate(String cinema_session_date) {this.cinema_session_date = cinema_session_date;}

    public String getMovieName() {
        return movie_name;
    }
    public void setMovieName(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getCinema_name() {
        return cinema_name;
    }
    public void setCinema_name(String cinema_name) {
        this.cinema_name = cinema_name;
    }

    public String getCinema_address() {
        return cinema_address;
    }
    public void setCinema_address(String cinema_address) {this.cinema_address = cinema_address;}

    public String getCinema_time_slots() {
        return cinema_time_slots;
    }
    public void getCinema_time_slots(String cinema_time_slots) {this.cinema_time_slots = cinema_time_slots;}

    public String getCinema_ticket_price() {
        return cinema_ticket_price;
    }
    public void setCinema_ticket_price(String cinema_ticket_price) {this.cinema_ticket_price = cinema_ticket_price;}

    public int getTicketsLeft() {return cinema_tickets_left;}
    public void setTicketsLeft(int left_tickets) {
        this.cinema_tickets_left = left_tickets;
    }
}