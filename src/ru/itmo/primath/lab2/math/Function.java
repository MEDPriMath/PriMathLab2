package ru.itmo.primath.lab2.math;

public interface Function {

    double value(Vector v);

    Vector grad(Vector v);

}
