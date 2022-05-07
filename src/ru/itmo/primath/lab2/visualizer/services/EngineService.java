package ru.itmo.primath.lab2.visualizer.services;

import ru.itmo.primath.lab2.math.Path;
import ru.itmo.primath.lab2.math.Vector2;
import ru.itmo.primath.lab2.methods.GDMinimizer;
import ru.itmo.primath.lab2.visualizer.engine.EngineContext;
import ru.itmo.primath.lab2.visualizer.graphics.Renderable;
import ru.itmo.primath.lab2.visualizer.shaders.Shaders;

public class EngineService {

    private final EngineContext engineContext;

    public EngineService(EngineContext engineContext) {
        this.engineContext = engineContext;
        reset();
    }

    public void reset() {
        engineContext.camera.reset();
        engineContext.stepSizeExp = -1;
        engineContext.stepSizeMantis = 1;
    }

    public void chooseMesh(int index) {
        if (index < 0)
            index = 0;
        if (index >= engineContext.meshes.size())
            index = engineContext.meshes.size() - 1;
        if (engineContext.activeMesh == engineContext.meshes.get(index))
            return;
        engineContext.activeMesh = engineContext.meshes.get(index);
        engineContext.activePath = null;
    }

    public void activatePath(GDMinimizer minimizer) {
        if (engineContext.activePath != null) {
            engineContext.activePath.dispose();
            engineContext.activePath = null;
        }
        var f = engineContext.meshFunctions.get(engineContext.meshes.indexOf(engineContext.activeMesh));
        Path<Vector2> path =
                minimizer.minimize(
                        f,
                        new Vector2(engineContext.camera.getX(), engineContext.camera.getZ()),
                        1E-3,
                        getStepSize());
//        path.getPoints().forEach(System.out::println);

        System.out.println(minimizer + ": " + (path.length() - 1));
        engineContext.activePath = Renderable.pathLine(path, f);
    }

    public void nextMeshShader() {
        engineContext.activeShaderIndex = (engineContext.activeShaderIndex + 1) % Shaders.meshShaders.size();
    }

    public void previousMeshShader() {
        engineContext.activeShaderIndex = (engineContext.activeShaderIndex - 1 + Shaders.meshShaders.size())
                % Shaders.meshShaders.size();
    }

    public void increaseStepSize() {
        engineContext.stepSizeMantis++;
        if (engineContext.stepSizeMantis >= 10) {
            engineContext.stepSizeMantis = 1;
            engineContext.stepSizeExp++;
        }
        reportStepSize();
    }

    public void decreaseStepSize() {
        engineContext.stepSizeMantis--;
        if (engineContext.stepSizeMantis <= 0) {
            engineContext.stepSizeMantis = 9;
            engineContext.stepSizeExp--;
        }
        reportStepSize();
    }

    public double getStepSize() {
        return Math.pow(10, engineContext.stepSizeExp) * engineContext.stepSizeMantis;
    }

    private void reportStepSize() {
        System.out.printf("Step size is %f\n", getStepSize());
    }
}
