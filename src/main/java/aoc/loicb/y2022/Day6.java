package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day6 implements Day<String, Integer> {

    public static void main(String[] args) {
        DayExecutor<String> de = new DayExecutor<>(rawInput -> rawInput);
        de.execute(new Day6());

    }

    @Override
    public Integer partOne(String input) {
        return findStartOfPackerMarker(input);
    }

    private int findStartOfPackerMarker(String input) {
        return findMarker(input, 4);
    }


    private int findMarker(String input, int markerSize) {
        return IntStream
                .range(markerSize - 1, input.length())
                .filter(index -> !hasDuplicates(getPacket(input, index, markerSize)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No marker")) + 1;
    }

    private List<Character> getPacket(String input, int position, int markerSize) {
        return IntStream
                .range(0, markerSize)
                .mapToObj(operand -> input.charAt(position - operand))
                .collect(Collectors.toList());
    }

    private boolean hasDuplicates(List<Character> characters) {
        return (new HashSet<>(characters)).size() < characters.size();
    }

    @Override
    public Integer partTwo(String input) {
        return findStartOfMessageMarker(input);
    }

    private int findStartOfMessageMarker(String input) {
        return findMarker(input, 14);
    }
}
