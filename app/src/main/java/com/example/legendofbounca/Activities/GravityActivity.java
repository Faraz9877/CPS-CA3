package com.example.legendofbounca.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.legendofbounca.Config.Config;
import com.example.legendofbounca.Entities.Ball;
import com.example.legendofbounca.R;

import java.util.Locale;
import java.util.Random;

public class GravityActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor gravitySensor;
    public float readSensorTimestamp = 1;
    private View layout;
    private Ball ball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravity);
        this.layout = findViewById(R.id.gravityLayout);
        this.ball = new Ball((ImageView)findViewById(R.id.movingBall), 0, 0, Config.MASS);
        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
    }

    public void screenClick(View view) {
        Random random = new Random();
        float x0 = random.nextInt(this.layout.getRight() - 1);
        float y0 = random.nextInt(this.layout.getBottom() - 1);
        ball.changeInitialPosition(x0, y0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_GAME);
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
                float gravityX = - sensorEvent.values[0];
                float gravityY = sensorEvent.values[1];
                float gravityZ = sensorEvent.values[2];
                readSensorTimestamp = sensorEvent.timestamp;
                ball.move(gravityX, gravityY, dT, this.layout);
                updateSensorValues(gravityX, gravityY, gravityZ);
            }
        }
    }

    public void updateSensorValues(float gravityX, float gravityY, float gravityZ) {
        TextView sensorStatus = findViewById(R.id.sensorValues);
        String sensorValues = String.format(Locale.ENGLISH, "gravity_x: %.2f\n" +
                "gravity_y: %.2f\ngravity_z: %.2f\nx: %.2f\ny: %.2f\nvx: %.2f\nvy: %.2f\n",
                gravityX, gravityY, gravityZ, ball.getX(), ball.getY(), ball.getVx(), ball.getVy());
        sensorStatus.setText(sensorValues);
    }



}