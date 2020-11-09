package com.example.android1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Bundle foodstats =getIntent().getExtras();//this will collect any intent data that is passed into this activity
        if(foodstats==null){
            return;
        }
        // so from here i have gotten the key values pairs , nameley the satay chickenrice duckrice keys and their values like how many food


    }
}