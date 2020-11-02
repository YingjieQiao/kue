package com.example.android1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showStats(View view) {
        Log.d(LOG_TAG, "Stats Button clicked!");
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);
    }

    public void showCook(View view) {
        Log.d(LOG_TAG, "Cook Button clicked!");
        Intent intent = new Intent(this, CookActivity.class);
        startActivity(intent);
    }

    public void showSettings(View view) {
    }

    public void showCashier(View view) {
        Log.d(LOG_TAG, "Cashier Button clicked!");
        Intent intent = new Intent(this, CashierActivity.class);
        startActivity(intent);
    }
}