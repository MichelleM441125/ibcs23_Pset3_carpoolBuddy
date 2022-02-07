package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class bookVehicle extends AppCompatActivity implements bookVehicleAdapter.bookVehicleListener {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebase;

    private ArrayList<Vehicle> vehicleList;
    public static Vehicle bookVehicle;

    private ArrayList<String> models;
    private ArrayList<String> types;
    private ArrayList<Integer> capacities;
    private ArrayList<String> prices;
    private ArrayList<Integer> spaces;

    private FirebaseUser user;

    private RecyclerView recView;
    private bookVehicleAdapter newAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_vehicle);

        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseFirestore.getInstance();

        vehicleList = new ArrayList<>();
        bookVehicle = new Vehicle();

        models = new ArrayList<>();
        types = new ArrayList<>();
        capacities = new ArrayList<>();
        prices = new ArrayList<>();
        spaces = new ArrayList<>();

        newAdapter = new bookVehicleAdapter(models, types, prices, this);
        recView = findViewById(R.id.bookVehicleRecView);
        recView.setAdapter(newAdapter);
        recView.setLayoutManager(new LinearLayoutManager(this));

        user = mAuth.getCurrentUser();

        populateData();
    }

    public void populateData()
    {

        firebase.collection("Vehicles")
                .whereEqualTo("open", true).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if(task.isSuccessful())
                {

                    for (DocumentSnapshot ds : task.getResult().getDocuments())
                    {
                        Vehicle getVehicle = ds.toObject(Vehicle.class);
                        vehicleList.add(getVehicle);
                        System.out.println(vehicleList);
                    }

                    for(Vehicle eachVehicle : vehicleList)
                    {
                        String eachModel = eachVehicle.getModel();
                        models.add(eachModel);

                        String eachType = eachVehicle.getVehicleType();
                        types.add(eachType);

                        //int eachCapacity = eachVehicle.getCapacity();
                        //capacities.add(eachCapacity);

                        String eachPrice = eachVehicle.getPrice().toString();
                        prices.add(eachPrice);

                    }

                    newAdapter.newData(models, types, prices);
                    newAdapter.notifyDataSetChanged();


                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No vehicle is available now", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void bookVehicleOnClick(int position) {
        bookVehicle = vehicleList.get(position);
        Intent intent = new Intent(this, bookVehicleProfile.class);
        startActivity(intent);
    }
}