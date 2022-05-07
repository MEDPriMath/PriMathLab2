package ru.itmo.primath.lab2.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Path<T extends Vector<T>> {
    private final List<T> path = new ArrayList<>();

    public void addPoint(T v) {
        path.add(v);
    }

    public List<T> getPoints() {
        return new ArrayList<>(path);
    }

    public int length() {
        return path.size();
    }

    public T last() {
        return path.get(path.size() - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path1 = (Path) o;
        return Objects.equals(path, path1.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
