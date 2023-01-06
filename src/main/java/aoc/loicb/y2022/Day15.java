package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputTransformer;

import java.util.*;
import java.util.stream.Collectors;

public class Day15 implements Day<List<Reading>, Long> {
    public static void main(String[] args) {
        DayExecutor<List<Reading>> de = new DayExecutor<>(new Day15InputTransformer());
        de.execute(new Day15());
    }

    @Override
    public Long partOne(List<Reading> readings) {
        int maxX = getMaxX(readings);
        Map<Reading, Integer> readingToDistance = calculateDistances(readings);
        int y = isTestSet(readings) ? 10 : 2000000;
        int x = -maxX;
        long cpt = 0;
        boolean possible = false;
        while (!possible || x < maxX) {
            Position position = new Position(x, y);
            possible = isPositionPossible(position, readingToDistance);
            if (!possible && !isBeacon(position, readings)) cpt++;
            x++;
        }
        return cpt;
    }

    private boolean isBeacon(Position potentialBeacon, List<Reading> readings) {
        return readings.stream().anyMatch(reading -> reading.beacon().equals(potentialBeacon));
    }

    private boolean isPositionPossible(Position potentialBeacon, Map<Reading, Integer> readingToDistance) {
        return readingToDistance
                .keySet()
                .stream()
                .noneMatch(reading -> calculateDistance(reading.sensor(), potentialBeacon) <= readingToDistance.get(reading));
    }

    private boolean isTestSet(List<Reading> readings) {
        return readings.get(0).sensor().x() == 2;
    }


    private Map<Reading, Integer> calculateDistances(List<Reading> readings) {
        return readings
                .stream()
                .collect(Collectors.toMap(reading -> reading, this::calculateDistance));
    }

    private int calculateDistance(Reading reading) {
        return calculateDistance(reading.sensor(), reading.beacon());
    }


    private int calculateDistance(Position sensor, Position beacon) {
        int distance = Math.abs(sensor.x() - beacon.x());
        return distance + Math.abs(sensor.y() - beacon.y());
    }


    private int getMaxX(List<Reading> readings) {
        return readings
                .stream()
                .mapToInt(this::getMaxX)
                .max()
                .orElseThrow();
    }

    private int getMaxX(Reading reading) {
        return Math.max(reading.sensor().x(), reading.beacon().x());
    }

    @Override
    public Long partTwo(List<Reading> readings) {
        int max = isTestSet(readings) ? 20 : 4000000;
        Set<Position> positions = new HashSet<>();
        for (Reading reading : readings) addAroundPositions(reading, max, positions);
        Map<Reading, Integer> readingToDistance = calculateDistances(readings);
        Position free = positions.stream().filter(position -> isPositionPossible(position, readingToDistance)).findFirst().orElseThrow();
        return free.x() * 4000000L + free.y();
    }

    private void addAroundPositions(Reading reading, int max, Set<Position> positions) {
        int distance = calculateDistance(reading) + 1;
        for (int i = 0; i <= distance; i++) {
            if (isPositionInside(reading.sensor().x() + i, reading.sensor().y() + distance - i, max))
                positions.add(new Position(reading.sensor().x() + i, reading.sensor().y() + distance - i));
            if (isPositionInside(reading.sensor().x() - i, reading.sensor().y() + distance - i, max))
                positions.add(new Position(reading.sensor().x() - i, reading.sensor().y() + distance - i));
            if (isPositionInside(reading.sensor().x() + i, reading.sensor().y() - distance + i, max))
                positions.add(new Position(reading.sensor().x() + i, reading.sensor().y() - distance + i));
            if (isPositionInside(reading.sensor().x() - i, reading.sensor().y() - distance + i, max))
                positions.add(new Position(reading.sensor().x() - i, reading.sensor().y() - distance + i));
        }
    }

    private boolean isPositionInside(int x, int y, int max) {
        if (x < 0) return false;
        if (y < 0) return false;
        if (x > max) return false;
        return y <= max;
    }
}


record Reading(Position sensor, Position beacon) {

}


class Day15InputTransformer implements InputTransformer<List<Reading>> {
    @Override
    public List<Reading> transform(String rawInput) {
        return Arrays.stream(rawInput.split("\\r?\\n"))
                .map(this::transformLine)
                .collect(Collectors.toList());
    }

    private Reading transformLine(String line) {
        String[] items = line.split("closest");
        return new Reading(transformItem(items[0]), transformItem(items[1]));
    }

    private Position transformItem(String item) {
        String[] numbers = item.trim().split("[^-?0-9]+");
        return new Position(Integer.parseInt(numbers[1]), Integer.parseInt(numbers[2]));
    }


}