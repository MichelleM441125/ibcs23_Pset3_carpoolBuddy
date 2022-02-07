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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.rpc.context.AttributeContext;

import java.util.ArrayList;
import java.util.Objects;

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


        mAuth.createUserWithEmailAndPassword(newUserEmail, newUserPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    User newUser = new User(newUserName, newUserType, newUserEmail, newUserPassword, myVehicles, 300.0);
                    firebase.collection("User").add(newUser);

                    Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Oops, something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void updateUI(FirebaseUser user)
    {
        if(user != null)
        {
            startActivity(new Intent(this, AuthCarpool.class));
        }
    }


}