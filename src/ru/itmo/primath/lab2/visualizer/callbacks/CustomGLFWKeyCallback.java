package ru.itmo.primath.lab2.visualizer.callbacks;

import org.lwjgl.glfw.GLFWKeyCallbackI;
import ru.itmo.primath.lab2.visualizer.services.InputService;

public class CustomGLFWKeyCallback implements GLFWKeyCallbackI {
    private final InputService inputService;

    public CustomGLFWKeyCallback(InputService inputService) {
        this.inputService = inputService;
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        inputService.onKeyEvent(window, key, scancode, action, mods);
    }
}
