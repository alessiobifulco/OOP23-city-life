package unibo.citysimulation.model.zone;

import java.util.Random;

import unibo.citysimulation.utilities.Pair;

public record Zone(String name, float personPercents, float businessPercents, Pair<Integer, Integer> wellfareMinMax,
        Pair<Integer, Integer> ageMinMax, Boundary boundary) {
            static Random random = new Random();

            public Pair<Integer, Integer> getRandomPosition() {
                int x = random.nextInt(boundary.getWidth()) + boundary.getX();
                int y = random.nextInt(boundary.getHeight()) + boundary.getY();
                return new Pair<>(x, y);
            }
}