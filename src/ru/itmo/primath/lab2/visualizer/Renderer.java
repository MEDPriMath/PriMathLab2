package ru.itmo.primath.lab2.visualizer;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.util.Collection;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW_MATRIX;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetFloatv;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11C.GL_TRIANGLES;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static ru.itmo.primath.lab2.util.IOUtils.readResourceFile;

public class Renderer {
    Shader mainShader;

    int projectionPosition, minPosition, maxPosition;
    float[] projection = new float[16];

    private final Camera camera;

    public Renderer(Camera camera) {
        this.camera = camera;
        GL.createCapabilities();

        mainShader = new Shader(
                readResourceFile("shaders/vertex.glsl"),
                readResourceFile("shaders/fragment.glsl"));

        projectionPosition = glGetUniformLocation(mainShader.program, "projection");
//        minPosition = glGetUniformLocation(mainShader.program, "min");
//        maxPosition = glGetUniformLocation(mainShader.program, "max");

        glClearColor(0.2f, 0.5f, 1, 0.0f);
    }

    public void render(Collection<Entity> entities) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glPushMatrix();
        glEnable(GL_DEPTH_TEST);
        camera.apply();
        {
            glGetFloatv(GL_MODELVIEW_MATRIX, projection);

            glUniformMatrix4fv(projectionPosition, false, projection);
//            glUniform1f(minPosition, minY);
//            glUniform1f(maxPosition, maxY);

            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);

            glUseProgram(mainShader.program);
            entities.forEach(Entity::render);

            renderAxes();
        }
        glDisable(GL_DEPTH_TEST);
        glPopMatrix();
    }

    public void renderAxes() {
        GL11.glBegin(GL_TRIANGLES);
        {
            int length = 100;
            // x axis
            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(0.0f, 0.0f, -0.01f);

            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(length, 0.0f, -0.01f);

            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(0.0f, 0.0f, 0.01f);

            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(length, 0.0f, 0.01f);

            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(length, 0.0f, -0.01f);

            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(0.0f, 0.0f, 0.01f);

            // z axis
            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(0.01f, 0.0f, 0.0f);

            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(0.01f, 0.0f, length);

            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(-0.01f, 0.0f, length);

            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(0.01f, 0.0f, 0.0f);

            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(-0.01f, 0.0f, 0.0f);

            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(-0.01f, 0.0f, length);

            // y axis
            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(0.01f, 0.0f, 0.0f);

            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(0.01f, length, 0.0f);

            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(-0.01f, length, 0.0f);

            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(0.01f, 0.0f, 0.0f);

            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(-0.01f, 0.0f, 0.0f);

            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            GL11.glVertex3f(-0.01f, length, 0.0f);
        }
        GL11.glEnd();
    }
}
