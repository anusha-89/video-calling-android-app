package com.example.teamsclone2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FirstLoginActivity extends AppCompatActivity {

    TextInputLayout emailInput,passwordInput;
    Button loginButton,newAccountButton,forgotPasswordButton,phoneSignUpButton;
    FirebaseAuth authorise;
    View daySky,nightSky;
    DayNightSwitch dayNightSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);
        authorise = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.InputEmail);
        passwordInput = findViewById(R.id.InputPassword);
        daySky = findViewById(R.id.day_bg);
        nightSky = findViewById(R.id.night_bg);
        dayNightSwitch = findViewById(R.id.day_night_switch);

        //day-night theme changer switch
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

        loginButton = findViewById(R.id.LoginButton);
        newAccountButton = findViewById(R.id.NewAccountButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        phoneSignUpButton = findViewById(R.id.signInUsingPhone);

        //directing user to SendOTP Activity if "Login using phone number" button is clicked
        phoneSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstLoginActivity.this, com.example.teamsclone2.SendOTPActivity.class));
            }
        });

        //sign in user using FireBase Authentication
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email = emailInput.getEditText().getText().toString();
                password = passwordInput.getEditText().getText().toString();
                authorise.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(FirstLoginActivity.this, "Logging you in!", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(FirstLoginActivity.this,EnteringRoomActivity.class));
                       }else{
                           Toast.makeText(FirstLoginActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                       }
                    }
                });
            }
        });

         //sending password reset link to user using FireBase
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter your email to receive password reset link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //sending reset link
                        String mail = resetMail.getText().toString();
                        authorise.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(FirstLoginActivity.this, "Reset Link Sent to your Email! ", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(FirstLoginActivity.this, "Error! Reset Link not sent! "+ e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close the dialog

                    }
                });

                passwordResetDialog.create().show();


            }
        });

        newAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstLoginActivity.this, com.example.teamsclone2.SignUpActivity.class));
            }
        });
    }
}