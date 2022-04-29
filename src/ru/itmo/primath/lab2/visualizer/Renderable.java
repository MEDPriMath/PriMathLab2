package ru.itmo.primath.lab2.visualizer;

import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderable {
    public Renderable(int vertexArrayId, int indicesBufferId, int indicesCount, int drawMode) {
        this.vertexArrayId = vertexArrayId;
        this.indicesBufferId = indicesBufferId;
        this.indicesCount = indicesCount;
        this.drawMode = drawMode;
    }

    private final int vertexArrayId;
    private final int indicesBufferId;
    private final int indicesCount;

    private final int drawMode;

    public void render() {
        glBindVertexArray(vertexArrayId);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesBufferId);
        glDrawElements(drawMode, indicesCount, GL_UNSIGNED_INT, 0);
    }
}
