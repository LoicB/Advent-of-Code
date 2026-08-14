package aoc.loicb.y2015;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;
import com.github.javaparser.utils.Pair;

import java.util.*;

public class Day7 implements Day<List<String>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day7());
    }

    @Override
    public Integer partOne(List<String> instructions) {
        return executeInstructions(instructions).get("a");
    }

    Map<String, Integer> executeInstructions(List<String> instructions) {
        Map<String, Integer> signals = new HashMap<>();
        var queue = new LinkedList<>(instructions);
        while (!queue.isEmpty()) {
            var instruction = queue.poll();
            var split = instruction.split(" ");
            var output = executeInstruction(split, signals);
            if (output.isEmpty()) {
                queue.add(instruction);
            } else {
                signals.put(split[split.length - 1], output.get());
            }
        }
        return signals;
    }

    Optional<Integer> executeInstruction(String instruction, Map<String, Integer> current) {
        var split = instruction.split(" ");
        return executeInstruction(split, current);
    }

    private Optional<Integer> executeInstruction(String[] split, Map<String, Integer> current) {
        if (isBitwiseAnd(split)) return extractPair(split, current).map(this::applyBitwiseAnd);
        if (isBitwiseOr(split)) return extractPair(split, current).map(this::applyBitwiseOr);
        if (isLeftShift(split)) return extractShift(split, current).map(this::applyLeftShift);
        if (isRightShift(split)) return extractShift(split, current).map(this::applyRightShift);
        if (isNot(split)) return extractSingle(split, current).map(this::applyNot);
        return extractValue(split[0], current);
    }


    Optional<Integer> extractValue(String wire, Map<String, Integer> current) {
        if (isNumeric(wire)) return Optional.of(Integer.parseInt(wire));
        if (current.containsKey(wire)) return Optional.of(current.get(wire));
        return Optional.empty();
    }

    public boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    Optional<Pair<Integer, Integer>> extractPair(String[] instruction, Map<String, Integer> current) {
        var wire1 = instruction[0];
        var wire2 = instruction[2];
        var a = extractValue(wire1, current);
        return a.flatMap(integer -> extractValue(wire2, current).map(b -> new Pair<>(integer, b)));
    }

    Optional<Pair<Integer, Integer>> extractShift(String[] instruction, Map<String, Integer> current) {
        var wire1 = instruction[0];
        var left = Integer.parseInt(instruction[2]);
        if (!current.containsKey(wire1)) return Optional.empty();
        int a = current.get(wire1);
        return Optional.of(new Pair<>(a, left));
    }

    Optional<Integer> extractSingle(String[] instruction, Map<String, Integer> current) {
        var wire = instruction[1];
        return extractValue(wire, current);
    }

    boolean isBitwiseAnd(String[] instruction) {
        return "AND".equals(instruction[1]);
    }

    int applyBitwiseAnd(Pair<Integer, Integer> pair) {
        return pair.a & pair.b;
    }

    boolean isBitwiseOr(String[] instruction) {
        return "OR".equals(instruction[1]);
    }

    int applyBitwiseOr(Pair<Integer, Integer> pair) {
        return pair.a | pair.b;
    }

    boolean isLeftShift(String[] instruction) {
        return "LSHIFT".equals(instruction[1]);
    }

    int applyLeftShift(Pair<Integer, Integer> pair) {
        return pair.a << pair.b;
    }

    boolean isRightShift(String[] instruction) {
        return "RSHIFT".equals(instruction[1]);
    }

    int applyRightShift(Pair<Integer, Integer> pair) {
        return pair.a >> pair.b;
    }

    boolean isNot(String[] instruction) {
        return "NOT".equals(instruction[0]);
    }

    int applyNot(int a) {
        return (65536 + ~a) % 65536;
    }

    @Override
    public Integer partTwo(List<String> instructions) {
        var signals = executeInstructions(instructions);
        var partOne = signals.get("a");
        var newInstructions = instructions.stream().map(s -> isB(s) ? partOne + " -> b" : s).toList();
        return executeInstructions(newInstructions).get("a");

    }

    private boolean isB(String instruction) {
        return instruction.endsWith(" -> b");
    }
}
