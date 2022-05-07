package ru.itmo.primath.lab2.methods;

import ru.itmo.primath.lab2.math.Function2;
import ru.itmo.primath.lab2.math.Path;
import ru.itmo.primath.lab2.math.Vector2;

public class SplitStepGDMinimizer implements GDMinimizer {
    private double split = 0.5;

    @Override
    public Path<Vector2> minimize(Function2 func, Vector2 startPoint, double epsilon, double step) {
        Path<Vector2> path = new Path<>();
        path.addPoint(startPoint);

        Vector2 x = startPoint;
        double value = func.value(x);
        Vector2 gradient = func.grad(x);

        while (gradient.length() > epsilon && path.length() <= 500) {
            Vector2 prevX = x;
            x = prevX.decrease(gradient.multiply(step));
            double newValue;
            while ((newValue = func.value(x)) > value) {
                step *= split;
                System.out.printf("%e %e %e\n", step, newValue, value);
                x = prevX.decrease(gradient.multiply(step));
            }
            value = newValue;
            gradient = func.grad(x);

            path.addPoint(x);
        }

        return path;
//        Path path = new Path();
//        path.addPoint(startPoint);
//
//        Vector2 prevPoint = startPoint;
//        double prevValue = func.value(prevPoint);
//        Vector2 prevGrad = func.grad(prevPoint);
//
//        Vector2 currPoint = prevPoint.decrease(prevGrad.multiply(step));
//        double currValue = func.value(currPoint);
//        Vector2 currGrad = func.grad(currPoint);
//
//        double diff = currPoint.distance(prevPoint);
//
//        int i = 0;
//        while (diff > epsilon) {
//            //todo проверить правильность условия Армихо
//            ++i;
//            // Fxk - Fxk+1 > какая-нибудь константа * шаг * квадрат длины градиента
//            if (prevValue - currValue > Math.pow(10, -3) * step * Math.pow(prevGrad.length(), 2)) {
//                path.addPoint(currPoint);
//                prevPoint = currPoint;
//                prevGrad = currGrad;
//                prevValue = currValue;
//                currPoint = prevPoint.decrease(prevGrad.multiply(step));
//                currValue = func.value(currPoint);
//                currGrad = func.grad(currPoint);
//                diff = currPoint.distance(prevPoint);
//            } else {
//                step = split * step;
//            }
//        }
//        System.out.println(this.getClass().getSimpleName() + "steps: " + i);
//        return path;
    }

    @Override
    public String toString() {
        return "Split Step GD Minimizer";
    }
}
