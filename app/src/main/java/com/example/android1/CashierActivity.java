package com.example.android1;
import android.os.Bundle;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class CashierActivity extends AppCompatActivity {

    TextView cost;
    TextView orderList;
    Button submitBtn;
    DatabaseReference foodMenu;
    DatabaseReference order;
    DatabaseReference webOrder;
    DatabaseReference stats;
    DatabaseReference foodStats;
    Double totalCost;
    HashMap<String, String> orderLs = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);

        // text views and btn
        cost = findViewById(R.id.totalCost);
        orderList = findViewById(R.id.orderList);
        submitBtn = findViewById(R.id.submitbutton);

        // firebase references
        order = FirebaseDatabase.getInstance().getReference().child("accounts").child("username1").child("orders");
        webOrder = FirebaseDatabase.getInstance().getReference().child("accounts").child("username1").child("order_web");
        foodMenu = FirebaseDatabase.getInstance().getReference().child("accounts").child("username1").child("foods");
        stats = FirebaseDatabase.getInstance().getReference().child("accounts").child("username1").child("stats");
        foodStats = FirebaseDatabase.getInstance().getReference().child("accounts").child("username1").child("stats").child("food");


        retrieveFoodMenu(); // query all food info from firebase

        submitBtn.setOnClickListener(v -> {
            statsUpdate(); // update statistics
            submitOrder(); // for cashier to submit an order
        });

    }

    // query all food info (list of foods, prices, eta) from firebase
    private void retrieveFoodMenu() {
        foodMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // create ArrayList for all foods, prices and their ETAs
                ArrayList<String> foodList = new ArrayList<>();
                ArrayList<Double> foodCost = new ArrayList<>();
                ArrayList<String> foodETA = new ArrayList<>();

                if (snapshot.hasChildren()) {
                    for (DataSnapshot myDataSnapshot : snapshot.getChildren()) {
                        HashMap<String, Object> foodInfo = (HashMap<String, Object>) myDataSnapshot.getValue(); // get "food" data from firebase

                        foodList.add(myDataSnapshot.getKey()); // add to the ArrayList of all foods
                        foodCost.add((Double) foodInfo.get("price")); // add to the ArrayList of all prices
                        foodETA.add(foodInfo.get("eta").toString()); // add to the ArrayList of the ETA for each food

                    }
                    // create buttons programmatically
                    createMenuButtons(foodList, foodCost, foodETA);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    // create buttons programmatically for all foods to make orders
    private void createMenuButtons(ArrayList<String> foodList, ArrayList<Double> foodCost, ArrayList<String> foodETA) {

        LinearLayout layout = findViewById(R.id.CashierActivity);
        totalCost = 0.;

        //loop through the food ArrayList to create the add/delete food buttons
        for (String food: foodList) {

            //create the "Add" & "Delete" buttons for each food to be added to the menu
            Button addFoodBtn = new Button(this);
            Button removeFoodBtn = new Button((this));
            addFoodBtn.setText("Add " + food);
            removeFoodBtn.setText("Delete " + food);
            layout.addView(addFoodBtn);
            layout.addView(removeFoodBtn);

            //add a food to the order
            addFoodBtn.setOnClickListener(v -> {
                //totalCost += foodCost.get(foodList.indexOf(food)); // calculate total cost of food

                if (orderLs.containsKey(food)) {
                    int foodCnt = Integer.parseInt(orderLs.get(food));
                    orderLs.put(food, Integer.toString(foodCnt+1));
                } else { orderLs.put(food, Integer.toString(1)); }

                orderList.setText(orderLs.toString());
                cost.setText(String.valueOf(totalCost));
            });

            //remove selected food from order
            removeFoodBtn.setOnClickListener(v -> {
                if (orderLs.containsKey(food)) {
                    int foodCnt = Integer.parseInt(orderLs.get(food));
                    if (foodCnt > 0) {
                        orderLs.put(food, Integer.toString(foodCnt - 1)); //remove 1 from the food order count
                        //totalCost -= foodCost.get(foodList.indexOf(food)); // calculate total cost of food
                    }
                }
                orderList.setText(orderLs.toString());
                cost.setText(String.valueOf(totalCost));
            });
        }
    }

    // submit a new order
    private void submitOrder() {

        // adding order details
        String receiptId = UUID.randomUUID().toString();//this will generate a random uuid for the receipt order
        orderLs.put("totalCost", Double.toString(totalCost));
        orderLs.put("receiptOrder", receiptId);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date orderDate = new Date();

        // create the order
        Order newOrder = new Order(orderLs, System.currentTimeMillis(), (long) -1, dateFormat.format(orderDate), receiptId);

        // send order to firebase
        order.push().setValue(newOrder);
        webOrder.push().setValue(newOrder);

        // inform that the order has been made
        Toast.makeText(CashierActivity.this,"Your order" + " has been submitted", Toast.LENGTH_SHORT).show();

    }


    private void statsUpdate() {

        stats.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Stats statistics = snapshot.getValue(Stats.class);
                //System.out.println("Stats: " + snapshot.getValue());
                //System.out.println("Stats Class: " + snapshot.getValue().getClass());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
