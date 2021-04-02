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

public class GyroActivity extends AppCompatActivity implements SensorEventListener {

    //Sensor
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;

    public float readSensorTimestamp = 1;

    private View layout;

    private Ball ball;

    double[] tetha = {0, 0, Math.PI / 2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravity);
        this.layout = findViewById(R.id.gravityLayout);
        this.ball = new Ball((ImageView)findViewById(R.id.movingBall), 0, 0);
        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void screenClick(View view) {
        Random random = new Random();
        float x0 = random.nextInt(this.layout.getRight() - 1);
        float y0 = random.nextInt(this.layout.getBottom() - 1);
        ball.changeInitialPosition(x0, y0);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float dT = (sensorEvent.timestamp - readSensorTimestamp) * Config.NS2US;
            if (dT > Config.READ_SENSOR_RATE) {
                double rotationAroundX = sensorEvent.values[0];
                double rotationAroundY = sensorEvent.values[1];
                double rotationAroundZ = sensorEvent.values[2];

                tetha[0] += rotationAroundX * dT * Config.US2S;
                tetha[1] += rotationAroundY * dT * Config.US2S;
                tetha[2] += rotationAroundZ * dT * Config.US2S;

                double gravityX = Config.STANDARD_GRAVITY * Math.sin(tetha[0]);
                double gravityY = Config.STANDARD_GRAVITY * Math.sin(tetha[1]);
                double gravityZ = Config.STANDARD_GRAVITY * Math.cos(tetha[2]);

                ball.move(gravityX, gravityY, dT, this.layout);
                updateSensorValues(gravityX, gravityY, gravityZ);
                readSensorTimestamp = sensorEvent.timestamp;
            }
        }
    }

    public void updateSensorValues(double gravityX, double gravityY, double gravityZ) {
        TextView sensorStatus = findViewById(R.id.sensorValues);
        String sensorValues = String.format(Locale.ENGLISH, "gravity_x: %.2f\n" +
                        "gravity_y: %.2f\ngravity_z: %.2f\nx: %.2f\ny: %.2f\nvx: %.2f\nvy: %.2f\n",
                gravityX, gravityY, gravityZ, ball.getX(), ball.getY(), ball.getVx(), ball.getVy());
        sensorStatus.setText(sensorValues);
    }
}