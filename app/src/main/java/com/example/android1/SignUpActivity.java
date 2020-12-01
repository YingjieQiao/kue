package com.example.android1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    EditText email_edit;
    EditText password_edit;
    EditText password_verify_edit;
    Button sign_up_edit;
    DatabaseReference accountRef = FirebaseDatabase.getInstance().getReference().child("accounts");
    static final String TAG = "SignUpPage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email_edit = findViewById(R.id.signup_username);
        password_edit = findViewById(R.id.signup_password);
        password_verify_edit = findViewById(R.id.signup_password_verify);
        sign_up_edit = findViewById(R.id.button_finish_signup);

        sign_up_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = password_edit.getText().toString();
                String password_verify = password_verify_edit.getText().toString();
                String email = email_edit.getText().toString();
                String username = email.split("@")[0];
                Log.i(TAG, password);
                Log.i(TAG, password_verify);
                if (!password.equals(password_verify)){
                    Toast.makeText(SignUpActivity.this, "passwords are not the same!", Toast.LENGTH_LONG).show();
                    return;
                }
                HashMap<String, Object> dataToBePushed = new HashMap<>();
                dataToBePushed.put("email", email);
                dataToBePushed.put("password", password);
                accountRef.child(username).setValue(dataToBePushed);
                Toast.makeText(SignUpActivity.this, " Sign up Suceefully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}