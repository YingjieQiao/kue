package com.example.android1;

import java.util.*;

public class Order {
    HashMap<String,String> Food;

    Long OrderTime;
    Long FinishTime;
    String OrderDate;
    String OrderID;

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public Order() {}  // Needed for Firebase

    public void setFood(HashMap<String, String> food) {

        Food = food;
    }

    public void setOrderTime(Long orderTime) {
        OrderTime = orderTime;
    }

    public void setFinishTime(Long finishTime) {
        FinishTime = finishTime;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public HashMap<String,String> getFood() {

        return Food;
    }

    public Long getOrderTime() {
        return OrderTime;
    }

    public Long getFinishTime() {
        return FinishTime;
    }

    public String getOrderDate() {
        return OrderDate;
    }


    public Order(HashMap<String,String> food, Long orderTime, Long finishTime,
                 String orderDate, String id) {

        Food = food;
        OrderTime = orderTime;
        FinishTime = finishTime;
        OrderDate = orderDate;
        OrderID = id;
    }
}

