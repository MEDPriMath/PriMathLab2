package ru.itmo.primath.lab2.methods;

import ru.itmo.primath.lab2.linearminimizers.GoldenRationMinimizer;
import ru.itmo.primath.lab2.linearminimizers.LinearMinimizerAdapter;
import ru.itmo.primath.lab2.math.Function2;
import ru.itmo.primath.lab2.math.Vector2;
import ru.itmo.primath.lab2.util.Path;

public class ReevesMinimizer implements GDMinimizer {
    @Override
    public Path<Vector2> minimize(Function2 func, Vector2 startPoint, double epsilon, double step) {
        Path path = new Path();
        path.addPoint(startPoint);

        Vector2 prevPoint = startPoint;
        Vector2 prevGrad = func.grad(prevPoint);

        if (prevGrad.length() < epsilon) {
            return path;
        }

        var d = prevGrad;

        final float range = 2.5f;

        step = LinearMinimizerAdapter.minimize(new GoldenRationMinimizer(), epsilon, 0, range,
                prevPoint, d, func);

        Vector2 currPoint = prevPoint.decrease(d.multiply(step));
        Vector2 currGrad = func.grad(currPoint);
        path.addPoint(currPoint);

        double diff = currPoint.distance(prevPoint);

        while (diff > epsilon && currGrad.length() > epsilon) {
            double b = Math.pow(currGrad.length(), 2) / Math.pow(prevGrad.length(), 2);
            d = currGrad.increase(prevGrad.multiply(b));
            step = LinearMinimizerAdapter.minimize(new GoldenRationMinimizer(), epsilon, 0, range,
                    currPoint, d, func);
            prevPoint = currPoint;
            prevGrad = currGrad;
            currPoint = prevPoint.decrease(d.multiply(step));
            path.addPoint(currPoint);
            diff = currPoint.distance(prevPoint);
        }

        return path;
    }
}
