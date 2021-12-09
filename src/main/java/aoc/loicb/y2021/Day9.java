package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.*;
import java.util.stream.Collectors;

public class Day9 implements Day<int[][], Integer> {

    public static void main(String[] args) {
        DayExecutor<int[][]> de = new DayExecutor<>(rawInput -> {
            String[] lines = rawInput.split("\\r?\\n");
            int[][] input = new int[lines.length][];
            for (int i = 0; i < lines.length; i++) {
                input[i] = new int[lines[i].length()];
                for (int j = 0; j < lines[i].length(); j++) {
                    input[i][j] = Character.getNumericValue(lines[i].charAt(j));
                }
            }
            return input;
        });
        de.execute(new Day9());
    }

    @Override
    public Integer partOne(int[][] heightmap) {
        return searchForLowPoints(heightmap)
                .stream()
                .mapToInt(point -> heightmap[point.x()][point.y()] + 1).sum();
    }

    private List<Point> searchForLowPoints(int[][] heightmap) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < heightmap.length; i++) {
            for (int j = 0; j < heightmap[i].length; j++) {
                if (heightmap[i][j] < Collections.min(getAdjacentValues(heightmap, i, j))) {
                    points.add(new Point(i, j));
                }
            }
        }
        return points;
    }


    private List<Integer> getAdjacentValues(int[][] heightmap, int x, int y) {
        return getAdjacentLocations(heightmap, x, y)
                .stream()
                .map(point -> heightmap[point.x()][point.y()])
                .collect(Collectors.toList());
    }


    private List<Point> getAdjacentLocations(int[][] heightmap, int x, int y) {
        List<Point> locations = new ArrayList<>();
        if (areInArray(heightmap, x - 1, y)) locations.add(new Point(x - 1, y));
        if (areInArray(heightmap, x, y - 1)) locations.add(new Point(x, y - 1));
        if (areInArray(heightmap, x + 1, y)) locations.add(new Point(x + 1, y));
        if (areInArray(heightmap, x, y + 1)) locations.add(new Point(x, y + 1));
        return locations;
    }

    private boolean areInArray(int[][] heightmap, int x, int y) {
        return x >= 0 && y >= 0 && x < heightmap.length && y < heightmap[x].length;
    }

    @Override
    public Integer partTwo(int[][] heightmap) {
        List<Integer> scores = searchForLowPoints(heightmap)
                .stream()
                .map(point -> calculateBasinScore(heightmap, point.x(), point.y()))
                .sorted()
                .toList();
        return scores.get(scores.size() - 1) * scores.get(scores.size() - 2) * scores.get(scores.size() - 3);
    }

    protected int calculateBasinScore(int[][] heightmap, int x, int y) {
        int score = 1;
        boolean[][] passed = new boolean[heightmap.length][];
        for (int i = 0; i < heightmap.length; i++) {
            passed[i] = new boolean[heightmap[i].length];
        }
        passed[x][y] = true;
        List<Point> adjacent = getAdjacentLocations(heightmap, x, y);
        Queue<Point> queueA = new LinkedList<>(adjacent);
        while (!queueA.isEmpty()) {
            Point point = queueA.poll();
            if (heightmap[point.x()][point.y()] != 9 && !passed[point.x()][point.y()]) {
                passed[point.x()][point.y()] = true;
                queueA.addAll(getAdjacentLocations(heightmap, point.x(), point.y()));
                score++;
            }
        }
        return score;
    }
}


