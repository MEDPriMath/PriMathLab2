package ru.itmo.primath.lab2.methods;

import ru.itmo.primath.lab2.util.Coordinates;
import ru.itmo.primath.lab2.util.Point;

public class ConstStepMethod extends Method {
    public ConstStepMethod(Point<Double> startingPoints, double step, double eps) {
        super(startingPoints, step, eps);
    }

    @Override
    public Coordinates minimizeFunc(int funcNumber) {
        Coordinates coords = new Coordinates(points.x, points.y);
        Point<Double> prevCoords = coords.getCoords(0);
        double prevFunc = funcAndGradStorage.calculateFunc(prevCoords.x, prevCoords.y);
        double prevGrad = funcAndGradStorage.calculateGrad(prevCoords.x, prevCoords.y);

        Point<Double> currCoords = new Point<>(prevCoords.x - step * prevGrad, prevCoords.y - step * prevGrad);
        coords.addCoords(currCoords);

        double diff = calculateDiff(currCoords.x, prevCoords.x, currCoords.y, prevCoords.y);

        while (diff > eps) {
            double currFunc = funcAndGradStorage.calculateFunc(currCoords.x, currCoords.y);
            double currGrad = funcAndGradStorage.calculateGrad(currCoords.x, currCoords.y);
            if (currFunc > prevFunc) {
                step = -step / 2;
            }
            prevCoords = currCoords;
            currCoords = new Point<>(prevCoords.x - step * prevGrad, prevCoords.y - step * prevGrad);
            coords.addCoords(currCoords);
            prevGrad = currGrad;
            prevFunc = currFunc;
            diff = calculateDiff(currCoords.x, prevCoords.x, currCoords.y, prevCoords.y);
        }

        return coords;
    }

    private double calculateDiff(double x, double prevX, double y, double prevY) {
        return Math.sqrt(Math.pow(x - prevX, 2) + Math.pow(y - prevY, 2));
    }
}
