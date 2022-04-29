package ru.itmo.primath.lab2.linearminimizers;

import ru.itmo.primath.lab2.Function2;
import ru.itmo.primath.lab2.Vector2;

public class LinearMinimizerAdapter {

    public static double minimize(Minimizer minimizer, double epsilon, int a, int b,
                                Vector2 startPoint, Vector2 grad, Function2 f) {
        Oracle oracle = x -> f.value(startPoint.decrease(grad.multiply(x)));
        return minimizer.minimize(oracle, epsilon, a, b);
    }

}
