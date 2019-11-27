package com.example.imyas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imyas.Artist.Artist_Registration;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText username,password;
    Button login;
    TextView signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        login  = findViewById(R.id.login);

        signup = findViewById(R.id.signup);




        //login process
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username.setError(null);
                password.setError(null);


                String user = username.getText().toString();
                String pass = password.getText().toString();

                View focusView = null;
                boolean cancel = false;

                if (TextUtils.isEmpty(pass)) {
                    password.setError("Field Required");
                    focusView = password;
                    cancel = true;
                }

                if (TextUtils.isEmpty(user)) {
                    username.setError("Field Required");
                    focusView = username;
                    cancel = true;
                }



                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                } else {
                    // Show a progress spinner, and kick off a background task to
                    // perform the user login attempt.

                    //showProgress();
                    Toast.makeText(MainActivity.this,"hahahah",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //------------------------------



        //signup process
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.signup_dialog);
                dialog.show();


                Button user,artist;

                user = dialog.findViewById(R.id.user);
                artist = dialog.findViewById(R.id.artist);


                //reg-user//
                //------------



                //reg-artist
                artist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.hide();

                        Intent i  = new Intent(MainActivity.this, Artist_Registration.class);
                        startActivity(i);
                        finish();

                    }
                });
                //------------


            }
        });








    }
}
