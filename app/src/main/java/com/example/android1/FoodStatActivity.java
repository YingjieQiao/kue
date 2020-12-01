package com.example.android1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodStatActivity extends AppCompatActivity {

    // declare objects
    TextView dailyRevenue;
    BarChart foodsChart;
    DatabaseReference database;
    DatabaseReference revenueStats;
    DatabaseReference foodStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_stat);

        // text view & bar chart
        dailyRevenue = findViewById(R.id.totalRevenue);
        foodsChart = findViewById(R.id.foodStats);

        // firebase references
        String username = AppProperties.getInstance().username;
        database = FirebaseDatabase.getInstance().getReference().
                child("accounts").child(username).child("stats");
        revenueStats = database.child("dailyRevenue");
        foodStats = database.child("food");

        retrieveRevenueData();  //query the daily revenue and display in text view
        retrieveFoodData(); // query the food statistics from firebase and plot bar chart
    }

    // query the total daily revenue
    private void retrieveRevenueData() {
        revenueStats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dailyRevenue.setText("Today's Revenue: $" + snapshot.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    //read realtime firebase data
    private void retrieveFoodData() {
        foodStats.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> foodNames = new ArrayList<>(); //ArrayList of all the food
                ArrayList<BarEntry> foodArr = new ArrayList<>(); //ArrayList of all the number of purchases for each food to plot bar chart

                // iteratively query and save each entry of all food into 2 ArrayLists
                if (snapshot.hasChildren()) {
                    float count = 0;
                    for(DataSnapshot myDataSnapshot : snapshot.getChildren()) {
                        System.out.println("Values: " + myDataSnapshot.getValue());
                        System.out.println("Keys: " + myDataSnapshot.getKey());

                        foodArr.add(new BarEntry(count,  ((Long) myDataSnapshot.getValue()).floatValue())); //add the no. of purchases of each food into ArrayList
                        foodNames.add(myDataSnapshot.getKey()); //add the name of each food into ArrayList

                        count ++;
                    }
                    System.out.println(foodArr);

                    //plot the bar chart
                    createBarChart(foodArr, foodNames.toArray(new String[foodNames.size()]));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    //plot bar chart
    private void createBarChart(ArrayList<BarEntry> foodArr, String[] foodNames) {

        BarDataSet foodDataSet = new BarDataSet(foodArr, "Types of Food");  // set the bar chart data as foodArr (no. of purchases of each food)

        // pie chart UI formatting
        foodDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        foodDataSet.setValueTextColor(Color.BLACK);
        foodDataSet.setValueTextSize(12f);
        XAxis xAxis = foodsChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        foodsChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(foodNames)); // set the x-axis labels as the names of all the foods, foodNames

        BarData foodData = new BarData(foodDataSet);

        foodsChart.setFitBars(true);
        foodsChart.setData(foodData);
        foodsChart.getDescription().setEnabled(false);
        foodsChart.getLegend().setEnabled(false);
        foodsChart.animateY(1000);

    }
}