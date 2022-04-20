package ru.itmo.primath.lab2;

import ru.itmo.primath.lab2.methods.ConstantStepGDMinimizer;
import ru.itmo.primath.lab2.util.Path;

import static ru.itmo.primath.lab2.util.TemporaryFunctionStorage.squareFunction1;

public class Main {

    public static void main(String[] args) {
        var constStepMinimizer = new ConstantStepGDMinimizer();
        Path path = constStepMinimizer.minimize(squareFunction1, new Vector2(20d, 20d), 1E-7, 5);
        System.out.println(path.last());
        System.out.println("Iterations: " + path.length());

        path.getPoints().forEach(System.out::println);
    }

}
