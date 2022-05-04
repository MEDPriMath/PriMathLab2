package ru.itmo.primath.lab2.methods;

import ru.itmo.primath.lab2.linearminimizers.FibonacciMinimizer;
import ru.itmo.primath.lab2.linearminimizers.LinearMinimizerAdapter;
import ru.itmo.primath.lab2.math.Function2;
import ru.itmo.primath.lab2.math.Vector2;
import ru.itmo.primath.lab2.util.Path;

public class FibonacciGDMinimizer implements GDMinimizer {

    @Override
    public Path minimize(Function2 func, Vector2 startPoint, double epsilon, double step) {
        Path<Vector2> path = new Path<>();
        path.addPoint(startPoint);

        Vector2 prevPoint = startPoint;
        Vector2 prevGrad = func.grad(prevPoint);

        final float range = 2.5f;

        step = LinearMinimizerAdapter.minimize(new FibonacciMinimizer(), epsilon, 0, range,
                prevPoint, prevGrad, func);

        Vector2 currPoint = prevPoint.decrease(prevGrad.multiply(step));
        path.addPoint(currPoint);

        double diff = currPoint.distance(prevPoint);

        while (diff > epsilon && path.length() < 500) {
            Vector2 currGrad = func.grad(currPoint);
            step = LinearMinimizerAdapter.minimize(new FibonacciMinimizer(), epsilon, 0, range,
                    currPoint, currGrad, func);
            prevPoint = currPoint;
            currPoint = prevPoint.decrease(prevGrad.multiply(step));
            path.addPoint(currPoint);
            prevGrad = currGrad;
            diff = currPoint.distance(prevPoint);
        }

        return path;
    }
}
