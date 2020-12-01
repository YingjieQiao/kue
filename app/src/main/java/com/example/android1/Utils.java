package com.example.android1;

import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;


public class Utils {
    private static String sharedPrefFile = "com.example.android1.mainsharedprefs";
    private static String USER; // the current user logged in the app
    private static String DB_KEY_USERNAME; // the child in firebase to query from


    public static void generate_db_key(SharedPreferences mPreferences) {
        /*
        This method is called when logged in. It takes in the logged in username and
        generates the database key in firebase.
        eg: user1@xxx --> username1; user2@xxx --> username2
        The key is saved in shared preferences.
         */

        String username = mPreferences.getString(USER, "ERROR");
        String[] username_split = username.split("@");
        String username_before_domain = username_split[0];
        Character num_ch = username_before_domain.charAt(username_before_domain.length()-1);
        String db_key = "username" + num_ch;
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(DB_KEY_USERNAME, db_key);
        preferencesEditor.apply();
    }
    public static String generateURL(String username, String receiptId){
        return "http://3.82.106.27/" + username + "/" + receiptId;
    }


}
