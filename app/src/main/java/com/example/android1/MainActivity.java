package com.example.android1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private Handler mHandler;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference accounts = database.getReference("accounts");
    Button loginButton;
    EditText username;
    EditText password;
    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginButton = findViewById(R.id.button_cash);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signUp = findViewById(R.id.button_start_sign_up);

        signUp.setText(Html.fromHtml("<u>sign up here</u>"));
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Login Button clicked!");
                String[] parts = username.getText().toString().split("@");
                AppProperties.setUsername(parts[0]);
                checkPassword(username.getText().toString(), password.getText().toString(),
                            new CompareValueCallback<Boolean>() {
                        @Override
                        public void callback(Boolean data) {
                            if (data) {
                                Intent intent = new Intent(MainActivity.this,
                                        HomePageActivity.class);
                                startActivity(intent);
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        System.out.println(data);
                                        Toast.makeText(MainActivity.this,
                                                "Wrong username or password",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    });
                }
            });


    }


    private void checkPassword(String username, String password,
                                       @NonNull CompareValueCallback<Boolean> finishedCallback) {
        accounts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot db : snapshot.getChildren()) {
                    Map<String, Object> each_restaurant = (Map<String, Object>) db.getValue();
                    String db_username = (String) each_restaurant.get("email");
                    String db_password = (String) each_restaurant.get("password");
                    //System.out.println(db_username.equals(username));
                    //System.out.println(db_password.equals(password));
                    if (db_username.equals(username) && db_password.equals(password)) {
                        finishedCallback.callback(true);
                        return;
                    }
                }
                finishedCallback.callback(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


}