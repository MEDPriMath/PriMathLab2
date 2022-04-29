package ru.itmo.primath.lab2.linearminimizers;

public class GoldenRationMinimizer implements Minimizer {

    private static final double RATIO = 0.38196601125d;

    public double minimize(Oracle oracle, double epsilon, double a, double b) {
        var p1 = new OracleProbe(oracle, a + (b - a) * RATIO);
        var p2 = new OracleProbe(oracle, b - (b - a) * RATIO);

        while ((b - a) > epsilon) {
            if (p1.getValue() < p2.getValue()) {
                b = p2.getX();
                p2.set(p1);
                p1.makeProbe(a + (b - a) * RATIO);
            } else {
                a = p1.getX();
                p1.set(p2);
                p2.makeProbe(b - (b - a) * RATIO);
            }
        }
        return (b + a) / 2;
    }

    @Override
    public String toString() {
        return "Golden Ratio Minimizer";
    }

}
