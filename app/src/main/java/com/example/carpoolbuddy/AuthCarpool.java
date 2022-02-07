package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.UUID;

// this class is for user to sign in
public class AuthCarpool extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public FirebaseFirestore firebase;
    private EditText userEmail;
    private EditText userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_carpool);

        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseFirestore.getInstance();
        userEmail = findViewById(R.id.editTextEmail);
        userPassword = findViewById(R.id.editTextPassword);

        // if the user already signed in, then directly send he or she to the user profile
        if (mAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
        }

    }

    public void logIn(View v)
    {
        String emailField = userEmail.getText().toString();
        String passwordField = userPassword.getText().toString();

        // sign in the user via firebase authentication
        mAuth.signInWithEmailAndPassword(emailField, passwordField).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("Sign in", "Successfully logged in the user");
                    Toast.makeText(getApplicationContext(), "welcome", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    gotoUserProfile(user);
                }
                else
                {
                    Log.w("Sign in", "fail", task.getException());
                    Toast.makeText(getApplicationContext(),
                            "fail to sign in, check your email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // if the user is a new user, direct
    public void signUp(View x) {
        Intent intent = new Intent(this, newUser.class);
        startActivity(intent);
    }

    // this method check whether the user is successfully signed up through mAuth
    public void gotoUserProfile(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
        }

    }
}

