package com.example.android1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class CookActivity extends AppCompatActivity {
    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    public static Context contextOfApplication;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);
        LinearLayout cookLayout = findViewById(R.id.cookLayout);
        contextOfApplication = getApplicationContext();


        String db_key_username = AppProperties.getInstance().username;
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
                holder.checkBoxFinished.setChecked(false);
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