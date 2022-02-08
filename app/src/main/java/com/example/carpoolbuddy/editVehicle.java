
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class editVehicle extends AppCompatActivity {

    private TextView name;
    private TextView newType;
    private TextView newID;
    private TextView newCapacity;
    private TextView newPrice;

    // the name of the vehicle that is being edited
    private String editVcName;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);

        // get the name of the editing vehicle
        editVcName = getIntent().getStringExtra("editV");

        name = findViewById(R.id.editVcNameView);
        name.setText(editVcName);
        newType = findViewById(R.id.newTypeText);
        newID = findViewById(R.id.newIdText);
        newCapacity = findViewById(R.id.newCapText);
        newPrice = findViewById(R.id.newPriceText);

        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseFirestore.getInstance();
    }

    public void edit(View v)
    {
        // read in the all the input
        String newtype = newType.getText().toString();
        int newcapacity = Integer.parseInt(newCapacity.getText().toString());
        String newid = newID.getText().toString();
        double newBasePrice = Double.parseDouble(String.valueOf(newPrice.getText()));

       // get the chosen vehicle from the database using its model name
        firebase.collection("Vehicles").whereEqualTo("model", editVcName)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                for (DocumentSnapshot ds : task.getResult().getDocuments())
                {
                    // update the information with the ID of the document
                    String ID = ds.getId();
                    firebase.collection("Vehicles").document(ID)
                            .update("vehicleType", newtype);
                    firebase.collection("Vehicles").document(ID)
                            .update("capacity", newcapacity);
                    firebase.collection("Vehicles").document(ID)
                            .update("vehicleID", newid);
                    firebase.collection("Vehicles").document(ID)
                            .update("basePrice", newBasePrice);
                }
            }
        });

        Toast.makeText(getApplicationContext(),"Successfully Edited!", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, vehiclesInfo.class));
    }

}
