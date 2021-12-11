package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Day11 implements Day<int[][], Integer> {
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
        de.execute(new Day11());
    }

    @Override
    public Integer partOne(int[][] energyLevels) {
        int[][] levels = Arrays.stream(energyLevels).map(int[]::clone).toArray(int[][]::new);
        return IntStream.range(0, 100).map(value -> nextStep(levels)).sum();
    }

    protected int nextStep(int[][] energyLevels) {
        int flashCount = 0;
        increaseLevels(energyLevels);
        boolean flashing;
        do {
            flashing = false;
            for (int i = 0; i < energyLevels.length; i++) {
                for (int j = 0; j < energyLevels[i].length; j++) {
                    if (energyLevels[i][j] > 9) {
                        flashCount++;
                        flashing = true;
                        triggerFlash(energyLevels, i, j);
                    }
                }
            }
        } while (flashing);
        return flashCount;
    }

    private void increaseLevels(int[][] energyLevels) {
        for (int i = 0; i < energyLevels.length; i++) {
            for (int j = 0; j < energyLevels[i].length; j++) {
                energyLevels[i][j]++;
            }
        }
    }

    protected void triggerFlash(int[][] energyLevels, int x, int y) {
        energyLevels[x][y] = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if ((i != x || j != y) && checkCoordinate(energyLevels, i, j) && energyLevels[i][j] != 0) {
                    energyLevels[i][j]++;
                }
            }
        }
    }

    private boolean checkCoordinate(int[][] energyLevels, int x, int y) {
        if (x < 0) return false;
        if (y < 0) return false;
        if (x >= energyLevels.length) return false;
        return y < energyLevels[x].length;
    }

    @Override
    public Integer partTwo(int[][] energyLevels) {
        int count = 0;
        while (!areAllOctopusFlashing(energyLevels)) {
            count++;
            nextStep(energyLevels);
        }
        return count;
    }

    private boolean areAllOctopusFlashing(int[][] energyLevels) {
        for (int[] energyLevelLine : energyLevels) {
            for (int energyLevel : energyLevelLine) {
                if (energyLevel != 0) return false;
            }
        }
        return true;
    }

}
