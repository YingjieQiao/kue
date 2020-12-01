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
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;
import org.threeten.bp.LocalTime;


public class CashierActivity extends AppCompatActivity {

    TextView cost, orderList;
    Button submitBtn;
    DatabaseReference db, foodMenu, order, webOrder, stats;
    HashMap<String, String> orderLs = new HashMap<>();
    Double totalCost;
    Double ETA = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidThreeTen.init(this);

        //button & textviews
        setContentView(R.layout.activity_cashier);
        cost = findViewById(R.id.totalCost);
        orderList = findViewById(R.id.orderList);
        submitBtn = findViewById(R.id.submitbutton);

        String username = AppProperties.getInstance().username;
        db = FirebaseDatabase.getInstance().getReference().child("accounts").child(username);
        order = db.child("orders");
        webOrder = db.child("order_web");
        foodMenu = db.child("menu");
        stats = db.child("stats");

        retrieveFoodMenu();

        submitBtn.setOnClickListener(v -> {
            String receiptId = UUID.randomUUID().toString(); // generate order receipt ID

            submitOrder(receiptId); // submit an order
            statsUpdate(); // update restaurant statistics based on new orders
            generateQRCode(username, receiptId); // generate QR code for customers check orders on website
        });

    }

    // get all dishes from firebase
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
                        Double eta = Double.parseDouble(dish.child("eta").getValue().toString());

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

        //loop through food ArrayList to create food buttons
        for (String food: foodList) {

            //create "Add" & "Delete" food buttons
            Button addFoodBtn = new Button(this);
            Button removeFoodBtn = new Button((this));

            addFoodBtn.setText("Add " + food);
            removeFoodBtn.setText("Delete " + food);

            layout.addView(addFoodBtn);
            layout.addView(removeFoodBtn);

            //add food to order
            addFoodBtn.setOnClickListener(v -> {
                totalCost += foodCost.get(foodList.indexOf(food));
                ETA += Double.parseDouble(foodETA.get(foodList.indexOf(food)));

                if (orderLs.containsKey(food)) {
                    int foodCnt = Integer.parseInt(orderLs.get(food));
                    orderLs.put(food, Integer.toString(foodCnt+1));
                } else {
                    orderLs.put(food, Integer.toString(1));
                }

                orderList.setText(orderLs.toString());
                cost.setText(String.valueOf(totalCost));
            });

            //remove food from order
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

    // submit a new order
    private void submitOrder(String receiptId) {

        // adding order details
        orderLs.put("totalCost", Double.toString(totalCost)); //add total cost into order
        orderLs.put("receiptOrder", receiptId); //add receipt ID into order
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date orderDate = new Date();
        Order newOrder = new Order(orderLs, System.currentTimeMillis(), (long) -1,
                dateFormat.format(orderDate), receiptId, (double) 0, ETA); // create new order

        //send order to firebase
        order.push().setValue(newOrder);
        webOrder.push().setValue(newOrder);

        Toast.makeText(CashierActivity.this, "Your order" + " has been submitted", Toast.LENGTH_SHORT).show(); // notify cashiers an order has been made
    }


    // update restaurant statistics
    private void statsUpdate() {
        stats.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Stats statistics = snapshot.getValue(Stats.class); // retrieve existing statistics

                //updateRatingStats(statistics); //retrieve all ratings & update stats
                updateDailyRevenue(statistics); // update daily revenue stats
                updateFoodStats(statistics); // update food stats
                updateCustomerTraffic(statistics); // update customer traffic stats
                stats.setValue(statistics); // update firebase

                resetOrder(); //reset order queue
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    //retrieve all ratings & update stats
    private void updateRatingStats(Stats statistics) {
        webOrder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long ratings = 0L;
                Long count = 0L;

                if (snapshot.hasChildren()) {
                    for (DataSnapshot rating : snapshot.getChildren()) {
                        ratings += (Long) rating.child("rating").getValue();
                        count ++;
                    }
                }
                statistics.averageRating = ((Long)(ratings/count)).doubleValue();
                statistics.totalRatings = count;
                stats.setValue(statistics);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }


    //update daily revenue
    private void updateDailyRevenue(Stats statistics) {
        statistics.dailyRevenue = statistics.dailyRevenue + totalCost;
    }

    //update food stats
    private void updateFoodStats(Stats statistics) {
        orderLs.remove("receiptOrder");
        orderLs.remove("totalCost");

        for (HashMap.Entry<String, String> o : orderLs.entrySet()) {
            String key = o.getKey();
            if (statistics.food.containsKey(key)) {
                int orderLs_value = Integer.parseInt(o.getValue());
                Long foods_value = statistics.food.get(key);
                Long totalCnt = orderLs_value + foods_value;
                statistics.food.put(key, totalCnt);
            } else {
                statistics.food.put(key, Long.parseLong(o.getValue()));
            }
        }
    }


    // update customer traffic stats
    private void updateCustomerTraffic(Stats statistics) {

        // declare different timings to check for customer traffic
        LocalTime _10am = LocalTime.parse("10:00:00.00");
        LocalTime _2pm = LocalTime.parse("14:00:00.00");
        LocalTime _6pm = LocalTime.parse("18:00:00.00");
        LocalTime _10pm = LocalTime.parse("22:00:00.00");

        // get the current time
        LocalTime time = LocalTime.now();

        // update customer traffic based on the orders at different timings
        if (time.isAfter(_10am) && time.isBefore(_2pm)) { statistics.customerTraffic.put("10am-2pm", statistics.customerTraffic.get("10am-2pm") + 1); }
        if (time.isAfter(_2pm) && time.isBefore(_6pm)) { statistics.customerTraffic.put("2pm-6pm", statistics.customerTraffic.get("2pm-6pm") + 1); }
        if (time.isAfter(_6pm) && time.isBefore(_10pm)) { statistics.customerTraffic.put("6pm-10pm", statistics.customerTraffic.get("6pm-10pm") + 1); }

        statistics.totalCustomers ++;

    }

    // reset order
    private void resetOrder() {
        totalCost = 0.; // set order cost = 0
        orderLs.clear(); // clear order list
        cost.setText("0"); // set cost TextView to be "0"
        orderList.setText("No Order"); // set order list TextView to be "No Order"
    }

    // generate a QR code for customers to view their order on website
    private void generateQRCode(String username, String receiptId) {
        String url = Utils.generateURL(username, receiptId);
        Intent qrPageIntent = new Intent(this, QRCodeActivity.class);
        qrPageIntent.putExtra(QRCodeActivity.key, url);
        startActivity(qrPageIntent);
    }
}
