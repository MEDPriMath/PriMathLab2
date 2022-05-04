package ru.itmo.primath.lab2.visualizer.graphics;

import ru.itmo.primath.lab2.math.Function2;
import ru.itmo.primath.lab2.math.Vector2;
import ru.itmo.primath.lab2.util.Path;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static ru.itmo.primath.lab2.visualizer.graphics.GraphicsUtils.indicesBuffer;
import static ru.itmo.primath.lab2.visualizer.graphics.GraphicsUtils.vertexArray7;
import static ru.itmo.primath.lab2.visualizer.graphics.GraphicsUtils.verticesBuffer;

public class Renderable {

    private final int vertexArrayId;
    private final int indicesBufferId;
    private final int indicesCount;

    private final int drawMode;
    private final int[] buffersToDispose;

    public Renderable(float[] vertices, int[] indices, int drawMode) {
        int verticesBuffer = verticesBuffer(vertices);
        vertexArrayId = vertexArray7(verticesBuffer);
        indicesBufferId = indicesBuffer(indices);
        indicesCount = indices.length;
        this.drawMode = drawMode;
        buffersToDispose = new int[0];
    }

    public Renderable(int vertexArrayId, int indicesBufferId, int indicesCount, int drawMode, int... buffersToDispose) {
        this.vertexArrayId = vertexArrayId;
        this.indicesBufferId = indicesBufferId;
        this.indicesCount = indicesCount;
        this.drawMode = drawMode;
        this.buffersToDispose = buffersToDispose;
    }

    public static Renderable pathLine(Path<Vector2> path, Function2 f) {
        int verticesCount = path.length();

        float[] verticesData = new float[verticesCount * 7];
        int index = 0;
        for (var point : path.getPoints()) {
            verticesData[index++] = (float) point.x();
            verticesData[index++] = (float) f.value(point.x(), point.y());
            verticesData[index++] = (float) point.y();
            verticesData[index++] = 1;
            verticesData[index++] = 0;
            verticesData[index++] = 0;
            verticesData[index++] = 1;
        }

        int[] indices = new int[verticesCount];
        for (index = 0; index < verticesCount; index++) {
            indices[index] = index;
        }

        int verticesBufferId = verticesBuffer(verticesData);
        int indicesBufferId = indicesBuffer(indices);
        int vertexArrayId = vertexArray7(verticesBufferId);

        return new Renderable(vertexArrayId, indicesBufferId, indices.length, GL_LINE_STRIP, verticesBufferId);
    }

    public void render() {
        glBindVertexArray(vertexArrayId);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesBufferId);
        glDrawElements(drawMode, indicesCount, GL_UNSIGNED_INT, 0);
    }

    public void dispose() {
        glDeleteVertexArrays(vertexArrayId);
        glDeleteBuffers(indicesBufferId);
        for (int bufferId : buffersToDispose) {
            glDeleteBuffers(bufferId);
        }
    }
}
