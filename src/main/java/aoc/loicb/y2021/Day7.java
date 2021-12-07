package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.Collections;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day7 implements Day<List<Integer>, Integer> {

    public static void main(String[] args) {
        DayExecutor<List<Integer>> de = new DayExecutor<>(rawInput -> Stream.of(rawInput.split(",")).map(Integer::parseInt).collect(Collectors.toList()));
        de.execute(new Day7());
    }

    @Override
    public Integer partOne(List<Integer> positions) {
        int min = Collections.min(positions);
        int max = Collections.max(positions);
        return IntStream.range(min, max).map(operand -> getFuelQuantity(positions, operand)).min().orElseThrow();
    }

    protected int getFuelQuantity(List<Integer> positions, int finalPosition) {
        return getFuelQuantity(positions, finalPosition, position -> Math.abs(finalPosition - position));
    }

    private int getFuelQuantity(List<Integer> positions, int finalPosition, ToIntFunction<Integer> quantityCalculator) {
        return positions.stream().mapToInt(quantityCalculator).sum();
    }

    @Override
    public Integer partTwo(List<Integer> positions) {
        int min = Collections.min(positions);
        int max = Collections.max(positions);
        return IntStream.range(min, max).map(operand -> getFuelQuantityWithProperRate(positions, operand)).min().orElseThrow();
    }

    protected int getFuelQuantityWithProperRate(List<Integer> positions, int finalPosition) {
        return getFuelQuantity(positions, finalPosition, position -> sumOfNumber(Math.abs(finalPosition - position)));
    }

    private int sumOfNumber(int n) {
        return n * (n + 1) / 2;
    }
}
