package com.example.endgamealarm;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextClock;
import android.widget.TimePicker;

import java.util.Timer;
import java.util.TimerTask;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    TimePicker theAlarm;
    TextClock timeAsOfNow;
    // alarm has been turned off.
    private boolean alarmYeet = true;
    // alarm status.
    private boolean isRinging;
    private TextView xText, yText, zText;
    // sensor variables.
    private Sensor theSensor;
    private SensorManager management;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        management = (SensorManager)getSystemService(SENSOR_SERVICE);
        theSensor = management.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        management.registerListener(this, theSensor, SensorManager.SENSOR_DELAY_NORMAL);
        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);
        // the Alarm.
        theAlarm = findViewById(R.id.timePicker);
        timeAsOfNow = findViewById(R.id.textClock);
        final Ringtone alarmRing = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        Timer settingTime = new Timer();
        settingTime.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (alarmYeet) {
                    if (timeAsOfNow.getText().toString().equals(TheAlarm())) {
                        alarmRing.play();
                        isRinging = true;
                    }
                    if (isRinging && counter >= 25) {
                        alarmRing.stop();
                        counter = 0;
                        isRinging = false;
                        alarmYeet = false;
                    }
                }
                if (!(timeAsOfNow.getText().toString().equals(TheAlarm()))) {
                    alarmYeet = true;
                }
            }
        }, 0, 1000);
    }

    // accelerometer detection
    @Override
    public void onSensorChanged(SensorEvent event) {
        xText.setText("X; " + event.values[0]);
        yText.setText("Y; " + event.values[1]);
        zText.setText("Z; " + event.values[2]);
        if (isRinging) {
            if (event.values[1] > 9 || event.values[1] < 9) {
                counter++;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Do not interfere with that which you do not understand. Mainly because there will be an error.
    }

    public String TheAlarm() {
        Integer laHoraDeAlarma = theAlarm.getCurrentHour();
        Integer losMinutos = theAlarm.getCurrentMinute();
        String theAlarmAsString;
        String theAlarmMinutesString;
        if (losMinutos < 10) {
            theAlarmAsString = "0";
            theAlarmMinutesString = theAlarmAsString.concat(losMinutos.toString());
        } else {
            theAlarmMinutesString = losMinutos.toString();
        }
        if (laHoraDeAlarma > 12) {
            laHoraDeAlarma = laHoraDeAlarma - 12;
            theAlarmAsString = laHoraDeAlarma.toString().concat(":").concat(theAlarmMinutesString).concat(" PM");
        } else {
            theAlarmAsString = laHoraDeAlarma.toString().concat(":").concat(theAlarmMinutesString).concat(" AM");
        }
        return theAlarmAsString;
    }
}
