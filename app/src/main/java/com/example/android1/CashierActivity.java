package com.example.android1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CashierActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView chickenricenumber = findViewById(R.id.integer_number);
        TextView duckricenumber = findViewById(R.id.integer_number_2);
        TextView sataynumber = findViewById(R.id.integer_number_3);
        TextView totalcost = findViewById(R.id.totalcostvalue);
        Button submitbutton = findViewById(R.id.submitbutton);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference restaurant_db = db.getReference("accounts");
                Orders order=new Orders("Order " + numberoforders,chickenricenumber.getText().toString(),duckricenumber.getText().toString(),sataynumber.getText().toString(),totalcost.getText().toString());
                restaurant_db.child("orders").push().setValue(order);
            }
        });
    }


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


/*    public void Submittodatabase(View view) {
        TextView displayInteger=findViewById(R.id.totalcostvalue);
        TextView displaychickenrice = findViewById(R.id.integer_number);
        TextView displayduckrice = findViewById(R.id.integer_number_2);
        TextView displaysatay = findViewById(R.id.integer_number_3);
        displaychickenrice.setText(0);
        displayduckrice.setText(0);
        displaysatay.setText(0);
        displayInteger.display_4(double
        )
//        count=0;*/
//        count_2=0;
//        count_3=0;
//        total_cost=0;
}
