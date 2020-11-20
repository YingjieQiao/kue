package com.example.android1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class CashierActivity extends AppCompatActivity implements View.OnClickListener {
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
    String db_key_username = MyProperties.getInstance().username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);


        DatabaseReference db_menu =  db.getReference("accounts")
                .child(db_key_username).child("menu");
        HashMap<String, HashMap<String, Double>> menu = new HashMap<>();
        db_menu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dish : snapshot.getChildren()) {
                    double price = (double) dish.child("price").getValue();
                    double ETA = (double) dish.child("ETA").getValue();
                    HashMap<String, Double> food_item = new HashMap<>();
                    food_item.put("price", price);
                    food_item.put("ETA", ETA);
                    menu.put(dish.getKey(), food_item);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        for (String dishname: menu.keySet()) { // getting the keys of the hashmap which are the dishes
            HashMap<String, Double> food_item = menu.get(dishname);//this will get the food name
            double price = food_item.get("price");//price of food
            double ETA = food_item.get("ETA");//get the eta timing based on the dishname

            // programatically create buttons


            // bind the 1 same onclick to all b

            minus_button.getID("minus")
        }
        @Override
        public void onClick(View v) {

        }

        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.increase:
                        count = count + 1;
                        //total_cost=total_cost+pricing_chicken_rice;//writing the price of the food
                        display(count);
                        display_4(total_cost);
                        break;
                    case R.id.decrease:
                        TextView chickenrice =findViewById(R.id.integer_number);
                        if(Integer.parseInt(chickenrice.getText().toString())>0){//checks whether the counter value is >=0 so that normal additionals can be done
                            count = count - 1;
                            total_cost=total_cost-pricing_chicken_rice;//writing the price of the food
                            display_4(total_cost);
                            display(count);}
                        else{//this checks whether is it = 0 anot
                            display_4(0);//this is to prevent the value of the counter to go below 0 when pressed

                        }
                        break;
                }

            }
        };

        // the following are hardcoded textvies and buttons
        TextView chickenricenumber = findViewById(R.id.integer_number);
        /*TextView duckricenumber = findViewById(R.id.integer_number_2);
        TextView sataynumber = findViewById(R.id.integer_number_3);
        TextView totalcost = findViewById(R.id.totalcostvalue);
        Button submitbutton = findViewById(R.id.submitbutton);*/
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

/*        submitbutton.setOnClickListener(new View.OnClickListener() {
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
    }*/


        public void increaseInteger(View v) {//increase the number of counter of counter of chicken rice
        count = count + 1;
        total_cost=total_cost+pricing_chicken_rice;//writing the price of the food
        display(count);
        display_4(total_cost);

    }public void decreaseInteger(View v){// the number of counter for chicken rice
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

}
