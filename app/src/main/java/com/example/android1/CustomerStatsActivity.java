package com.example.android1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerStatsActivity extends AppCompatActivity {

    // declare objects
    TextView dailyCustomers;
    PieChart customerTrafficChart;
    DatabaseReference database;
    DatabaseReference customersStats;
    DatabaseReference trafficStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_stats);

        // text view & pie chart
        dailyCustomers = findViewById(R.id.totalCustomers);
        customerTrafficChart = findViewById(R.id.customerTraffic);

        // database references
        database = FirebaseDatabase.getInstance().getReference().child("accounts").child("username1").child("stats");
        customersStats = database.child("totalCustomers");
        trafficStats = database.child("customerTraffic");

        retrieveCustomerData(); // query the total no. of customers daily
        retrieveTrafficData(); // query customer traffic data and plot pie chart
    }

    // query total no.of customers
    private void retrieveCustomerData() {
        customersStats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { dailyCustomers.setText("Today's Customers: " + snapshot.getValue().toString()); }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }


    // query customer traffic
    private void retrieveTrafficData() {
        trafficStats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<PieEntry> customerTraffic = new ArrayList<>();

                // iteratively query customer traffic data in the "customerTraffic" branch on firebase
                if (snapshot.hasChildren()) {
                    for (DataSnapshot myDataSnapshot : snapshot.getChildren()) {

                        System.out.println("Values: " + myDataSnapshot.getValue());
                        System.out.println("Keys: " + myDataSnapshot.getKey());

                        // add each entry of data (e.g. "10am-2pm" : 30) to an ArrayList
                        customerTraffic.add(new PieEntry(((Long) myDataSnapshot.getValue()), myDataSnapshot.getKey()));

                    }
                    System.out.println(customerTraffic);

                    // plot pie chart using the ArrayList with all the values queried from firebase
                    createPieChart(customerTraffic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });


    }


    // plot the customer traffic pie chart
    private void createPieChart(ArrayList<PieEntry> customerTraffic) {

        PieDataSet customerDataSet = new PieDataSet(customerTraffic, "Customer Traffic"); // set the dataset used to the plot the pie chart, which will be the ArrayList
        customerDataSet.setColors(ColorTemplate.PASTEL_COLORS); //set colour for pie chart
        customerDataSet.setValueTextColor(Color.BLACK); //set text colour for pie chart
        customerDataSet.setValueTextSize(12f); //set font size

        PieData custData = new PieData(customerDataSet); //create the pie chart data

        customerTrafficChart.setData(custData); //make the chart using the pie chart data
        customerTrafficChart.getDescription().setEnabled(false); //turn off extra descriptions for the chart
        customerTrafficChart.setCenterText(" Customer Traffic"); //label the pie chart
        customerTrafficChart.getLegend().setEnabled(false); //no legends
        customerTrafficChart.animate(); //animate the chart to appear

    }
}