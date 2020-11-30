package com.example.android1;
import android.content.Intent;
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
    Double totalCost;
    HashMap<String, String> orderLs = new HashMap<>();
    Double ETA = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);

        cost = findViewById(R.id.totalCost);
        orderList = findViewById(R.id.orderList);
        submitBtn = findViewById(R.id.submitbutton);
        String username = AppProperties.getInstance().username;
        order = FirebaseDatabase.getInstance().getReference().child("accounts").child(username).child("orders");
        webOrder = FirebaseDatabase.getInstance().getReference().child("accounts").child(username).child("order_web");
        foodMenu = FirebaseDatabase.getInstance().getReference().child("accounts").child(username).child("menu");

        retrieveFoodMenu();

        submitBtn.setOnClickListener(v -> {

            String receiptId = UUID.randomUUID().toString();//this will generate a random uuid for the receipt order
            orderLs.put("totalCost", Double.toString(totalCost));
            orderLs.put("receiptOrder", receiptId);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date orderDate = new Date();

            /*foodMenu.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dish : snapshot.getChildren()) {
                        String dishname = (String) dish.child("name").getValue();
                        System.out.println(dishname);
                        Double eta = Double.parseDouble(dish.child("price").getValue().toString());
                        if (orderLs.containsKey(dishname)) {
                            int quantity = Integer.parseInt(orderLs.get(dishname));
                            ETA += quantity * eta;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });*/

            Order newOrder = new Order(orderLs, System.currentTimeMillis(), (long) -1,
                    dateFormat.format(orderDate), receiptId, (double) 0, ETA);
            order.push().setValue(newOrder);
            webOrder.push().setValue(newOrder);

            Toast.makeText(CashierActivity.this,
                    "Your order" + " has been submitted", Toast.LENGTH_SHORT).show();

            // reset order
            totalCost = 0.;
            orderLs.clear();
            cost.setText("0");
            orderList.setText("No Order");

            String url = Utils.generateURL(username, receiptId);
            Intent qrPageIntent = new Intent(this, QRCodeActivity.class);
            qrPageIntent.putExtra(QRCodeActivity.key, url);
            startActivity(qrPageIntent);
        });

    }

    private void retrieveFoodMenu() {
        foodMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<String> foodList = new ArrayList<>();
                ArrayList<Double> foodCost = new ArrayList<>();
                ArrayList<String> foodETA = new ArrayList<>();

                if (snapshot.hasChildren()) {
                    for (DataSnapshot dish : snapshot.getChildren()) {
                        Double price = Double.parseDouble(dish.child("price").getValue().toString());
                        Double eta = Double.parseDouble(dish.child("price").getValue().toString());

                        foodList.add((String) dish.child("name").getValue());
                        foodCost.add(price);
                        foodETA.add(String.valueOf(eta));

                    }
                    createMenuButtons(foodList, foodCost, foodETA);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    private void createMenuButtons(ArrayList<String> foodList, ArrayList<Double> foodCost,
                                   ArrayList<String> foodETA) {

        LinearLayout layout = findViewById(R.id.CashierActivity);
        totalCost = 0.;

        for (String food: foodList) {
            Button addFoodBtn = new Button(this);
            addFoodBtn.setText("Add " + food);
            addFoodBtn.setId(foodList.indexOf(food)+10); // index + 10 is the id for the button to add food
            layout.addView(addFoodBtn);

            addFoodBtn.setOnClickListener(v -> {
                totalCost += foodCost.get(foodList.indexOf(food));

                if (orderLs.containsKey(food)) {
                    int foodCnt = Integer.parseInt(orderLs.get(food));
                    orderLs.put(food, Integer.toString(foodCnt+1));
                    ETA += Double.parseDouble(foodETA.get(foodList.indexOf(food)));
                } else {
                    orderLs.put(food, Integer.toString(1));
                    ETA += Double.parseDouble(foodETA.get(foodList.indexOf(food)));
                }

                orderList.setText(orderLs.toString());
                cost.setText(String.valueOf(totalCost));
            });

            Button removeFoodBtn = new Button((this));
            removeFoodBtn.setText("Delete " + food);
            removeFoodBtn.setId((foodList.indexOf(food))); //index is the id for the button to remove food
            layout.addView(removeFoodBtn);

            removeFoodBtn.setOnClickListener(v -> {
                if (orderLs.containsKey(food)) {
                    int foodCnt = Integer.parseInt(orderLs.get(food));
                    if (foodCnt > 0) {
                        orderLs.put(food, Integer.toString(foodCnt - 1));
                        totalCost -= foodCost.get(foodList.indexOf(food));
                    }
                }
                orderList.setText(orderLs.toString());
                cost.setText(String.valueOf(totalCost));
            });
        }
    }
}
