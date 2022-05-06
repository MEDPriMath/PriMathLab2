package ru.itmo.primath.lab2.visualizer.services;

import ru.itmo.primath.lab2.math.Vector2;
import ru.itmo.primath.lab2.methods.GDMinimizer;
import ru.itmo.primath.lab2.util.Path;
import ru.itmo.primath.lab2.visualizer.engine.EngineContext;
import ru.itmo.primath.lab2.visualizer.graphics.Renderable;
import ru.itmo.primath.lab2.visualizer.shaders.Shaders;

public class EngineService {

    private final EngineContext engineContext;

    public EngineService(EngineContext engineContext) {
        this.engineContext = engineContext;
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
                minimizer.minimize(f, new Vector2(engineContext.camera.getX(), engineContext.camera.getZ()), 1E-6, 1);
        System.out.println(minimizer.getClass().getSimpleName() + ": " + path.length() + " " + path.last());
        engineContext.activePath = Renderable.pathLine(path, f);
    }

    public void nextMeshShader() {
        engineContext.activeShaderIndex = (engineContext.activeShaderIndex + 1) % Shaders.meshShaders.size();
    }

    public void previousMeshShader() {
        engineContext.activeShaderIndex = (engineContext.activeShaderIndex - 1 + Shaders.meshShaders.size())
                % Shaders.meshShaders.size();
    }
}
