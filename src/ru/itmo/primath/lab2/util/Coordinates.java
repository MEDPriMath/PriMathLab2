package ru.itmo.primath.lab2.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Coordinates {
    private final List<Pair<Double>> coordsList; // list of all coordinates, result coordinates are in the end
    private int iterations;

    public Coordinates(double x, double y) {
        iterations = 0;
        coordsList = new ArrayList<>();
        coordsList.add(new Pair<>(x, y));
    }

    public void addCoords(double x, double y) {
        coordsList.add(new Pair<>(x, y));
        iterations++;
    }

    public void addCoords(Pair<Double> coords) {
        coordsList.add(coords);
        iterations++;
    }

    public Pair<Double> getCoords(int id) {
        return coordsList.get(id);
    }

    public List<Pair<Double>> getCoordsList() {
        return new ArrayList<>(coordsList);
    }

    public int getIterationsNumber() {
        return iterations;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates coords = (Coordinates) o;
        return coords.getCoordsList().equals(this.coordsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordsList);
    }
}
