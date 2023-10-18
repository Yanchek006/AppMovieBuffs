package com.moviebuffs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.moviebuffs.MainActivity;
import com.moviebuffs.R;
import com.moviebuffs.Session.SessionManager;
import com.moviebuffs.entities.Users;
import com.moviebuffs.repository.MyDBHandler;

public class LoginActivity extends AppCompatActivity {


    //declare
    EditText emailET;
    EditText passwordET;
    MyDBHandler myDbHandler;
    SessionManager session;

    Button loginB;
    TextView registerTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailET = findViewById(R.id.emailEditText);
        passwordET = findViewById(R.id.passwordEditText);
        myDbHandler = new MyDBHandler(this, "MovieBuffs.db", null,2);
        loginB = findViewById(R.id.loginButton);
        registerTV = findViewById(R.id.registrationButton);
        session = new SessionManager(getApplicationContext());

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();


                Users user  = new Users(email,password);
                String id = myDbHandler.getUserId(email,password);

                if(email.equals("")||password.equals("")){
                    Toast.makeText(LoginActivity.this, "Пожалуйста, заполните все поля!", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkLoginParametersExist = myDbHandler.checkLoginParametersExist(user);
                    if(checkLoginParametersExist == true){
                        Toast.makeText(LoginActivity.this, "Вы успешно авторизовались!", Toast.LENGTH_SHORT).show();
                        session.saveSession(Integer.parseInt(id));
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Неверные данные!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }





    //this redirects to registration-activity
    public void register(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }


    //this is used for
}