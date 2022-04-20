//package ru.itmo.primath.lab2;
//
//import static ru.itmo.primath.lab2.util.MathUtils.sqr;
//
//public class Data {
//
//    public static Function2 RosenbrocksValley = new Function2() {
//
//        @Override
//        public double value(double x, double y) {
//            return sqr(1 - x) + 100 * sqr(y - sqr(x));
//        }
//
//        private final double epsilon = 1E-9;
//
//        @Override
//        public Vector2 grad(double x, double y) {
//            double scale = 1 / epsilon;
//            double value = value(x, y);
//            double dx = value(x + epsilon, y) - value;
//            double dy = value(x, y + epsilon) - value;
//            return new Vector2(dx, dy).scale(1 / epsilon);
//        }
//    };
//
//}
