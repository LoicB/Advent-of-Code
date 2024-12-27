package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.*;
import java.util.stream.IntStream;

public class Day19 implements Day<List<String>, Long> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day19());

    }

    @Override
    public Long partOne(List<String> input) {
        var patterns = extractPatterns(input);
        var designs = extractDesigns(input);
        return designs.stream().filter(design -> isDesignPossible(design, patterns)).count();
    }

    boolean isDesignPossible(String design, List<String> patterns) {
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(0);
        Set<Integer> history = new HashSet<>();
        history.add(0);
        int max = 0;
        while (!queue.isEmpty() && max < design.length()) {
            int current = queue.poll();
            max = Math.max(current, max);
            var next = comparePatterns(design, patterns, current);
            for (int nextStep : next) {
                if (history.add(nextStep)) queue.add(nextStep);
            }
        }
        return max == design.length();
    }

    private List<Integer> comparePatterns(String design, List<String> patterns, int index) {
        return patterns.stream().filter(pattern -> comparePattern(design, pattern, index)).map(string -> string.length() + index).toList();
    }

    private boolean comparePattern(String design, String pattern, int index) {
        if (index + pattern.length() > design.length()) return false;
        for (int i = 0; i < pattern.length(); i++) {
            if (design.charAt(index + i) != pattern.charAt(i)) return false;
        }
        return true;

    }


    private List<String> extractPatterns(List<String> input) {
        return Arrays.stream(input.getFirst().split(", ")).sorted((o1, o2) -> o2.length() - o1.length()).toList();
    }


    private List<String> extractDesigns(List<String> input) {
        return IntStream
                .range(2, input.size())
                .mapToObj(input::get)
                .toList();
    }


    @Override
    public Long partTwo(List<String> input) {
        var patterns = extractPatterns(input);
        var designs = extractDesigns(input);
        return designs.stream().mapToLong(design -> countPossibleDesigns(design, patterns)).sum();
    }


    long countPossibleDesigns(String design, List<String> patterns) {
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(0);
        Map<Integer, List<Integer>> buffer = new HashMap<>();
        Set<Integer> history = new HashSet<>();
        history.add(0);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            var next = comparePatterns(design, patterns, current);
            for (int nextStep : next) {
                if (history.add(nextStep)) queue.add(nextStep);
            }
            buffer.put(current, next);
        }
        long[] counters = new long[design.length() + 1];
        counters[0] = 1;
        IntStream
                .range(0, design.length())
                .forEach(index -> buffer
                        .getOrDefault(index, new ArrayList<>())
                        .forEach(integer -> counters[integer] += counters[index]));
        return counters[counters.length - 1];
    }
}
