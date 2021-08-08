package aoc.loicb.y2015;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

public class Day1 implements Day<String, Integer> {
    public static void main(String[] args) {
        DayExecutor<String> de = new DayExecutor<>(rawInput -> rawInput);
        de.execute(new Day1());
    }

    @Override
    public Integer partOne(String input) {
        return input.chars()
                .map(this::bracketsValue)
                .reduce(0, Integer::sum);
    }

    private int bracketsValue(int c) {
        if (c == '(') return 1;
        return -1;
    }

    @Override
    public Integer partTwo(String input) {
        int floor = 0;
        int index = 0;
        while (floor >= 0) {
            floor += bracketsValue(input.charAt(index));
            index++;
        }
        return index;
    }
}
