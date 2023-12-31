package com.moviebuffs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.moviebuffs.R;
import com.moviebuffs.Session.SessionManager;
import com.moviebuffs.activities.LoginActivity;
import com.moviebuffs.activities.StartActivity;
import com.moviebuffs.repository.MyDBHandler;


public class EditUserFragment extends Fragment {


    MyDBHandler myDbHandler;
    SessionManager session;

    TextView currentName;
    TextView currentEmail;
    TextView currentPassword;

    EditText editName;
    EditText editEmail;
    EditText editPassword;

    Button editButton;
    Button deleteButton;

    int current_user_id;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        session = new SessionManager(getContext());
        current_user_id = session.getSession();
        myDbHandler = new MyDBHandler(getContext(),null,null,1);

        return inflater.inflate(R.layout.fragment_edit_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        currentName = view.findViewById(R.id.currentName);
        currentEmail = view.findViewById(R.id.currentEmail);
        currentPassword = view.findViewById(R.id.currentPassword);
        deleteButton = view.findViewById(R.id.deleteButton);
        editButton = view.findViewById(R.id.editButton);
        editName = view.findViewById(R.id.personNameET);
        editEmail =view.findViewById(R.id.emailET);
        editPassword = view.findViewById(R.id.passwordET);

        String[] nameFromDB = myDbHandler.getUserName(String.valueOf(current_user_id));
        String[] emailFromDB = myDbHandler.getUserEmail(String.valueOf(current_user_id));
        String[] passwordFromDB = myDbHandler.getUserPassword(String.valueOf(current_user_id));

        //using this for creating a field with number of * stars from passwordCount symbols
        String passwordSequence = passwordFromDB[0];
        int passwordCount = passwordSequence.length();
        String points = "";
        for (int i = 0; i<passwordCount; i++ ){
            points += "●";
        }



        currentName.setText("Ваше имя: " + nameFromDB[0]);
        currentEmail.setText("Ваш e-mail: " + emailFromDB[0]);
        currentPassword.setText("Ваш пароль: " + points);



        super.onViewCreated(view, savedInstanceState);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDbHandler.deleteUser(current_user_id);
                Toast.makeText(getActivity(), "Успешное удаление аккаунта!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), StartActivity.class));
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //working with first variation and the second one
                String newName = String.valueOf(editName.getText());
                String newEmail = editEmail.getText().toString();
                String newPassword = editPassword.getText().toString();

                if(newPassword.equals("")&&newName.equals("")&&newEmail.equals("")){
                    Toast.makeText(getContext(),
                            "Вы не ввели никаких данных для изменения!", Toast.LENGTH_SHORT).show();
                }else{
                    if(newName.equals("")){
                        newName = nameFromDB[0];
                    }
                    if (newEmail.equals("")){
                        newEmail = emailFromDB[0];
                    }
                    if(newPassword.equals("")){
                        newPassword = passwordFromDB[0];
                    }
                    myDbHandler.editUser(current_user_id, newName, newEmail, newPassword);
                    Toast.makeText(getActivity(),
                            "Успешное изменение! Пожалуйста, войдите в систему с измененными данными!",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });


    }
}