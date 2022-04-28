package ru.itmo.primath.lab2.visualizer;

public class Chunk {

    public final float x;
    public final float z;
    public final float xSize;
    public final float ySize;
    private Entity entity;

    public Chunk(float x, float z, float xSize, float ySize) {
        this.x = x;
        this.z = z;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public Entity getEntity() {
        return entity;
    }
}
