package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;
import com.github.javaparser.utils.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day1 implements Day<List<Pair<Integer, Integer>>, Integer> {

    public static void main(String[] args) {
        DayExecutor<List<Pair<Integer, Integer>>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public Pair<Integer, Integer> transformObject(String string) {
                var values = string.split(" {3}");
                return new Pair<>(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            }
        });
        de.execute(new Day1());

    }

    @Override
    public Integer partOne(List<Pair<Integer, Integer>> input) {
        var listLeft = input.stream().map(value -> value.a).sorted().toList();
        var listRight = input.stream().map(value -> value.b).sorted().toList();
        return calculateDistances(listLeft, listRight);
    }

    private int calculateDistances(List<Integer> locationIdsLeft, List<Integer> locationIdsRight) {
        int size = Math.min(locationIdsLeft.size(), locationIdsRight.size());
        return IntStream
                .range(0, size)
                .mapToObj(index -> Math.abs(locationIdsLeft.get(index) - locationIdsRight.get(index)))
                .mapToInt(value -> value)
                .sum();

    }

    @Override
    public Integer partTwo(List<Pair<Integer, Integer>> input) {
        var listLeft = input.stream().map(value -> value.a).toList();
        var listRight = input.stream().map(value -> value.b).toList();
        var occurrenceRight = countOccurrences(listRight);
        return listLeft.stream().mapToInt(integer -> integer * occurrenceRight.get(integer)).sum();
    }

    private List<Integer> countOccurrences(List<Integer> locationIds) {
        var max = locationIds.stream().mapToInt(value -> value).max().orElse(1);
        Integer[] data = new Integer[max + 1];
        Arrays.fill(data, 0);
        locationIds.forEach(integer -> data[integer]++);
        return Arrays.asList(data);
    }
}
