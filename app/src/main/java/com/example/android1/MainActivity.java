package com.example.android1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


interface SimpleCallback<T> {
    void callback(T data);
}


public class MainActivity extends AppCompatActivity {

    public boolean login_success = false, async_done = false;
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference accounts = database.getReference("accounts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button loginButton = findViewById(R.id.button_login);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Login Button clicked!");
                    checkPassword(username.getText().toString(), password.getText().toString(),
                            new SimpleCallback<Boolean>() {
                        @Override
                        public void callback(Boolean data) {
                            if (data) {
                                Intent intent = new Intent(MainActivity.this,
                                        HomePageActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Wrong username or password",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
    }


    public void checkPassword(String username, String password,
                                       @NonNull SimpleCallback<Boolean> finishedCallback) {
        accounts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ss : snapshot.getChildren()) {
                    Map<String, Object> each_restaurant = (Map<String, Object>) ss.getValue();
                    String db_username = (String) each_restaurant.get("email");
                    String db_password = (String) each_restaurant.get("password");
                    System.out.println(db_username.equals(username));
                    System.out.println(db_password.equals(password));
                    if (db_username.equals(username) && db_password.equals(password)) {
                        finishedCallback.callback(true);
                        return;
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}