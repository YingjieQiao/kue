package com.example.android1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        DatabaseReference stats_menu = database.getReference().child("accounts")
                .child(db_key_username).child("stats").child("food");

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                try {
                    String name = nameinput.getText().toString().trim();
                    String price_str = priceinput.getText().toString().trim();
                    String eta_str = etainput.getText().toString().trim();

                    Utils.checkValidString(name);
                    Utils.checkValidString(price_str);
                    Utils.checkValidString(eta_str);

                    price = Double.parseDouble(price_str);
                    eta = Double.parseDouble(eta_str);

                    Utils.checkValidNumber(price);
                    Utils.checkValidNumber(eta);

                    dish.setName(name);
                    dish.setPrice(price);
                    dish.setEta(eta);
                    System.out.println(menu);
                    menu.push().setValue(dish);
                    stats_menu.child(name).setValue(0);
                    showToastMsg("dish inserted succesfully");
                    //Toast.makeText(SettingsActivity.this, "dish inserted succesfully", Toast.LENGTH_LONG).show();
                } catch (IllegalArgumentException ex) {
                    Log.e("SETTINGS", "user input error");
                    showToastMsg("user input error");
                    Toast.makeText(SettingsActivity.this, "user input error", Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Log.e("SETTINGS", "unknown error");
                    showToastMsg("user input error, please try again");
                    Toast.makeText(SettingsActivity.this, "unknown error", Toast.LENGTH_LONG).show();
                }
            }
        });

        removebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_delete = nameinput_delete.getText().toString().trim();

                try {
                    Utils.checkValidString(name_delete);
                    menu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dish : snapshot.getChildren()) {
                                if (name_delete.equals(dish.getKey())) {
                                    dish.getRef().removeValue();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    stats_menu.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dish : snapshot.getChildren()) {
                                if (name_delete.equals(dish.getKey())) {
                                    dish.getRef().removeValue();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } catch (IllegalArgumentException ex) {
                    Log.e("SETTINGS", "user input error");
                    SettingsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SettingsActivity.this, "user input error", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception ex) {
                    Log.e("SETTINGS", "unknown error");
                    SettingsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SettingsActivity.this, "unknown error", Toast.LENGTH_LONG).show();
                        }
                    });
                }


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

    public void showToastMsg(String msg) {
        SettingsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
