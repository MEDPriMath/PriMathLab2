package ru.itmo.primath.lab2.methods;

import ru.itmo.primath.lab2.math.Function2;
import ru.itmo.primath.lab2.math.Vector2;
import ru.itmo.primath.lab2.util.Path;

public class MyConstantStepGDMinimizer implements GDMinimizer {

    @Override
    public Path<Vector2> minimize(Function2 func, Vector2 startPoint, double epsilon, double step) {
        Path<Vector2> path = new Path<>();
        path.addPoint(startPoint);

        Vector2 currentPoint = startPoint;
        double currValue = func.value(currentPoint);

        double diff = 100;

        while (diff > epsilon && path.length() < 500) {
            var newPoint = currentPoint.decrease(func.grad(currentPoint).multiply(step));
            double newValue = func.value(newPoint);

            diff = Math.abs(newValue - currValue);
            path.addPoint(currentPoint);

            currentPoint = newPoint;
            currValue = newValue;
        }

        return path;
    }
}
