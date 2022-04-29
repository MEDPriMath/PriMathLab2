package ru.itmo.primath.lab2.visualizer;

import ru.itmo.primath.lab2.Data;

import java.util.ArrayList;
import java.util.Collection;

public class VisualizerMain {

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.run(
                1280,
                720,
                entityProvider);
    }

    private static final EntityProvider entityProvider = new EntityProvider() {
        ArrayList<Entity> entities;

        @Override
        public Collection<Entity> getEntities() {
            if (entities == null)
                gen();
            return entities;
        }

        private void gen() {
            entities = new ArrayList<>();

            Chunk chunk = new Chunk(Data.TestFunc1, -100, -100, 200, 1000);

            entities.add(chunk.getEntity());
        }
    };
}
