package ru.itmo.primath.lab2.visualizer.graphics;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glValidateProgram;

public class Shader {
    public final int program;

    public Shader(String vertexShaderCode, String fragmentShaderCode) {
        program = glCreateProgram();

        int vertexShader = compileShader(GL_VERTEX_SHADER, vertexShaderCode, program);
        int fragmentShader = compileShader(GL_FRAGMENT_SHADER, fragmentShaderCode, program);

        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) != GL_TRUE) {
            throw new RuntimeException(String.format("Program linkage error: %s", glGetProgramInfoLog(program)));
        }

        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) != GL_TRUE) {
            throw new RuntimeException(String.format("Program validate error: %s", glGetProgramInfoLog(program)));
        }
    }

    private int compileShader(int type, String code, int program) {
        int shaderId = glCreateShader(type);
        glShaderSource(shaderId, code);
        glCompileShader(shaderId);
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) != GL_TRUE) {
            throw new RuntimeException(String.format("Shader compile error: %s", glGetShaderInfoLog(shaderId)));
        }
        glAttachShader(program, shaderId);
        return shaderId;
    }
}
