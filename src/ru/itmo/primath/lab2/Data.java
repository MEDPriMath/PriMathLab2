package ru.itmo.primath.lab2;

import static ru.itmo.primath.lab2.util.MathUtils.sqr;

public class Data {

    public static final Function2 RosenbrocksValley = new Function2() {

        @Override
        public double value(double x, double y) {
            return sqr(1 - x) + 100 * sqr(y - sqr(x));
        }

        private final double epsilon = 1E-9;

        @Override
        public Vector2 grad(double x, double y) {
            double scale = 1 / epsilon;
            double value = value(x, y);
            double dx = value(x + epsilon, y) - value;
            double dy = value(x, y + epsilon) - value;
            return new Vector2(dx, dy).multiply(1 / epsilon);
        }
    };

    public static final Function2 TestFunc1 = new Function2() {

        @Override
        public double value(double x, double y) {
            var num =  1 / (Math.exp(x) + Math.exp(-x))
                    + 1 / (Math.exp(x - 4) + Math.exp(-x + 4))
                    + 1 / (Math.exp(y) + Math.exp(-y))
                    + 1 / (Math.exp(y - 4) + Math.exp(-y + 4));


            return num * 2;
        }

        private final double epsilon = 1E-9;

        @Override
        public Vector2 grad(double x, double y) {
            double scale = 1 / epsilon;
            double value = value(x, y);
            double dx = value(x + epsilon, y) - value;
            double dy = value(x, y + epsilon) - value;
            return new Vector2(dx, dy).multiply(1 / epsilon);
        }
    };

}
