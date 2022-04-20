package ru.itmo.primath.lab2.util;

import ru.itmo.primath.lab2.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Path {
    private final List<Vector<?>> path = new ArrayList<>();

    public void addPoint(Vector<?> v) {
        path.add(v);
    }

    public List<Vector<?>> getPoints() {
        return new ArrayList<>(path);
    }

    public int length() {
        return path.size();
    }

    public Vector<?> last() {
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
