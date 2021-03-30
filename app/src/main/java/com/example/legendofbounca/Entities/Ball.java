package com.example.legendofbounca.Entities;

import android.view.View;
import android.widget.ImageView;

import com.example.legendofbounca.Config.Config;
import com.example.legendofbounca.R;

public class Ball {
    ImageView imageView;
    private float mass;
    private float x, y;
    private float vx, vy;
    private float ax, ay;

    public Ball(ImageView imageView, float x, float y, float mass) {
        this.imageView = imageView;
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.vx = 0;
        this.vy = 0;
    }

    public void changeInitialPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;

        imageView.setX(x);
        imageView.setY(y);
    }

    public void move(float gravityX, float gravityY, float gravityZ, float dT, View boardLayout) {
        final float time_slice = dT * Config.US2S;
        float fx = vx == 0 ? (gravityX - (gravityZ * Config.MU_S)) : (gravityX - (gravityZ * Config.MU_K));
        float fy = vy == 0 ? (gravityY - (gravityZ * Config.MU_S)) : (gravityY - (gravityZ * Config.MU_K));

        float ax = fx / Config.MASS;
        float ay = fy / Config.MASS;

        float newX = (0.5f) * ax * time_slice * time_slice + vx * time_slice + x;
        float newY = (0.5f) * ay * time_slice * time_slice  + vy * time_slice + y;

        int RightWall = boardLayout.getRight() - this.imageView.getWidth();
        int Floor = boardLayout.getBottom() - this.imageView.getHeight();
        x = (newX >= RightWall) ? RightWall : (float) ((newX <= 0) ? 0 : newX);
        y = (newY >= Floor) ? Floor : (float) ((newY <= 0) ? 0 : newY);

        float newVX = ax * time_slice + vx;
        float newVY = ay * time_slice + vy;

        vx = (newX >= RightWall || newX <= 0) ? -newVX * Config.LOSS_COEFFICIENT : newVX;
        vy = (newY >= Floor || newY <= 0) ? -newVY * Config.LOSS_COEFFICIENT : newVY;

        this.imageView.setX((float) this.x);
        this.imageView.setY((float) this.y);
    }

    public int getWidth() {
        return this.imageView.getWidth();
    }

    public int getHeight() {
        return this.imageView.getHeight();
    }
}
