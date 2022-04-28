package ru.itmo.primath.lab2.util;

import ru.itmo.primath.lab2.Vector2;

public class MathUtils {
    public static double Sqr(double x) {
        return x * x;
    }
    public static double CalcVectorLength(Vector2 vec) {
        return Math.sqrt(Math.pow(vec.x(), 2) + Math.pow(vec.y(), 2));
    }
}
