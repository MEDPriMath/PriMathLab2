package ru.itmo.primath.lab2.methods;

import ru.itmo.primath.lab2.linearminimizers.FibonacciMinimizer;
import ru.itmo.primath.lab2.linearminimizers.LinearMinimizerAdapter;
import ru.itmo.primath.lab2.math.Function2;
import ru.itmo.primath.lab2.math.Path;
import ru.itmo.primath.lab2.math.Vector2;

import static java.lang.Math.max;

public class FibonacciGDMinimizer implements GDMinimizer {

    @Override
    public Path<Vector2> minimize(Function2 func, Vector2 startPoint, double epsilon, double step) {
        Path<Vector2> path = new Path<>();
        path.addPoint(startPoint);

        Vector2 x = startPoint;
        Vector2 gradient = func.grad(x);

        while (gradient.length() > epsilon && path.length() <= 500) {
            double range = 10;
            step = LinearMinimizerAdapter.minimize(new FibonacciMinimizer(), epsilon / max(1, gradient.sqrLength()), 0, range,
                    x, gradient, func);

            x = x.decrease(gradient.multiply(step));
            gradient = func.grad(x);

            path.addPoint(x);
        }

        return path;
    }

    @Override
    public String toString() {
        return "Fibonacci GD Minimizer";
    }
}
