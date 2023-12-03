package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Day3 implements Day<char[][], Integer> {
    public static void main(String[] args) {
        DayExecutor<char[][]> de = new DayExecutor<>(rawInput -> {
            String[] inputLines = rawInput.split("\\r?\\n");
            char[][] input = new char[inputLines.length][];
            IntStream.range(0, inputLines.length).forEach(value -> input[value] = inputLines[value].toCharArray());
            return input;
        });
        de.execute(new Day3());

    }

    @Override
    public Integer partOne(char[][] schematic) {
        Map<Position, List<Integer>> indexes = indexing(schematic);
        return indexes
                .values()
                .stream()
                .mapToInt(integers -> integers
                        .stream()
                        .mapToInt(value -> value).sum())
                .sum();
    }

    private Map<Position, List<Integer>> indexing(char[][] schematic) {
        Map<Position, List<Integer>> index = new HashMap<>();
        for (int i = 0; i < schematic.length; i++) {
            for (int j = 0; j < schematic[i].length; j++) {
                if (isSymbol(schematic[i][j])) {
                    index.put(new Position(i, j), getAdjacentNumbers(schematic, i, j));
                }
            }
        }
        return index;
    }

    List<Integer> getAdjacentNumbers(char[][] schematic, int x, int y) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = x - 1; i <= x + 1; i++) {
            int number = 0;
            boolean valid = false;
            for (int j = Math.max(0, y - 3); j <= Math.min(schematic[x].length, y + 3); j++) {
                if (Character.isDigit(schematic[i][j])) {
                    number = 10 * number + (schematic[i][j] - '0');
                    valid |= areAdjacent(x, y, i, j);
                } else {
                    if (number != 0 && valid) numbers.add(number);
                    number = 0;
                    valid = false;
                }
            }
            if (number != 0 && valid) numbers.add(number);
        }
        return numbers;
    }

    private boolean areAdjacent(int x, int y, int i, int j) {
        return Math.abs(x - i) <= 1 && Math.abs(y - j) <= 1;
    }

    private boolean isSymbol(char c) {
        return !Character.isDigit(c) && c != '.';
    }

    @Override
    public Integer partTwo(char[][] schematic) {
        Map<Position, List<Integer>> indexes = indexing(schematic);
        return indexes
                .keySet()
                .stream().filter(position -> schematic[position.x()][position.y()] == '*')
                .map(indexes::get)
                .filter(integers -> integers.size() == 2).
                mapToInt(integers -> integers.get(0) * integers.get(1))
                .sum();
    }

    record Position(int x, int y) {
    }
}
