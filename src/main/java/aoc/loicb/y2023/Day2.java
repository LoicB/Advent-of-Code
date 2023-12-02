package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day2 implements Day<List<String>, Integer> {
    private static final Pattern RED_PATTERN = Pattern.compile("(\\d+) red");
    private static final Pattern BLUE_PATTERN = Pattern.compile("(\\d+) blue");
    private static final Pattern GREEN_PATTERN = Pattern.compile("(\\d+) green");

    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day2());

    }

    @Override
    public Integer partOne(List<String> input) {
        return IntStream
                .range(0, input.size())
                .filter(value -> isPossible(input.get(value)))
                .map(id -> id + 1)
                .sum();
    }

    boolean isPossible(String string) {
        Matcher redMatcher = RED_PATTERN.matcher(string);
        Matcher blueMatcher = BLUE_PATTERN.matcher(string);
        Matcher greenMatcher = GREEN_PATTERN.matcher(string);
        return validateNumberOfCubes(redMatcher, 12)
                && validateNumberOfCubes(greenMatcher, 13)
                && validateNumberOfCubes(blueMatcher, 14);
    }

    private boolean validateNumberOfCubes(Matcher m, int maximum) {
        while (m.find()) {
            if (Integer.parseInt(m.group(1)) > maximum) return false;
        }
        return true;
    }

    @Override
    public Integer partTwo(List<String> input) {
        return input
                .stream()
                .mapToInt(this::calculatePower)
                .sum();
    }

    int calculatePower(String input) {
        Matcher redMatcher = RED_PATTERN.matcher(input);
        Matcher blueMatcher = BLUE_PATTERN.matcher(input);
        Matcher greenMatcher = GREEN_PATTERN.matcher(input);
        return calculateMaxNumberOfCubes(redMatcher)
                * calculateMaxNumberOfCubes(blueMatcher)
                * calculateMaxNumberOfCubes(greenMatcher);
    }


    private int calculateMaxNumberOfCubes(Matcher m) {
        int max = 0;
        while (m.find()) {
            max = Math.max(Integer.parseInt(m.group(1)), max);
        }
        return max;
    }
}
