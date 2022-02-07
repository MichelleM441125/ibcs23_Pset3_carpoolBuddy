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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class bookVehicleProfile extends AppCompatActivity {

    private TextView bookNameTextView;
    private TextView bookTypeTextView;
    private TextView bookIdTextView;
    private TextView bookPriceTextView;

    private Vehicle chosenVC;
    private ArrayList<String> ridersID;
    private Integer currCap;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebase;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_vehicle_profile);

        bookNameTextView = findViewById(R.id.bookVehicleNameText);
        bookTypeTextView = findViewById(R.id.bookVehicleTypeText);
        bookIdTextView = findViewById(R.id.bookVehicleIdText);
        bookPriceTextView = findViewById(R.id.bookVehiclePriceText);

        ridersID = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();

        chosenVC = bookVehicle.bookVehicle;

        String nameText = chosenVC.getModel();
        String typeText = chosenVC.getVehicleType();
        String idText = chosenVC.getVehicleID();
        String priceText = chosenVC.getPrice().toString();

        bookNameTextView.setText(nameText);
        bookTypeTextView.setText(typeText);
        bookIdTextView.setText(idText);
        bookPriceTextView.setText(priceText);
    }

    public void book(View x)
    {
        firebase.collection("User").whereEqualTo("email", user.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                for (DocumentSnapshot ds : task.getResult().getDocuments())
                {
                    String ID = ds.getId();
                    ridersID.add(ID);
                }
            }
        });

        firebase.collection("Vehicles").whereEqualTo("model", chosenVC.getModel())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                for (DocumentSnapshot ds : task.getResult().getDocuments())
                {
                    Vehicle getVehicle = ds.toObject(Vehicle.class);
                    currCap = getVehicle.getCapacity();
                    Integer newCap = currCap - 1;
                    String ID = ds.getId();
                    firebase.collection("Vehicles").document(ID).update("ridersUIDs", ridersID);
                    firebase.collection("Vehicles").document(ID).update("capacity", newCap);
                }
            }
        });

        startActivity(new Intent(this, vehiclesInfo.class));

    }



}