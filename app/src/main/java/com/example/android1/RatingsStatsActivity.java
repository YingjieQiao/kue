package com.example.android1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RatingsStatsActivity extends AppCompatActivity {

    // declare objects
    TextView ratings;
    TextView rating;
    RatingBar ratingBar;
    DatabaseReference database;
    DatabaseReference ratersStats;
    DatabaseReference ratingsStats;
    DatabaseReference order_web;

    int totalRaters = 0;
    Double totalRating = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings_stats);

        ratings = findViewById(R.id.ratings); // text view
        rating = findViewById(R.id.rating);
        ratingBar = findViewById(R.id.ratingBar); // ratings bar

        // firebase references
        String username = AppProperties.getInstance().username;
        database = FirebaseDatabase.getInstance().getReference()
                .child("accounts").child(username);
        ratersStats = database.child("stats").child("totalRatings");
        ratingsStats = database.child("stats").child("averageRating");
        order_web =  database.child("order_web");
      
        getData();
        showRatings(); // display ratings
    }


    // set the number of stars on the RatingsBar
    private void showRatings() {
        ratingsStats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Double ratingDouble = totalRating / totalRaters;
                ratingBar.setRating(ratingDouble.floatValue());
                ratings.setText("Total Raters: " + totalRaters);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    private void getData() {
        order_web.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot order : snapshot.getChildren()) {
                    Double rating_val = Double.parseDouble(order.child("rating").getValue().toString());
                    if (rating_val != null && rating_val != 0) {
                        totalRating += rating_val;
                        totalRaters += 1;
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}