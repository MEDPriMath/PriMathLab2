package ru.itmo.primath.lab2.visualizer.callbacks;

import org.lwjgl.glfw.GLFWWindowRefreshCallback;
import ru.itmo.primath.lab2.visualizer.services.InputService;

import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

public class CustomGLFWWindowRefreshCallback extends GLFWWindowRefreshCallback {
    private final InputService inputService;
    private final int[] width = new int[1];
    private final int[] height = new int[1];

    public CustomGLFWWindowRefreshCallback(InputService inputService) {
        this.inputService = inputService;
    }

    @Override
    public void invoke(long window) {
        int prevWidth = width[0];
        int prevHeight = height[0];
        glfwGetWindowSize(window, width, height);
        if (prevWidth != width[0] || prevHeight != height[0]) {
            inputService.onWindowSizeChanged(width[0], height[0]);
        }
    }
}
