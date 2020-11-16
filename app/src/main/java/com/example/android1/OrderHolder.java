package com.example.android1;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderHolder extends RecyclerView.ViewHolder {
    ArrayList<String> Food;
    Long OrderTime;
    Integer FinishTime;
    String OrderDate;

    public OrderHolder(@NonNull View itemView) {
        super(itemView);
    }


    void setOrder(Order order) {
        String Food_content = order.getFood().toString();
        String OrderTime_content = order.getOrderTime().toString();
        String FinishTime_content = order.getFinishTime().toString();
        String OrderDate_content = order.getOrderDate().toString();
    }
}
