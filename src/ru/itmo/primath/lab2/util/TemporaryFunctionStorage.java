package ru.itmo.primath.lab2.util;

import ru.itmo.primath.lab2.Function2;
import ru.itmo.primath.lab2.Vector2;

public class TemporaryFunctionStorage {
    // todo: придумать нормальную хуйню для хранения функций и создания их градиента, а то кринге

    public static final Function2 squareFunction1 = new Function2() {
        @Override
        public double value(double x, double y) {
            return 4 * Math.pow(x, 2) + 2 * x * y + Math.pow(y, 2);
        }

        @Override
        public Vector2 grad(double x, double y) {
            return new Vector2(
                    2 * (4 * x + y),
                    2 * (x + y));
        }
    };
}
