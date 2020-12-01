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
    DatabaseReference db;
    DatabaseReference ratersStats;
    DatabaseReference ratingsStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings_stats);

        ratings = findViewById(R.id.ratings); // text view
        rating = findViewById(R.id.rating);
        ratingBar = findViewById(R.id.ratingBar); // ratings bar

        // firebase references
        db = FirebaseDatabase.getInstance().getReference().child("accounts").child("username1").child("stats");
        ratersStats = db.child("totalRatings");
        ratingsStats = db.child("averageRating");

        totalRaters(); // display total no. of raters
        setRatings(); // display ratings
    }

    // query the total no. of customers who rated
    private void totalRaters() {
        ratersStats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { ratings.setText("Total Raters: " + snapshot.getValue()); }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    // set the number of stars on the RatingsBar
    private void setRatings() {
        ratingsStats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rating.setText("Restaurant Ratings: " + snapshot.getValue());
                ratingBar.setRating(((Long) snapshot.getValue()).floatValue());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }
}