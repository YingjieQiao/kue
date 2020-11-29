package com.example.android1;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.PieEntry;
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
    long totalCost;
    HashMap<String, String> orderLs = new HashMap<>();



    /*
    int count=0;
    int count_2=0;
    int count_3=0;
    double pricing_chicken_rice =5.50;
    double pricing_duck_rice =4.50;
    double pricing_satay=3.50;
    double total_cost=0;
    int numberoforders=0;
    public FirebaseDatabase db = FirebaseDatabase.getInstance();//creating the database instance
    private static final String TAG = CashierActivity.class.getSimpleName();
    private String sharedPrefFile = "com.example.android1.mainsharedprefs";
    SharedPreferences mPreferences;
    public static String USER; // the current user logged in the app
    public static String DB_KEY_USERNAME; // the child in firebase to query from


     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);

        cost = findViewById(R.id.total_cost);
        orderList = findViewById(R.id.orderList);
        submitBtn = findViewById(R.id.submitbutton);

        order = FirebaseDatabase.getInstance().getReference().child("accounts").child("username1").child("orders");
        webOrder = FirebaseDatabase.getInstance().getReference().child("accounts").child("username1").child("order_web");
        foodMenu = FirebaseDatabase.getInstance().getReference().child("accounts").child("username1").child("foods");

        retrieveFoodMenu();

        /*
        setContentView(R.layout.activity_cashier);
        TextView chickenricenumber = findViewById(R.id.integer_number);
        TextView duckricenumber = findViewById(R.id.integer_number_2);
        TextView sataynumber = findViewById(R.id.integer_number_3);
        TextView totalcost = findViewById(R.id.totalcostvalue);
        Button submitbutton = findViewById(R.id.submitbutton);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Order submitted");
                // String db_key_username = mPreferences.getString(DB_KEY_USERNAME, "ERROR");
                String db_key_username = MyProperties.getInstance().username;
                DatabaseReference restaurant_db = db.getReference("accounts").child(db_key_username);//getting the path towards where to place the data
                HashMap<String, String> foodorder = new HashMap<String,String>();
                String receiptid = UUID.randomUUID().toString();//this will generate a random uuid for the receipt order
                //Long receiptid = System.currentTimeMillis()/2;
                foodorder.put("ChickenRice",chickenricenumber.getText().toString());
                foodorder.put("DuckRice",duckricenumber.getText().toString());
                foodorder.put("Satay",sataynumber.getText().toString());
                foodorder.put("TotalCost",totalcost.getText().toString());
                //foodorder.put("receiptorder", String.valueOf(receiptid));
                DateFormat df = new SimpleDateFormat("dd/MM/yy");
                Date dateobj = new Date();

                Order order = new Order(foodorder, System.currentTimeMillis(), (long) -1,
                        df.format(dateobj), receiptid);
                restaurant_db.child("orders").push().setValue(order);
                restaurant_db.child("order_web").push().setValue(order);
                Toast.makeText(CashierActivity.this,"Order " + receiptid + " has been submitted", Toast.LENGTH_SHORT).show();
            }
        });


         */

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String receiptId = UUID.randomUUID().toString();//this will generate a random uuid for the receipt order
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                Date orderDate = new Date();
                Order newOrder = new Order(orderLs, System.currentTimeMillis(), (long) -1, dateFormat.format(orderDate), receiptId);
                order.push().setValue(newOrder);
                webOrder.push().setValue(newOrder);

                Toast.makeText(CashierActivity.this,"Order " + receiptId + " has been submitted", Toast.LENGTH_SHORT).show();

                // String db_key_username = mPreferences.getString(DB_KEY_USERNAME, "ERROR");
                //String db_key_username = MyProperties.getInstance().username;
                //DatabaseReference restaurant_db = db.getReference("accounts").child(db_key_username);//getting the path towards where to place the data
                //Long receiptid = System.currentTimeMillis()/2;
                //foodorder.put("ChickenRice",chickenricenumber.getText().toString());
                //foodorder.put("DuckRice",duckricenumber.getText().toString());
                //foodorder.put("Satay",sataynumber.getText().toString());
                //foodorder.put("TotalCost",totalcost.getText().toString());
                //foodorder.put("receiptorder", String.valueOf(receiptid));
                //DateFormat df = new SimpleDateFormat("dd/MM/yy");
                //Date dateobj = new Date();

                //Order order = new Order(foodorder, System.currentTimeMillis(), (long) -1,
                //        df.format(dateobj), receiptid);
                //restaurant_db.child("orders").push().setValue(order);
                //restaurant_db.child("order_web").push().setValue(order);
                //Toast.makeText(CashierActivity.this,"Order " + receiptid + " has been submitted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void retrieveFoodMenu() {
        foodMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<String> foodList = new ArrayList<>();
                ArrayList<Long> foodCost = new ArrayList<>();
                ArrayList<String> foodETA = new ArrayList<>();


                // iteratively query customer traffic data in the "customerTraffic" branch on firebase
                if (snapshot.hasChildren()) {
                    for (DataSnapshot myDataSnapshot : snapshot.getChildren()) {
                        HashMap<String, Long> foodInfo = (HashMap<String, Long>) myDataSnapshot.getValue();

                        System.out.println("Values: " + myDataSnapshot.getValue());
                        System.out.println("Keys: " + myDataSnapshot.getKey());

                        // add each entry of data (e.g. "10am-2pm" : 30) to an ArrayList
                        foodList.add(myDataSnapshot.getKey());
                        foodCost.add(foodInfo.get("price"));
                        foodETA.add(foodInfo.get("eta").toString());

                    }
                    System.out.println(foodList);
                    System.out.println(foodCost);
                    System.out.println(foodETA);

                    // add all buttons based on the list of food
                    createMenuButtons(foodList, foodCost, foodETA);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    private void createMenuButtons(ArrayList<String> foodList, ArrayList<Long> foodCost, ArrayList<String> foodETA) {

        LinearLayout layout = findViewById(R.id.CashierActivity);

        for (String food: foodList) {
            Button addFoodBtn = new Button(this);
            addFoodBtn.setText("Add " + food);
            addFoodBtn.setId(foodList.indexOf(food)+10); // index + 10 is the id for the button to add food
            layout.addView(addFoodBtn);

            addFoodBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (orderLs.containsKey(food)) {

                        //double value = (double) foodCost.get(0);
                        //double value1 = value.doubleValue();
                        //totalCost += foodCost.get(foodList.indexOf(food)).doubleValue();

                        int foodCnt = Integer.parseInt(orderLs.get(food));
                        orderLs.put(food, Integer.toString(foodCnt+1));
                    } else {
                        orderLs.put(food, Integer.toString(1));

                    }
                    orderList.setText(orderLs.toString());
                }
            });

            Button removeFoodBtn = new Button((this));
            removeFoodBtn.setText("Delete " + food);
            removeFoodBtn.setId((foodList.indexOf(food))); //index is the id for the button to remove food
            layout.addView(removeFoodBtn);

            removeFoodBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (orderLs.containsKey(food)) {
                        int foodCnt = Integer.parseInt(orderLs.get(food));
                        if (foodCnt > 0) {
                            orderLs.put(food, Integer.toString(foodCnt - 1));
                        }

                        //totalCost += (double) foodCost.get(foodList.indexOf(food));
                    }
                    orderList.setText(orderLs.toString());
                }
            });

        }

    }

    /*
    public void increaseInteger(View v) {//increase the number of counter of counter of chicken rice
        count = count + 1;
        total_cost=total_cost+pricing_chicken_rice;//writing the price of the food
        display(count);
        display_4(total_cost);

    }
    public void decreaseInteger(View v){// the number of counter for chicken rice
        TextView chickenrice =findViewById(R.id.integer_number);
        if(Integer.parseInt(chickenrice.getText().toString())>0){//checks whether the counter value is >=0 so that normal additionals can be done
        count = count - 1;
        total_cost=total_cost-pricing_chicken_rice;//writing the price of the food
        display_4(total_cost);
        display(count);}
        else{//this checks whether is it = 0 anot
            display_4(0);//this is to prevent the value of the counter to go below 0 when pressed

        }
    }

    private void display(int number) {//display value for the chicken rice counter
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number);
        displayInteger.setText("" + number);
    }
    //this is for second button onwards
    public void increaseInteger_2(View v) {//increase the counter for the duck rice
        count_2 = count_2 + 1;
        total_cost=total_cost+pricing_duck_rice;//writing the price of the food
        display_4(total_cost);

        display_2(count_2);

    }public void decreaseInteger_2(View v){//drecrease the counter for the duck rice
        TextView duckrice = findViewById(R.id.integer_number_2);//find id of the duck rice
        if(Integer.parseInt(duckrice.getText().toString())>0){
        count_2 = count_2 - 1;
        total_cost=total_cost-pricing_duck_rice;//writing the price of the food
        display_4(total_cost);
        display_2(count_2);}
        else{
            display_2(0);//set this counter to 0 if try to decrease below 0
        }


    }

    private void display_2(int number) {//display for the duck rice
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number_2);
        displayInteger.setText("" + number);
    }
    //this is for third button onwards
    public void increaseInteger_3(View v) {//increase counter for the satay
        count_3 = count_3 + 1;
        total_cost=total_cost+pricing_satay;//writing the price of the food
        display_4(total_cost);//pricing of food
        display_3(count_3);}


    public void decreaseInteger_3(View v){//decrease for the counter for satay
        TextView satay = findViewById(R.id.integer_number_3);
        if(Integer.parseInt(satay.getText().toString())>0){
        count_3 = count_3 - 1;
        total_cost=total_cost-pricing_satay;//writing the price of the food
        display_4(total_cost);

        display_3(count_3);}else{//preventing the counter for satay to go down below 0
            display_3(0);
        }
    }

    private void display_3(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number_3);
        displayInteger.setText("" + number);
    }
    private void display_4(double number){//display for the total cost value
        TextView displayInteger = findViewById(R.id.totalcostvalue);
        displayInteger.setText(""+number);
    }


     */

}
