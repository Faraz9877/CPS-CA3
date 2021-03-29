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

public class GyroActivity extends AppCompatActivity implements SensorEventListener {

    private float readSensorTimestamp = 1;

    private View movingBall;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro);
        this.movingBall = findViewById(R.id.movingBall);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);
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
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float dT = (sensorEvent.timestamp - readSensorTimestamp) * Config.NS2US;
            if (dT > Config.READ_SENSOR_RATE) {
                // rate of rotation around the x axis
                double rotationAroundX = sensorEvent.values[0];
                // rate of rotation around the y axis
                double rotationAroundY = sensorEvent.values[1];
                // rate of rotation around the z axis
                double rotationAroundZ = sensorEvent.values[2];
                // TODO: update ball with this three value.
                System.out.println("rotationAroundX:" + rotationAroundX +
                        " rotationAroundY:" + rotationAroundY +
                        " rotationAroundZ:" + rotationAroundZ);
                readSensorTimestamp = sensorEvent.timestamp;
            }
        }
    }
}