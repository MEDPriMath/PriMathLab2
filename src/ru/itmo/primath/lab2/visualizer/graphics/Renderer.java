package ru.itmo.primath.lab2.visualizer.graphics;

import org.lwjgl.opengl.GL;
import ru.itmo.primath.lab2.visualizer.Mesh;
import ru.itmo.primath.lab2.visualizer.shaders.MeshShader;
import ru.itmo.primath.lab2.visualizer.shaders.Shaders;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW_MATRIX;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetFloatv;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11C.GL_LINES;
import static ru.itmo.primath.lab2.visualizer.shaders.Shaders.defaultShader;

public class Renderer {
    float[] projection = new float[16];

    private final Camera camera;
    private final Renderable axes;

    public Renderer(Camera camera) {
        this.camera = camera;
        GL.createCapabilities();

        Shaders.init();
        axes = createAxes();

        glClearColor(0.2f, 0.5f, 1, 1.0f);
        glLineWidth(5);
    }

    public void render(Mesh activeMesh, Renderable activePath, MeshShader meshShader) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glPushMatrix();
        glEnable(GL_DEPTH_TEST);
        camera.apply();
        glGetFloatv(GL_MODELVIEW_MATRIX, projection);
        {
//            whiteCellsShader.enable(projection, activeMesh);
            meshShader.enable(projection, activeMesh);
            activeMesh.render();

            defaultShader.enable(projection);
                axes.render();
                if (activePath != null) {
                    activePath.render();
                }
            defaultShader.disable();
        }
        glDisable(GL_DEPTH_TEST);
        glPopMatrix();
    }

    private Renderable createAxes() {
        float length = 100;
        float[] vertices = new float[]{
                0, 0, 0, 0, 1, 0, 1,
                length, 0, 0, 0, 1, 0, 1,
                0, length, 0, 0, 1, 0, 1,
                0, 0, length, 0, 1, 0, 1};

        int[] indices = new int[]{0, 1, 0, 2, 0, 3};

        return new Renderable(vertices, indices, GL_LINES);
    }
}
