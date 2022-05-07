package ru.itmo.primath.lab2.methods;

import ru.itmo.primath.lab2.math.Function2;
import ru.itmo.primath.lab2.math.Path;
import ru.itmo.primath.lab2.math.Vector2;

public class ConstantStepGDMinimizer implements GDMinimizer {

    @Override
    public Path<Vector2> minimize(Function2 func, Vector2 startPoint, double epsilon, double step) {
        Path<Vector2> path = new Path<>();
        path.addPoint(startPoint);

        Vector2 x = startPoint;
        Vector2 gradient = func.grad(x);

        while (gradient.length() > epsilon && path.length() <= 500) {
            x = x.decrease(gradient.multiply(step));
            gradient = func.grad(x);

            path.addPoint(x);
        }

        return path;
    }

    @Override
    public String toString() {
        return "Constant Step GD Minimizer";
    }

    //    @Override
//    public Path minimize(Function2 func, Vector2 startPoint, double epsilon, double step) {
//        Path path = new Path();
//        path.addPoint(startPoint);
//
//        Vector2 prevPoint = startPoint;
//        double prevValue = func.value(prevPoint);
//        Vector2 prevGrad = func.grad(prevPoint);
//
//        Vector2 currPoint = prevPoint.decrease(prevGrad.multiply(step));
//        path.addPoint(currPoint);
//
//        double diff = currPoint.distance(prevPoint);
//        int i = 0;
//        while (diff > epsilon) {
//            ++i;
//            double currValue = func.value(currPoint);
//            while (currValue > prevValue) {
//                step = step / 2;
//                currPoint = prevPoint.decrease(prevGrad.multiply(step));
//                currValue = func.value(currPoint);
//            }
//            Vector2 currGrad = func.grad(currPoint);
//            prevPoint = currPoint;
//            currPoint = prevPoint.decrease(prevGrad.multiply(step));
//            path.addPoint(currPoint);
//            prevGrad = currGrad;
//            prevValue = currValue;
//            diff = currPoint.distance(prevPoint);
//        }
//        System.out.println(this.getClass().getSimpleName() + "steps: " + i);
//        return path;
//    }
}
