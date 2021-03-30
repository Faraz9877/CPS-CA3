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

    //Sensor
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
        setupBall();
        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
    }

    private void setupBall() {
        Random random = new Random();
        float x0 = random.nextInt(this.layout.getRight() - 1);
        float y0 = random.nextInt(this.layout.getBottom() - 1);
        this.ball = new Ball((ImageView)findViewById(R.id.movingBall), x0, y0, Config.MASS);

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
                ball.move(gravityX, gravityY, gravityZ, dT, this.layout);
//                moveBall(gravityX, gravityY, gravityZ, dT);
//                updateSensorValues(gravityX, gravityY, gravityZ);
            }
        }
    }

//    public void moveBall(float gravityX, float gravityY, float gravityZ, float dT) {
//        final float time_slice = dT * Config.US2S;
//        fx = vx == 0 ? (gravityX - (gravityZ * Config.MU_S)) : (gravityX - (gravityZ * Config.MU_K));
//        fy = vy == 0 ? (gravityY - (gravityZ * Config.MU_S)) : (gravityY - (gravityZ * Config.MU_K));
//
//        float ax = fx / Config.MASS;
//        float ay = fy / Config.MASS;
//
//        float newX = (0.5f) * ax * time_slice * time_slice + vx * time_slice + x;
//        float newY = (0.5f) * ay * time_slice * time_slice  + vy * time_slice + y;
//
//        View layout = findViewById(R.id.gravityLayout);
//
//        int RIGHTEST_POSITION = layout.getRight() - ball.getWidth();
//        int BOTTOMMOST_POSITION = layout.getBottom() - ball.getHeight();
//        x = (newX >= RIGHTEST_POSITION) ? RIGHTEST_POSITION : (float) ((newX <= 0) ? 0 : newX);
//        y = (newY >= BOTTOMMOST_POSITION) ? BOTTOMMOST_POSITION : (float) ((newY <= 0) ? 0 : newY);
//
//        float newVX = ax * time_slice + vx;
//        float newVY = ay * time_slice + vy;
//
//        vx = (newX >= RIGHTEST_POSITION || newX <= 0) ? -newVX * Config.LOSS_COEFFICIENT : newVX;
//        vy = (newY >= BOTTOMMOST_POSITION || newY <= 0) ? -newVY * Config.LOSS_COEFFICIENT : newVY;
//
//        ball.setX((float) this.x);
//        ball.setY((float) this.y);
//    }

//    public void updateSensorValues(float gravityX, float gravityY, float gravityZ) {
//        TextView sensorStatus = findViewById(R.id.sensorValues);
//        String sensorValues = String.format(Locale.ENGLISH, "gravity_x: %.2f\n" +
//                "gravity_y: %.2f\ngravity_z: %.2f\nx: %.2f\ny: %.2f\nvx: %.2f\nvy: %.2f\n",
//                gravityX, gravityY, gravityZ, this.x, this.y, this.vx, this.vy);
//        sensorStatus.setText(sensorValues);
//    }



}