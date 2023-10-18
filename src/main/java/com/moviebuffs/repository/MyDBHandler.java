package com.moviebuffs.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.annotation.Nullable;

import com.moviebuffs.entities.Cinema;
import com.moviebuffs.entities.Movies;
import com.moviebuffs.entities.Orders;
import com.moviebuffs.entities.Users;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "MovieBuffs.db";

    //table USERS info
    public static final String TABLE_USERS_NAME = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAME = "name";
//    public static final String COLUMN_IS_CURRENT_USER = "isCurrentUser";

    ////////////////////////////////////////////////////////
    //table PRODUCTS info
    public static final String TABLE_MOVIES_NAME = "movies";
    public static final String COLUMN_MOVIE_ID = "id";
    public static final String COLUMN_MOVIE_IMAGE = "image_id";
    public static final String COLUMN_MOVIE_NAME = "movie_name";
    public static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DURATION = "movie_duration";
    public static final String COLUMN_PRICE = "price";

    public static final String COLUMN_GENRES = "genres";

    public static final String COLUMN_TOTAL_TICKETS = "total_tickets";
    private static final String COLUMN_LEFT_TICKETS = "left_tickets";

    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_YEAR_COUNTRY = "year_country";
    ///////////////////////////////////////////////////////


    private static final String TABLE_CINEMA_NAME = "cinemas";
    private static final String COLUMN_CINEMA_ID = "cinema_id";
    private static final String COLUMN_CINEMA_MOVIE_NAME = "cinema_movie_name";
    private static final String COLUMN_CINEMA_SESSION_DATE = "cinema_session_date";
    private static final String COLUMN_CINEMA_NAME = "cinema_name";
    private static final String COLUMN_CINEMA_ADDRESS = "cinema_address";
    private static final String COLUMN_CINEMA_TIME_SLOTS = "cinema_timeslots";
    private static final String COLUMN_CINEMA_TICKET_PRICE = "cinema_ticket_price";
    private static final String COLUMN_CINEMA_TICKET_LEFT = "cinema_ticket_left";



    private static final String TABLE_ORDERS_NAME = "orders";
    private static final String COLUMN_ORDER_ID = "order_id";
    private static final String COLUMN_ORDER_USER_ID = "user_order_id";
    private static final String COLUMN_ORDER_MOVIE_ID = "order_movie_id";
    private static final String COLUMN_ORDER_CINEMA_NAME = "order_cinema_name";
    private static final String COLUMN_ORDER_MOVIE_NAME = "order_movie_name";
    private static final String COLUMN_SEANS_DATE = "seans_date";
    private static final String COLUMN_SEANS_TIME = "seans_time";
    private static final String COLUMN_ORDER_QUANTITY = "order_quantity";
    private static final String COLUMN_ORDER_PRICE = "order_price";





    public MyDBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

//        sqLiteDatabase.delete(TABLE_MOVIES_NAME, null, null);
//        clearTable(sqLiteDatabase, "TABLE_MOVIES_NAME");

        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS_NAME + "(" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EMAIL + " TEXT ," +
                COLUMN_PASSWORD + " TEXT ," +
                COLUMN_NAME + " TEXT " +
//                COLUMN_IS_CURRENT_USER + " TEXT " +
                ");";

//        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_MOVIES_NAME); // удаление всех записей из таблицы

        //if price is string could be easiest for retrieving data and put in the fragment using TextView
        String query2 = "CREATE TABLE IF NOT EXISTS " + TABLE_MOVIES_NAME + "(" +
                COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MOVIE_IMAGE + " TEXT ," +
                COLUMN_MOVIE_NAME + " TEXT ," +
                COLUMN_DESCRIPTION + " TEXT ," +
                COLUMN_PRICE + " TEXT ," +
                COLUMN_DURATION + " TEXT ," +
                COLUMN_GENRES + " TEXT ," +
                COLUMN_TOTAL_TICKETS + " INTEGER DEFAULT 50 ," +
                COLUMN_LEFT_TICKETS + " INTEGER DEFAULT 50 ," +
                COLUMN_RATING + " TEXT ," +
                COLUMN_YEAR_COUNTRY + " TEXT " +
                ");";

//        sqLiteDatabase.delete(TABLE_MOVIES_NAME, null, null);
        String query3 = "CREATE TABLE IF NOT EXISTS " + TABLE_ORDERS_NAME + "(" +
                COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ORDER_USER_ID + " INTEGER ," +
                COLUMN_ORDER_MOVIE_ID + " INTEGER ," +
                COLUMN_ORDER_CINEMA_NAME + " INTEGER ," +
                COLUMN_ORDER_MOVIE_NAME + " TEXT ," +
                COLUMN_SEANS_DATE + " TEXT ," +
                COLUMN_SEANS_TIME + " TEXT ," +
                COLUMN_ORDER_QUANTITY + " INTEGER ," +
                COLUMN_ORDER_PRICE + " TEXT " +
                ");";

        String query4 = "CREATE TABLE IF NOT EXISTS " + TABLE_CINEMA_NAME + "(" +
                COLUMN_CINEMA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CINEMA_MOVIE_NAME + " INTEGER ," +
                COLUMN_CINEMA_SESSION_DATE + " TEXT ," +
                COLUMN_CINEMA_NAME + " TEXT ," +
                COLUMN_CINEMA_ADDRESS + " TEXT ," +
                COLUMN_CINEMA_TIME_SLOTS + " TEXT ," +
                COLUMN_CINEMA_TICKET_PRICE + " TEXT ," +
                COLUMN_CINEMA_TICKET_LEFT + " INTEGER " +
                ");";



        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query2);
        sqLiteDatabase.execSQL(query3);
        sqLiteDatabase.execSQL(query4);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES_NAME);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS_NAME);
        onCreate(sqLiteDatabase);
    }


public Boolean checkSeansExist(Cinema cinema) {
    SQLiteDatabase db = getWritableDatabase();
    Cursor cursor = null;
    try {
        cursor = db.rawQuery("SELECT * FROM " + TABLE_CINEMA_NAME + " WHERE " + COLUMN_CINEMA_NAME + " = ? AND " + COLUMN_CINEMA_SESSION_DATE + " = ?",
                new String[]{cinema.getCinema_name(), cinema.getSessionDate()});
        return cursor.getCount() > 0;
    } finally {
        if (cursor != null) {
            cursor.close();
        }
    }
}




    public Cinema getSeansByMovieAndDate(String movieName, String sessionDate) {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.query(TABLE_CINEMA_NAME, new String[] {COLUMN_CINEMA_MOVIE_NAME, COLUMN_CINEMA_SESSION_DATE, COLUMN_CINEMA_NAME,
                    COLUMN_CINEMA_ADDRESS, COLUMN_CINEMA_TIME_SLOTS, COLUMN_CINEMA_TICKET_PRICE, COLUMN_CINEMA_TICKET_LEFT},
            COLUMN_CINEMA_MOVIE_NAME + "=? AND " + COLUMN_CINEMA_SESSION_DATE + "=?", new String[] {movieName, sessionDate},
            null, null, null, null);

    Cinema cinema = null;
    if (cursor != null && cursor.moveToFirst()) {
        cinema = new Cinema(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6));
    }

    if (cursor != null) {
        cursor.close();
    }

    return cinema;
}

    public List<Cinema> getSeansListByMovieAndDate(String movieName, String sessionDate) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CINEMA_NAME, new String[] {COLUMN_CINEMA_MOVIE_NAME, COLUMN_CINEMA_SESSION_DATE, COLUMN_CINEMA_NAME,
                        COLUMN_CINEMA_ADDRESS, COLUMN_CINEMA_TIME_SLOTS, COLUMN_CINEMA_TICKET_PRICE, COLUMN_CINEMA_TICKET_LEFT},
                COLUMN_CINEMA_MOVIE_NAME + "=? AND " + COLUMN_CINEMA_SESSION_DATE + "=?", new String[] {movieName, sessionDate},
                null, null, null, null);

        List<Cinema> cinemaList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Cinema cinema = new Cinema(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6));
                cinemaList.add(cinema);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return cinemaList;
    }

    public ArrayList<Cinema> allSeanses() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * from " + TABLE_CINEMA_NAME;
        Cursor cursor = null;
        ArrayList<Cinema> cinemaArrayList = new ArrayList<>();

        try {
            cursor = db.rawQuery(query, null);

            // from first to last row in table products
            if (cursor.moveToFirst()) {
                do {
                    //adding the data
                    cinemaArrayList.add(new Cinema(
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getInt(7)));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        //return ArrayList for easiest use
        return cinemaArrayList;
    }


    public void addSeans(Cinema cinema) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CINEMA_MOVIE_NAME, cinema.getMovieName());
        values.put(COLUMN_CINEMA_SESSION_DATE, cinema.getSessionDate());
        values.put(COLUMN_CINEMA_NAME, cinema.getCinema_name());
        values.put(COLUMN_CINEMA_ADDRESS, cinema.getCinema_address());
        values.put(COLUMN_CINEMA_TIME_SLOTS, cinema.getCinema_time_slots());
        values.put(COLUMN_CINEMA_TICKET_PRICE, cinema.getCinema_ticket_price());
        values.put(COLUMN_CINEMA_TICKET_LEFT, cinema.getTicketsLeft());

        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_CINEMA_NAME, null, values);
        db.close();
    }

    public void updateSeans(Cinema cinema) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CINEMA_MOVIE_NAME, cinema.getMovieName());
        values.put(COLUMN_CINEMA_SESSION_DATE, cinema.getSessionDate());
        values.put(COLUMN_CINEMA_NAME, cinema.getCinema_name());
        values.put(COLUMN_CINEMA_ADDRESS, cinema.getCinema_address());
        values.put(COLUMN_CINEMA_TIME_SLOTS, cinema.getCinema_time_slots());
        values.put(COLUMN_CINEMA_TICKET_PRICE, cinema.getCinema_ticket_price());
        values.put(COLUMN_CINEMA_TICKET_LEFT, cinema.getTicketsLeft());

        SQLiteDatabase db = getWritableDatabase();

        db.update(TABLE_CINEMA_NAME, values, COLUMN_CINEMA_ID + "=?", new String[]{String.valueOf(cinema.getId())});
        db.close();
    }

    public int getMovieId(String movieName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int id = -1; // значение по умолчанию, если фильм не найден
        String[] columns = {COLUMN_MOVIE_ID};
        String selection = COLUMN_MOVIE_NAME + "=?";
        String[] selectionArgs = {movieName};
        Cursor cursor = db.query(TABLE_MOVIES_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return id;
    }

    //add a new product in table Products
    public Boolean checkMovieExist(Movies movie) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("Select * from movies where movie_name = ?",
                    new String[]{movie.getName()});
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public void addMovie(Movies movie) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_IMAGE, movie.getImage());
        values.put(COLUMN_MOVIE_NAME, movie.getName());
        values.put(COLUMN_DESCRIPTION, movie.getDescription());
        values.put(COLUMN_PRICE, movie.getPrice());
        values.put(COLUMN_DURATION, movie.getDuration());
        values.put(COLUMN_GENRES, movie.getGenres());
        values.put(COLUMN_TOTAL_TICKETS, movie.getTotalTickets());
        values.put(COLUMN_LEFT_TICKETS, movie.getLeftTickets());
        values.put(COLUMN_RATING, movie.getRating());
        values.put(COLUMN_YEAR_COUNTRY, movie.getYearCountry());

        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_MOVIES_NAME, null, values);
        db.close();
    }
    public void updateMovie(Movies movie) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_IMAGE, movie.getImage());
        values.put(COLUMN_MOVIE_NAME, movie.getName());
        values.put(COLUMN_DESCRIPTION, movie.getDescription());
        values.put(COLUMN_PRICE, movie.getPrice());
        values.put(COLUMN_DURATION, movie.getDuration());
        values.put(COLUMN_GENRES, movie.getGenres());
        values.put(COLUMN_LEFT_TICKETS, movie.getTotalTickets()); // было total, хз почему. вернись сюда если будут проблемы
        values.put(COLUMN_RATING, movie.getRating());
        values.put(COLUMN_YEAR_COUNTRY, movie.getYearCountry());

        SQLiteDatabase db = getWritableDatabase();

        db.update(TABLE_MOVIES_NAME, values, COLUMN_MOVIE_ID + "=?", new String[]{String.valueOf(movie.getId())});
        db.close();
    }
//    public boolean isOrderExist(int userId, String movieName) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDERS_NAME + " WHERE " + COLUMN_ORDER_USER_ID + "=? AND " + COLUMN_ORDER_MOVIE_NAME + "=?", new String[]{String.valueOf(userId), movieName});
//        boolean exist = cursor.moveToFirst();
//        cursor.close();
//        db.close();
//        return exist;
//    }

    public String getMovieImageById(int movieId) {
        String imageUrl = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_MOVIE_IMAGE};
        String selection = COLUMN_MOVIE_ID + " = ?";
        String[] selectionArgs = {String.valueOf(movieId)};
        Cursor cursor = db.query(TABLE_MOVIES_NAME, projection, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            imageUrl = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return imageUrl;
    }



    public void addOrder(int userId, int movie_id, String cinemaName, String movieName, String date, String time, int quantity, String price, int ticket_left) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ORDER_USER_ID, userId);
        values.put(COLUMN_ORDER_MOVIE_ID, movie_id);
        values.put(COLUMN_ORDER_CINEMA_NAME, cinemaName);
        values.put(COLUMN_ORDER_MOVIE_NAME, movieName);
        values.put(COLUMN_SEANS_DATE, date);
        values.put(COLUMN_SEANS_TIME, time);
        values.put(COLUMN_ORDER_QUANTITY, quantity);
        values.put(COLUMN_ORDER_PRICE, price);

        db.insert(TABLE_ORDERS_NAME, null, values);
        //decreaseLeftTickets(db, movieName, cinemaName, date, time, price, ticket_left);
        db.close();

    }


    public void deleteOrder(int orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDERS_NAME, COLUMN_ORDER_ID + "=?", new String[]{String.valueOf(orderId)});
        db.close();
    }

    public void decreaseLeftTickets(SQLiteDatabase db, String movieName, String cinemaName, String date, String time, String price, int left_tickets) {
        String query = "UPDATE " + TABLE_CINEMA_NAME + " SET " + COLUMN_CINEMA_TICKET_LEFT + " = " + COLUMN_CINEMA_TICKET_LEFT + " - 1 WHERE "
                + COLUMN_CINEMA_MOVIE_NAME + " = '" + movieName + "'"
                + " AND " + COLUMN_CINEMA_NAME + " = '" + cinemaName + "'"
                + " AND " + COLUMN_CINEMA_SESSION_DATE + " = '" + date + "'"
                + " AND " + COLUMN_CINEMA_TIME_SLOTS + " = '" + time + "'"
                + " AND " + COLUMN_CINEMA_TICKET_PRICE + " = '" + price + "'"
                + " AND " + COLUMN_CINEMA_TICKET_LEFT + " = '" + left_tickets + "'";
        db.execSQL(query);
    }


    public void setLeftTickets(String movieName, String date, List<Integer> leftTicketsList) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_CINEMA_MOVIE_NAME + " = ? AND " + COLUMN_CINEMA_SESSION_DATE + " = ?";
        String[] whereArgs = {movieName, date};
        Cursor cursor = db.query(TABLE_CINEMA_NAME, null, whereClause, whereArgs, null, null, null);
        if (cursor.moveToFirst()) {
            int i = 0;
            do {
                ContentValues values = new ContentValues();
                values.put(COLUMN_CINEMA_TICKET_LEFT, leftTicketsList.get(i));
                db.update(TABLE_CINEMA_NAME, values, COLUMN_CINEMA_ID + " = ?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_CINEMA_ID)))});
                i++;
            } while (cursor.moveToNext() && i < leftTicketsList.size());
        }
        cursor.close();
        db.close();
    }
    public List<Integer> getLeftTickets(String movieName, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Integer> leftTicketsList = new ArrayList<>();
        String query = "SELECT " + COLUMN_CINEMA_TICKET_LEFT + " FROM " + TABLE_CINEMA_NAME +
                " WHERE " + COLUMN_CINEMA_MOVIE_NAME + " = '" + movieName + "'" +
                " AND " + COLUMN_CINEMA_SESSION_DATE + " = '" + date + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int leftTickets = cursor.getInt(0);
                leftTicketsList.add(leftTickets);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return leftTicketsList;
    }


    public ArrayList<Orders> getUserOrders(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Orders> orders = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDERS_NAME + " WHERE " + COLUMN_ORDER_USER_ID + "=?", new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                Orders order = new Orders(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7),
                        cursor.getString(8)
                );
                orders.add(order);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return orders;
    }



    public Users getCurrentUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_USER_ID, COLUMN_EMAIL, COLUMN_PASSWORD, COLUMN_NAME};
        String selection = COLUMN_USER_ID + "=?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(TABLE_USERS_NAME, columns, selection, selectionArgs, null, null, null);

        Users user = null;

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String email = cursor.getString(1);
            String password = cursor.getString(2);
            String name = cursor.getString(3);

            user = new Users(email, password);
        }

        cursor.close();
        db.close();

        return user;
    }



    public ArrayList<Movies> allMovies() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * from " + TABLE_MOVIES_NAME;
        Cursor cursor = null;
        ArrayList<Movies> moviesArrayList = new ArrayList<>();

        try {
            cursor = db.rawQuery(query, null);

            // from first to last row in table products
            if (cursor.moveToFirst()) {
                do {
                    //adding the data
                    moviesArrayList.add(new Movies(
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getInt(7),
                            cursor.getInt(8),
                            cursor.getString(9),
                            cursor.getString(10)));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        //return ArrayList for easiest use
        return moviesArrayList;
    }

    public Movies GetMovieInfoByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_MOVIES_NAME + " WHERE " +
                COLUMN_MOVIE_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{name});

        Movies movieInfo = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_ID));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_IMAGE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            String price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
            String duration = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DURATION));
            String genres = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENRES));
            int total_tickets = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_TICKETS));
            int left_tickets = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_TICKETS));
            String rating = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENRES));
            String year_country = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENRES));
            movieInfo = new Movies(image, name, description, price, duration, genres, total_tickets, left_tickets, rating, year_country);
            movieInfo.setId(id);
        }

        cursor.close();
        return movieInfo;
    }

    //add a new row to the table Users
    public Boolean Registration(Users user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
//        values.put(COLUMN_IS_CURRENT_USER, bool);
        SQLiteDatabase db = getWritableDatabase();
        //db.insert returns type long
        long result = db.insert(TABLE_USERS_NAME, null, values);
        db.close();

        return result != -1;
    }

    //I use this for registration logic
    //this method check if the email already exists in the table users
    public Boolean checkRegistrationExist(Users user) {
        SQLiteDatabase db = getWritableDatabase();

        //rawQuery method ask for query and array where to put the selected info
        Cursor cursor = db.rawQuery("Select * from users where email = ?",
                new String[]{user.getEmail()});

        if (cursor.getCount() > 0)
        {
            cursor.close();
            return true;
        }
        else
        {
            cursor.close();
            return false;
        }
    }

    //Here I use this method for login purpose
    ////this method should check if the email and password was in the database
//    public Boolean checkLoginParametersExist(Users user) {
//        SQLiteDatabase db = getWritableDatabase();
//
//
//        //rawQuery method ask for query and array where to put the selected info
//        Cursor cursor = db.rawQuery("Select * from users where email = ? and password = ?",
//                new String[]{user.getEmail(), user.getPassword()});
//
//
//        return cursor.getCount() > 0;
//
//    }
    public Boolean checkLoginParametersExist(Users user) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from users where email = ? and password = ?",
                new String[]{user.getEmail(), user.getPassword()});

        Boolean result = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return result;
    }

    public String[] getUserEmail(String user_id) {
        SQLiteDatabase db = getWritableDatabase();
        String email= "";
        String[] result = new String[1];
        Cursor cursor = db.rawQuery("Select email from users where id =" + user_id ,null);

        if(cursor.moveToFirst()){
            email += cursor.getString(0);

        }else{
            email = "not found";
        }

        result[0] = email;
        cursor.close();
        return result;
    }

    public String[] getUserName(String user_id) {
        SQLiteDatabase db = getWritableDatabase();
        String name= "";
        String[] result = new String[1];
        Cursor cursor = db.rawQuery("Select name from users where id =" + user_id ,null);

        if(cursor.moveToFirst()){
            name += cursor.getString(0);

        }else{
            name = "not found";
        }

        result[0] = name;
        cursor.close();
        return result;
    }


    public String[] getUserPassword(String user_id) {
        SQLiteDatabase db = getWritableDatabase();
        String password= "";
        String[] result = new String[1];
        Cursor cursor = db.rawQuery("Select password from users where id =" + user_id ,null);

        if(cursor.moveToFirst()){
            password += cursor.getString(0);

        }else{
            password = "not found";
        }

        result[0] = password;

        cursor.close();
        return result;
    }

    public String getUserId(String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery("Select id from users where email = ? and password = ?",
                new String[]{email, password});

        if (cursor.moveToFirst()) {
            result = result + cursor.getString(0);
        } else {
            result = result + "result not found";
        }

        cursor.close();
        db.close();

        return result;
    }

    public void deleteUser(int userId){
        SQLiteDatabase db = getWritableDatabase();
        String query  = String.format("Delete from users where id = %d", userId);
        db.execSQL(query);
    }

    public void editUser(int id,String name, String email, String password){
        SQLiteDatabase db = getWritableDatabase();
        String query =
                String.format("Update users " +
                                "set name = '%s', email = '%s', password = '%s' " +
                                " where id = %d",
                        name,email,password, id);

        db.execSQL(query);

    }




    public Bitmap decodeBase64ToBitmap(String base64Image) {
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return bitmap;
    }

    public void clearDatabase(SQLiteDatabase db, String TABLE_NAME) {
        db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM " + TABLE_NAME;
//        db = this.getReadableDatabase();
//        String clearDBQuery = "DELETE FROM " + TABLE_NAME;
        db.execSQL(clearDBQuery);
    }

    public void clearTable(String table_name) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(table_name, null, null);
//        String query = "DELETE FROM " + table_name;
//        db.execSQL(query);
        db.close();
    }

}