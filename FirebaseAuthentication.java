package com.example.fuelq;

import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseAuthentication {

    static String userUid;
    static FirebaseDatabase referenceUrl = FirebaseDatabase.getInstance("https://fuelq-1ba5b-default-rtdb.asia-southeast1.firebasedatabase.app");;


    public FirebaseAuthentication(String uid){

        this.userUid = uid;

    }

    public static FirebaseUser signOut(){
        FirebaseAuth.getInstance().signOut();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        return user;

    }

    public static FirebaseUser getCurrentU(){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();

        return user;

    }

    public static DatabaseReference getDatabaseReference(){

       return referenceUrl.getReference().child("USER").child(getCurrentU().getUid());
    }


}
