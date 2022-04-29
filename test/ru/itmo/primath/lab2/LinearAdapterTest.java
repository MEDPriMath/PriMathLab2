package ru.itmo.primath.lab2;

import org.junit.jupiter.api.Test;
import ru.itmo.primath.lab2.linearminimizers.GoldenRationMinimizer;
import ru.itmo.primath.lab2.linearminimizers.LinearMinimizerAdapter;

public class LinearAdapterTest {
    @Test
    public void linearAdapterTest() {
        Function2 f = (x, y) -> Math.abs(x - 1);

        Vector2 start = new Vector2(0.5f, 0.5f);
        Vector2 grad = f.grad(start);

        double num = LinearMinimizerAdapter.minimize(new GoldenRationMinimizer(), 1E-9, -100, 100,
                start, grad, f);
        Vector2 minimum = start.decrease(grad.multiply(num));
        System.out.println(num + " " + minimum);
    }
}
