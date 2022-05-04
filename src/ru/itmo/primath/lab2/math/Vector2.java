package ru.itmo.primath.lab2.math;

public class Vector2 extends Vector<Vector2> {

    public Vector2(double... coords) {
        super(coords);
        if (coords.length > 2) {
            throw new IllegalArgumentException();
        }
    }

    public Vector2(double x, double y) {
        super(x, y);
    }

    public double x() {
        return value(0);
    }

    public double y() {
        return value(1);
    }

    @Override
    protected Vector2 createVector(double... coords) {
        return new Vector2(coords);
    }
}
