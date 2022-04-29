package ru.itmo.primath.lab2.visualizer;

import ru.itmo.primath.lab2.Function2;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
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
import static ru.itmo.primath.lab2.util.MathUtils.sqr;

public class Mesh {
    private final int indicesBufferId;
    private final int vertexArrayId;
    private final int indicesCount;

    public Mesh(Function2 function, float xPos, float zPos, float size, int steps) {
        int verticesCount = sqr(steps + 1);
        float[] verticesBuffer = new float[verticesCount * 3];
        int index = 0;
        for (int x = 0; x <= steps; x++) {
            for (int z = 0; z <= steps; z++) {
                verticesBuffer[index] = xPos + size * x / steps;
                verticesBuffer[index + 2] = zPos + size * z / steps;
                verticesBuffer[index + 1] = (float) function.value(verticesBuffer[index], verticesBuffer[index + 2]);
                index += 3;
            }
        }

        indicesCount = sqr(steps) * 2 * 3;
        int[] indices = new int[indicesCount];
        index = 0;
        for (int i = 0; i < steps; i++) {
            for (int j = 0; j < steps; j++) {
                indices[index++] = i * (steps + 1) + j;
                indices[index++] = i * (steps + 1) + j + 1;
                indices[index++] = (i + 1) * (steps + 1) + j;

                indices[index++] = (i + 1) * (steps + 1) + j + 1;
                indices[index++] = i * (steps + 1) + j + 1;
                indices[index++] = (i + 1) * (steps + 1) + j;
            }
        }

        int verticesBufferId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, verticesBufferId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        indicesBufferId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesBufferId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        vertexArrayId = glGenVertexArrays();
        glBindVertexArray(vertexArrayId);
        glBindBuffer(GL_ARRAY_BUFFER, verticesBufferId);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, NULL);
    }

    public void render() {
        // TODO Привет, странник. Как думаешь, почему эта строка меняет цвет осей на зелёный?
        glBindVertexArray(vertexArrayId);
//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesBufferId);
//        glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_INT, 0);
    }
}
