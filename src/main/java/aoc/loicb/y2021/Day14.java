package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Day14 implements Day<PolymerisationInstructions, Number> {
    public static void main(String[] args) {
        DayExecutor<PolymerisationInstructions> de = new DayExecutor<>(rawInput -> {
            String[] inputLines = rawInput.split("\\r?\\n");
            Map<String, String> rules = new HashMap<>();
            IntStream.range(2, inputLines.length).forEach(index -> rules.put(inputLines[index].substring(0, 2), inputLines[index].substring(6)));
            return new PolymerisationInstructions(inputLines[0], rules);
        });
        de.execute(new Day14());
    }

    @Override
    public Long partOne(PolymerisationInstructions polymerisationInstructions) {
        String current = polymerisationInstructions.polymerTemplate();
        current = performSteps(current, 10, polymerisationInstructions.rules());
        Map<Character, Long> frequencies = frequencies(current);
        return getResultFromFrequencies(frequencies);
    }

    private String performSteps(String current, int numberOfSteps, Map<String, String> rules) {
        StringBuilder sb = new StringBuilder(current);
        for (int i = 0; i < numberOfSteps; i++) {
            sb = polymerisation(sb, rules);
        }
        return sb.toString();
    }

    protected String polymerisation(String current, Map<String, String> rules) {
        return polymerisation(new StringBuilder(current), rules).toString();
    }

    private StringBuilder polymerisation(StringBuilder current, Map<String, String> rules) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while (index < current.length() - 1) {
            sb.append(current.charAt(index));
            String key = current.substring(index, index + 2);
            if (rules.containsKey(key)) sb.append(rules.get(key));
            index++;
        }
        sb.append(current.charAt(index));
        return sb;
    }

    @Override
    public Long partTwo(PolymerisationInstructions polymerisationInstructions) {
        String polymere = polymerisationInstructions.polymerTemplate();
        polymere = performSteps(polymere, 20, polymerisationInstructions.rules());
        Map<Character, Long> frequencies = new HashMap<>();
        Map<String, Map<Character, Long>> polymereBuffer = new HashMap<>();
        for (int i = 0; i < polymere.length() - 1; i++) {
            String partPolymere = polymere.substring(i, i + 2);
            if (!polymereBuffer.containsKey(partPolymere)) {
                String tmpPolymere = performSteps(partPolymere, 20, polymerisationInstructions.rules());
                polymereBuffer.put(partPolymere, frequencies(tmpPolymere.substring(0, tmpPolymere.length() - 1)));
            }
            Map<Character, Long> currentFrequencies = polymereBuffer.get(partPolymere);
            for (Character key : currentFrequencies.keySet()) {
                frequencies.put(key, frequencies.getOrDefault(key, 0L) + currentFrequencies.get(key));
            }

        }
        char lastChar = polymere.charAt(polymere.length() - 1);
        frequencies.put(lastChar, frequencies.get(lastChar) + 1L);
        return getResultFromFrequencies(frequencies);
    }

    private Map<Character, Long> frequencies(String polymere) {
        Map<Character, Long> frequencies = new HashMap<>();
        for (char ch : polymere.toCharArray()) {
            frequencies.put(ch, frequencies.getOrDefault(ch, 0L) + 1);
        }
        return frequencies;
    }

    private long getResultFromFrequencies(Map<Character, Long> frequencies) {
        long max = frequencies.values().stream().mapToLong(value -> value).max().orElseThrow();
        long min = frequencies.values().stream().mapToLong(value -> value).min().orElseThrow();
        return max - min;
    }

}


record PolymerisationInstructions(String polymerTemplate, Map<String, String> rules) {
}

//    result part one: 3259
//        result part two: 3459174981021