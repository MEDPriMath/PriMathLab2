package ru.itmo.primath.lab2.methods;

import ru.itmo.primath.lab2.util.Interval;
import ru.itmo.primath.lab2.util.TemporaryFuncAndGradStorage;

import java.util.ArrayList;
import java.util.List;

public abstract class Method {
    private final double[] points;
    private final double step;
    private final int iterations;
    private final double eps;
    private final TemporaryFuncAndGradStorage funcAndGradStorage = new TemporaryFuncAndGradStorage();

    public Method(double[] startingPoints, double step, int iterations, double eps) {
        points = startingPoints;
        this.step = step;
        this.iterations = iterations;
        this.eps = eps;
    }

    public List<Interval> minimizeAllFunc() {
        List<Interval> result = new ArrayList<>();
        for (int i = 0; i < funcAndGradStorage.getNumberOfFunctions(); i++) {
            funcAndGradStorage.setOperatedFuncNumber(i);
            result.add(minimizeFunc(i));
        }
        return result;
    }

    public abstract Interval minimizeFunc(int funcNumber);
}
