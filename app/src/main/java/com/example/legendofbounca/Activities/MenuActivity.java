package com.example.legendofbounca.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.legendofbounca.R;

public class MenuActivity extends AppCompatActivity {

    public void gyroscopeButtonClicked(View view) {
        Intent intent = new Intent(this, GyroActivity.class);
        startActivity(intent);
    }

    public void gravityButtonClicked(View view) {
        Intent intent = new Intent(this, GravityActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
