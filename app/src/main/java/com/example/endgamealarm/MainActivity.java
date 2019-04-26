package com.example.endgamealarm;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextClock;
import android.widget.TimePicker;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TimePicker theAlarm;
    TextClock timeAsOfNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        theAlarm = findViewById(R.id.timePicker);
        timeAsOfNow = findViewById(R.id.textClock);
        final Ringtone alarmRing = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        Timer settingTime = new Timer();
        settingTime.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (timeAsOfNow.getText().toString().equals(TheAlarm())) {
                    alarmRing.play();
                } else {
                    alarmRing.stop();
                }

            }
        }, 0, 1000);
    }
    public String TheAlarm() {
        Integer laHoraDeAlarma = theAlarm.getCurrentHour();
        Integer losMinutos= theAlarm.getCurrentMinute();
        String theAlarmAsString;
        String theAlarmMinutesString;
        if (losMinutos < 10) {
            theAlarmAsString = "0";
            theAlarmMinutesString = theAlarmAsString.concat(losMinutos.toString());
        } else {
            theAlarmMinutesString = losMinutos.toString();
        }
        if(laHoraDeAlarma > 12) {
            laHoraDeAlarma = laHoraDeAlarma - 12;
            theAlarmAsString = laHoraDeAlarma.toString().concat(":").concat(theAlarmMinutesString).concat(" PM");
        } else {
            theAlarmAsString = laHoraDeAlarma.toString().concat(":").concat(theAlarmMinutesString).concat(" AM");
        }
        return theAlarmAsString;
    }
}
