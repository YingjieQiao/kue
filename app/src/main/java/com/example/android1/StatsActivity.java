package com.example.android1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        findViewById(R.id.foodDetails).setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), FoodStatActivity.class)));
        findViewById(R.id.customerTraffic).setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CustomerStatsActivity.class)));
        findViewById(R.id.ratings).setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RatingsStatsActivity.class)));
    }

}
