package ru.itmo.primath.lab2.methods;

import ru.itmo.primath.lab2.util.Coordinates;
import ru.itmo.primath.lab2.util.Point;
import ru.itmo.primath.lab2.util.TemporaryFuncAndGradStorage;

import java.util.ArrayList;
import java.util.List;


public abstract class Method {
    protected final Point<Double> points;
    protected final double eps;
    protected final TemporaryFuncAndGradStorage funcAndGradStorage = new TemporaryFuncAndGradStorage();
    protected double step;

    public Method(Point<Double> startingCoords, double step, double eps) {
        points = startingCoords;
        this.step = step;
        this.eps = eps;
    }

    // minimizes all functions in a row
    public List<Coordinates> minimizeAllFunc() {
        List<Coordinates> result = new ArrayList<>();
        for (int i = 0; i < funcAndGradStorage.getNumberOfFunctions(); i++) {
            funcAndGradStorage.setOperatedFuncNumber(i);
            result.add(minimizeFunc(i));
        }
        return result;
    }

    // minimizes single function
    public abstract Coordinates minimizeFunc(int funcNumber);
}
