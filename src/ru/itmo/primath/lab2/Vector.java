package ru.itmo.primath.lab2;

import static java.lang.Math.sqrt;
import static ru.itmo.primath.lab2.util.MathUtils.sqr;

public abstract class Vector<T extends Vector<?>> {

    protected final double[] coords;

    public Vector(double... coords) {
        this.coords = coords;
    }

//    public T scale(double scalar) {
//        for (int i = 0; i < coords.length; i++) {
//            coords[i] *= scalar;
//        }
//
//        return (T) this;
//    }

    public double value(int index) {
        return coords[index];
    }

//    public T increase(T other) {
//        if (coords.length != other.coords.length) {
//            throw new IllegalArgumentException(String.format("Different count of coords, %d and %d", coords.length, other.coords.length));
//        }
//        double[] newCoords = new double[coords.length];
//        for (int i = 0; i < coords.length; i++) {
//            newCoords[i] = coords[i] + other.coords[i];
//        }
//        return createVector(newCoords);
//    }

    public T decrease(T other) {
        if (coords.length != other.coords.length) {
            throw new IllegalArgumentException(String.format("Different count of coords, %d and %d", coords.length, other.coords.length));
        }
        double[] newCoords = new double[coords.length];
        for (int i = 0; i < coords.length; i++) {
            newCoords[i] = coords[i] - other.coords[i];
        }
        return createVector(newCoords);
    }

    public T multiply(double scalar) {
        double[] newCoords = new double[coords.length];
        for (int i = 0; i < coords.length; i++) {
            newCoords[i] = coords[i] * scalar;
        }
        return createVector(newCoords);
    }

    public double distance(T other) {
        if (coords.length != other.coords.length) {
            throw new IllegalArgumentException(String.format("Different count of coords, %d and %d", coords.length, other.coords.length));
        }
        double sum = 0;
        for (int i = 0; i < coords.length; i++) {
            sum += sqr(coords[i] - other.coords[i]);
        }
        return sqrt(sum);
    }

    protected abstract T createVector(double... coords);

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(').append(coords[0]);
        for (int i = 1; i < coords.length; i++) {
            sb.append(", ").append(String.format("%f", coords[i]));
        }
        sb.append(')');
        return sb.toString();
    }
}
