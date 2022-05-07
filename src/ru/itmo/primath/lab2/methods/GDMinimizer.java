package ru.itmo.primath.lab2.methods;

import ru.itmo.primath.lab2.math.Function2;
import ru.itmo.primath.lab2.math.Path;
import ru.itmo.primath.lab2.math.Vector2;


/**
 * GD Minimizer - Gradient Descent Minimizer
 */
public interface GDMinimizer {

    Path<Vector2> minimize(Function2 func, Vector2 startPoint, double epsilon, double step);

}
