package com.example.android1;

import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;


public class Utils {

    public static boolean checkValidNumber(Double d)
            throws IllegalArgumentException{

        if( d <= 0) throw new IllegalArgumentException(
                "ETA or price should be a positive number");
        return true;
    }

    public static boolean checkValidString(String in)
            throws IllegalArgumentException{

        if(in.equals("")) throw new IllegalArgumentException(
                "Empty input is not allowed");
        return true;
    }

    public static String generateURL(String username, String receiptId){
        return "http://3.82.106.27/" + username + "/" + receiptId;
    }



}
