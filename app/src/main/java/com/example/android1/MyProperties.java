package com.example.android1;

public class MyProperties {
    private static MyProperties mInstance= null;

    public String username = "username1";

    protected MyProperties(){}

    public static synchronized MyProperties getInstance() {
        if(null == mInstance){
            mInstance = new MyProperties();
        }
        return mInstance;
    }
}
