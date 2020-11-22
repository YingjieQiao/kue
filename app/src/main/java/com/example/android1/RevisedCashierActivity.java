package com.example.android1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;


public class RevisedCashierActivity extends AppCompatActivity implements View.OnClickListener {
    int count=0;
    double total_cost=0;
    public FirebaseDatabase db = FirebaseDatabase.getInstance();//creating the database instance
    private static final String TAG = CashierActivity.class.getSimpleName();
    private String sharedPrefFile = "com.example.android1.mainsharedprefs";
    SharedPreferences mPreferences;
    public static String USER; // the current user logged in the app
    String db_key_username = MyProperties.getInstance().username;
    LinearLayout mlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revised_cashier);
        Button increase =findViewById(R.id.increase);
        Button decrease =findViewById(R.id.decrease);
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
        })
        ;
        int count = menu.size();//number of food in the menu
        Button[] Increasebuttonarray = new Button[count];// creating an array for the number of increase buttons depending on the number of food in the menu
        Button[] Decreasebuttonarray = new Button[count];//creating an array for the number of decrease buttons depending on the number of food in menu
        TextView[] textViewarray = new TextView[count];//creating an array for the text view so depending on how many food are there in the menu will appear
        TextView[] foodarrayname = new TextView[count];//creating an array for the food names
        TextView[] pricearray = new TextView[count]//creating an array for the prices of the food
        int count_2=0;//this will be the counter for the objects created , the int count=menu.size() is the upper bound

        for (String dishname: menu.keySet()) { // getting the keys of the hashmap which are the dishes
            HashMap<String, Double> food_item = menu.get(dishname);//this will get the food name
            double price = food_item.get("price");//price of food
            double ETA = food_item.get("ETA");//get the eta timing based on the dishname

            // programatically create buttons



            textViewarray[count_2] =new TextView(this);//creating a new object in the array that is textview
            textViewarray[count_2].setTextSize(10);//setting the textview display number text size 10 display the counter

            Increasebuttonarray[count_2] = new Button(this);//creating a new object in the array that is the decrease button
            Increasebuttonarray[count_2].setText("+");

            Decreasebuttonarray[count-2]= new Button(this);//creating a new object in the array that is the increase button
            Decreasebuttonarray[count_2].setText("-");

            foodarrayname[count_2] = new TextView(this);
            foodarrayname[count_2].setText(dishname);//name of dish
            foodarrayname[count_2].setTextSize(10);//test the size of the wording

            pricearray[count_2] =new TextView(this);
            pricearray[count_2].setText(String.valueOf(price));


            LinearLayout l = findViewById(R.id.mainlayout);//reference to the linear layout in the Activity_revised cashier xml that is nest in the first linear layout
            //l.setOrientation(LinearLayout.HORIZONTAL);//horizontal orientation

            //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);//setting out the width and height for the new layout
            //l.setLayoutParams(lp);
            LinearLayout.LayoutParams minusplustextparameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);//Creating the parameters for the the minus plus and text view layout width and height

            l.addView(Decreasebuttonarray[count_2],minusplustextparameters);// adding the view to layout l which was specified
            l.addView(textViewarray[count_2],minusplustextparameters);//adding these views to the layout l which was specified
            l.addView(Increasebuttonarray[count_2],minusplustextparameters);// adding these views to the layout
            l.addView(foodarrayname[count_2],minusplustextparameters);
            l.addView(pricearray[count_2],minusplustextparameters);

            count_2+=1;



            }


            // bind the 1 same onclick to all b

        }
        //mPreferences=getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        //SharedPreferences.Editor preferenceseditor =mPreferences.edit();

        //to edit the file name com.example.android1.mainsharedprefs
    
    //stuck
    @Override
    public void onClick(View v) {//multiple switch buttons
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
                    total_cost=2;//writing the price of the food
                    display_4(total_cost);
                    display(count);}
                else{//this checks whether is it = 0 anot
                    display_4(0);//this is to prevent the value of the counter to go below 0 when pressed

                }
                break;
        }

    }
    private void display_4(double number){//display for the total cost value
        TextView displayInteger = findViewById(R.id.totalcostvalue);
        displayInteger.setText(""+number);
    }
    private void display(int number) {//display value for the chicken rice counter
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number);
        displayInteger.setText("" + number);
    }
}