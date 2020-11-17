package com.example.android1;

import java.util.*;

public class Order {
    HashMap<String,String> Food;
    Long OrderTime;
    Integer FinishTime;
    String OrderDate;

    public boolean isDisplayed() {
        return Displayed;
    }

    public void setDisplayed(boolean displayed) {
        Displayed = displayed;
    }

    boolean Displayed;

    public Order() {}  // Needed for Firebase

    public void setFood(HashMap<String, String> food) {
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

    public HashMap<String,String> getFood() {
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

    public Order(HashMap<String,String> food, Long orderTime, Integer finishTime,
                 String orderDate, boolean displayed) {
        Food = food;
        OrderTime = orderTime;
        FinishTime = finishTime;
        OrderDate = orderDate;
        Displayed = displayed;
    }
}