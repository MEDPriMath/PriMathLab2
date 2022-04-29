package ru.itmo.primath.lab2.visualizer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Entity {
    private int verticesBufferId;
    private int colorsBufferId;
    private int indicesBufferId;

    private int vertexArrayId;
    private int indicesCount;

    private boolean isInit = false;

    protected Entity() {}

    public Entity(float[] vertices, float[] colors, int[] indices) {
        init(vertices, colors, indices);
    }

    protected void init(float[] vertices, float[] colors, int[] indices) {
        if (isInit) {
            throw new IllegalStateException("Already inited");
        }
        isInit = true;

        if (vertices.length / 3 != colors.length / 3) {
            throw new IllegalArgumentException("Vertices and colors counts differ");
        }
        if ((indices.length % 3) != 0) {
            throw new IllegalArgumentException("Indices count cannot be drawn as triangles: " + indices.length);
        }
        indicesCount = indices.length;

        verticesBufferId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, verticesBufferId);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        colorsBufferId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, colorsBufferId);
        glBufferData(GL_ARRAY_BUFFER, colors, GL_STATIC_DRAW);

        indicesBufferId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesBufferId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        vertexArrayId = glGenVertexArrays();
        glBindVertexArray(vertexArrayId);
        glBindBuffer(GL_ARRAY_BUFFER, verticesBufferId);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, NULL);
        glBindBuffer(GL_ARRAY_BUFFER, colorsBufferId);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, NULL);
    }

    public void render() {
        if (!isInit) {
            throw new IllegalStateException("Entity has not been inited");
        }
        glBindVertexArray(vertexArrayId);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesBufferId);
        glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_INT, 0);
    }
}
