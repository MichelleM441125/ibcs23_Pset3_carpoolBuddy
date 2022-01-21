package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.UUID;

public class AuthCarpool extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public FirebaseFirestore firebase;

    private EditText userEmail;
    private EditText userPassword;
    private ArrayList<String> emails;

    private String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_carpool);


        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseFirestore.getInstance();

        userEmail = findViewById(R.id.editTextEmail);
        userPassword = findViewById(R.id.editTextPassword);

        emails = new ArrayList<>();
    }

    public void logIn(View v)
    {
        String emailField = userEmail.getText().toString();
        String passwordField = userPassword.getText().toString();

        if(emails.contains(emailField))
        {
            mAuth.signInWithEmailAndPassword(emailField,passwordField);
            Toast.makeText(getApplicationContext(),"Welcome!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please Log In First", Toast.LENGTH_SHORT).show();
        }

    }

    public void signUp(View x)
    {
        Intent intent = new Intent(this, newUser.class);
        startActivity(intent);

        //mAuth.createUserWithEmailAndPassword(emailField, passwordField).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

        /*
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Log.d("SIGN UP", "Successfully signed up the new user");
                    FirebaseUser user = mAuth.getCurrentUser();
                    firebase.collection("Users").document("Teachers").set(user);
                    updateUI(user);
                }
                else
                {
                    Log.w("SIGN UP", "createUserWithEmail:failure", task.getException());
                    //Toast.makeText(this,"Authentication failed.", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });

         */
    }

}