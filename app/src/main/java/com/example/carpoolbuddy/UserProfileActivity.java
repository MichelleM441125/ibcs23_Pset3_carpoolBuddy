package com.example.carpoolbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();
        userEmail = findViewById(R.id.signInEmail);

        String userEmailDisplay = mAuth.getCurrentUser().getEmail();
        userEmail.setText(userEmailDisplay);
    }

    public void signOut(View v)
    {
        mAuth.signOut();
        startActivity(new Intent(this,AuthCarpool.class));
        System.out.println("Sign Out Successfully");
        finish();
    }

    public void seeVehicles(View v)
    {
        startActivity(new Intent(this, vehiclesInfo.class));
        System.out.println("ayeeeeee");
    }
}