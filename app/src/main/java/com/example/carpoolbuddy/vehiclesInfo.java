package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public class vehiclesInfo extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebase;
    private vehiclesInfo Vehicle;
    private ArrayList<Vehicle> vehicleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles_info);

        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseFirestore.getInstance();
        vehicleList = new ArrayList<>();

    }


    public void getAndPopulateData()
    {
        firebase.collection("Vehicles").whereEqualTo("Owner", mAuth.getCurrentUser()).get().addOnCompleteListener( new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                for (DocumentSnapshot ds : task.getResult().getDo)
                {
                    Vehicle getVehicle = ds.toObject(Vehicle.class);
                    vehicleList.add(getVehicle);
                }

                for(Vehicle eachVehicle : vehicleList)
                {
                    Log.d("vehicle","get vehicles successfully")
                }

            }
        });

    }

    public void goAddVehicle(View x)
    {
        startActivity(new Intent(this, addNewVehicle.class));
    }

}