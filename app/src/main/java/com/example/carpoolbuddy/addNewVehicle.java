package com.example.carpoolbuddy;

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

    public Boolean fromValid()
    {
        Boolean isValid = true;
        if(vcModel == null || vcType == null || vcCapacity == null || vcID == null || vcBasePrice == null)
        {
            isValid = false;
        }

        return isValid;
    }

    public void addNewVehicle(View v)
    {
        if(fromValid() == true){
            String model = vcModel.getText().toString();
            String type = vcType.getText().toString();
            int capacity = Integer.parseInt(vcCapacity.getText().toString());
            String ID = vcID.getText().toString();
            double basePrice = Double.parseDouble(String.valueOf(vcBasePrice.getText()));

            FirebaseUser addVehicleUser = mAuth.getCurrentUser();
            if(addVehicleUser != null)
            {
                String name = addVehicleUser.getDisplayName();
                Boolean open = true;
                Vehicle newVehicle = new Vehicle(name, model, type, capacity, ID, open, basePrice);

                firebase.collection("Vehicles").document(ID).set(newVehicle);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"PLEASE LOG IN FIRST", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"PLEASE COMPLETE THE INFO", Toast.LENGTH_SHORT).show();
        }

    }
    
}
