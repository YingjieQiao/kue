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
    Double totalCost;
    HashMap<String, String> orderLs = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);

        cost = findViewById(R.id.totalCost);
        orderList = findViewById(R.id.orderList);
        submitBtn = findViewById(R.id.submitbutton);

        order = FirebaseDatabase.getInstance().getReference().child("accounts").child("username1").child("orders");
        webOrder = FirebaseDatabase.getInstance().getReference().child("accounts").child("username1").child("order_web");
        foodMenu = FirebaseDatabase.getInstance().getReference().child("accounts").child("username1").child("foods");

        retrieveFoodMenu();

        submitBtn.setOnClickListener(v -> {

            String receiptId = UUID.randomUUID().toString();//this will generate a random uuid for the receipt order
            orderLs.put("totalCost", Double.toString(totalCost));
            orderLs.put("receiptOrder", receiptId);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date orderDate = new Date();
            Order newOrder = new Order(orderLs, System.currentTimeMillis(), (long) -1, dateFormat.format(orderDate), receiptId, (double) 0);
            order.push().setValue(newOrder);
            webOrder.push().setValue(newOrder);

            Toast.makeText(CashierActivity.this,"Your order" + " has been submitted", Toast.LENGTH_SHORT).show();

            // reset order
            totalCost = 0.;
            orderLs.clear();
            cost.setText("0");
            orderList.setText("No Order");

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
                    for (DataSnapshot myDataSnapshot : snapshot.getChildren()) {
                        HashMap<String, Object> foodInfo = (HashMap<String, Object>) myDataSnapshot.getValue();

                        foodList.add(myDataSnapshot.getKey());
                        foodCost.add((Double) foodInfo.get("price"));
                        foodETA.add(foodInfo.get("eta").toString());

                    }
                    createMenuButtons(foodList, foodCost, foodETA);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    private void createMenuButtons(ArrayList<String> foodList, ArrayList<Double> foodCost, ArrayList<String> foodETA) {

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
                } else { orderLs.put(food, Integer.toString(1)); }

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
