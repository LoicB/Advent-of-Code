package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.function.BiPredicate;
import java.util.stream.IntStream;

public class Day12 implements Day<char[][], Integer> {


    public static void main(String[] args) {
        DayExecutor<char[][]> de = new DayExecutor<>(rawInput -> {
            String[] inputLines = rawInput.split("\\r?\\n");
            char[][] input = new char[inputLines.length][];
            IntStream.range(0, inputLines.length).forEach(value -> input[value] = inputLines[value].toCharArray());
            return input;
        });
        de.execute(new Day12());

    }

    @Override
    public Integer partOne(char[][] heightmap) {
        int[] startingPoint = getStartingPoint(heightmap);
        int[] endPoint = getEndPoint(heightmap);
        heightmap[startingPoint[0]][startingPoint[1]] = 'a';
        heightmap[endPoint[0]][endPoint[1]] = 'z';
        int[][] distances = createDistances(heightmap);
        navigate(heightmap, distances, startingPoint[0], startingPoint[1], 0, (character, character2) -> character - character2 <= 1);
        return distances[endPoint[0]][endPoint[1]];
    }

    private void navigate(char[][] heightmap, int[][] distances, int x, int y, int currentDistance, BiPredicate<Character, Character> validator) {
        if (distances[x][y] <= currentDistance) return;
        distances[x][y] = currentDistance;
        char current = heightmap[x][y];
        if (x > 0 && validator.test(heightmap[x - 1][y], current)) {
            navigate(heightmap, distances, x - 1, y, currentDistance + 1, validator);
        }
        if (y > 0 && validator.test(heightmap[x][y - 1], current)) {
            navigate(heightmap, distances, x, y - 1, currentDistance + 1, validator);
        }
        if (x < heightmap.length - 1 && validator.test(heightmap[x + 1][y], current)) {
            navigate(heightmap, distances, x + 1, y, currentDistance + 1, validator);
        }
        if (y < heightmap[x].length - 1 && validator.test(heightmap[x][y + 1], current)) {
            navigate(heightmap, distances, x, y + 1, currentDistance + 1, validator);
        }
    }


    private int[][] createDistances(char[][] heightmap) {
        int[][] distances = new int[heightmap.length][];
        for (int i = 0; i < heightmap.length; i++) {
            distances[i] = new int[heightmap[i].length];
            for (int j = 0; j < heightmap[i].length; j++) {
                distances[i][j] = Integer.MAX_VALUE;
            }
        }
        return distances;
    }

    private int[] getStartingPoint(char[][] heightmap) {
        return getPoint(heightmap, 'S');
    }

    private int[] getEndPoint(char[][] heightmap) {
        return getPoint(heightmap, 'E');
    }

    private int[] getPoint(char[][] heightmap, char point) {
        for (int i = 0; i < heightmap.length; i++) {
            for (int j = 0; j < heightmap[i].length; j++) {
                if (heightmap[i][j] == point) return new int[]{i, j};
            }
        }
        return new int[]{0, 0};
    }

    @Override
    public Integer partTwo(char[][] heightmap) {
        int[] startingPoint = getStartingPoint(heightmap);
        int[] endPoint = getEndPoint(heightmap);
        heightmap[startingPoint[0]][startingPoint[1]] = 'a';
        heightmap[endPoint[0]][endPoint[1]] = 'z';
        int[][] distances = createDistances(heightmap);
        navigate(heightmap, distances, endPoint[0], endPoint[1], 0, (map, current) -> current != 'a' && current - map <= 1);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < heightmap.length; i++) {
            for (int j = 0; j < heightmap[i].length; j++) {
                if (heightmap[i][j] == 'a') min = Math.min(min, distances[i][j]);
            }
        }
        return min;
    }
}


