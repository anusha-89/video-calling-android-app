package com.example.teamsclone2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.allyants.notifyme.NotifyMe;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class scheduleMeetingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    Calendar now = Calendar.getInstance();
    TimePickerDialog tpd;
    DatePickerDialog dpd;
    EditText meetingDescription;
    TextView scheduleMeetingText;

    //when activity is created
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_meeting);
        Button NotifyBtn = findViewById(R.id.scheduleMeetingButton);
        meetingDescription = findViewById(R.id.meetingDescription);
        scheduleMeetingText = findViewById(R.id.meetingTitle);

        Button cancelBtn = findViewById(R.id.btnCancel);

        //initialising Date Picker dialog with current date
        dpd = DatePickerDialog.newInstance(
                scheduleMeetingActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        //initialising Time Picker dialog with current time
        tpd = TimePickerDialog.newInstance(
                scheduleMeetingActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                false
        );


        //cancel Notification
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(scheduleMeetingActivity.this,EnteringRoomActivity.class));
            }
        });

        //show Notification
        NotifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd.show(getFragmentManager(),"Datepickerdialog");

            }
        });
    }

    //setting custom date to Date Picker Dialog
    @Override
    public void onDateSet (DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        now.set(Calendar.YEAR,year);
        now.set(Calendar.MONTH,monthOfYear);
        now.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        tpd.show(getFragmentManager(),"Timepickerdialog");

    }

    //Handling notification actions
    public void onTimeSet (TimePickerDialog view, int hourOfDay, int minute, int second) {
        now.set(Calendar.HOUR_OF_DAY,hourOfDay);
        now.set(Calendar.MINUTE,minute);
        now.set(Calendar.SECOND,second);
        Toast.makeText(this, "Meeting scheduled successfully! You'll receive a notification at the scheduled time!", Toast.LENGTH_SHORT).show();

        //Notification initialisation
        NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                .title(scheduleMeetingText.getText().toString())
                .content(meetingDescription.getText().toString())
                .color(255,0,0,255)
                .led_color(255,255,255,255)
                .time(now)
                .addAction(new Intent(),"Snooze",false)
                .key("Test")
                .addAction(new Intent(),"Dismiss",true,false)
                .addAction(new Intent(),"Done")
                .large_icon(R.drawable.teamsicon)
                .build();

    }
}