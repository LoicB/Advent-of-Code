package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.*;
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
        int count = 1;
        int gardX = position.x;
        int gardY = position.y;
        int newX = gardX + moveX(input[gardY][gardX]);
        int newY = gardY + moveY(input[gardY][gardX]);
        while (isMap(input, newX, newY)) {
            char gard = input[gardY][gardX];
            if (input[newY][newX] == '.' || input[newY][newX] == 'X') {
                count += input[newY][newX] == '.' ? 1 : 0;
                input[newY][newX] = input[gardY][gardX];
                input[gardY][gardX] = 'X';
                gardX = newX;
                gardY = newY;
                newX = newX + moveX(gard);
                newY = newY + moveY(gard);
            } else if (input[newY][newX] == '#') {
                input[gardY][gardX] = turn(input, gardX, gardY);
                newX = gardX + moveX(input[gardY][gardX]);
                newY = gardY + moveY(input[gardY][gardX]);
            }
        }
        return count;
    }

    private Optional<Integer> moveGard(char[][] map, int gardX, int gardY) {
        int count = 1;
        int newX = gardX + moveX(map[gardY][gardX]);
        int newY = gardY + moveY(map[gardY][gardX]);
        Set<GardState> cycleWatch = new HashSet<>();
        while (isMap(map, newX, newY)) {
            char gard = map[gardY][gardX];
            if (!cycleWatch.add(new GardState(gardX, gardY, gard))) {
                return Optional.empty();
            }
            if (map[newY][newX] == '.' || map[newY][newX] == 'X') {
                count += map[newY][newX] == '.' ? 1 : 0;
                map[newY][newX] = map[gardY][gardX];
                map[gardY][gardX] = 'X';
                gardX = newX;
                gardY = newY;
                newX = newX + moveX(gard);
                newY = newY + moveY(gard);
            } else if (map[newY][newX] == '#') {
                map[gardY][gardX] = turn(map, gardX, gardY);
                newX = gardX + moveX(map[gardY][gardX]);
                newY = gardY + moveY(map[gardY][gardX]);
            }
        }
        return Optional.of(count);
    }


    private boolean isMap(char[][] map, int x, int y) {
        return y >= 0 && y < map.length && x >= 0 && x < map[y].length;
    }

    private char turn(char[][] map, int x, int y) {
        char c = map[y][x];
        return switch (c) {
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
        char[][] copy = copyMap(input);
        Position startingPosition = findGard(input);
        moveGard(copy, startingPosition.x(), startingPosition.y());
        copy[startingPosition.y()][startingPosition.x()] = '.';
        List<Position> cycleCandidates = getHistory(copy).stream()
                .filter(position -> {
                    char[][] newCopy = copyMap(input);
                    newCopy[position.y()][position.x()] = '#';
                    return moveGard(newCopy, startingPosition.x(), startingPosition.y()).isEmpty();
                }).toList();
        return cycleCandidates.size();
    }

    private List<Position> getHistory(char[][] map) {
        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'X' || map[i][j] == 'v' || map[i][j] == '<' || map[i][j] == '^' || map[i][j] == '>') {
                    positions.add(new Position(j, i));
                }
            }
        }
        return positions;
    }

    private char[][] copyMap(char[][] map) {
        char[][] copy = new char[map.length][];
        for (int i = 0; i < map.length; i++) {
            char[] line = map[i];
            int aLength = line.length;
            copy[i] = new char[aLength];
            System.arraycopy(line, 0, copy[i], 0, aLength);
        }
        return copy;
    }

    record Position(int x, int y) {
    }

    record GardState(int x, int y, char c) {
    }

}
