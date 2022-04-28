package ru.itmo.primath.lab2.util;

import ru.itmo.primath.lab2.Function2;
import ru.itmo.primath.lab2.Vector2;

import static ru.itmo.primath.lab2.util.MathUtils.Sqr;

public class TemporaryFunctionStorage {

    public static final Function2 squareFunction1 = new Function2() {
        @Override
        public double value(double x, double y) {
            return 4 * Sqr(x) + 2 * x * y + Sqr(y);
        }

        @Override
        public Vector2 grad(double x, double y) {
            return new Vector2(
                    2 * (4 * x + y),
                    2 * (x + y));
        }
    };
}
