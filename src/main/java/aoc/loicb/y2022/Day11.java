package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day11 implements Day<List<Monkey>, Long> {
    public static void main(String[] args) {
        DayExecutor<List<Monkey>> de = new DayExecutor<>(new Day11InputTransformer());
        de.execute(new Day11());
    }

    @Override
    public Long partOne(List<Monkey> monkeys) {
        long[] inspectedItemsCount = executeRound(monkeys, 20, worryLevel -> worryLevel / 3);
        Arrays.sort(inspectedItemsCount);
        return inspectedItemsCount[inspectedItemsCount.length - 1] * inspectedItemsCount[inspectedItemsCount.length - 2];
    }

    protected long[] executeRound(List<Monkey> monkeys, int numberOfRound, Function<Long, Long> worryLowering) {
        long[] inspectedItemsCount = new long[monkeys.size()];
        Arrays.fill(inspectedItemsCount, 0);
        IntStream.range(0, numberOfRound).forEach(value -> round(monkeys, inspectedItemsCount, worryLowering));
        return inspectedItemsCount;
    }

    private void round(List<Monkey> monkeys, long[] inspectedItemsCount, Function<Long, Long> worryLowering) {
        IntStream.range(0, monkeys.size()).forEach(index -> {
            Monkey monkey = monkeys.get(index);
            monkey.items.forEach(item -> {
                inspectedItemsCount[index]++;
                long newItemWorry = worryLowering.apply(monkey.operation.apply(item));
                monkeys.get(monkey.test.apply(newItemWorry)).items.add(newItemWorry);
            });
            monkey.items.clear();
        });
    }

    @Override
    public Long partTwo(List<Monkey> monkeys) {
        int mod = monkeys.stream().mapToInt(value -> value.modulo).reduce(1, (a, b) -> a * b);
        long[] inspectedItemsCount = executeRound(monkeys, 10000, worryLevel -> worryLevel % mod);
        Arrays.sort(inspectedItemsCount);
        return inspectedItemsCount[inspectedItemsCount.length - 1] * inspectedItemsCount[inspectedItemsCount.length - 2];
    }
}


class Monkey {
    final Function<Long, Long> operation;
    final Function<Long, Integer> test;
    final int modulo;
    List<Long> items;

    Monkey(List<Long> items, Function<Long, Long> operation, Function<Long, Integer> test, int modulo) {
        this.items = items;
        this.operation = operation;
        this.test = test;
        this.modulo = modulo;
    }


    @Override
    public String toString() {
        return "Monkey{" +
                "items=" + items +
                '}';
    }
}


class Day11InputTransformer implements InputTransformer<List<Monkey>> {
    @Override
    public List<Monkey> transform(String rawInput) {
        String[] inputLines = rawInput.split("\\r?\\n");
        List<Monkey> monkeys = new ArrayList<>();
        for (int i = 0; i < inputLines.length; i += 7) {
            monkeys.add(createMonkey(inputLines, i));
        }
        return monkeys;
    }

    private Monkey createMonkey(String[] inputLines, int index) {
        return new Monkey(createIemList(inputLines[index + 1]), createOperation(inputLines[index + 2]), createTest(inputLines, index + 3), getDivisibleValue(inputLines[index + 3]));
    }

    private List<Long> createIemList(String line) {
        String itemString = line.split(":")[1].trim();
        String[] idsString = itemString.split(",");
        return Arrays.stream(idsString).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
    }

    private Function<Long, Long> createOperation(String line) {
        String[] part = line.split(" +");
        String operation = part[5];
        return old -> {
            String operand = part[6];
            return operand.equals("old") ? applyOperation(old, operation, old) : applyOperation(old, operation, Integer.parseInt(operand));
        };
    }

    private long applyOperation(long old, String operation, long operand) {
        switch (operation) {
            default -> {
                return old + operand;
            }
            case "/" -> {
                return old / operand;
            }
            case "*" -> {
                return old * operand;
            }
            case "-" -> {
                return old - operand;
            }
        }
    }


    private Function<Long, Integer> createTest(String[] inputLines, int index) {
        long divisibleValue = getDivisibleValue(inputLines[index]);
        int trueNext = getTestOutput(inputLines[index + 1]);
        int falseNext = getTestOutput(inputLines[index + 2]);
        return integer -> integer % divisibleValue == 0 ? trueNext : falseNext;

    }

    private int getDivisibleValue(String line) {
        return Integer.parseInt(line.split(" +")[4]);
    }

    private int getTestOutput(String line) {
        return Integer.parseInt(line.split(" +")[6]);
    }

}