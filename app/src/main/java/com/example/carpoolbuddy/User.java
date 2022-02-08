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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.UUID;


public class User {
    public String name;
    public String email;
    public String password;
    public String userType;
    public ArrayList<String> ownedVehicles;
    public double money;

    public User()
    {

    }

    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public User(String name, String userType, String email, String password, ArrayList<String>ownedVehicles, double money)
    {
        this.name = name;
        this.userType = userType;
        this.email = email;
        this.password = password;
        this.ownedVehicles = ownedVehicles;
        this.money = money;

    }

    public ArrayList<String> getOwnedVehicles() {
        return ownedVehicles;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public double getMoney() {
        return money;
    }
}