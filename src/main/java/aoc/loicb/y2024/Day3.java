package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 implements Day<String, Integer> {
    public static void main(String[] args) {
        DayExecutor<String> de = new DayExecutor<>(rawInput -> rawInput);
        de.execute(new Day3());
    }

    @Override
    public Integer partOne(String input) {
        Pattern p = Pattern.compile("mul\\((\\d+),(\\d+)\\)", Pattern.DOTALL);
        return calculateInstructions(input, p);
    }

    private int calculateInstructions(String input, Pattern pattern) {
        Matcher m = pattern.matcher(input);
        int result = 0;
        while (m.find()) {
            result += Integer.parseInt(m.group(1)) * Integer.parseInt(m.group(2));
        }
        return result;
    }

    @Override
    public Integer partTwo(String input) {
        Pattern p = Pattern.compile("mul\\((\\d+),(\\d+)\\)", Pattern.DOTALL);
        return Arrays.stream(input.split("(?=do)"))
                .filter(s -> !s.startsWith("don't"))
                .mapToInt(value -> calculateInstructions(value, p))
                .sum();
    }
}
