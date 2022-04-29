package ru.itmo.primath.lab2.methods;

import ru.itmo.primath.lab2.Function2;
import ru.itmo.primath.lab2.Vector2;
import ru.itmo.primath.lab2.util.FibonacciMinimizer;
import ru.itmo.primath.lab2.util.GoldenRationMinimizer;
import ru.itmo.primath.lab2.util.Path;

public class FibonacciGDMinimizer implements GDMinimizer {

    @Override
    public Path minimize(Function2 func, Vector2 startPoint, double epsilon, double step) {
        Path path = new Path();
        path.addPoint(startPoint);

        Vector2 prevPoint = startPoint;
        Vector2 prevGrad = func.grad(prevPoint);

        step = FibonacciMinimizer.CalcMinimize(-100, 100, prevPoint, prevGrad, func);

        Vector2 currPoint = prevPoint.decrease(prevGrad.multiply(step));
        path.addPoint(currPoint);

        double diff = currPoint.distance(prevPoint);

        while (diff > epsilon) {
            Vector2 currGrad = func.grad(currPoint);
            step = FibonacciMinimizer.CalcMinimize(-100, 100, currPoint, currGrad, func);
            prevPoint = currPoint;
            currPoint = prevPoint.decrease(prevGrad.multiply(step));
            path.addPoint(currPoint);
            prevGrad = currGrad;
            diff = currPoint.distance(prevPoint);
        }

        return path;
    }
}
