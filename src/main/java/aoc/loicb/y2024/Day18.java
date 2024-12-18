package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.*;
import java.util.stream.Stream;

public class Day18 implements Day<List<Coordinate>, Object> {

    public static void main(String[] args) {
        DayExecutor<List<Coordinate>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public Coordinate transformObject(String string) {
                var values = string.split(",");
                return new Coordinate(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            }
        });
        de.execute(new Day18());

    }

    @Override
    public Integer partOne(List<Coordinate> input) {
        var map = generateMap(input, 71, 1024);
        return findStepToExit(map);
    }

    int findStepToExit(char[][] map) {
        Set<Coordinate> history = new HashSet<>();
        var scores = generateScoreMap(map.length);
        LinkedList<Coordinate> queue = new LinkedList<>();
        queue.add(new Coordinate(0, 0));
        scores[0][0] = 0;
        while (!queue.isEmpty()) {
            var current = queue.poll();
            getNeighbour(current, map).forEach(coordinate -> {
                if (history.add(coordinate)) {
                    scores[coordinate.y()][coordinate.x()] = scores[current.y()][current.x()] + 1;
                    queue.add(coordinate);
                }
            });
        }
        return scores[map.length - 1][map.length - 1];
    }

    private List<Coordinate> getNeighbour(Coordinate original, char[][] map) {
        return Stream.of(
                        new Coordinate(original.x() - 1, original.y()),
                        new Coordinate(original.x(), original.y() - 1),
                        new Coordinate(original.x() + 1, original.y()),
                        new Coordinate(original.x(), original.y() + 1)
                )
                .filter(coordinate -> !isOutside(map, coordinate) && !isWall(map, coordinate))
                .toList();
    }

    private boolean isOutside(char[][] map, Coordinate coordinate) {
        return coordinate.y() < 0 || coordinate.y() >= map.length || coordinate.x() < 0 || coordinate.x() >= map[coordinate.y()].length;
    }

    private boolean isWall(char[][] map, Coordinate coordinate) {
        return map[coordinate.y()][coordinate.x()] == '#';
    }

    char[][] generateMap(List<Coordinate> coordinates, int size, int numberOfBytes) {
        char[][] map = generateMap(size);
        for (int i = 0; i < numberOfBytes; i++) {
            map[coordinates.get(i).y()][coordinates.get(i).x()] = '#';
        }
        return map;
    }

    char[][] generateMap(int size) {
        char[][] map = new char[size][];
        for (int i = 0; i < map.length; i++) {
            map[i] = new char[size];
            Arrays.fill(map[i], '.');
        }
        return map;
    }


    int[][] generateScoreMap(int size) {
        int[][] score = new int[size][];
        for (int i = 0; i < score.length; i++) {
            score[i] = new int[size];
            Arrays.fill(score[i], Integer.MAX_VALUE);
        }
        return score;
    }

    @Override
    public String partTwo(List<Coordinate> input) {
        var blockingByte = findBlockingByte(input, 71, 1024);
        return blockingByte.x() + "," + blockingByte.y();
    }

    Coordinate findBlockingByte(List<Coordinate> coordinates, int size, int numberOfBytes) {
        int from = numberOfBytes;
        int to = coordinates.size();
        while (from + 1 < to) {
            int index = (to - from) / 2 + from;
            if (findStepToExit(generateMap(coordinates, size, index)) == Integer.MAX_VALUE) {
                to = index;
            } else {
                from = index;
            }
        }
        return coordinates.get(from);
    }

}


record Coordinate(int x, int y) {
}