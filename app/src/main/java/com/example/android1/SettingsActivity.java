package com.example.android1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

public class SettingsActivity extends AppCompatActivity {

    Double price, eta;

    EditText nameinput;
    EditText priceinput;
    EditText etainput;
    Button submitbutton;

    EditText nameinput_delete;
    Button removebutton;

    EditText priceinput_update;
    EditText nameinput_update_price;
    Button updatebutton_price;

    EditText etainput_update;
    EditText nameinput_update_eta;
    Button updatebutton_eta;

    Dish dish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        nameinput = (EditText) findViewById(R.id.nameinput);
        priceinput = (EditText) findViewById(R.id.priceinput);
        etainput = (EditText) findViewById(R.id.etainput);
        submitbutton = (Button) findViewById(R.id.submitbutton);

        nameinput_delete = findViewById(R.id.nameinput_delete);
        removebutton = findViewById(R.id.deletebutton);

        nameinput_update_price = findViewById(R.id.nameinput_update_price);
        priceinput_update = findViewById(R.id.priceinput_update);
        updatebutton_price = findViewById(R.id.updatebutton_price);

        nameinput_update_eta = findViewById(R.id.nameinput_update_eta);
        etainput_update = findViewById(R.id.etainput_update);
        updatebutton_eta = findViewById(R.id.updatebutton_eta);

        dish = new Dish();

        String db_key_username = AppProperties.getInstance().username;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference menu =  database.getReference("accounts")
                .child(db_key_username).child("menu");

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameinput.getText().toString().trim();
                price = Double.parseDouble(priceinput.getText().toString().trim());
                eta = Double.parseDouble(etainput.getText().toString().trim());

                dish.setName(name);
                dish.setPrice(price);
                dish.setEta(eta);
                System.out.println(menu);
                menu.push().setValue(dish);
                Toast.makeText(SettingsActivity.this, "dish inserted succesfully", Toast.LENGTH_LONG).show();
            }
        });

        removebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_delete = nameinput_delete.getText().toString().trim();
                menu.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dish : snapshot.getChildren()) {
                            if (name_delete.equals(dish.child("name").getValue())) {
                                dish.getRef().removeValue();
                                }
                            }
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Toast.makeText(SettingsActivity.this,
                        "dish removed succesfully", Toast.LENGTH_LONG).show();
            }
        });

        updatebutton_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameinput_update_price.getText().toString().trim();
                String new_price = priceinput_update.getText().toString().trim();
                menu.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dish : snapshot.getChildren()) {
                            if (name.equals(dish.child("name").getValue())) {
                                String postKey = dish.getRef().getKey();
                                assert postKey != null;
                                menu.child(postKey).child("price").setValue(new_price);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Toast.makeText(SettingsActivity.this,
                        "price updated succesfully", Toast.LENGTH_LONG).show();
            }
        });

        updatebutton_eta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameinput_update_eta.getText().toString().trim();
                String new_eta = etainput_update.getText().toString().trim();
                menu.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dish : snapshot.getChildren()) {
                            if (name.equals(dish.child("name").getValue())) {
                                String postKey = dish.getRef().getKey();
                                assert postKey != null;
                                menu.child(postKey).child("eta").setValue(new_eta);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Toast.makeText(SettingsActivity.this,
                        "ETA updated succesfully", Toast.LENGTH_LONG).show();
            }
        });
    }
}
