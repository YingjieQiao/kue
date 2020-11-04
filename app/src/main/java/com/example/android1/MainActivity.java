package com.example.android1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.button_login);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Login Button clicked!");
                if (checkPassword(username.getText().toString(),
                        password.getText().toString())) {
                    Intent intent = new Intent(MainActivity.this,
                            HomePageActivity.class);
                    startActivity(intent);
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference accounts = database.getReference("accounts");
                    System.out.println(accounts);
                    Toast.makeText(getApplicationContext(), "Wrong username or password",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private boolean checkPassword(String username, String password) {
        // query the db to get the data
        // create the diagram
        // send to frontend
        return false;
    }
}