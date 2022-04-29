package ru.itmo.primath.lab2.visualizer.services;

import org.lwjgl.glfw.GLFW;
import ru.itmo.primath.lab2.methods.ConstantStepGDMinimizer;
import ru.itmo.primath.lab2.methods.FibonacciGDMinimizer;
import ru.itmo.primath.lab2.methods.GDMinimizer;
import ru.itmo.primath.lab2.methods.GoldenRatioGDMinimizer;
import ru.itmo.primath.lab2.methods.SplitStepGDMinimizer;
import ru.itmo.primath.lab2.visualizer.graphics.Camera;
import ru.itmo.primath.lab2.visualizer.graphics.Direction;

import java.util.function.Consumer;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_I;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_O;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_U;
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
    private final Consumer<Integer> chooseMesh;
    private final Consumer<GDMinimizer> activatePath;

    public InputService(Camera camera, Consumer<Integer> chooseMesh, Consumer<GDMinimizer> activatePath) {
        this.camera = camera;
        this.chooseMesh = chooseMesh;
        this.activatePath = activatePath;
    }

    public void onMouseMove(double dx, double dy) {
        float speedX = 0.15f;
        float speedY = 0.15f;
        camera.rotate(-dx * speedX, -dy * speedY);
    }

    public void onKeyEvent(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_RELEASE) {
            if (GLFW.GLFW_KEY_1 <= key && key <= GLFW.GLFW_KEY_9) {
                chooseMesh.accept(key - GLFW.GLFW_KEY_1);
            } else switch (key) {
                case GLFW_KEY_ESCAPE -> glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
                case GLFW_KEY_R -> camera.reset();
                case GLFW_KEY_P -> activatePath.accept(new ConstantStepGDMinimizer());
                case GLFW_KEY_O -> activatePath.accept(new SplitStepGDMinimizer());
                case GLFW_KEY_I -> activatePath.accept(new GoldenRatioGDMinimizer());
                case GLFW_KEY_U -> activatePath.accept(new FibonacciGDMinimizer());
//                case GLFW_KEY_Y -> activatePath.accept(new ConstantStepGDMinimizer());
//                case GLFW_KEY_T -> activatePath.accept(new ConstantStepGDMinimizer());
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

    public void onMouseScroll(double xOffset, double yOffset) {
        float scaleSensitivity = 0.1f;
        camera.scale((xOffset + yOffset) * scaleSensitivity);
    }
}
