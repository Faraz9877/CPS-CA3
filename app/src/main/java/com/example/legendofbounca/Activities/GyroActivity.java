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

import java.util.Random;

public class GyroActivity extends AppCompatActivity implements SensorEventListener {

    //Sensor
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;

    public float readSensorTimestamp = 1;

    // Screen layout
    int layoutRight;
    int layoutBottom;

    // Ball positions
    private float x;
    private float y;
    private double vx = 0;
    private double vy = 0;
    private double fx;
    private double fy;
    int movingBallWidth;
    int movingBallHeight;
    private View movingBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravity);
        this.movingBall = findViewById(R.id.movingBall);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
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

    public void screenClick(View view) {
        View layout = findViewById(R.id.layout);
        this.layoutRight = layout.getRight();
        this.layoutBottom = layout.getBottom();

        this.movingBallWidth = movingBall.getWidth();
        this.movingBallHeight = movingBall.getHeight();

        Random random = new Random();
        this.x = random.nextInt(this.layoutRight - 1);
        this.y = random.nextInt(this.layoutBottom - 1);
        this.vx = 0;
        this.vy = 0;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float dT = (sensorEvent.timestamp - readSensorTimestamp) * Config.NS2US;
            if (dT > Config.READ_SENSOR_RATE) {
                double rotationAroundX = sensorEvent.values[0];
                double rotationAroundY = sensorEvent.values[1];
                double rotationAroundZ = sensorEvent.values[2];
                moveBall(rotationAroundX, rotationAroundY, rotationAroundZ, dT);
                readSensorTimestamp = sensorEvent.timestamp;
            }
        }
    }

    public void moveBall(double rotationAroundX, double rotationAroundY,
                         double rotationAroundZ, float dT) {
        final double time_slice = dT * Config.US2S;

        double tetha[] = {0, 0, Math.PI / 2};

        tetha[0] += rotationAroundX * dT * Config.US2S;
        tetha[1] += rotationAroundY * dT * Config.US2S;
        tetha[2] += rotationAroundZ * dT * Config.US2S;

        double gravityX = Config.STANDARD_GRAVITY * Math.sin(tetha[0]);
        double gravityY = Config.STANDARD_GRAVITY * Math.sin(tetha[1]);
        double gravityZ = Config.STANDARD_GRAVITY * Math.cos(tetha[2]);

        fx = vx == 0 ? (gravityX - (gravityZ * Config.MU_S)) : (gravityX - (gravityZ * Config.MU_K));
        fy = vy == 0 ? (gravityY - (gravityZ * Config.MU_S)) : (gravityY - (gravityZ * Config.MU_K));

        double ax = fx / Config.MASS;
        double ay = fy / Config.MASS;

        double newX = (0.5) * ax * Math.pow(time_slice, 2) + vx * time_slice + x;
        double newY = (0.5) * ay * Math.pow(time_slice, 2) + vy * time_slice + y;

        int RIGHTEST_POSITION = this.layoutRight - this.movingBallWidth;
        int BOTTOMMOST_POSITION = this.layoutBottom - this.movingBallHeight;
        x = (newX >= RIGHTEST_POSITION) ? RIGHTEST_POSITION : (float) ((newX <= 0) ? 0 : newX);
        y = (newY >= BOTTOMMOST_POSITION) ? BOTTOMMOST_POSITION : (float) ((newY <= 0) ? 0 : newY);

        double newVX = ax * time_slice + vx;
        double newVY = ay * time_slice + vy;

        vx = (newX >= RIGHTEST_POSITION || newX <= 0) ? -newVX * Config.LOSS_COEFFICIENT : newVX;
        vy = (newY >= BOTTOMMOST_POSITION || newY <= 0) ? -newVY * Config.LOSS_COEFFICIENT : newVY;

        movingBall.setX((float) this.x);
        movingBall.setY((float) this.y);
    }

}