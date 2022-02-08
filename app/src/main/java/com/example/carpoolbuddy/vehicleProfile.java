package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

//this class is what the user see after they click on to one of the vehicle displayed in VehicleInfo
public class vehicleProfile extends AppCompatActivity {

    private TextView nameTextView;
    private TextView typeTextView;
    private TextView IDTextView;
    private TextView priceTextView;
    private TextView openTextView;

    Vehicle chosenVC;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_profile);

        nameTextView = findViewById(R.id.VehicleNameText);
        typeTextView = findViewById(R.id.VehicleTypeText);
        IDTextView = findViewById(R.id.VehicleIdText);
        priceTextView = findViewById(R.id.VehiclePriceText);
        openTextView = findViewById(R.id.vehicleOpenText);

        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseFirestore.getInstance();

        chosenVC = vehiclesInfo.vehicle;

        String nameText = chosenVC.getModel();
        String typeText = chosenVC.getVehicleType();
        String IdText = chosenVC.getVehicleID();
        String openText = chosenVC.getOpen().toString();
        String priceText = chosenVC.getPrice().toString();

        // show all the informaiton about the chosen vehicle on the screen
        nameTextView.setText(nameText);
        typeTextView.setText(typeText);
        IDTextView.setText(IdText);
        priceTextView.setText(priceText);
        openTextView.setText(openText);
    }

    // this method changes the open field of the vehicle in Firestore to "false"
    public void closeVehicle(View x)
    {
        firebase.collection("Vehicles").whereEqualTo("model", chosenVC.getModel())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                for (DocumentSnapshot ds : task.getResult().getDocuments())
                {
                    String ID = ds.getId();
                    firebase.collection("Vehicles").document(ID).update("open", false);
                }
            }
        });

        startActivity(new Intent(this, vehiclesInfo.class));
    }

    // this method changes the open field of the vehicle in Firestore to "true"
    public void openVehicle(View x)
    {
        firebase.collection("Vehicles").whereEqualTo("model", chosenVC.getModel())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                for (DocumentSnapshot ds : task.getResult().getDocuments())
                {
                    String ID = ds.getId();
                    firebase.collection("Vehicles").document(ID).update("open", true);
                }
            }
        });

        startActivity(new Intent(this, vehiclesInfo.class));
    }

    // this method send the user to update the info of the vehicle
    public void editVehicle(View x)
    {
        Intent intent = new Intent(this, editVehicle.class);
        // send the name of the vehicle to the editVehicle activity
        intent.putExtra("editV", chosenVC.getModel());
        startActivity(intent);

    }

}