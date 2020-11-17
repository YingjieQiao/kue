package com.example.android1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CookActivity extends AppCompatActivity {
    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    public static Context contextOfApplication;

    private String sharedPrefFile = "com.example.android1.mainsharedprefs";
    SharedPreferences mPreferences;
    public static String DB_KEY_USERNAME; // the child in firebase to query from
    String db_key_username;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<Order> list;
    OrderAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);
        LinearLayout cookLayout = findViewById(R.id.cookLayout);
        contextOfApplication = getApplicationContext();

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        db_key_username = mPreferences.getString(DB_KEY_USERNAME, "ERROR");
        RecyclerView recyclerView = findViewById(R.id.cook_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = getAdapter();
        recyclerView.setAdapter(adapter);
    }


    private OrderAdapter getAdapter() {
        DatabaseReference mRef = database.getReference("/accounts/"+db_key_username+"/orders");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    System.out.println(dataSnapshot.getChi);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return adapter;
    };
}