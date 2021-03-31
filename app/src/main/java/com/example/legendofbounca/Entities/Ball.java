package com.example.legendofbounca.Entities;

import android.view.View;
import android.widget.ImageView;

import com.example.legendofbounca.Config.Config;
import com.example.legendofbounca.R;

public class Ball {
    ImageView imageView;
    private float x, y;
    private float vx, vy;
    private float ax, ay;

    public Ball(ImageView imageView, float x, float y, float mass) {
        this.imageView = imageView;
        this.x = x;
        this.y = y;
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
        double angleX = Math.asin(gravityX / Config.STANDARD_GRAVITY);
        double angleY = Math.asin(gravityY / Config.STANDARD_GRAVITY);

        int RightWall = boardLayout.getRight() - this.imageView.getWidth();
        int Floor = boardLayout.getBottom() - this.imageView.getHeight();

        boolean bottomX = gravityX > 0; // False for Leftwall as bottom, true for RightWall as bottom
        boolean bottomY = gravityY > 0; // False for Ceiling as bottom, true for Floor as bottom

        boolean isOnBottomX = ((!bottomX || gravityX == 0) && x == 0 || (bottomX || gravityX == 0) && x == RightWall);
        boolean isOnBottomY = ((!bottomY || gravityY == 0) && y == 0 || (bottomY || gravityY == 0) && y == Floor);

        final float time_slice = dT * Config.US2S;

        float fx, fy;

        if(isOnBottomX) {
            fy = (vx == 0 && vy == 0 && Math.tan(angleX) < Config.MU_S) ? 0 :
                    (gravityY - (vy != 0 ? vy / Math.abs(vy) : 0) * Math.abs(gravityX) * Config.MU_K) * Config.MASS;
        }
        else {
            fy = gravityY * Config.MASS;
        }

        if(isOnBottomY) {
            fx = (vy == 0 && vx == 0 && Math.tan(angleY) < Config.MU_S) ? 0 :
                    (gravityX - (vx != 0 ? vx / Math.abs(vx) : 0) * Math.abs(gravityY) * Config.MU_K) * Config.MASS;
        }
        else {
            fx = gravityX * Config.MASS;
        }

        float ax = fx / Config.MASS * Config.ACCELERATION_COEFFICIENT;
        float ay = fy / Config.MASS * Config.ACCELERATION_COEFFICIENT;

        float newX = (0.5f) * ax * time_slice * time_slice + vx * time_slice + x;
        float newY = (0.5f) * ay * time_slice * time_slice  + vy * time_slice + y;

        x = (newX >= RightWall) ? RightWall : (newX <= 0) ? 0 : newX;
        y = (newY >= Floor) ? Floor : (newY <= 0) ? 0 : newY;

        float newVX = ax * time_slice + vx;
        float newVY = ay * time_slice + vy;

        vx = (newX >= RightWall || newX <= 0) ? (float) (-newVX) : newVX;
        vy = (newY >= Floor || newY <= 0) ? (float) (-newVY) : newVY;

        double collisionReduction = (newX >= RightWall || newX <= 0 || newY >= Floor || newY <= 0) ?
                Math.sqrt(Config.LOSS_COEFFICIENT) : 1;
        vx *= collisionReduction;
        vy *= collisionReduction;

        if(isOnBottomX && Math.abs(vx) < 20)
            vx = 0;
        if(isOnBottomY && Math.abs(vy) < 20)
            vy = 0;

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
