package com.example.android1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {

    private static final String LOG_TAG =
            HomePageActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Button statsButton = findViewById(R.id.button_statistics);
        Button cookButton = findViewById(R.id.button_cook);
        Button settingsButton = findViewById(R.id.button_settings);
        Button cashierButton = findViewById(R.id.button_cashier);

        statsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Stats Button clicked!");
                Intent intent = new Intent(HomePageActivity.this,
                        StatsActivity.class);
                startActivity(intent);
            }
        });

        cookButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Cook Button clicked!");
                Intent intent = new Intent(HomePageActivity.this,
                        CookActivity.class);
                startActivity(intent);
            }
        });


        settingsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Settings Button clicked!");
                Intent intent = new Intent(HomePageActivity.this,
                        SettingsActivity.class);
                startActivity(intent);
            }
        });

        cashierButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Cashier Button clicked!");
                Intent intent = new Intent(HomePageActivity.this,
                        CashierActivity.class);
                startActivity(intent);
            }
        });
    }
}