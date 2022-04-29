package ru.itmo.primath.lab2.visualizer;

import ru.itmo.primath.lab2.visualizer.services.InputService;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class Engine {
    Camera camera;
    InputService inputService;
    Window window;
    Renderer renderer;

    EntityProvider entityProvider;

    public void run(int windowWidth,
                    int windowHeight,
                    EntityProvider entityProvider) {
        this.entityProvider = entityProvider;

        camera = new Camera();
        inputService = new InputService(camera);
        window = new Window(inputService, windowWidth, windowHeight);

        renderer = new Renderer(camera);

        loop();

        glfwFreeCallbacks(window.windowHandle);
        glfwDestroyWindow(window.windowHandle);

        glfwTerminate();
        var callback = glfwSetErrorCallback(null);
        if (callback != null)
            callback.free();
    }

    private void loop() {
        while (!glfwWindowShouldClose(window.windowHandle)) {
            inputService.checkKeys(window.windowHandle);
            renderer.render(entityProvider.getEntities());

            glfwSwapBuffers(window.windowHandle);
            glfwPollEvents();
        }
    }
}
