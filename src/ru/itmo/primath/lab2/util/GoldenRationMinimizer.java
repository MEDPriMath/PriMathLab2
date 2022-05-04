package ru.itmo.primath.lab2.util;

import ru.itmo.primath.lab2.math.Function2;
import ru.itmo.primath.lab2.math.Vector2;

public class GoldenRationMinimizer {
    private static final double RATIO = 0.38196601125d;

    public static double CalcMinimize(double a, double b, Vector2 point, Vector2 grad, Function2 func) {
        var p1 = a + (b - a) * RATIO;
        var p2 = b - (b - a) * RATIO; // a ---- p1 ---- p2 ---- b

        var f1 = func.value(point.decrease(grad.multiply(p1)));
        var f2 = func.value(point.decrease(grad.multiply(p2)));

        while (b - a > Math.pow(10, -3)) {
            if (f1 < f2) {
                b = p2;
                p2 = p1;
                f2 = f1;
                p1 = a + (b - a) * RATIO;
                f1 = func.value(point.decrease(grad.multiply(p1)));
            } else {
                a = p1;
                p1 = p2;
                f1 = f2;
                p2 = b - (b - a) * RATIO;
                f2 = func.value(point.decrease(grad.multiply(p2)));
            }
        }
        return (a + b) / 2;
    }

}
