package ru.itmo.primath.lab2.visualizer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
import ru.itmo.primath.lab2.visualizer.callbacks.CustomGLFWCursorPosCallback;
import ru.itmo.primath.lab2.visualizer.callbacks.CustomGLFWKeyCallback;
import ru.itmo.primath.lab2.visualizer.callbacks.CustomGLFWScrollCallback;
import ru.itmo.primath.lab2.visualizer.callbacks.CustomGLFWWindowRefreshCallback;
import ru.itmo.primath.lab2.visualizer.services.InputService;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowRefreshCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    public final long windowHandle;

    public Window(InputService inputService, int initialWidth, int initialHeight, boolean isFullscreen) {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (videoMode == null) {
            throw new RuntimeException("'videoMode' is not expected to be null");
        }

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_SAMPLES, 4);

        if (isFullscreen) {
            windowHandle = glfwCreateWindow(
                    videoMode.width(), videoMode.height(), "Pri Math Lab 2", glfwGetPrimaryMonitor(), NULL);
        } else {
            glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
            windowHandle = glfwCreateWindow(initialWidth, initialHeight, "Pri Math Lab 2", NULL, NULL);
        }

        if (windowHandle == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(windowHandle, new CustomGLFWKeyCallback(inputService));
        glfwSetWindowRefreshCallback(windowHandle, new CustomGLFWWindowRefreshCallback(inputService));
        glfwSetCursorPosCallback(windowHandle, new CustomGLFWCursorPosCallback(inputService));
        glfwSetScrollCallback(windowHandle, new CustomGLFWScrollCallback(inputService));

        glfwSetInputMode(windowHandle, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(windowHandle, pWidth, pHeight);

            // Center the window
            glfwSetWindowPos(
                    windowHandle,
                    (videoMode.width() - pWidth.get(0)) / 2,
                    (videoMode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        glfwMakeContextCurrent(windowHandle);
        // Enable v-sync
        glfwSwapInterval(1);
        glfwShowWindow(windowHandle);
    }
}
