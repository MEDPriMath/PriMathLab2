package ru.itmo.primath.lab2.visualizer.graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GraphicsUtils {
    public static int verticesBuffer(float[] verticesData) {
        int verticesBufferId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, verticesBufferId);
        glBufferData(GL_ARRAY_BUFFER, verticesData, GL_STATIC_DRAW);
        return verticesBufferId;
    }

    public static int indicesBuffer(int[] indicesData) {
        int indicesBufferId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesBufferId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesData, GL_STATIC_DRAW);
        return indicesBufferId;
    }

    public static int vertexArray7(int verticesBufferId) {
        int vertexArrayId = glGenVertexArrays();
        glBindVertexArray(vertexArrayId);
        glBindBuffer(GL_ARRAY_BUFFER, verticesBufferId);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 28, NULL);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 28, 12);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        return vertexArrayId;
    }

    public static void checkGLError() {
        int error = glGetError();
        if (error != 0) {
            String message = String.format("GL Error has acquired: %d", glGetError());
            System.err.println(message);
            throw new RuntimeException();
        }
    }
}
