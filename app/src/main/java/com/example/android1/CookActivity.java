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

import java.util.HashMap;

public class CookActivity extends AppCompatActivity {
    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    public static Context contextOfApplication;

    private String sharedPrefFile = "com.example.android1.mainsharedprefs";
    SharedPreferences mPreferences;
    public static String DB_KEY_USERNAME; // the child in firebase to query from
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    FirebaseRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);
        LinearLayout cookLayout = findViewById(R.id.cookLayout);
        contextOfApplication = getApplicationContext();

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        //String db_key_username = mPreferences.getString(DB_KEY_USERNAME, "ERROR");
        String db_key_username = MyProperties.getInstance().username;
        Query orders = database.getReference().child("/accounts/"+db_key_username+"/orders");
        System.out.println(orders);
        RecyclerView recyclerView = findViewById(R.id.cook_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Order> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Order>()
                .setQuery(orders, Order.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Order, OrderHolder>(firebaseRecyclerOptions) {

            @NonNull
            @Override
            public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.cardview_orders, parent, false);

                Log.i("ADAPTER", "Order onCreateViewHolder is called");
                return new OrderHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderHolder holder, int position, @NonNull Order model) {
                Log.i("ADAPTER", "Order onBindViewHolder is called");
                holder.setOrder(model);
                holder.setId(model.getOrderID());
            }
        };
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}