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

        //критерий выхода
        if (prevGrad.length() < epsilon) {
            return path;
        }

        //коэф d, при 0 итерации является градиентом начальной точки, во всех остальных
        //итерациях высчитывается, как currGrad + prevGrad * b (смотри про b далее)
        var d = prevGrad;

        final float range = 2.5f;

        //ищем шаг по критерию минимума
        step = LinearMinimizerAdapter.minimize(new GoldenRationMinimizer(), epsilon, 0, range,
                prevPoint, d, func);

        Vector2 currPoint = prevPoint.decrease(d.multiply(step));
        Vector2 currGrad = func.grad(currPoint);
        path.addPoint(currPoint);

        double diff = currPoint.distance(prevPoint);
        int i = 0;
        while (diff > epsilon && currGrad.length() > epsilon) {
            ++i;
            //b - коэф, высчитывающийся как отношение квадрата длины curGrad к квадрату длины prevGrad
            double b = Math.pow(currGrad.length(), 2) / Math.pow(prevGrad.length(), 2);

            d = currGrad.increase(prevGrad.multiply(b));
            step = LinearMinimizerAdapter.minimize(new GoldenRationMinimizer(), epsilon, 0, range,
                    currPoint, d, func);
            prevPoint = currPoint;
            prevGrad = currGrad;
            currPoint = prevPoint.decrease(d.multiply(step));
            path.addPoint(currPoint);
            diff = currPoint.distance(prevPoint);
            if (i == 500)
                break;
        }

        System.out.println(this.getClass().getSimpleName() + "steps: " + i);
        return path;
    }
}
