package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Day1 implements Day<List<String>, Integer> {
    private final static List<String> letters = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day1());

    }

    @Override
    public Integer partOne(List<String> input) {
        return input.stream().mapToInt(this::recoverCalibration).sum();
    }

    int recoverCalibration(String input) {
        int from = 0;
        while (!Character.isDigit(input.charAt(from))) {
            from++;
        }
        int end = input.length() - 1;
        while (!Character.isDigit(input.charAt(end))) {
            end--;
        }
        return 10 * (input.charAt(from) - '0') + input.charAt(end) - '0';
    }

    @Override
    public Integer partTwo(List<String> input) {
        return input.stream().mapToInt(this::recoverCalibrationLetters).sum();
    }

    int recoverCalibrationLetters(String input) {
        int numberLeft = findNumber(input, 0, index -> index + 1);
        int numberRight = findNumber(input, input.length() - 1, index -> index - 1);
        return 10 * numberLeft + numberRight;
    }

    private int findNumber(String input, int startingIndex, Function<Integer, Integer> nextIndex) {
        int index = startingIndex;
        boolean numberFound = false;
        int number = 0;
        while (!numberFound) {
            if (Character.isDigit(input.charAt(index))) {
                numberFound = true;
                number = input.charAt(index) - '0';
            }
            if (!numberFound) {
                var letter = startsWithLetters(input.substring(index));
                if (letter.isPresent()) {
                    number = letters.indexOf(letter.get()) + 1;
                    numberFound = true;
                }
            }
            index = nextIndex.apply(index);
        }
        return number;
    }

    private Optional<String> startsWithLetters(String input) {
        return letters.stream().filter(input::startsWith).findFirst();
    }
}
