package ru.itmo.primath.lab2.util;

import java.util.List;
import java.util.function.BiFunction;

public class TemporaryFuncAndGradStorage {
    // todo: придумать нормальную хуйню для хранения функций и создания их градиента, а то кринге

    private final List<BiFunction<Double, Double, Double>> listOfFunctions = List.of(
            (x, y) -> 4 * Math.pow(x, 2) + 2 * x * y + Math.pow(y, 2)
    );


    private final List<BiFunction<Double, Double, Double>> listOfFunctionsGradient = List.of(
            (x, y) -> 8 * x + 2 * y + 2 * x + 2 * y
    );

    private int operatedFuncNumber;

    public TemporaryFuncAndGradStorage(int funcId) { // id between 0 and n - 1, where n - number of funcs in list;
        operatedFuncNumber = funcId;
    }

    public TemporaryFuncAndGradStorage() {
        this(0);
    }

    public void setOperatedFuncNumber(int id) {
        operatedFuncNumber = id;
    }

    public int getNumberOfFunctions() {
        return listOfFunctions.size();
    }

    public double calculateFunc(double x, double y) {
        return listOfFunctions.get(operatedFuncNumber).apply(x, y);
    }

    public double calculateGrad(double x, double y) {
        return listOfFunctionsGradient.get(operatedFuncNumber).apply(x, y);
    }
}
