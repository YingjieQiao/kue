package com.example.android1;


public class Orders {
    String ChickenRice,DuckRice,Satay,TotalCost,ordername;//creating json for the data
    Orders(){}
    Orders(String Ordername,String chickenRice,String duckRice, String satay,String totalCost){
        this.ordername=Ordername;
        this.ChickenRice=chickenRice;
        this.DuckRice=duckRice;
        this.Satay=satay;
        this.TotalCost=totalCost;

    }
}
