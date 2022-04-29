package ru.itmo.primath.lab2.visualizer.callbacks;

import org.lwjgl.glfw.GLFWScrollCallback;
import ru.itmo.primath.lab2.visualizer.services.InputService;

public class CustomGLFWScrollCallback extends GLFWScrollCallback {

    private final InputService inputService;

    public CustomGLFWScrollCallback(InputService inputService) {
        this.inputService = inputService;
    }

    @Override
    public void invoke(long window, double xOffset, double yOffset) {
        inputService.onMouseScroll(xOffset, yOffset);
    }
}
