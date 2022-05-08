package ru.itmo.primath.lab2;

import org.junit.jupiter.api.Test;
import ru.itmo.primath.lab2.util.TemporaryFunctionStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EssentialsTest {

    @Test
    public void jupiterTest() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void funcCalculationTest() {
        assertEquals(TemporaryFunctionStorage.squareFunction1.value(0, 0), 0);
        assertEquals(TemporaryFunctionStorage.squareFunction1.value(1, 1), 7);
        assertEquals(TemporaryFunctionStorage.squareFunction1.value(-1, -1), 7);
    }

    @Test
    public void gradCalculationTest() {
        assertEquals(TemporaryFunctionStorage.squareFunction1.grad(0, 0).x(), 0);
        assertEquals(TemporaryFunctionStorage.squareFunction1.grad(0, 0).y(), 0);
        assertEquals(TemporaryFunctionStorage.squareFunction1.grad(1, 1).x(), 10);
        assertEquals(TemporaryFunctionStorage.squareFunction1.grad(1, 1).y(), 4);
        assertEquals(TemporaryFunctionStorage.squareFunction1.grad(-1, -1).x(), -10);
        assertEquals(TemporaryFunctionStorage.squareFunction1.grad(-1, -1).y(), -4);
    }
}
