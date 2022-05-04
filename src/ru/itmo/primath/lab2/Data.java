package ru.itmo.primath.lab2;

import ru.itmo.primath.lab2.math.Function2;

import static java.lang.Math.sin;
import static ru.itmo.primath.lab2.util.MathUtils.sqr;

public class Data {

    public static final Function2[] TestFunctions = new Function2[] {
            (x, y) -> x * x + x + 2 * y + y * y,
            (x, y) -> sin(x) + sin(y),
            (x, y) -> sin(x) * sin(y),
            (x, y) -> (sqr(x/2) + sqr(y/2)) / 4,
            (x, y) -> (sqr(x/2) + sqr(y/3)) / 4,
            (x, y) -> 1*x*x*y*y + 2*x*x*y + 3*x*x + 4*x + 5*x*y*y + 6*x*y + 7*y*y + 8*y,
//            new Function2() {
//                @Override
//                public double value(double x, double y) {
//                    return Math.pow(sqr(x/2), 0.65) + Math.pow(sqr(y/2), 0.55)
//                            + Math.pow(sqr((x-10)/2), 0.65) + Math.pow(sqr((y-5)/2), 0.55);
//                }
//
////                @Override
//                public Vector2 grad(double x, double y) {
//                    return Vector2();
//                }
            };
//};
//            (x, y) -> Math.pow(sqr(x/2), 0.65) + Math.pow(sqr(y/2), 0.55)
//                    + Math.pow(sqr((x-10)/2), 0.65) + Math.pow(sqr((y-5)/2), 0.55)
//            ,
//    };

//    public static final Function2 RosenbrocksValley = (x, y) -> sqr(1 - x) + 100 * sqr(y - sqr(x));
//
//            new Function2() {
//
//        @Override
//        public double value(double x, double y) {
//            return ;
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
//            return new Vector2(dx, dy).multiply(1 / epsilon);
//        }
//    };
//
//    public static final Function2 TestFunc1 = new Function2() {
//
//        @Override
//        public double value(double x, double y) {
//            var num =  1 / (Math.exp(x) + Math.exp(-x))
//                    + 1 / (Math.exp(x - 4) + Math.exp(-x + 4))
//                    + 1 / (Math.exp(y) + Math.exp(-y))
//                    + 1 / (Math.exp(y - 4) + Math.exp(-y + 4));
//
//
//            return num * 2;
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
//            return new Vector2(dx, dy).multiply(1 / epsilon);
//        }
//    };

}
