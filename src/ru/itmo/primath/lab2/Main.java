package ru.itmo.primath.lab2;

import ru.itmo.primath.lab2.methods.ConstStepMethod;
import ru.itmo.primath.lab2.methods.Method;
import ru.itmo.primath.lab2.util.Pair;

public class Main {

    public static void main(String[] args) {
        Method constStepMethod = new ConstStepMethod(new Pair<>(20d, 20d), 0.1, 1E-7);
        var coords = constStepMethod.minimizeFunc(0);
        System.out.println(coords.getCoords(coords.getIterationsNumber()).toString());
        System.out.println("Iterations: " + coords.getIterationsNumber());
    }

}
