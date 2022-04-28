package ru.itmo.primath.lab2.methods;

import ru.itmo.primath.lab2.Function2;
import ru.itmo.primath.lab2.Vector;
import ru.itmo.primath.lab2.Vector2;
import ru.itmo.primath.lab2.util.MathUtils;
import ru.itmo.primath.lab2.util.Path;

public class SplitStepGDMinimizer implements GDMinimizer {
    private double split = 0.95;

    @Override
    public Path minimize(Function2 func, Vector2 startPoint, double epsilon, double step) {
        Path path = new Path();
        path.addPoint(startPoint);

        Vector2 prevPoint = startPoint;
        double prevValue = func.value(prevPoint);
        Vector2 prevGrad = func.grad(prevPoint);

        Vector2 currPoint = prevPoint.decrease(prevGrad.multiply(step));
        double currValue = func.value(currPoint);
        Vector2 currGrad = func.grad(currPoint);

        double diff = currPoint.distance(prevPoint);

        while (diff > epsilon) {
            //todo проверить правильность условия Армихо

            // Fxk - Fxk+1 > какая-нибудь константа * шаг * квадрат длины градиента
            if (prevValue - currValue > Math.pow(10, -3) * step * Math.pow(MathUtils.CalcVectorLength(prevGrad), 2)) {
                path.addPoint(currPoint);
                prevPoint = currPoint;
                prevGrad = currGrad;
                prevValue = currValue;
                currPoint = prevPoint.decrease(prevGrad.multiply(step));
                currValue = func.value(currPoint);
                currGrad = func.grad(currPoint);
                diff = currPoint.distance(prevPoint);
            } else {
                step = split * step;
            }
        }

        return path;
    }

    public void setSplit(double split) {
        this.split = split;
    }
}
