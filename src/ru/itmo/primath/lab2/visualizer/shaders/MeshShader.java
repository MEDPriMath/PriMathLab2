package ru.itmo.primath.lab2.visualizer.shaders;

import ru.itmo.primath.lab2.visualizer.Mesh;

public abstract class MeshShader {

    protected final ShaderProgram shaderProgram;

    public MeshShader(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    public MeshShader(String vertexShaderCode, String fragmentShaderCode) {
        shaderProgram = new ShaderProgram(vertexShaderCode, fragmentShaderCode);
    }

    public abstract void enable(float[] projectionMatrix, Mesh activeMesh);
}
