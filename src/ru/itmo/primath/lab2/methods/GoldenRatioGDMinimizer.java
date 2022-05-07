package ru.itmo.primath.lab2.methods;

import ru.itmo.primath.lab2.linearminimizers.GoldenRationMinimizer;
import ru.itmo.primath.lab2.linearminimizers.LinearMinimizerAdapter;
import ru.itmo.primath.lab2.math.Function2;
import ru.itmo.primath.lab2.math.Path;
import ru.itmo.primath.lab2.math.Vector2;

public class GoldenRatioGDMinimizer implements GDMinimizer {

    @Override
    public Path<Vector2> minimize(Function2 func, Vector2 startPoint, double epsilon, double step) {
        Path<Vector2> path = new Path<>();
        path.addPoint(startPoint);

        Vector2 x = startPoint;
        Vector2 gradient = func.grad(x);

        while (gradient.length() > epsilon && path.length() <= 500) {
            double range = 1000;
            step = LinearMinimizerAdapter.minimize(new GoldenRationMinimizer(), epsilon * epsilon, 0, range,
                    x, gradient, func);

            x = x.decrease(gradient.multiply(step));
            gradient = func.grad(x);

            path.addPoint(x);
        }

        return path;
    }

    @Override
    public String toString() {
        return "Golden Ratio GD Minimizer";
    }
}
