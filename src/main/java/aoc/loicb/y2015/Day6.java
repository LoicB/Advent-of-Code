package aoc.loicb.y2015;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Day6 implements Day<List<String>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day6());
    }

    @Override
    public Integer partOne(List<String> input) {
        boolean[][] grid = new boolean[1000][1000];
        input.forEach(s -> {
            if (isToggle(s)) {
                toggle(grid, s);
            } else if (isTurnOn(s)) {
                turnOn(grid, s);
            } else if (isTurnOff(s)) {
                turnOff(grid, s);
            }
        });
        return countLightOn(grid);
    }

    boolean isTurnOn(String input) {
        return input.charAt(1) == 'u' && input.charAt(6) == 'n';
    }

    private void turnOn(boolean[][] grid, String input) {
        applyInstruction(
                extractCoordinates(input, false),
                (x, y) -> grid[x][y] = true);
    }

    boolean isTurnOff(String input) {
        return input.charAt(1) == 'u' && input.charAt(6) == 'f';
    }

    private void turnOff(boolean[][] grid, String input) {
        applyInstruction(
                extractCoordinates(input, false),
                (x, y) -> grid[x][y] = false);

    }

    boolean isToggle(String input) {
        return input.charAt(1) == 'o';
    }

    private void toggle(boolean[][] grid, String input) {
        applyInstruction(
                extractCoordinates(input, true),
                (x, y) -> grid[x][y] = !grid[x][y]);

    }

    List<Integer> extractCoordinates(String input) {
        return extractCoordinates(input, isToggle(input));
    }


    List<Integer> extractCoordinates(String input, boolean isToggle) {
        String[] parts = input.split(" ");
        String[] coordinates1 = parts[isToggle ? 1 : 2].split(",");
        String[] coordinates2 = parts[isToggle ? 3 : 4].split(",");
        List<Integer> coordinates = new ArrayList<>();
        coordinates.add(Integer.parseInt(coordinates1[0]));
        coordinates.add(Integer.parseInt(coordinates1[1]));
        coordinates.add(Integer.parseInt(coordinates2[0]));
        coordinates.add(Integer.parseInt(coordinates2[1]));
        return coordinates;
    }

    private void applyInstruction(List<Integer> coordinates, BiConsumer<Integer, Integer> instruction) {
        for (int i = coordinates.get(0); i <= coordinates.get(2); i++) {
            for (int j = coordinates.get(1); j <= coordinates.get(3); j++) {
                instruction.accept(i, j);
            }
        }
    }

    private int countLightOn(boolean[][] grid) {
        int count = 0;
        for (boolean[] line : grid) {
            for (boolean isOn : line) {
                if (isOn) count++;
            }
        }
        return count;
    }

    @Override
    public Integer partTwo(List<String> input) {
        int[][] grid = new int[1000][1000];
        input.forEach(s -> {
            if (isToggle(s)) {
                toggleBrightness(grid, s);
            } else if (isTurnOn(s)) {
                turnOnBrightness(grid, s);
            } else if (isTurnOff(s)) {
                turnOffBrightness(grid, s);
            }
        });
        return countTotalBrightness(grid);
    }


    private void turnOnBrightness(int[][] grid, String input) {
        applyInstruction(
                extractCoordinates(input, false),
                (x, y) -> grid[x][y] += 1);
    }


    private void turnOffBrightness(int[][] grid, String input) {
        applyInstruction(
                extractCoordinates(input, false),
                (x, y) -> grid[x][y] = Math.max(0, grid[x][y] - 1));

    }

    private void toggleBrightness(int[][] grid, String input) {
        applyInstruction(
                extractCoordinates(input, true),
                (x, y) -> grid[x][y] += 2);

    }

    private int countTotalBrightness(int[][] grid) {
        int count = 0;
        for (int[] line : grid) {
            for (int brightness : line) {
                count += brightness;
            }
        }
        return count;
    }
}
