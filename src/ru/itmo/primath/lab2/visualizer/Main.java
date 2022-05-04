package ru.itmo.primath.lab2.visualizer;

import ru.itmo.primath.lab2.Data;
import ru.itmo.primath.lab2.visualizer.engine.Engine;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Engine engine = new Engine();
        final float meshSize = 200;
        engine.run(
                1280,
                720,
                List.of(Data.TestFunctions),
                -meshSize/2,
                -meshSize/2,
                meshSize,
                1000);
    }
}
