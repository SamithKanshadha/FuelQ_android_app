package com.example.fuelq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DealerDashboard extends AppCompatActivity {

    private TextView petrol92 , petrol95 , diesel , supDiesel ,kerosene;
    private EditText search;
    private Button searchBtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private Button btn_LogOut, addCapacityBtn;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_dashboard);

        petrol92 =  findViewById(R.id.petrol_92);
        petrol95 = findViewById(R.id.petrol_95);
        diesel =  findViewById(R.id.diesel);
        supDiesel = findViewById(R.id.SupDiesel);
        kerosene =  findViewById(R.id.Kerosene);
        search = findViewById(R.id.Search_txt);
        searchBtn = findViewById(R.id.search_btn);
        btn_LogOut = findViewById(R.id.logOut_btn);
        addCapacityBtn = findViewById(R.id.Add_Cap);
        getUserData();

        btn_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = FirebaseAuthentication.signOut();

                if(user == null){
                    startActivity(new Intent(DealerDashboard.this, LoginActivity.class));
                }else{
                    Toast.makeText(DealerDashboard.this, "Can't Log out",  Toast.LENGTH_SHORT).show();
                }
            }
        });

        addCapacityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DealerDashboard.this, CapacityAdder.class));
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUser();
            }
        });

    }

    private void getUserData(){

        firebaseDatabase = FirebaseAuthentication.referenceUrl;
        reference = FirebaseAuthentication.getDatabaseReference();

            reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                petrol92.setText(snapshot.child("petrol92").getValue().toString());
                petrol95.setText(snapshot.child("petrol95").getValue().toString());
                diesel.setText(snapshot.child("diesel").getValue().toString());
                supDiesel.setText(snapshot.child("dieselSuper").getValue().toString());
                kerosene.setText(snapshot.child("kerosene").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void searchUser(){
        Log.d("DealerDashboard", "method");
        String searchUser = search.getText().toString().trim();
        //Log.d("DealerDashboard", searchUser);
        final String[] userType = new String[1];

        reference = firebaseDatabase.getReference().child("USER");


        reference.orderByChild("id").equalTo(searchUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if(!snapshot.exists()){
                    Toast.makeText(DealerDashboard.this,"Sorry there's no such user",
                            Toast.LENGTH_LONG ).show();
                }

                for(DataSnapshot snapshot1 : snapshot.getChildren()){

                   userType[0] = snapshot1.child("userType").getValue().toString();

                   if(userType[0].equals("User")){

                       Intent intent = new Intent(getBaseContext(), CustomerDetails.class);
                        intent.putExtra("customer_id", searchUser);
                        intent.putExtra("customer_type", "User");
                        //Log.d("DealerDashboards", snapshot1.child("userType").getValue().toString());
                        startActivity(intent);

                   }else if(userType[0].equals("Kerosene User")){

                       Intent intent = new Intent(getBaseContext(), CustomerDetails.class);
                        intent.putExtra("customer_id", searchUser);
                       intent.putExtra("customer_type", "Kerosene User");
                        //Log.d("DealerDashboards", snapshot1.child("userType").getValue().toString());
                        startActivity(intent);

                   }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}