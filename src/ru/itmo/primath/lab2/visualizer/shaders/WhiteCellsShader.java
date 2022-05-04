package ru.itmo.primath.lab2.visualizer.shaders;

import ru.itmo.primath.lab2.visualizer.Mesh;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class WhiteCellsShader extends MeshShader {

    private final int projectionUniformLocation;

    public WhiteCellsShader(String vertexShaderCode, String fragmentShaderCode) {
        super(vertexShaderCode, fragmentShaderCode);
        projectionUniformLocation = glGetUniformLocation(shaderProgram.program, "projection");
    }

    @Override
    public void enable(float[] projectionMatrix, Mesh activeMesh) {
        glUseProgram(shaderProgram.program);
        glUniformMatrix4fv(projectionUniformLocation, false, projectionMatrix);
    }
}
