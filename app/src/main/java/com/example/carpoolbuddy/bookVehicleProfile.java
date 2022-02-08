package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    private String thisUserID;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebase;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_vehicle_profile);

        bookNameTextView = findViewById(R.id.bookVehicleName);
        bookTypeTextView = findViewById(R.id.bookVehicleType);
        bookIdTextView = findViewById(R.id.bookVehicleID);
        bookPriceTextView = findViewById(R.id.bookVehiclePrice);


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
        // get the document ID of the current user
        firebase.collection("User").whereEqualTo("email", user.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                for (DocumentSnapshot ds : task.getResult().getDocuments())
                {
                    thisUserID = ds.getId();
                }
            }
        });

        // get the vehicle document via its model name and "bookable" capacity
        firebase.collection("Vehicles").whereEqualTo("model", chosenVC.getModel())
                .whereNotEqualTo("capacity", 0).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                for (DocumentSnapshot ds : task.getResult().getDocuments())
                {
                    Vehicle getVehicle = ds.toObject(Vehicle.class);
                    // set the new arraylist to the ridersID arraylist of the chosen vehicle
                    ridersID = getVehicle.getRidersIDs();
                    // get its current capacity
                    currCap = getVehicle.getCapacity();
                    // get the ID of the document and use it to update the ridersID arraylist
                    String ID = ds.getId();
                    firebase.collection("Vehicles").document(ID).update("ridersIDs", ridersID);
                    // minus one from the current capacity
                    Integer newCap = currCap - 1;
                    // and update the capacity of the chosen vehicle
                    firebase.collection("Vehicles").document(ID).update("capacity", newCap);
                    if(currCap == 0)
                    {
                        Toast.makeText(getApplicationContext(),"Sorry There's No Seat Left",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        startActivity(new Intent(this, vehiclesInfo.class));

    }



}