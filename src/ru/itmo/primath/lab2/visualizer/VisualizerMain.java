package ru.itmo.primath.lab2.visualizer;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowRefreshCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

import static java.lang.String.format;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowRefreshCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW_MATRIX;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glFrustum;
import static org.lwjgl.opengl.GL11.glGetFloatv;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class VisualizerMain {
    private final Camera camera = new Camera(0, 0.5f, 3f, 0, 0);
    private long window;
    private double windowWidth;
    private double windowHeight;

    public static void main(String[] args) {
        new VisualizerMain().run(1280, 720);
    }

    public void run(int initialWidth, int initialHeight) {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init(initialWidth, initialHeight);
        startLoop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init(int initialWidth, int initialHeight) {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(initialWidth, initialHeight, "Hello World!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (action == GLFW_RELEASE) {
                switch (key) {
                    case GLFW_KEY_ESCAPE:
                        glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
                        break;
//                    case GLFW_KEY_W:
//                        camera.move(Direction.FORWARD, 1);
//                        break;
                }
            }
//            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
//                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        glfwSetWindowRefreshCallback(window, new GLFWWindowRefreshCallback() {
            final int[] width = new int[1];
            final int[] height = new int[1];

            @Override
            public void invoke(long window) {
                glfwGetWindowSize(window, width, height);
                resizeWindow(width[0], height[0]);
            }
        });

        glfwSetCursorPosCallback(window, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                double dx = xpos - windowWidth / 2;
                double dy = ypos - windowHeight / 2;

                if (dx != 0 || dy != 0) {
                    onMouseMove(dx, dy);
                    glfwSetCursorPos(window, windowWidth / 2, windowHeight / 2);
                }
//                System.out.println(xpos + " " + ypos);
            }
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void checkKeys() {
        double speed = 0.3716666 / 5;
        if (glfwGetKey(window, GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS) {
            speed *= 2;
        }
        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            camera.move(Direction.FORWARD, speed);
        }
        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
            camera.move(Direction.BACKWARD, speed);
        }
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            camera.move(Direction.LEFT, speed);
        }
        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            camera.move(Direction.RIGHT, speed);
        }
        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
            camera.move(Direction.UP, speed);
        }
        if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
            camera.move(Direction.DOWN, speed);
        }
    }

    private void resizeWindow(int width, int height) {
        windowWidth = width;
        windowHeight = height;

        float ratio = (float) width / height;
        double k = 0.1;
        glViewport(0, 0, width, height);
        glLoadIdentity();
        glFrustum(-ratio * k, ratio * k, -k, k, k * 2, 100);
    }

    private void onMouseMove(double dx, double dy) {
        float speedX = 0.15f;
        float speedY = 0.15f;
        camera.rotate(-dx * speedX, -dy * speedY);
    }

    int obj;
    Shader shader;
    float[] projection = new float[16];
    int projectionPosition;

    private String readResourceFile(String name) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(name);
        if (is == null) {
            throw new RuntimeException(format("Could not find resource file '%s'", name));
        }
        return readFile(is);
    }

    private String readFile(InputStream is) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] cache = new byte[8192];
            int length;
            while ((length = is.read(cache)) > 0) {
                os.write(cache, 0, length);
            }
            is.close();
            os.close();
            return os.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startLoop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(0.2f, 0.5f, 1, 0.0f);

        shader = new Shader(readResourceFile("shaders/vertex.glsl"),
                readResourceFile("shaders/fragment.glsl"));

        projectionPosition = glGetUniformLocation(shader.program, "projection");
        glUseProgram(shader.program);

        int points = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, points);
        glBufferData(GL_ARRAY_BUFFER, new float[]{0,1,0, 1,1,0, 1,0,0}, GL_STATIC_DRAW);

        int colors = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, colors);
        glBufferData(GL_ARRAY_BUFFER, new float[]{1,0,0, 0,1,0, 0,0,1}, GL_STATIC_DRAW);

        obj = glGenVertexArrays();
        glBindVertexArray(obj);
        glBindBuffer(GL_ARRAY_BUFFER, points);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, NULL);
        glBindBuffer(GL_ARRAY_BUFFER, colors);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, NULL);

        while (!glfwWindowShouldClose(window)) {
            checkKeys();
            render();

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
//        System.out.println(camera.x + " " + camera.y + " " + camera.z);

        glPushMatrix();
        {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            camera.apply();
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            {
                glGetFloatv(GL_MODELVIEW_MATRIX, projection);
                glUniformMatrix4fv(projectionPosition, false, projection);
                glBindVertexArray(obj);
                glDrawArrays(GL_TRIANGLES, 0, 3);
            }
            GL11.glBegin(GL_TRIANGLES);
            {
                // x axis
                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(0.0f, 0.0f, -0.01f);

                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(10.0f, 0.0f, -0.01f);

                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(0.0f, 0.0f, 0.01f);

                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(10.0f, 0.0f, 0.01f);

                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(10.0f, 0.0f, -0.01f);

                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(0.0f, 0.0f, 0.01f);

                // z axis
                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(0.01f, 0.0f, 0.0f);

                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(0.01f, 0.0f, 10.0f);

                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(-0.01f, 0.0f, 10.0f);

                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(0.01f, 0.0f, 0.0f);

                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(-0.01f, 0.0f, 0.0f);

                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(-0.01f, 0.0f, 10.0f);

                // y axis
                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(0.01f, 0.0f, 0.0f);

                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(0.01f, 10.0f, 0.0f);

                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(-0.01f, 10.0f, 0.0f);

                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(0.01f, 0.0f, 0.0f);

                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(-0.01f, 0.0f, 0.0f);

                GL11.glColor3f(0.0f, 0.0f, 0.0f);
                GL11.glVertex3f(-0.01f, 10.0f, 0.0f);
            }
            GL11.glEnd();
        }
        glPopMatrix();
    }
}
