package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class Day6 implements Day<char[][], Integer> {
    public static void main(String[] args) {
        DayExecutor<char[][]> de = new DayExecutor<>(rawInput -> {
            String[] inputLines = rawInput.split("\\r?\\n");
            char[][] input = new char[inputLines.length][];
            IntStream.range(0, inputLines.length).forEach(value -> input[value] = inputLines[value].toCharArray());
            return input;
        });
        de.execute(new Day6());

    }

    @Override
    public Integer partOne(char[][] input) {
        Position position = findGard(input);
        int gardX = position.x;
        int gardY = position.y;
        char gard = input[gardY][gardX];
        input[gardY][gardX] = '.';
        return moveGard(input, gardX, gardY, gard).size();
    }

    private Set<Position> moveGard(char[][] map, int gardX, int gardY, char gard) {
        int newX = gardX + moveX(gard);
        int newY = gardY + moveY(gard);
        Set<Position> history = new HashSet<>();
        Set<GardState> cycleWatch = new HashSet<>();
        while (isMap(map, newX, newY)) {
            if (!cycleWatch.add(new GardState(gardX, gardY, gard))) {
                return new HashSet<>();
            }
            if (map[newY][newX] == '.' || map[newY][newX] == 'X') {
                history.add(new Position(gardX, gardY));
                gardX = newX;
                gardY = newY;
                newX = newX + moveX(gard);
                newY = newY + moveY(gard);
            } else if (map[newY][newX] == '#') {
                gard = turn(gard);
                newX = gardX + moveX(gard);
                newY = gardY + moveY(gard);
            }
        }
        history.add(new Position(gardX, gardY));
        return history;
    }


    private boolean isMap(char[][] map, int x, int y) {
        return y >= 0 && y < map.length && x >= 0 && x < map[y].length;
    }

    private char turn(char gard) {
        return switch (gard) {
            default -> 0;
            case '^' -> '>';
            case '>' -> 'v';
            case 'v' -> '<';
            case '<' -> '^';
        };
    }

    private int moveX(char c) {
        return switch (c) {
            default -> 0;
            case '>' -> 1;
            case '<' -> -1;
        };
    }

    private int moveY(char c) {
        return switch (c) {
            default -> 0;
            case 'v' -> 1;
            case '^' -> -1;
        };
    }

    private Position findGard(char[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '^') return new Position(j, i);
            }
        }
        throw new RuntimeException("No guard");
    }

    @Override
    public Integer partTwo(char[][] input) {
        Position startingPosition = findGard(input);
        char gard = input[startingPosition.y()][startingPosition.x()];
        input[startingPosition.y()][startingPosition.x()] = '.';
        Set<Position> history = moveGard(input, startingPosition.x(), startingPosition.y(), gard);
        List<Position> cycleCandidates = history.stream()
                .filter(position -> {
                    input[position.y()][position.x()] = '#';
                    boolean isCycling = moveGard(input, startingPosition.x(), startingPosition.y(), gard).isEmpty();
                    input[position.y()][position.x()] = '.';
                    return isCycling;
                }).toList();
        return cycleCandidates.size();
    }
    record Position(int x, int y) {
    }

    record GardState(int x, int y, char c) {
    }

}
