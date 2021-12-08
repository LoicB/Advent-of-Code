package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class Day8 implements Day<List<Signal>, Integer> {

    public static void main(String[] args) {
        DayExecutor<List<Signal>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public Signal transformObject(String string) {
                String[] inputOutput = string.split("\\|");
                List<String> inputs = List.of(inputOutput[0].split(" "));
                List<String> outputs = List.of(inputOutput[1].split(" "));
                return new Signal(inputs, outputs);
            }
        });
        de.execute(new Day8());
    }

    @Override
    public Integer partOne(List<Signal> signals) {
        return signals.stream().map(Signal::output).mapToInt(value -> (int) value.stream().filter(this::isSizeUnique).count()).sum();
    }

    private boolean isSizeUnique(String value) {
        return isOne(value) || isFour(value) || isSeven(value) || isHeight(value);
    }

    private boolean isOne(String value) {
        return value.length() == 2;
    }

    private boolean isFour(String value) {
        return value.length() == 4;
    }

    private boolean isSeven(String value) {
        return value.length() == 3;
    }

    private boolean isHeight(String value) {
        return value.length() == 7;
    }

    @Override
    public Integer partTwo(List<Signal> signals) {
        return signals.stream().mapToInt(this::analyseSignal).sum();
    }

    protected int analyseSignal(Signal signal) {
        String one = findNumber(signal.input(), this::isOne);
        String four = findNumber(signal.input(), this::isFour);
        String seven = findNumber(signal.input(), this::isSeven);
        String height = findNumber(signal.input(), this::isHeight);
        int outputValue = 0;
        for (String value : signal.output()) {
            outputValue *= 10;
            outputValue += analyseInfo(value, one, four, seven, height);
        }
        return outputValue;
    }

    private String findNumber(List<String> numbers, Predicate<String> predicate) {
        return numbers.stream().filter(predicate).findAny().orElseThrow();
    }

    private int analyseInfo(String value, String one, String four, String seven, String height) {
        if (isOne(value)) return 1;
        if (isFour(value)) return 4;
        if (isSeven(value)) return 7;
        if (isHeight(value)) return 8;
        Map<Integer, Integer> overlaps = Map.of(1, commonChars(value, one),
                4, commonChars(value, four),
                7, commonChars(value, seven),
                8, commonChars(value, height));
        if (isZero(overlaps)) return 0;
        if (isTwo(overlaps)) return 2;
        if (isThree(overlaps)) return 3;
        if (isFive(overlaps)) return 5;
        if (isSix(overlaps)) return 6;
        if (isNine(overlaps)) return 9;
        return 0;
    }

    private boolean isZero(Map<Integer, Integer> overlaps) {
        return overlaps.get(1) == 2 && overlaps.get(4) == 3 && overlaps.get(7) == 3 && overlaps.get(8) == 6;
    }

    private boolean isTwo(Map<Integer, Integer> overlaps) {
        return overlaps.get(1) == 1 && overlaps.get(4) == 2 && overlaps.get(7) == 2 && overlaps.get(8) == 5;
    }

    private boolean isThree(Map<Integer, Integer> overlaps) {
        return overlaps.get(1) == 2 && overlaps.get(4) == 3 && overlaps.get(7) == 3 && overlaps.get(8) == 5;
    }

    private boolean isFive(Map<Integer, Integer> overlaps) {
        return overlaps.get(1) == 1 && overlaps.get(4) == 3 && overlaps.get(7) == 2 && overlaps.get(8) == 5;
    }

    private boolean isSix(Map<Integer, Integer> overlaps) {
        return overlaps.get(1) == 1 && overlaps.get(4) == 3 && overlaps.get(7) == 2 && overlaps.get(8) == 6;
    }

    private boolean isNine(Map<Integer, Integer> overlaps) {
        return overlaps.get(1) == 2 && overlaps.get(4) == 4 && overlaps.get(7) == 3 && overlaps.get(8) == 6;
    }

    public int commonChars(String s1, String s2) {
        Set<Character> set = new HashSet<>();
        for (Character c : s1.toCharArray()) {
            if (s2.indexOf(c) >= 0) {
                set.add(c);
            }
        }
        return set.size();
    }
}


record Signal(List<String> input, List<String> output) {
}