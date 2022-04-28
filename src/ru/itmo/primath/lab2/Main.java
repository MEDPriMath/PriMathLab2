package ru.itmo.primath.lab2;

import ru.itmo.primath.lab2.methods.ConstantStepGDMinimizer;
import ru.itmo.primath.lab2.methods.SplitStepGDMinimizer;
import ru.itmo.primath.lab2.util.Path;

import static ru.itmo.primath.lab2.util.TemporaryFunctionStorage.squareFunction1;

public class Main {

    public static void main(String[] args) {
        var constStepMinimizer = new ConstantStepGDMinimizer();
        Path path1 = constStepMinimizer.minimize(squareFunction1, new Vector2(20d, 20d), 1E-7, 0.1);
        System.out.println(path1.last());
        System.out.println("Iterations: " + path1.length());
        //path1.getPoints().forEach(System.out::println);

        System.out.println("\n\n\n\n\n\n\n\n\n");

        var splitStepGDMinimizer = new SplitStepGDMinimizer();
        Path path2 = splitStepGDMinimizer.minimize(squareFunction1, new Vector2(20d, 20d), 1E-7, 0.1);
        System.out.println(path2.last());
        System.out.println("Iterations: " + path2.length());
        //path2.getPoints().forEach(System.out::println);
    }

}
