package com.example.teamsclone2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class EnteringRoomActivity extends AppCompatActivity {

    EditText codeText;
    Button joinButton,codeSharingButton,gameButton,schedulingMeetingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entering_room);

        URL videoCallUrl;
        try {
            videoCallUrl = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions byDeafault =
                    new  JitsiMeetConferenceOptions.Builder()
                            .setServerURL(videoCallUrl)
                            .setWelcomePageEnabled(false)
                            .build();
            JitsiMeet.setDefaultConferenceOptions(byDeafault);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        codeText = findViewById(R.id.roomCode);
        joinButton = findViewById(R.id.joinButton);
        codeSharingButton = findViewById(R.id.sharingButton);
        gameButton = findViewById(R.id.gameButton);
        schedulingMeetingBtn = findViewById(R.id.schedulingMeeting);

        //schedule meeting activity
        schedulingMeetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnteringRoomActivity.this, com.example.teamsclone2.scheduleMeetingActivity.class));
            }
        });

        //TIC-TAC-TOE game
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnteringRoomActivity.this,TicTacToeGame.class));
            }
        });

        //sharing the room code on multiple platforms
        codeSharingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codeText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(EnteringRoomActivity.this, "Enter room code", Toast.LENGTH_SHORT).show();
                    return;
                }

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String shareBody = "Join the meeting room by entering the code " + codeText.getText().toString() + ".";
                    String sharesub = "Video Calling App";
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
                    shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);

                    startActivity(Intent.createChooser(shareIntent,"Share using"));

            }
        });


        //launching JITSI
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JitsiMeetConferenceOptions conferenceTalk = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(codeText.getText().toString())
                        .setWelcomePageEnabled(false)
                        .build();

                JitsiMeetActivity.launch(EnteringRoomActivity.this,conferenceTalk);
            }
        });

    }
}