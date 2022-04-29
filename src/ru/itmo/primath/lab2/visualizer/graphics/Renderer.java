package ru.itmo.primath.lab2.visualizer.graphics;

import org.lwjgl.opengl.GL;
import ru.itmo.primath.lab2.visualizer.Mesh;
import ru.itmo.primath.lab2.visualizer.Renderable;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
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
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static ru.itmo.primath.lab2.util.IOUtils.readResourceFile;

public class Renderer {
    Shader mainShader, meshShader, defaultShader;

    int projectionMainPosition, projectionMeshPosition, projectionDefaultPosition;
    float[] projection = new float[16];

    private final Camera camera;

    public Renderer(Camera camera) {
        this.camera = camera;
        GL.createCapabilities();

        mainShader = new Shader(
                readResourceFile("shaders/vertex.glsl"),
                readResourceFile("shaders/colorful_fragment.glsl"));
        meshShader = new Shader(
                readResourceFile("shaders/mesh_vertex.glsl"),
                readResourceFile("shaders/mesh_white_cell.glsl"));
        defaultShader = new Shader(
                readResourceFile("shaders/default_vertex.glsl"),
                readResourceFile("shaders/default_fragment.glsl"));

        projectionMainPosition = glGetUniformLocation(mainShader.program, "projection");
        projectionMeshPosition = glGetUniformLocation(meshShader.program, "projection");
        projectionDefaultPosition = glGetUniformLocation(defaultShader.program, "projection");

        createAxes();

        glClearColor(0.2f, 0.5f, 1, 0.0f);
    }

    public void render(Mesh activeMesh, Renderable activePath) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glPushMatrix();
        glEnable(GL_DEPTH_TEST);
        camera.apply();
        {
            glUseProgram(meshShader.program);

            glEnableVertexAttribArray(0);

            glGetFloatv(GL_MODELVIEW_MATRIX, projection);
            glUniformMatrix4fv(projectionDefaultPosition, false, projection);

            activeMesh.render();

            glUseProgram(defaultShader.program);

            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);

            glUniformMatrix4fv(projectionDefaultPosition, false, projection);
            renderAxes();

            if (activePath != null) {
                activePath.render();
            }
        }
        glDisable(GL_DEPTH_TEST);
        glPopMatrix();
    }

    Renderable axes;
    private void createAxes() {
        float length = 100;
        float[] vertices = new float[]{
                0, 0, 0, 0, 1, 0, 1,
                length, 0, 0, 0, 1, 0, 1,
                0, length, 0, 0, 1, 0, 1,
                0, 0, length, 0, 1, 0, 1};

        int[] indices = new int[] {0, 1, 0, 2, 0, 3};

        int verticesBufferId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, verticesBufferId);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        int indicesBufferId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesBufferId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        int vertexArrayId = glGenVertexArrays();
        glBindVertexArray(vertexArrayId);
        glBindBuffer(GL_ARRAY_BUFFER, verticesBufferId);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 28, 0);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 28, 12);

        axes = new Renderable(vertexArrayId, indicesBufferId, indices.length, GL_LINES, verticesBufferId);
    }

    public void renderAxes() {
        glLineWidth(3);
        axes.render();
    }
}
