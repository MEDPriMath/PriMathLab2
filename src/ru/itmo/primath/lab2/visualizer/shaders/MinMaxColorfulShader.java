package ru.itmo.primath.lab2.visualizer.shaders;

import ru.itmo.primath.lab2.visualizer.Mesh;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class MinMaxColorfulShader extends MeshShader {

    private final int projectionUniformLocation, minLocation, maxLocation;

    public MinMaxColorfulShader(String vertexShaderCode, String fragmentShaderCode) {
        super(vertexShaderCode, fragmentShaderCode);
        projectionUniformLocation = glGetUniformLocation(shaderProgram.program, "projection");
        minLocation = glGetUniformLocation(shaderProgram.program, "min");
        maxLocation = glGetUniformLocation(shaderProgram.program, "max");
    }

    @Override
    public void enable(float[] projectionMatrix, Mesh activeMesh) {
        glUseProgram(shaderProgram.program);
        glUniformMatrix4fv(projectionUniformLocation, false, projectionMatrix);
        glUniform1f(minLocation, activeMesh.minY);
        glUniform1f(maxLocation, activeMesh.maxY);
    }
}