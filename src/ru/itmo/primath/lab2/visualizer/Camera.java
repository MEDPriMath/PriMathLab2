package ru.itmo.primath.lab2.visualizer;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

public class Camera {
    float x, y, z, yaw, pitch;

    public Camera() {
        this(0, 0, 0, 0, 0);
    }

    public Camera(float x, float y, float z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void reset() {
        x = 0;
        y = 0;
        z = 0;
        yaw = 0;
        pitch = 0;
    }

    public void apply() {
        glRotatef(-pitch, 1, 0, 0);
        glRotatef(-yaw, 0, 1, 0);
        glTranslatef(-x, -y, -z);
    }

    public void rotate(double yaw, double pitch) {
        this.yaw += yaw;
        if (this.yaw < -180) this.yaw += 360;
        if (this.yaw > 180) this.yaw -= 360;
        this.pitch += pitch;
        if (this.pitch < -90) this.pitch = -90;
        if (this.pitch > 90) this.pitch = 90;
    }

    public void move(Direction direction, double speed) {
        double rads = yaw / 180 * Math.PI;
        switch (direction) {
            case FORWARD -> {
                z -= speed * Math.cos(rads);
                x -= speed * Math.sin(rads);
            }
            case BACKWARD -> {
                z += speed * Math.cos(rads);
                x += speed * Math.sin(rads);
            }
            case LEFT -> {
                x -= speed * Math.cos(rads);
                z += speed * Math.sin(rads);
            }
            case RIGHT -> {
                x += speed * Math.cos(rads);
                z -= speed * Math.sin(rads);
            }
            case UP -> y += speed;
            case DOWN -> y -= speed;
        }
    }
}
