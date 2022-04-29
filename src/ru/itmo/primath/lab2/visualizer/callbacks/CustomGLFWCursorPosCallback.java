package ru.itmo.primath.lab2.visualizer.callbacks;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import ru.itmo.primath.lab2.visualizer.services.InputService;

import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

public class CustomGLFWCursorPosCallback extends GLFWCursorPosCallback {

    private final InputService inputService;
    private final int[] width = new int[1];
    private final int[] height = new int[1];

    public CustomGLFWCursorPosCallback(InputService inputService) {
        this.inputService = inputService;
    }

    @Override
    public void invoke(long window, double xpos, double ypos) {
        glfwGetWindowSize(window, width, height);
        double dx = xpos - width[0] / 2f;
        double dy = ypos - height[0] / 2f;

        if (dx != 0 || dy != 0) {
            inputService.onMouseMove(dx, dy);
            glfwSetCursorPos(window, width[0] / 2f, height[0] / 2f);
        }
    }
}
