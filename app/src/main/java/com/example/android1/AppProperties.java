package com.example.android1;

public class AppProperties {
    private static AppProperties mInstance= null;

    public static String username;

    protected AppProperties(){}

    public static synchronized AppProperties getInstance() {
        if(null == mInstance){
            mInstance = new AppProperties();
        }
        return mInstance;
    }

    public static void setUsername(String username_string) {
        username = username_string;
    }
}
