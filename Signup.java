package com.example.fuelq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Signup extends AppCompatActivity {

    private Button btnDriver;
    private Button btnKeroseneCustomer;
    private Button btnDealer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnDriver = findViewById(R.id.Driver);
        btnKeroseneCustomer = findViewById(R.id.KeroseneCustomer);
        btnDealer =findViewById(R.id.Dealer);



        btnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, RegisterActivity.class));
            }
        });

        btnKeroseneCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this, KeroseneRegister.class));
            }
        });

        btnDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this,DealerRegister.class));
            }
        });
    }

}
