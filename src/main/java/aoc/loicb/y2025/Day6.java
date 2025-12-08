package aoc.loicb.y2025;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToStringArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class Day6 implements Day<String[], Number> {
    public static void main(String[] args) {
        DayExecutor<String[]> de = new DayExecutor<>(new InputToStringArray());
        de.execute(new Day6());
    }

    @Override
    public Number partOne(String[] input) {
        List<List<Long>> numbers = new ArrayList<>();
        for (int i = 0; i < input.length - 1; i++) {
            numbers.add(evaluateNumericLine(input[i]));
        }
        var symbols = evaluateSymbols(input[input.length - 1]);
        return IntStream
                .range(0, symbols.size())
                .mapToLong(i -> solveProblem(numbers.stream().map(integers -> integers.get(i)).toList(), symbols.get(i)))
                .sum();
    }

    List<Long> evaluateNumericLine(String line) {
        var split = line.trim().split(" +");
        return Arrays.stream(split).map(Long::parseLong).toList();
    }

    List<Character> evaluateSymbols(String line) {
        var split = line.split(" +");
        return Arrays.stream(split).map(s -> s.charAt(0)).toList();
    }

    int solveProblem(char symbol, int... numbers) {
        if (symbol == '+') return Arrays.stream(numbers).sum();
        return (Arrays.stream(numbers).reduce((left, right) -> left * right)).orElse(0);
    }

    long solveProblem(List<Long> numbers, char symbol) {
        if (symbol == '+') return numbers.stream().mapToLong(value -> value).sum();
        return (numbers.stream().reduce((left, right) -> left * right)).orElse(0L);
    }

    @Override
    public Number partTwo(String[] input) {
        long sum = 0;
        List<Long> numbers = new ArrayList<>();
        int index = input[0].length() - 1;
        while (index >= 0) {
            numbers.add((long) extractNumber(input, index));
            var symbol = extractSymbol(input, index);
            if (symbol.isPresent()) {
                sum += solveProblem(numbers, symbol.get());
                numbers = new ArrayList<>();
                index--;
            }
            index--;
        }
        return sum;
    }

    int extractNumber(String[] input, int index) {
        int number = 0;
        int lineIndex = 0;
        while (lineIndex < input.length - 1 && (input[lineIndex].charAt(index) != ' ' || number == 0)) {
            if (input[lineIndex].charAt(index) != ' ') {
                number = (number * 10) + (input[lineIndex].charAt(index) - (int) '0');
            }
            lineIndex++;
        }
        return number;
    }

    Optional<Character> extractSymbol(String[] input, int index) {
        char c = input[input.length - 1].charAt(index);
        if (c == ' ') return Optional.empty();
        return Optional.of(c);
    }
}
