package com.example.teamsclone2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth authorise;
    TextInputLayout emailInput,passwordInput,nameInput,phoneInput;
    Button loginButton,newAccountButton;
    FirebaseFirestore database;
    View daySky,nightSky;
    DayNightSwitch dayNightSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        authorise = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        emailInput = findViewById(R.id.InputEmail);
        passwordInput = findViewById(R.id.InputPassword);
        nameInput = findViewById(R.id.UserName);
        phoneInput = findViewById(R.id.InputPhoneNumber);
        daySky = findViewById(R.id.day_bg);
        nightSky = findViewById(R.id.night_bg);
        dayNightSwitch = findViewById(R.id.day_night_switch);

        newAccountButton = findViewById(R.id.CreateNewAccountButton);

        loginButton = findViewById(R.id.BacktoLoginScreen);

        dayNightSwitch.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean is_night) {
                if(is_night)
                {
                    daySky.animate().alpha(0).setDuration(1000);
                }
                else
                {
                    daySky.animate().alpha(1).setDuration(1000);
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,FirstLoginActivity.class));
            }
        });

        //storing new account data in Firebase Database in collection path UsersData
        //UserInfo is the JAVA class made for storing the SignUp Activity fields
        newAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password,name,phone;
                email = emailInput.getEditText().getText().toString();
                password = passwordInput.getEditText().getText().toString();
                name = nameInput.getEditText().getText().toString();
                phone = phoneInput.getEditText().getText().toString();

                UserInfo user = new UserInfo();
                user.setEmail(email);
                user.setName(name);
                user.setPassword(password);
                user.setPhonenumber(phone);


                authorise.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            database.collection("UsersData")
                                    .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    startActivity(new Intent(SignUpActivity.this,FirstLoginActivity.class));
                                }
                            });
                            Toast.makeText(SignUpActivity.this, "New Account created successfully!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


    }
}