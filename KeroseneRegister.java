package com.example.fuelq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.annotations.Nullable;

public class KeroseneRegister extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private Boolean isAlreadyRegistered;
    private EditText email,password,name,address,nic;
    private Button btnRegister;
    private Button btnExLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kerosene_register);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.kRegister_email);
        password =  findViewById(R.id.kRegister_password);
        name = findViewById(R.id.kFullname);
        address = findViewById(R.id.kAddress);
        nic = findViewById(R.id.kNIC);
        btnRegister = findViewById(R.id.kRegister);
        btnExLogin = findViewById(R.id.kEx_login);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();
            }
        });

        btnExLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KeroseneRegister.this, LoginActivity.class));
                finishAffinity();
            }
        });
    }

    public void register(){
        String userEmail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String fname = name.getText().toString().trim();
        String add = address.getText().toString().trim();
        String Nic = nic.getText().toString().trim();

        if(userEmail.isEmpty())
        {
            email.setError("Email can not be empty");
        }
        if(pass.isEmpty())
        {
            password.setError("Password can not be empty");
        }
        if(fname.isEmpty())
        {
            name.setError("Full name can not be empty");
        }
        if(add.isEmpty())
        {
            address.setError("Address can not be empty");
        }
        if(Nic.isEmpty())
        {
            nic.setError("NIC can not be empty");
        }

        else {
            firebaseProcess(userEmail, pass, fname, Nic, add, "Kerosene", 4, 4);

        }
    }

    private void firebaseProcess(String email, String password, String name, String nic,
                                 String address , String fuelType, int quota, int availableQuota) {
//        progressBar.setVisibility(View.VISIBLE);
        isAlreadyRegistered = false;

        rootNode = FirebaseDatabase.getInstance("https://fuelq-1ba5b-default-rtdb.asia-southeast1.firebasedatabase.app");
        reference = rootNode.getReference("USER");

        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {

                        String nicId = dataSnapshot.child("id").getValue().toString();
                        if(nic.equals(nicId)){
                            isAlreadyRegistered = true;
                            Toast.makeText(KeroseneRegister.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();

                        }

                    }

                    if (!isAlreadyRegistered) {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(KeroseneRegister.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {

                                    FirebaseUser user = mAuth.getCurrentUser();


                                    KeroseneUser keroseneUser = new KeroseneUser(user.getUid(),name, email,"Kerosene User",  password,address, fuelType, quota, availableQuota,nic);

                                    reference.child(user.getUid()).setValue(keroseneUser, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {

                                            Toast.makeText(KeroseneRegister.this, "Successfully registered.",
                                                    Toast.LENGTH_LONG).show();

                                            startActivity(new Intent(KeroseneRegister.this, CustomerDashboard.class));
                                            finishAffinity();

                                        }
                                    });
                                } else {
                                    Toast.makeText(KeroseneRegister.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                    }
                } else {
//                    showErrorDialog("Internal server error");
                }
            }
        });
    }
}