package ru.itmo.primath.lab2.methods;

import ru.itmo.primath.lab2.Function2;
import ru.itmo.primath.lab2.Vector2;
import ru.itmo.primath.lab2.util.GoldenRationMinimizer;
import ru.itmo.primath.lab2.util.Path;

// todo исправить неработающую хуйню
// поработайте, мрази
public class GoldenRatioGDMinimizer implements GDMinimizer {

    @Override
    public Path minimize(Function2 func, Vector2 startPoint, double epsilon, double step) {
        Path path = new Path();
        path.addPoint(startPoint);

        Vector2 prevPoint = startPoint;
        Vector2 prevGrad = func.grad(prevPoint);

        final int range = 100;

        step = GoldenRationMinimizer.CalcMinimize(-range, range, prevPoint, prevGrad, func);

        Vector2 currPoint = prevPoint.decrease(prevGrad.multiply(step));
        path.addPoint(currPoint);

        double diff = currPoint.distance(prevPoint);

        while (diff > epsilon && path.length() < 500) {
            Vector2 currGrad = func.grad(currPoint);
            step = GoldenRationMinimizer.CalcMinimize(-range, range, currPoint, currGrad, func);
            prevPoint = currPoint;
            currPoint = prevPoint.decrease(prevGrad.multiply(step));
            path.addPoint(currPoint);
            prevGrad = currGrad;
            diff = currPoint.distance(prevPoint);
        }

        return path;
    }
}
