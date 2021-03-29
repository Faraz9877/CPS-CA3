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

public class GravityActivity extends AppCompatActivity {

    public float readSensorTimestamp = 1;
    private View movingBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravity);
        this.movingBall = findViewById(R.id.movingBall);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        setupGravitySensor(sensorManager);
    }


    private void setupGravitySensor(SensorManager sensorManager){
        Sensor gravity_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        SensorEventListener gravitySensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
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

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(gravitySensorListener, gravity_sensor,
                SensorManager.SENSOR_DELAY_FASTEST);

  }
}