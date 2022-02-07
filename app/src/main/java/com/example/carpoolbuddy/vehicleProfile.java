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

        nameTextView = findViewById(R.id.bookVehicleNameText);
        typeTextView = findViewById(R.id.bookVehicleTypeText);
        IDTextView = findViewById(R.id.bookVehicleIdText);
        priceTextView = findViewById(R.id.bookVehiclePriceText);
        openTextView = findViewById(R.id.vehicleOpenText);

        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseFirestore.getInstance();

        chosenVC = vehiclesInfo.vehicle;

        String nameText = chosenVC.getModel();
        String typeText = chosenVC.getVehicleType();
        String IdText = chosenVC.getVehicleID();
        String openText = chosenVC.getOpen().toString();
        String priceText = chosenVC.getPrice().toString();

        nameTextView.setText(nameText);
        typeTextView.setText(typeText);
        IDTextView.setText(IdText);
        priceTextView.setText(priceText);
        openTextView.setText(openText);
    }

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

    public void editVehicle(View x)
    {
        Intent intent = new Intent(this, editVehicle.class);
        intent.putExtra("editV", chosenVC.getModel());
        startActivity(intent);

    }

}