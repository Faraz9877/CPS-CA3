package com.example.legendofbounca.Entities;

import android.hardware.SensorManager;
import android.view.View;
import android.widget.ImageView;

import com.example.legendofbounca.Config.Config;
import com.example.legendofbounca.R;

import java.util.zip.CheckedOutputStream;

public class Ball {
    ImageView imageView;
    private float x, y;
    private float vx, vy;
    private float mass;

    public Ball(ImageView imageView, float x, float y, float mass) {
        this.imageView = imageView;
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.mass = mass;
    }

    public void changeInitialPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;

        imageView.setX(x);
        imageView.setY(y);
    }

    public void moveByGravity(float gravityX, float gravityY, float time_slice, View boardLayout) {
        float xN = (float) (this.mass * SensorManager.STANDARD_GRAVITY);
        float yN = (float) (this.mass * SensorManager.STANDARD_GRAVITY);

        int RightWall = boardLayout.getRight() - this.getWidth();
        int Floor = boardLayout.getBottom() - this.getHeight();

        if(this.vx == 0) {
            if(Math.abs(this.mass * gravityX) >= Math.abs(xN * Config.MU_S)) {
                this.vx += (gravityX * time_slice);
                float delta_x = (float) (gravityX * Math.pow(time_slice, 2) / 2 + this.vx * time_slice) * 250;
                this.x -= delta_x;
            }
        } else {
            if(this.vx > 0)
                gravityX -= this.mass / (Config.MU_K * xN);
            else
                gravityX += this.mass / (Config.MU_K * xN);
            this.vx += (gravityX * time_slice);
            float delta_x = (float) (gravityX * Math.pow(time_slice, 2) / 2 + this.vx * time_slice) * 250;
            this.x -= delta_x;
        }

        if (this.x >= RightWall) {
            this.x = RightWall;
            this.vx = (float) -Math.sqrt(Config.LOSS_COEFFICIENT) * this.vx;
        } else if (this.x <= 0) {
            this.x = 0;
            this.vx = (float) -Math.sqrt(Config.LOSS_COEFFICIENT) * this.vx;
        }

        if(this.vy == 0) {
            if(Math.abs(this.mass * gravityY) >= Math.abs(yN * Config.MU_S)) {
                this.vy += (gravityY * time_slice);
                float delta_y = (float) (gravityY * Math.pow(time_slice, 2) / 2 + this.vy * time_slice) * 250;
                this.y += delta_y;
            }
        } else {
            if(this.vy > 0)
                gravityY -= this.mass / (Config.MU_K * yN);
            else
                gravityY += this.mass / (Config.MU_K * yN);
            this.vy += (gravityY * time_slice);
            float delta_y = (float) (gravityY * Math.pow(time_slice, 2) / 2 + this.vy * time_slice) * 250;
            this.y += delta_y;
        }

        if (this.y > Floor) {
            this.y = Floor;
            this.vy =  (float) -Math.sqrt(Config.LOSS_COEFFICIENT) * this.vy;
        } else if (this.y < 0) {
            this.y = 0;
            this.vy =  (float) -Math.sqrt(Config.LOSS_COEFFICIENT) * this.vy;
        }

        this.imageView.setX(this.x);
        this.imageView.setY(this.y);
    }

    public int getWidth() {
        return this.imageView.getWidth();
    }

    public int getHeight() {
        return this.imageView.getHeight();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }
}
