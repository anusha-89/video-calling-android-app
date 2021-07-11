package com.example.teamsclone2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {


    TextView introdtext;
    LottieAnimationView lottie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        lottie = findViewById(R.id.lottie_animation);
        introdtext = findViewById(R.id.introText);


        //setting animation for main Activity
        introdtext.animate().translationY(-200).setDuration(1500).setStartDelay(0);
        lottie.animate().translationX(2000).setDuration(1500).setStartDelay(2000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, com.example.teamsclone2.FirstLoginActivity.class));

            }
        },3000);

    }
}