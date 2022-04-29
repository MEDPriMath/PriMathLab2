package ru.itmo.primath.lab2.visualizer;

import ru.itmo.primath.lab2.Function2;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11C.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11C.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;
import static ru.itmo.primath.lab2.util.MathUtils.sqr;
import static ru.itmo.primath.lab2.visualizer.graphics.GraphicsUtils.indicesBuffer;
import static ru.itmo.primath.lab2.visualizer.graphics.GraphicsUtils.verticesBuffer;

public class Mesh {
    private final int indicesBufferId;
    private final int vertexArrayId;
    private final int indicesCount;
    public float minY = Float.MAX_VALUE;
    public float maxY = Float.MIN_VALUE;

    public Mesh(Function2 function, float xPos, float zPos, float size, int steps) {
        int verticesCount = sqr(steps + 1);
        float[] verticesBuffer = new float[verticesCount * 3];
        int index = 0;
        for (int x = 0; x <= steps; x++) {
            for (int z = 0; z <= steps; z++) {
                verticesBuffer[index] = xPos + size * x / steps;
                verticesBuffer[index + 2] = zPos + size * z / steps;
                verticesBuffer[index + 1] = (float) function.value(verticesBuffer[index], verticesBuffer[index + 2]);

                maxY = max(maxY, verticesBuffer[index + 1]);
                minY = min(minY, verticesBuffer[index + 1]);
//                if (maxY < function.value(verticesBuffer[index], verticesBuffer[index + 2]))
//                    maxY = (float) function.value(verticesBuffer[index], verticesBuffer[index + 2]);
//                if (minY > function.value(verticesBuffer[index], verticesBuffer[index + 2]))
//                    minY = (float) function.value(verticesBuffer[index], verticesBuffer[index + 2]);
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

        int verticesBufferId = verticesBuffer(verticesBuffer);
        indicesBufferId = indicesBuffer(indices);

        vertexArrayId = glGenVertexArrays();
        glBindVertexArray(vertexArrayId);
        glBindBuffer(GL_ARRAY_BUFFER, verticesBufferId);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, NULL);
    }

    public void render() {
        glBindVertexArray(vertexArrayId);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesBufferId);
        glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_INT, 0);
    }
}
