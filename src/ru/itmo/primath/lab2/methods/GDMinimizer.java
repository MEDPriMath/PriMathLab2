package ru.itmo.primath.lab2.methods;

import ru.itmo.primath.lab2.Function2;
import ru.itmo.primath.lab2.Vector2;
import ru.itmo.primath.lab2.util.Path;


/**
 * GD Minimizer - Gradient Descent Minimizer
 */
public interface GDMinimizer {

    Path minimize(Function2 func, Vector2 startPoint, double epsilon, double startStep);

}