package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public class vehiclesInfo extends AppCompatActivity implements vehicleAdapter.vehicleListener{

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebase;
    private FirebaseUser user;

    private ArrayList<Vehicle> vehicleList;
    public  static Vehicle vehicle;

    private ArrayList<String> models;
    private ArrayList<String> types;
    private ArrayList<String> statuses;

    private RecyclerView recView;
    private vehicleAdapter newAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles_info);

        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseFirestore.getInstance();
        vehicleList = new ArrayList<>();

        models = new ArrayList<>();
        types = new ArrayList<>();
        statuses = new ArrayList<>();

        user = mAuth.getCurrentUser();
        recView = findViewById(R.id.userVehiclesRecView);

        newAdapter = new vehicleAdapter(models, types, statuses, this);
        recView.setAdapter(newAdapter);
        recView.setLayoutManager(new LinearLayoutManager(this));

        System.out.println(user.getEmail());

        getAndPopulateData();
    }

    public void getAndPopulateData()
    {
        // get all the vehicles that the current user owns
        firebase.collection("Vehicles").whereEqualTo("owner", user.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if(task.isSuccessful())
                {
                    for (DocumentSnapshot ds : task.getResult().getDocuments())
                    {
                        // convert the vehicle documents to Vehicle type
                        Vehicle getVehicle = ds.toObject(Vehicle.class);
                        // add them into the arraylist
                        vehicleList.add(getVehicle);
                    }
                    // run through each vehicle in the arraylist to get their model, type, and status
                    for(Vehicle eachVehicle : vehicleList)
                    {
                        String eachModel = eachVehicle.getModel();
                        models.add(eachModel);

                        String eachType = eachVehicle.getVehicleType();
                        types.add(eachType);

                        String eachStatus = eachVehicle.getOpen().toString();
                        statuses.add(eachStatus);
                    }
                    // send the arraylists that contain the info about each vehicle to the adapter
                    newAdapter.newData(models, types, statuses);
                    newAdapter.notifyDataSetChanged();
                }

                else // if the current user doesn't own any vehicle:
                {
                    Toast.makeText(getApplicationContext(), "you don't have any vehicle yet, " +
                            "go add some", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void goAddVehicle(View x)
    {
        startActivity(new Intent(this, addNewVehicle.class));
    }

    public void goBookVehicle(View v)
    {
        startActivity(new Intent(this, bookVehicle.class));
    }

    @Override
    public void vehicleOnClick(int position)
    {
        vehicle = vehicleList.get(position);
        Intent intent = new Intent(this, vehicleProfile.class);
        startActivity(intent);
    }
}