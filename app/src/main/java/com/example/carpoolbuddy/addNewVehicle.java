package com.example.carpoolbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class addNewVehicle extends AppCompatActivity {

    private EditText vcModel;
    private EditText vcType;
    private EditText vcCapacity;
    private EditText vcID;
    private EditText vcBasePrice;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_vehicle);

        vcModel = findViewById(R.id.vehicleModel);
        vcType = findViewById(R.id.VehicleType);
        vcCapacity = findViewById(R.id.VehicleCapacity);
        vcID = findViewById(R.id.VehicleID);
        vcBasePrice = findViewById(R.id.VehicleBasePrice);

        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseFirestore.getInstance();

    }

    public void addNewVehicle(View v)
    {
        // if none of the input is empty
        if(vcModel != null && vcType != null && vcCapacity != null && vcID != null && vcBasePrice
                != null){

            //read in the input from the textViews
            String model = vcModel.getText().toString();
            String type = vcType.getText().toString();
            int capacity = Integer.parseInt(vcCapacity.getText().toString());
            String ID = vcID.getText().toString();
            double basePrice = Double.parseDouble(String.valueOf(vcBasePrice.getText()));

            //find the user that is operating
            FirebaseUser addVehicleUser = mAuth.getCurrentUser();

            if(addVehicleUser != null)
            {
                //default the status of the vehicle to "open" and get the user's email
                Boolean open = true;
                String vcOwner = mAuth.getCurrentUser().getEmail();

                //create a new vehicle
                Vehicle newVehicle = new Vehicle(vcOwner, model, type, capacity, ID, open, basePrice);

                //upload the data to the "Vehicles"collection in firebase
                firebase.collection("Vehicles").add(newVehicle);

                //send the user back to vehicle info page
                startActivity(new Intent(this,vehiclesInfo.class));
            }
            else
            {
                // if the user hasn't signed in, tell he or she to do so
                Toast.makeText(getApplicationContext(),"PLEASE LOG IN FIRST",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            // if the user miss a line to fill, tell he or she to complete it
            Toast.makeText(getApplicationContext(),"PLEASE COMPLETE THE INFO",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
