package ru.itmo.primath.lab2.visualizer;

import ru.itmo.primath.lab2.Data;

import java.util.List;

public class VisualizerMain {

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.run(
                1280,
                720,
                List.of(Data.TestFunctions),
                -20,
                -20,
                40,
                1000);
    }
}
