package ru.itmo.primath.lab2.methods;

import ru.itmo.primath.lab2.Function2;
import ru.itmo.primath.lab2.Vector2;
import ru.itmo.primath.lab2.util.Path;

public class ConstantStepGDMinimizer implements GDMinimizer {

    private double step;

    public ConstantStepGDMinimizer(double step) {
        this.step = step;
    }

    public void setStep(double step) {
        if (step < 0) {
            throw new IllegalArgumentException(String.format("Step cannot be below zero, value got: %f", step));
        }
        this.step = step;
    }

    @Override
    public Path minimize(Function2 func, Vector2 startPoint, double epsilon, double startStep) {
        Path path = new Path();
        path.addPoint(startPoint);

        Vector2 prevPoint = startPoint;
        double prevValue = func.value(prevPoint);
        Vector2 prevGrad = func.grad(prevPoint);

        Vector2 currPoint = prevPoint.decrease(prevGrad.multiply(step));
        path.addPoint(currPoint);

        double diff = currPoint.distance(prevPoint);

        while (diff > epsilon) {
            double currValue = func.value(currPoint);
            Vector2 currGrad = func.grad(currPoint);
            if (currValue > prevValue) {
                step = step / 2;
            }
            prevPoint = currPoint;
            currPoint = prevPoint.decrease(prevGrad.multiply(step));
            path.addPoint(currPoint);
            prevGrad = currGrad;
            prevValue = currValue;
            diff = currPoint.distance(prevPoint);
        }

        return path;
    }
}
