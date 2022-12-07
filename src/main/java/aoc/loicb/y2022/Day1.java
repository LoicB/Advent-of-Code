package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Day1 implements Day<List<Optional<Integer>>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<Optional<Integer>>> de = new DayExecutor<>(new Day1InputTransformer());
        de.execute(new Day1());
    }

    @Override
    public Integer partOne(List<Optional<Integer>> input) {
        int[] calories = calculateCalories(input);
        return Arrays.stream(calories).max().orElse(0);
    }

    private int[] calculateCalories(List<Optional<Integer>> input) {
        int[] calories = new int[countElves(input)];
        int index = 0;
        for (var integer : input) {
            if (integer.isPresent()) {
                calories[index] += integer.orElse(0);
            } else {
                index++;
            }
        }
        return calories;
    }

    protected int countElves(List<Optional<Integer>> input) {
        return Math.toIntExact(input.stream().filter(Optional::isEmpty).count() + 1);
    }

    @Override
    public Integer partTwo(List<Optional<Integer>> input) {
        int[] calories = calculateCalories(input);
        Arrays.sort(calories);
        return calories[calories.length - 1] + calories[calories.length - 2] + calories[calories.length - 3];
    }
}

class Day1InputTransformer extends InputToObjectList<Optional<Integer>> {

    @Override
    public Optional<Integer> transformObject(String string) {
        if (string.isEmpty()) return Optional.empty();
        return Optional.of(Integer.parseInt(string));
    }
}