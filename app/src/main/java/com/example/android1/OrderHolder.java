package com.example.android1;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.*;

import static android.content.Context.MODE_PRIVATE;

public class OrderHolder extends RecyclerView.ViewHolder {
    private String sharedPrefFile = "com.example.android1.mainsharedprefs";
    SharedPreferences mPreferences = CookActivity.getContextOfApplication().
            getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
    public static String DB_KEY_USERNAME; // the child in firebase to query from
    String db_key_username = mPreferences.getString(DB_KEY_USERNAME, "ERROR");
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference orders =  database.getReference("accounts")
            .child(db_key_username).child("orders");

    View mView;
    TextView textViewOrder;
    TextView textViewTime;
    CheckBox checkBoxFinished;
    String orderId;

    public OrderHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        textViewOrder = mView.findViewById(R.id.cardView_order_item);
        textViewTime = mView.findViewById(R.id.cardView_time_of_ordering);
        checkBoxFinished = mView.findViewById(R.id.checkBox_finished);
        checkBoxFinished.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    orders.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot order : snapshot.getChildren()) {
                                System.out.println(order.child("orderID").getValue());
                                if (orderId.equals(order.child("orderID").getValue())) {
                                    String postKey = order.getRef().getKey();
                                    //Long value = (Long) order.child("finishTime").getValue();
                                    assert postKey != null;
                                    orders.child(postKey).child("finishTime").setValue(System.currentTimeMillis());
                                    //System.out.println("done");
                                }

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }


    void setOrder_(Order order) {
        Long finishTime_content = order.getFinishTime();

        if (finishTime_content == -1) setOrder(order);
    }


    void setOrder(Order order) {
        String food_content = order.getFood().toString();
        System.out.println(order.getFood());
        Long orderTime_content = order.getOrderTime();
        String orderDate_content = order.getOrderDate();

        textViewOrder.setText(food_content);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        Date date = new Date(orderTime_content);
        String orderTime = simpleDateFormat.format(date);
        String dateTime = orderDate_content + "   " +orderTime;
        textViewTime.setText(dateTime);
        Log.i("ORDER_HOLDER", "An order is added to cook page");
    }


    void setId(String id){
        this.orderId = id;
        Log.i("ORDER_HOLDER", "ORDER ID: " + id);
    }
}
