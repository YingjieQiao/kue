package com.example.android1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CashierActivity extends AppCompatActivity {
    int count=0;
    int count_2=0;
    int count_3=0;
    double pricing_chicken_rice =5.50;
    double pricing_duck_rice =4.50;
    double pricing_satay=3.50;
    double total_cost=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView chickenricenumber = findViewById(R.id.integer_number);
        TextView duckricenumber =findViewById(R.id.integer_number_2);
        TextView sataynumber=findViewById(R.id.integer_number_3);
        Button submitbutton = findViewById(R.id.submitbutton);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CashierActivity.this,StatsActivity.class);
                intent.putExtra("Chickenrice",chickenricenumber + "Chicken Rice");
                intent.putExtra("duckricenumber",duckricenumber+" Duck Rice");
                intent.putExtra("satayricenumber",sataynumber + " Satay");
                intent.putExtra("Cost",total_cost);
                startActivity(intent);

            }
        });
    }

    public void increaseInteger(View v) {
        count = count + 1;
        total_cost=total_cost+pricing_chicken_rice;//writing the price of the food
        display(count);
        display_4(total_cost);

    }public void decreaseInteger(View v){
        count = count - 1;
        total_cost=total_cost-pricing_chicken_rice;//writing the price of the food
        display_4(total_cost);
        display(count);
    }

    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number);
        displayInteger.setText("" + number);
    }
    //this is for second button onwards
    public void increaseInteger_2(View v) {
        count_2 = count_2 + 1;
        total_cost=total_cost+pricing_duck_rice;//writing the price of the food
        display_4(total_cost);

        display_2(count_2);

    }public void decreaseInteger_2(View v){
        count_2 = count_2 - 1;
        total_cost=total_cost-pricing_duck_rice;//writing the price of the food
        display_4(total_cost);
        display_2(count_2);


    }

    private void display_2(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number_2);
        displayInteger.setText("" + number);
    }
    //this is for third button onwards
    public void increaseInteger_3(View v) {
        count_3 = count_3 + 1;
        total_cost=total_cost+pricing_satay;//writing the price of the food
        display_4(total_cost);//pricing of food
        display_3(count_3);}


    public void decreaseInteger_3(View v){
        count_3 = count_3 - 1;
        total_cost=total_cost-pricing_satay;//writing the price of the food
        display_4(total_cost);

        display_3(count_3);
    }

    private void display_3(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number_3);
        displayInteger.setText("" + number);
    }
    private void display_4(double number){
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