package com.example.fuelq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private Boolean isAlreadyRegistered;
    private EditText email,password,name,address,nic,chassisNo,vehicleNo;
    private Spinner spinner;
    private RadioGroup radioGroup;
    private Button btnRegister;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(RegisterActivity.this, ""+parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.register_email);
        password =  findViewById(R.id.register_password);
        name = findViewById(R.id.fullname);
        address = findViewById(R.id.Address);
        nic = findViewById(R.id.NIC);
        radioGroup = findViewById(R.id.radio_group);
        chassisNo = findViewById(R.id.Chassis_number);
        vehicleNo = findViewById(R.id.Vehicle_number);
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
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finishAffinity();
            }
        });
    }


    private void register()
    {
        String userEmail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String fname = name.getText().toString().trim();
        String add = address.getText().toString().trim();
        String Nic = nic.getText().toString().trim();
        String vNo = vehicleNo.getText().toString().trim();
        String cNo = chassisNo.getText().toString().trim();
        String vehicleTypeSpinner = spinner.getSelectedItem().toString();
        String fuelType = "";
        int fuelQuota = 0;
        int availableQuota = 0;


        if(radioGroup.getCheckedRadioButtonId()==-1)
        {

            Toast.makeText(getApplicationContext(), "Please select Fuel Type", Toast.LENGTH_SHORT).show();
            return;
        }else{

            int selectedId = radioGroup.getCheckedRadioButtonId();
            View radioButton = radioGroup.findViewById(selectedId);
            int indexOfRadioGroup = radioGroup.indexOfChild(radioButton);

            if(indexOfRadioGroup == 0){
                fuelType = "Petrol";
            }else if(indexOfRadioGroup == 1){
                fuelType = "Diesel";
            }else{
                Toast.makeText(getApplicationContext(), "Please select Fuel Type", Toast.LENGTH_SHORT).show();
                return;
            }
        }

       if(spinner.getSelectedItem().equals("Bike")){
           fuelQuota = 4;
           availableQuota = 4;

       }
        if(spinner.getSelectedItem().equals("Three Wheel")) {
            fuelQuota = 10;
            availableQuota = 10;
        }

        if(spinner.getSelectedItem().equals("Car") || spinner.getSelectedItem().equals("Van")){
            fuelQuota = 20;
            availableQuota = 20;
        }


        if(spinner.getSelectedItem().equals("Bus") || spinner.getSelectedItem().equals("Lorry")){
            fuelQuota = 50;
            availableQuota = 50;
        }

        if(spinner.getSelectedItem().equals("Land Vehicle")){
            fuelQuota = 30;
            availableQuota = 20;
        }

        if(userEmail.isEmpty())
        {
            email.setError("Email can not be empty");
        }
        if(pass.isEmpty())
        {
            password.setError("Password can not be empty");
        }
        if(pass.length() < 6){
            password.setError("Password need at least 8 characters");
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

        else
        {
            firebaseProcess(userEmail,pass,fname,Nic, add,cNo, fuelType, fuelQuota, availableQuota, vehicleTypeSpinner, vNo);

        }
    }

    private void firebaseProcess(String email, String password, String name, String nic,
                                 String address ,String chassisNo , String fuelType,int fuelQuota, int availableQuota, String vehicleType , String vehicleNo) {

        isAlreadyRegistered = false;

        rootNode = FirebaseDatabase.getInstance("https://fuelq-1ba5b-default-rtdb.asia-southeast1.firebasedatabase.app");
        reference = rootNode.getReference("USER");

        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {

                if (task.isSuccessful()) {

                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {

                        String vehicleId = dataSnapshot.child("id").getValue().toString();

                        Log.d("UserRegister",vehicleId);

                            if(vehicleId.equals(vehicleNo)){
                                isAlreadyRegistered = true;
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_LONG).show();
                        }
                    }

                    if (!isAlreadyRegistered) {
                        Log.d("UserRegister","vehicleId");
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String key = reference.push().getKey();

                                    UserRole userRole = new UserRole(user.getUid(), nic, name, email,
                                            "User", address, fuelQuota, chassisNo, password, fuelType, availableQuota, vehicleType ,vehicleNo);

                                    reference.child(user.getUid()).setValue(userRole, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {

                                            Toast.makeText(RegisterActivity.this, "Successfully registered.", Toast.LENGTH_LONG).show();

                                            startActivity(new Intent(RegisterActivity.this, CustomerDashboard.class));
                                            finishAffinity();

                                        }
                                    });
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {

                        Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                            Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}


