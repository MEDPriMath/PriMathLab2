package ru.itmo.primath.lab2;

public interface Function2 {

    default double value(Vector2 v) {
        return this.value(v.x(), v.y());
    }

    default Vector2 grad(Vector2 v) {
        return this.grad(v.x(), v.y());
    }

    double value(double x, double y);

    Vector2 grad(double x, double y);

}
