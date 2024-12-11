package aoc.loicb.y2017;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.Arrays;

public class Day1 implements Day<int[], Integer> {
    public static void main(String[] args) {
        DayExecutor<int[]> de = new DayExecutor<>(rawInput -> Arrays.stream(rawInput.split("(?!^)")).mapToInt(Integer::parseInt).toArray());
        de.execute(new Day1());
    }

    @Override
    public Integer partOne(int[] input) {
        int sum = input[0] == input[input.length - 1] ? input[0] : 0;
        for (int i = 0; i < input.length - 1; i++) {
            if (input[i] == input[i + 1]) sum += input[i];
        }
        return sum;
    }

    @Override
    public Integer partTwo(int[] input) {
        int sum = 0;
        for (int i = 0; i < input.length / 2; i++) {
            if (input[i] == input[i + input.length / 2]) sum += input[i] + input[i + input.length / 2];
        }
        return sum;
    }
}
