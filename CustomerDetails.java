package com.example.fuelq;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CustomerDetails extends AppCompatActivity {

    private TextView vehicleNumOrNic, vehicleTypeOrUserType, vehicleNumOrNicETxt, vehicleTypeOrUserTypeETxt, fullQuota, availableAmount;
    private String userType, id;
    private EditText filledAmount;
    private Spinner spinner;
    private Button submitBtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        firebaseDatabase = FirebaseAuthentication.referenceUrl;

        Intent intent = getIntent();
        userType = intent.getStringExtra("customer_type");
        id = intent.getStringExtra("customer_id");

        vehicleNumOrNic = findViewById(R.id.vehicle_number);
        vehicleTypeOrUserType = findViewById(R.id.CvehicleType);
        vehicleNumOrNicETxt = findViewById(R.id.CvehicleNumbertxt);
        vehicleTypeOrUserTypeETxt = findViewById(R.id.CvehicleTypetxt);
        filledAmount = findViewById(R.id.Filled_amount);
        submitBtn = findViewById(R.id.Submitbtn);
        fullQuota = findViewById(R.id.Availble_Quotatxt);
        availableAmount = findViewById(R.id.Deductiontxt);

        spinner = (Spinner)findViewById(R.id.spinnerFuelType);

        if (userType.equals("Kerosene User")) {
            vehicleNumOrNic.setText("NIC                          :");
            vehicleTypeOrUserType.setText("User Type               :");

        }

        getKeroseneUserData();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int filledQ = Integer.parseInt(filledAmount.getText().toString());
                submitUserQuota(filledQ);
                finish();

            }
        });

    }


    void getKeroseneUserData() {

        reference = firebaseDatabase.getReference().child("USER");

        final String[] userType = new String[1];


        reference.orderByChild("id").equalTo(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    userType[0] = snapshot1.child("userType").getValue().toString();


                    if (userType[0].equals("User")) {

                        vehicleNumOrNicETxt.setText(id);
                        vehicleTypeOrUserTypeETxt.setText(snapshot1.child("vehicleType").getValue().toString());
                        fullQuota.setText(snapshot1.child("fuelQuota").getValue().toString());
                        availableAmount.setText(snapshot1.child("availableQuota").getValue().toString());
                        userId = snapshot1.child("userId").getValue().toString();

                    } else if (userType[0].equals("Kerosene User")) {

                        vehicleNumOrNicETxt.setText(id);
                        vehicleTypeOrUserTypeETxt.setText("Kerosene");
                        fullQuota.setText(snapshot1.child("fuelQuota").getValue().toString());
                        availableAmount.setText(snapshot1.child("availableQuota").getValue().toString());
                        userId = snapshot1.child("userId").getValue().toString();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void submitUserQuota(int filledQ){
        reference = firebaseDatabase.getReference().child("USER").child(userId);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int availQuota = Integer.parseInt(snapshot.child("availableQuota").getValue().toString());

                String userFuelType = snapshot.child("fuelType").getValue().toString();

                if (spinner.getSelectedItem().equals("Choose Fuel Type")) {
                    Toast.makeText(CustomerDetails.this, "Please select a fuel type",
                            Toast.LENGTH_LONG).show();
                    return;
                }


                if (availQuota < filledQ) {
                    Toast.makeText(CustomerDetails.this, "Please enter available quota",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (!(userFuelType.equals("Diesel") == spinner.getSelectedItem().toString().contains("D")) &&
                        !(userFuelType.equals("Diesel") == spinner.getSelectedItem().toString().contains("S")) && availQuota > 0){

                    Toast.makeText(CustomerDetails.this,"Please check the quota and available fuel type",
                            Toast.LENGTH_LONG ).show();


                }else if( availQuota > 0 && !(userFuelType.equals("Petrol") == spinner.getSelectedItem().toString().contains("P"))){

                    Toast.makeText(CustomerDetails.this,"Please check the quota and available fuel type",
                            Toast.LENGTH_LONG ).show();

                }else if(availQuota > 0 && !(userFuelType.equals("Kerosene") == spinner.getSelectedItem().equals("Kerosene"))){

                    Toast.makeText(CustomerDetails.this,"Please check the quota and available fuel type",
                            Toast.LENGTH_LONG ).show();
                }else{
                    snapshot.getRef().child("availableQuota").setValue(availQuota - filledQ);
                    submitDealerQuota(filledQ , spinner);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    void submitDealerQuota(int filledQ , Spinner selectedFuelType){

        reference = FirebaseAuthentication.getDatabaseReference();

        Spinner selectedSpinner = selectedFuelType;

        Log.d("CustomerDetails","Methods");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int availQuota = 0;
                int remainingQuota = 0;

                if(selectedSpinner.getSelectedItem().equals("Petrol 92")){

                    availQuota = Integer.parseInt(snapshot.child("petrol92").getValue().toString());

                    remainingQuota = availQuota - filledQ;

                    Log.d("CustomerDetails",String.valueOf(availQuota));

                    snapshot.getRef().child("petrol92").setValue(remainingQuota);

                }
                else if(selectedSpinner.getSelectedItem().equals("Petrol 95")){

                    availQuota = Integer.parseInt(snapshot.child("petrol95").getValue().toString());

                    remainingQuota = availQuota - filledQ;

                    snapshot.getRef().child("petrol95").setValue(remainingQuota);

                }
                else if(selectedSpinner.getSelectedItem().equals("Diesel")){

                    availQuota = Integer.parseInt(snapshot.child("diesel").getValue().toString());

                    remainingQuota = availQuota - filledQ;

                    snapshot.getRef().child("diesel").setValue(remainingQuota);


                }
                else if(selectedSpinner.getSelectedItem().equals("Super Diesel")){

                    availQuota = Integer.parseInt(snapshot.child("dieselSuper").getValue().toString());

                    remainingQuota = availQuota - filledQ;

                    snapshot.getRef().child("dieselSuper").setValue(remainingQuota);

                }
                else if(selectedSpinner.getSelectedItem().equals("Kerosene")){
                    availQuota = Integer.parseInt(snapshot.child("kerosene").getValue().toString());

                    remainingQuota = availQuota - filledQ;

                    snapshot.getRef().child("kerosene").setValue(remainingQuota);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}