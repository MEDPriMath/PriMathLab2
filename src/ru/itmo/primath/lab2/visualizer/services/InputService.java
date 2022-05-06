package ru.itmo.primath.lab2.visualizer.services;

import org.lwjgl.glfw.GLFW;
import ru.itmo.primath.lab2.methods.*;
import ru.itmo.primath.lab2.visualizer.graphics.Camera;
import ru.itmo.primath.lab2.visualizer.graphics.Direction;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glFrustum;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glViewport;

public class InputService {

    private final Camera camera;
    private final EngineService engineService;

    public InputService(Camera camera, EngineService engineService) {
        this.camera = camera;
        this.engineService = engineService;
    }

    public void onMouseMove(double dx, double dy) {
        float speedX = 0.15f;
        float speedY = 0.15f;
        camera.rotate(-dx * speedX, -dy * speedY);
    }

    public void onKeyEvent(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_RELEASE) {
            if (GLFW.GLFW_KEY_1 <= key && key <= GLFW.GLFW_KEY_9) {
                engineService.chooseMesh(key - GLFW.GLFW_KEY_1);
            } else switch (key) {
                case GLFW_KEY_ESCAPE ->
                        glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
                case GLFW_KEY_R -> camera.reset();
                case GLFW_KEY_P -> engineService.activatePath(new ConstantStepGDMinimizer());
                case GLFW_KEY_O -> engineService.activatePath(new SplitStepGDMinimizer());
                case GLFW_KEY_I -> engineService.activatePath(new GoldenRatioGDMinimizer());
                case GLFW_KEY_U -> engineService.activatePath(new FibonacciGDMinimizer());
                case GLFW_KEY_Y -> engineService.activatePath(new ReevesMinimizer());
                case GLFW_KEY_MINUS -> engineService.previousMeshShader();
                case GLFW_KEY_EQUAL -> engineService.nextMeshShader();
            }
        }
    }

    public void onWindowResize(int width, int height) {
        float ratio = (float) width / height;
        double k = 0.001;
        glViewport(0, 0, width, height);
        glLoadIdentity();
        glFrustum(-ratio * k, ratio * k, -k, k, k * 2, 1000);
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

    public void onMouseScroll(double xOffset, double yOffset) {
        float scaleSensitivity = 0.1f;
        camera.scale((xOffset + yOffset) * scaleSensitivity);
    }
}
