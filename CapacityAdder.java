package com.example.fuelq;

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

public class CapacityAdder extends AppCompatActivity {

    EditText petrol92Txt, petrol95Txt , dieselTxt , superDieselTxt , keroseneTxt;
    Button petrol92Btn, petrol95Btn ,dieselBtn , superDieselBtn , keroseneBtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacity_adder);

        petrol92Txt = findViewById(R.id.Npetrol_92);
        petrol92Btn = findViewById(R.id.add_p92);
        petrol95Txt = findViewById(R.id.Npetrol_95);
        petrol95Btn = findViewById(R.id.add_p95);
        dieselTxt   = findViewById(R.id.Ndiesel);
        dieselBtn = findViewById(R.id.add_Diesel);
        superDieselTxt = findViewById(R.id.NSupDiesel);
        superDieselBtn = findViewById(R.id.add_SDiesel);
        keroseneTxt = findViewById(R.id.NKerosene);
        keroseneBtn = findViewById(R.id.add_Kerosene);


        petrol92Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String petrol92 = petrol92Txt.getText().toString();
                addCapacity("petrol92", petrol92);
            }
        });

        petrol95Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String petrol95 = petrol95Txt.getText().toString();
                addCapacity("petrol95", petrol95);
            }
        });

        dieselBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diesel = dieselTxt.getText().toString();
                addCapacity("diesel", diesel);
            }
        });

        superDieselBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String superDiesel = superDieselTxt.getText().toString();
                addCapacity("dieselSuper", superDiesel);
            }
        });

        keroseneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kerosene = keroseneTxt.getText().toString();
                addCapacity("kerosene", kerosene);
            }
        });

    }

    void addCapacity(String fuelType, String addValue){

        if(addValue.isEmpty()){
            Toast.makeText(CapacityAdder.this, "Please type valid value", Toast.LENGTH_SHORT).show();
            return;
        }
        int valueNeededToAdd = Integer.parseInt(addValue);

        firebaseDatabase = FirebaseAuthentication.referenceUrl;
        reference = FirebaseAuthentication.getDatabaseReference();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               int availableQuota =  Integer.parseInt(snapshot.child(fuelType).getValue().toString());

               snapshot.getRef().child(fuelType).setValue(availableQuota + valueNeededToAdd);

               Toast.makeText(CapacityAdder.this, "Successfully Added", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}