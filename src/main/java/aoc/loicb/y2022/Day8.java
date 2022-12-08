package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

public class Day8 implements Day<int[][], Integer> {

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
        de.execute(new Day8());
    }

    @Override
    public Integer partOne(int[][] input) {
        int[][] map = createVisibilityMap(input);
        for (int i = 0; i < input.length; i++) {
            int max = -1;
            for (int j = 0; j < input[i].length; j++) {
                if (max < input[j][i]) {
                    max = input[j][i];
                } else {
                    map[j][i]--;
                }
            }
            max = -1;
            for (int j = 0; j < input[i].length; j++) {
                if (max < input[i][j]) {
                    max = input[i][j];
                } else {
                    map[i][j]--;
                }
            }
            max = -1;
            for (int j = 0; j < input[i].length; j++) {
                if (max < input[input[j].length - j - 1][i]) {
                    max = input[input[j].length - j - 1][i];
                } else {
                    map[input[j].length - j - 1][i]--;
                }
            }
            max = -1;
            for (int j = 0; j < input[i].length; j++) {
                if (max < input[i][input[j].length - j - 1]) {
                    max = input[i][input[j].length - j - 1];
                } else {
                    map[i][input[j].length - j - 1]--;
                }
            }
        }

        int count = 0;
        for (int[] ints : map) {
            for (int anInt : ints) {
                if (anInt > 0) count++;
            }
        }
        return count;
    }

    private int[][] createVisibilityMap(int[][] input) {
        int[][] map = new int[input.length][];
        for (int i = 0; i < input.length; i++) {
            map[i] = new int[input[i].length];
            for (int j = 0; j < input[i].length; j++) {
                map[i][j] = 4;
            }
        }
        return map;
    }

    @Override
    public Integer partTwo(int[][] input) {
        int max = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                max = Math.max(max, calculateScore(input, i, j));
            }
        }
        return max;
    }

    protected int calculateScore(int[][] input, int x, int y) {
        int count1 = 0;
        int index = x - 1;
        while (index >= 0) {
            if (input[x][y] > input[index][y]) {
                count1++;
            } else {
                count1++;
                break;
            }
            index--;
        }
        int count2 = 0;
        index = y - 1;
        while (index >= 0) {
            if (input[x][y] > input[x][index]) {
                count2++;
            } else {
                count2++;
                break;
            }
            index--;
        }
        int count3 = 0;
        index = x + 1;
        while (index < input.length) {
            if (input[x][y] > input[index][y]) {
                count3++;
            } else {
                count3++;
                break;
            }
            index++;
        }
        int count4 = 0;
        index = y + 1;
        while (index < input.length) {
            if (input[x][y] > input[x][index]) {
                count4++;
            } else {
                count4++;
                break;
            }
            index++;
        }
        return count1 * count2 * count3 * count4;
    }
}
