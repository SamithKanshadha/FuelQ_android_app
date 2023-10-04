package com.example.fuelq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.annotations.Nullable;

public class DealerRegister extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private Boolean isAlreadyRegistered;
    private EditText email,password,name,address,dealerRegNo,petrol92,petrol95,diesel, dieselSuper,kerosene;
    private Button btnRegister;
    private Button btnLogin;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_reg);


        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.register_email);
        password =  findViewById(R.id.register_password);
        name = findViewById(R.id.fuel_st_name);
        address = findViewById(R.id.Address);
        dealerRegNo= findViewById(R.id.Reg_no);
        petrol92 = findViewById(R.id.Petrol92_Cap);
        petrol95 = findViewById(R.id.Petrol95_Cap);
        diesel= findViewById(R.id.Diesel_Cap);
        dieselSuper= findViewById(R.id.SDiesel_Cap);
        kerosene = findViewById(R.id.Kerosene_Cap);
        btnRegister = findViewById(R.id.register);
        btnLogin = findViewById(R.id.ex_login);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DealerRegister.this, LoginActivity.class));
                finishAffinity();
            }
        });

    }

    private void register()
    {
        String userEmail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String Stname = name.getText().toString().trim();
        String add = address.getText().toString().trim();
        String Reg_no = dealerRegNo.getText().toString().trim();
        String p92_cap = petrol92.getText().toString().trim();
        String p95_cap = petrol95.getText().toString().trim();
        String d_cap = diesel.getText().toString().trim();
        String Sd_cap = dieselSuper.getText().toString().trim();
        String k_cap = kerosene.getText().toString().trim();
        if(userEmail.isEmpty())
        {
            email.setError("Email can not be empty");
        }
        if(pass.isEmpty())
        {
            password.setError("Password can not be empty");
        }
        if(Stname.isEmpty())
        {
            name.setError("Fuel Station Name can not be empty");
        }
        if(add.isEmpty())
        {
            address.setError("Address can not be empty");
        }
        if(Reg_no.isEmpty())
        {
           dealerRegNo.setError("Register No. can not be empty");
        }
        if(p92_cap.isEmpty())
        {
            petrol92.setError("Petrol 92 Capacity can not be empty");
        }
        if(p95_cap.isEmpty())
        {
            petrol95.setError("Petrol 95 Capacity can not be empty");
        }
        if(d_cap.isEmpty())
        {
            diesel.setError("Diesel Capacity can not be empty");
        }
        if(Sd_cap.isEmpty())
        {
           dieselSuper.setError("Super Diesel Capacity can not be empty");
        }
        if(k_cap.isEmpty())
        {
           kerosene.setError("Kerosene Capacity can not be empty");
        }
        else
        {
            firebaseProcess(userEmail, pass, Stname,add, Reg_no,p92_cap,p95_cap,d_cap,Sd_cap,k_cap);
        }
    }

    private void firebaseProcess(String email, String password, String name,String address,String dealerRegNo,String petrol92,String petrol95,String diesel,String dieselSuper,String kerosene) {

        isAlreadyRegistered = false;

        rootNode = FirebaseDatabase.getInstance("https://fuelq-1ba5b-default-rtdb.asia-southeast1.firebasedatabase.app");
        reference = rootNode.getReference("USER");


        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {


                    if (!isAlreadyRegistered) {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(DealerRegister.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    FirebaseUser user = mAuth.getCurrentUser();


                                    DealerRole dealerRole = new DealerRole(user.getUid(), name, email, "Dealer", password, address, dealerRegNo, petrol92,petrol95,diesel,dieselSuper,kerosene);


                                    reference.child(user.getUid()).setValue(dealerRole, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
;
                                            Toast.makeText(DealerRegister.this, "Successfully registered.",
                                                    Toast.LENGTH_LONG).show();

                                            startActivity(new Intent(DealerRegister.this, DealerDashboard.class));
                                            finishAffinity();

                                        }
                                    });
                                } else {
                                    Toast.makeText(DealerRegister.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                    }
                } else {

                }
            }
        });
    }
}


