package com.example.android1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CashierActivity extends AppCompatActivity {
    private static final String LOG_TAG =
            HomePageActivity.class.getSimpleName();
    private String sharedPrefFile = "com.example.android1.mainsharedprefs";
    SharedPreferences mPreferences;
    public static String USER; // the current user logged in the app
    public static String DB_KEY_USERNAME; // the child in firebase to query from

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        Button cash = findViewById(R.id.button_cash);

        cash.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "foodfood Button clicked!");

                String db_key_username = mPreferences.getString(DB_KEY_USERNAME, "ERROR");
                DatabaseReference restaurant_db = database.getReference("accounts")
                        .child(db_key_username);
                HashMap<String, Object> hmap = new HashMap<>();
                hmap.put("Food", "pasta");
                hmap.put("OrderTime", System.currentTimeMillis());
                restaurant_db.child("orders").push().setValue(hmap);
            }
        });
    }
}