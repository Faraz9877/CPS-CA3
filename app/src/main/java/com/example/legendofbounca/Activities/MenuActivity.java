package com.example.legendofbounca.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.legendofbounca.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button gravityButton = findViewById(R.id.gravityButton);
        Button gyroButton = findViewById(R.id.gyroscopeButton);

        gravityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, GravityActivity.class);
                startActivity(intent);
            }
        });

        gyroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, GyroActivity.class);
                startActivity(intent);
            }
        });
    }
}
