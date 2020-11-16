package com.example.android1;

import java.util.*;

public class Order {
    ArrayList<String> Food;
    Long OrderTime;
    Integer FinishTime;
    String OrderDate;

    public Order() {}  // Needed for Firebase

    public void setFood(ArrayList<String> food) {
        Food = food;
    }

    public void setOrderTime(Long orderTime) {
        OrderTime = orderTime;
    }

    public void setFinishTime(Integer finishTime) {
        FinishTime = finishTime;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public ArrayList<String> getFood() {
        return Food;
    }

    public Long getOrderTime() {
        return OrderTime;
    }

    public Integer getFinishTime() {
        return FinishTime;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public Order(ArrayList<String> food, Long orderTime, Integer finishTime, String orderDate) {
        Food = food;
        OrderTime = orderTime;
        FinishTime = finishTime;
        OrderDate = orderDate;
    }
}
