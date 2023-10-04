package com.example.fuelq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerDashboard extends AppCompatActivity {

    private TextView quota, resetDate, availableQuota;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private Button logOutBtn;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);


        quota = findViewById(R.id.Quota_Up);
        availableQuota = findViewById(R.id.availableQuota);
        resetDate =  findViewById(R.id.RestDate);
        logOutBtn = findViewById(R.id.log_out_btn);
        Log.d("CustomerDahsboard","Came to CustomerD");
        getUserData();

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = FirebaseAuthentication.signOut();

                if(user == null){
                    startActivity(new Intent(CustomerDashboard.this, LoginActivity.class));
                }else{
                    Toast.makeText(CustomerDashboard.this, "Can't Log out",  Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getUserData(){
        firebaseDatabase = FirebaseAuthentication.referenceUrl;
        reference = FirebaseAuthentication.getDatabaseReference();
        final String[] fuelQuota = {""};
        final String[] availableQ = {""};


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fuelQuota[0] =  snapshot.child("fuelQuota").getValue().toString();
                availableQ[0] = snapshot.child("availableQuota").getValue().toString();

                quota.setText(fuelQuota[0]);
                availableQuota.setText(availableQ[0]);

                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerDashboard.this);
        builder.setTitle("Exit");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit from the app?")
                .setCancelable(false)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}