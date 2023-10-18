package com.moviebuffs;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.moviebuffs.Session.SessionManager;
import com.moviebuffs.activities.StartActivity;
import com.moviebuffs.databinding.ActivityMainBinding;
import com.moviebuffs.interfaces.UserID_Interface;
import com.moviebuffs.repository.MyDBHandler;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements UserID_Interface {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    MyDBHandler myDbHandler;
    Button exitButton;
    TextView currentDateTV;
    TextView emailForHeaderTV;
    TextView nameForHeaderTV;

    public Menu menu;


    String currentDate ;

    //use this to get user_id from the login activity
    SessionManager session;

    //this should be the retrieved data from db
    String[] emailFromDB;
    String[] nameFromDB;

    int current_user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // Enable dim. This can also be done in xml, see R.attr#backgroundDimEnabled
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new SessionManager(this);

        myDbHandler = new MyDBHandler(this,"MovieBuffs.db",null,2);
        currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        current_user_id = session.getSession();

        emailFromDB = myDbHandler.getUserEmail(String.valueOf(current_user_id));
        nameFromDB = myDbHandler.getUserName(String.valueOf(current_user_id));

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.menu_about_item, R.id.menu_movies_item, R.id.menu_tickets_item, R.id.menu_edit_user_item)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public int getCurrentUserId() {
        return session.getSession();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.

        //here i initialize the optionMenu button- exit
        exitButton = findViewById(R.id.exitButton);

        emailForHeaderTV = findViewById(R.id.emailFromCurrentLoginTV);
        currentDateTV = findViewById(R.id.currentDateFull);
        nameForHeaderTV = findViewById(R.id.fullNameFromCurrentLoginTV);

        currentDateTV.setText(currentDate);
        emailForHeaderTV.setText(emailFromDB[0]);
        nameForHeaderTV.setText(nameFromDB[0]);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_about_item) {
            Navigation.findNavController(item.getActionView()).navigate(R.id.action_global_nav_home);
        } else if (item.getItemId() == R.id.menu_movies_item) {
            Navigation.findNavController(item.getActionView()).navigate(R.id.action_global_nav_movies);
        } else if (item.getItemId() == R.id.menu_tickets_item) {
            Navigation.findNavController(item.getActionView()).navigate(R.id.action_global_nav_tickets);
        } else if (item.getItemId() == R.id.action_global_nav_edit_user) {
            Navigation.findNavController(item.getActionView()).navigate(R.id.action_global_nav_edit_user);
        } else {
            Log.wtf("Menu","Страница меню не найдена");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    //this method retrieve int id from the resource and it is generic
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}