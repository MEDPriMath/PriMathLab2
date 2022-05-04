package ru.itmo.primath.lab2.visualizer.shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class DefaultShader extends Shader {

    private final int projectionUniformLocation;

    public DefaultShader(String vertexShaderCode, String fragmentShaderCode) {
        super(vertexShaderCode, fragmentShaderCode);
        projectionUniformLocation = glGetUniformLocation(shaderProgram.program, "projection");
    }

    @Override
    public void enable(float[] projectionMatrix) {
        glUseProgram(shaderProgram.program);
        glUniformMatrix4fv(projectionUniformLocation, false, projectionMatrix);
    }
}
