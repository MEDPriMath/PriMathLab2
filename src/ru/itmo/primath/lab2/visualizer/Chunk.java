package ru.itmo.primath.lab2.visualizer;

import ru.itmo.primath.lab2.Function2;

import java.util.Arrays;

public class Chunk {

    public final Function2 function2;
    public final float x;
    public final float z;
    public final float size;
    public final int steps;
    private Entity entity;

    private class Point{
        public final float x;
        public final float z;
        public final float y;

        private Point(float x, float z, float y) {
            this.x = x;
            this.z = z;
            this.y = y;
        }
    }

//    private class Triangle{
//        public final Point p1;
//        public final Point p2;
//        public final Point p3;
//
//        private Triangle(Point p1, Point p2, Point p3) {
//            this.p1 = p1;
//            this.p2 = p2;
//            this.p3 = p3;
//        }
//    }

    public Chunk(Function2 function2, float x, float z, float size, int steps) {
        this.function2 = function2;
        this.x = x;
        this.z = z;
        this.size = size;
        this.steps = steps;
    }

    private void createEntity(){
        int nodes = (steps + 1) * (steps + 1);
        float stepLength = size / steps;

        Point[][] points = new Point[steps + 1][steps + 1];
        for (int i = 0; i < steps + 1; ++i){
            for (int j = 0; j < steps + 1; ++j){
                float currX = x + stepLength * j;
                float currZ = z + stepLength * i;
                float currY = (float) function2.value(currX, currZ);
                points[i][j] = new Point(currX, currZ, currY);
            }
        }

        float[] vertices = new float[nodes * 3];
        for (int i = 0; i < steps + 1; i++) {
            for (int j = 0; j < steps + 1; j++) {
                vertices[3 * ((steps + 1) * i + j)] = points[i][j].x;
                vertices[3 * ((steps + 1) * i + j) + 1] = points[i][j].y;
                vertices[3 * ((steps + 1) * i + j) + 2] = points[i][j].z;
            }
        }

//        Triangle[][] bottomTriangles = new Triangle[steps][steps];
//        Triangle[][] topTriangles = new Triangle[steps][steps];
//        for (int i = 0; i < steps; i++){
//            for (int j = 0; j < steps; j++){
//                Point p1 = points[i][j];
//                Point p2 = points[i][j + 1];
//                Point p3 = points[i + 1][j];
//                bottomTriangles[i][j] = new Triangle(p1, p2, p3);
//                p1 = points[i + 1][j + 1];
//                topTriangles[i][j] = new Triangle(p1, p2, p3);
//            }
//        }
        int[] indices = new int[steps * steps * 2 * 3];
        for (int i = 0; i < steps; ++i){
            for (int j = 0; j < steps; ++j){
                indices[(i * steps + j) * 6] = i * (steps + 1) + j;
                indices[(i * steps + j) * 6 + 1] = i * (steps + 1) + j + 1;
                indices[(i * steps + j) * 6 + 2] = (i + 1) * (steps + 1) + j;

                indices[(i * steps + j) * 6 + 3] = (i + 1) * (steps + 1) + j + 1;
                indices[(i * steps + j) * 6 + 4] = i * (steps + 1) + j + 1;
                indices[(i * steps + j) * 6 + 5] = (i + 1) * (steps + 1) + j;
            }
        }

        float maxHeight = Float.MIN_VALUE;
        float minHeight = Float.MAX_VALUE;

        for (int i = 0; i < vertices.length / 3; i++) {
            if (vertices[3 * i + 1] > maxHeight)
                maxHeight = vertices[3 * i + 1];
            if (vertices[3 * i + 1] < minHeight)
                minHeight = vertices[3 * i + 1];
        }

        float[] colors = new float[nodes * 3];
        for (int i = 0; i < steps + 1; i++) {
            for (int j = 0; j < steps + 1; j++) {
                colors[3 * ((steps + 1) * i + j)] = 1f - (points[i][j].y - minHeight) / (maxHeight - minHeight);
                colors[3 * ((steps + 1) * i + j) + 1] = (points[i][j].y - minHeight) / (maxHeight - minHeight);
                colors[3 * ((steps + 1) * i + j) + 2] = 0;
            }
        }

        entity = new Entity(vertices, colors, indices);
    }

    public Entity getEntity() {
        this.createEntity();
        return entity;
    }
}
