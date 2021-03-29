package com.example.legendofbounca.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

import com.example.legendofbounca.Config.Config;
import com.example.legendofbounca.R;

public class GravityActivity extends AppCompatActivity implements SensorEventListener{

    public float readSensorTimestamp = 1;
    private View movingBall;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravity);
        this.movingBall = findViewById(R.id.movingBall);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GRAVITY) {
            float dT = (sensorEvent.timestamp - readSensorTimestamp) * Config.NS2US;
            if (dT > Config.READ_SENSOR_RATE) {
                double gravityX = sensorEvent.values[0];
                double gravityY = sensorEvent.values[1];
                double gravityZ = sensorEvent.values[2];
                // TODO: update ball with this three value.
                System.out.println("gravityX:" + gravityX + " gravityY:" + gravityY + " gravityZ:" + gravityZ);
                readSensorTimestamp = sensorEvent.timestamp;
            }
        }
    }


}