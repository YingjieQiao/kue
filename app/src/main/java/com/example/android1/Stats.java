package com.example.android1;

import java.util.HashMap;

public class Stats {

    public HashMap<String, Long> customerTraffic;
    public HashMap<String, Long> food;
    public Double averageRating;
    public Double dailyRevenue;
    public Long totalCustomers;
    public Long totalRatings;

    public Stats() {
    }

    public Stats(HashMap<String, Long> customerTraffic, HashMap<String, Long> food, Long averageRating, Long dailyRevenue, Long totalCustomers, Long totalRatings) {
        this.customerTraffic = customerTraffic;
        this.food = food;
        this.averageRating = ((Long) averageRating).doubleValue();
        this.dailyRevenue = ((Long) dailyRevenue).doubleValue();
        this.totalCustomers = totalCustomers;
        this.totalRatings = totalRatings;
    }

}

