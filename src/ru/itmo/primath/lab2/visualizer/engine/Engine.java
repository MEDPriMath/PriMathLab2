package ru.itmo.primath.lab2.visualizer.engine;

import ru.itmo.primath.lab2.math.Function2;
import ru.itmo.primath.lab2.visualizer.Mesh;
import ru.itmo.primath.lab2.visualizer.Window;
import ru.itmo.primath.lab2.visualizer.graphics.Renderer;
import ru.itmo.primath.lab2.visualizer.services.EngineService;
import ru.itmo.primath.lab2.visualizer.services.InputService;
import ru.itmo.primath.lab2.visualizer.shaders.Shaders;

import java.util.List;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class Engine {
    private final EngineContext engineContext;
    private final EngineService engineService;
    private final InputService inputService;

    Window window;
    Renderer renderer;

    public Engine() {
        engineContext = new EngineContext();
        engineService = new EngineService(engineContext);
        inputService = new InputService(engineContext.camera, engineService);
    }

    public void run(int windowWidth,
                    int windowHeight,
                    boolean isFullscreen,
                    List<Function2> meshFunctions,
                    float meshX,
                    float meshY,
                    float meshSize,
                    int meshResolution) {
        if (meshFunctions.size() == 0) {
            throw new IllegalArgumentException("At least one function is required");
        }

        window = new Window(inputService, windowWidth, windowHeight, isFullscreen);

        renderer = new Renderer(engineContext.camera);
        engineContext.meshFunctions = meshFunctions;
        loadMeshes(meshFunctions, meshX, meshY, meshSize, meshResolution);
        engineService.chooseMesh(4);

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
        engineContext.meshes = meshFunctions.stream().map(f -> new Mesh(f, meshX, meshY, meshSize, meshResolution)).toList();
    }

    private void loop() {
        while (!glfwWindowShouldClose(window.windowHandle)) {
            inputService.checkKeys(window.windowHandle);

            renderer.render(
                    engineContext.activeMesh,
                    engineContext.activePath,
                    Shaders.meshShaders.get(engineContext.activeShaderIndex));

            glfwSwapBuffers(window.windowHandle);
            glfwPollEvents();
        }
    }
}
