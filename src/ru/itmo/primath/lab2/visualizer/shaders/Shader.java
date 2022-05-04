package ru.itmo.primath.lab2.visualizer.shaders;

public abstract class Shader {

    protected final ShaderProgram shaderProgram;

    public Shader(String vertexShaderCode, String fragmentShaderCode) {
        shaderProgram = new ShaderProgram(vertexShaderCode, fragmentShaderCode);
    }

    public void enable(float[] projectionMatrix) {

    }

    public void disable() {

    }

}
