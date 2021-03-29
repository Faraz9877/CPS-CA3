package com.example.legendofbounca.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

import com.example.legendofbounca.R;

public class GravityActivity extends AppCompatActivity {

    private View movingBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravity);
        this.movingBall = findViewById(R.id.movingBall);
    }

    private void setPosition(double newX, double newY) {
        movingBall.setX((float) newX);
        movingBall.setY((float) newY);
    }
}