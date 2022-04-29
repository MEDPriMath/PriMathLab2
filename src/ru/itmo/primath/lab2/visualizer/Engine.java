package ru.itmo.primath.lab2.visualizer;

import ru.itmo.primath.lab2.Function2;
import ru.itmo.primath.lab2.visualizer.graphics.Camera;
import ru.itmo.primath.lab2.visualizer.graphics.Renderer;
import ru.itmo.primath.lab2.visualizer.services.InputService;

import java.util.List;

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

    List<Mesh> meshes;
    Mesh activeMesh;

    public void run(int windowWidth,
                    int windowHeight,
                    List<Function2> meshFunctions,
                    float meshX,
                    float meshY,
                    float meshSize,
                    int meshResolution) {
        if (meshFunctions.size() == 0) {
            throw new IllegalArgumentException("At least one function is required");
        }

        camera = new Camera();
        inputService = new InputService(camera, this::chooseMesh);
        window = new Window(inputService, windowWidth, windowHeight);

        renderer = new Renderer(camera);
        loadMeshes(meshFunctions, meshX, meshY, meshSize, meshResolution);
        chooseMesh(0);

        loop();

        glfwFreeCallbacks(window.windowHandle);
        glfwDestroyWindow(window.windowHandle);

        glfwTerminate();
        var callback = glfwSetErrorCallback(null);
        if (callback != null)
            callback.free();
    }

    private void loadMeshes(List<Function2> meshFunctions,
                            float meshX,
                            float meshY,
                            float meshSize,
                            int meshResolution) {
        meshes = meshFunctions.stream().map(f -> new Mesh(f, meshX, meshY, meshSize, meshResolution)).toList();
    }

    private void chooseMesh(int index) {
        if (index >= meshes.size())
            index = meshes.size() - 1;
        activeMesh = meshes.get(index);
    }

    private void loop() {
        while (!glfwWindowShouldClose(window.windowHandle)) {
            inputService.checkKeys(window.windowHandle);
            renderer.render(activeMesh);

            glfwSwapBuffers(window.windowHandle);
            glfwPollEvents();
        }
    }
}
