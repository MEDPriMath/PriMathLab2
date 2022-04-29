package ru.itmo.primath.lab2.visualizer.services;

import ru.itmo.primath.lab2.visualizer.Camera;
import ru.itmo.primath.lab2.visualizer.Direction;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.glFrustum;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glViewport;

public class InputService {
    private final Camera camera;

    public InputService(Camera camera) {
        this.camera = camera;
    }

    public void onMouseMove(double dx, double dy) {
        float speedX = 0.15f;
        float speedY = 0.15f;
        camera.rotate(-dx * speedX, -dy * speedY);
    }

    public void onKeyEvent(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_RELEASE) {
            switch (key) {
                case GLFW_KEY_ESCAPE -> glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
                case GLFW_KEY_R -> camera.reset();
            }
        }
    }

    public void onWindowSizeChanged(int width, int height) {
        float ratio = (float) width / height;
        double k = 0.1;
        glViewport(0, 0, width, height);
        glLoadIdentity();
        glFrustum(-ratio * k, ratio * k, -k, k, k * 2, 100);
    }

    public void checkKeys(long window) {
        double speed = 0.3716666 / 5;
        if (glfwGetKey(window, GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS) {
            speed *= 5;
        }
        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            camera.move(Direction.FORWARD, speed);
        }
        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
            camera.move(Direction.BACKWARD, speed);
        }
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            camera.move(Direction.LEFT, speed);
        }
        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            camera.move(Direction.RIGHT, speed);
        }
        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
            camera.move(Direction.UP, speed);
        }
        if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
            camera.move(Direction.DOWN, speed);
        }
    }

    public void activateFunction() {

    }
}
