package ru.itmo.primath.lab2.visualizer;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

public class Camera {
    float x, y, z, yaw, pitch;

    public Camera(float x, float y, float z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void apply() {
        glRotatef(-pitch, 1, 0, 0);
        glRotatef(-yaw, 0, 1, 0);
        glTranslatef(-x, -y, -z);
    }

    public void rotate(double yaw, double pitch) {
        this.yaw += yaw;
        if (yaw < -180) yaw += 360;
        if (yaw > 180) yaw -= 360;
        this.pitch += pitch;
        if (pitch < -90) pitch = -90;
        if (pitch > 90) pitch = 90;
//        System.out.printf("Rotation: %f %f\n", yaw, pitch);
    }
}
