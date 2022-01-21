package com.example.carpoolbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class newUser extends AppCompatActivity {

    private EditText name;
    private EditText userType;
    private EditText userEmail;
    private EditText userPassword;

    public FirebaseAuth mAuth;
    public FirebaseFirestore firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseFirestore.getInstance();

        name = findViewById(R.id.userName);
        userType = findViewById(R.id.userType);

        userEmail = findViewById(R.id.email);
        userPassword = findViewById(R.id.password);

    }

    public void completeSignUp(View v)
    {
        String newUserName = name.getText().toString();
        String newUserType = userType.getText().toString();
        String newUserEmail = userEmail.getText().toString();
        String newUserPassword = userPassword.getText().toString();
        ArrayList<String> myVehicles = new ArrayList<>();

        User newUser = new User(newUserName, newUserType, newUserEmail, newUserPassword, myVehicles);
        firebase.collection("User").add(newUser);

        mAuth.signInWithEmailAndPassword(newUserEmail,newUserPassword);

        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }


}