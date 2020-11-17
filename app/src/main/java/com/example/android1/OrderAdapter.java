package com.example.android1;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderHolder>{
    List<Order> orders;
    Context context;

    public OrderAdapter(List<Order> list, Context context) {
        this.orders = list;
        this.context = context;
    }

    public OrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.cardview_orders, viewGroup, false);

        Log.i("ADAPTER", "Order onCreateViewHolder is called");
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder orderHolder, int position) {
        Log.i("ADAPTER", "Order onBindViewHolder is called");
        Order order = orders.get(position);
        orderHolder.setOrder(order);
        orderHolder.setId(order.getOrderID());
    }

    @Override
    public int getItemCount() {
        int arr = 0;
        try {
            arr = orders.size();
        } catch (Exception e) {
        }
        return arr;
    }
}
