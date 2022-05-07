package ru.itmo.primath.lab2.visualizer.engine;

import ru.itmo.primath.lab2.math.Function2;
import ru.itmo.primath.lab2.visualizer.Mesh;
import ru.itmo.primath.lab2.visualizer.graphics.Camera;
import ru.itmo.primath.lab2.visualizer.graphics.Renderable;

import java.util.List;

public class EngineContext {

    public final Camera camera;
    public List<Mesh> meshes;
    public List<Function2> meshFunctions;
    public Mesh activeMesh;
    public Renderable activePath;
    public int activeShaderIndex;

    public int stepSizeExp;
    public int stepSizeMantis;

    EngineContext() {
        camera = new Camera();
    }
}
