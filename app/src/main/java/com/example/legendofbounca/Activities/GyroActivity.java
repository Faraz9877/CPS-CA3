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

public class GyroActivity extends AppCompatActivity {

    private float readSensorTimestamp = 1;

    private View movingBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro);
        this.movingBall = findViewById(R.id.movingBall);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        setupGyroscopeSensor(sensorManager);
    }

    private void setupGyroscopeSensor(SensorManager sensorManager){
        Sensor gravity_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        SensorEventListener gyroscopeSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
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

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(gyroscopeSensorListener, gravity_sensor,
                SensorManager.SENSOR_DELAY_FASTEST);

    }
}