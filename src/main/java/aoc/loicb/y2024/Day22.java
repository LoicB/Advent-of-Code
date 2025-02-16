package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day22 implements Day<List<Integer>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<Integer>> de = new DayExecutor<>(rawInput -> Stream.of(rawInput.split("\\r?\\n")).map(Integer::parseInt).collect(Collectors.toList()));
        de.execute(new Day22());
    }

    @Override
    public Long partOne(List<Integer> input) {
        return input.stream().mapToLong(this::calculateFinalSecretNumber).sum();
    }

    long calculateFinalSecretNumber(long secretNumber) {
        long nextSecretNumber = secretNumber;
        for (int i = 0; i < 2000; i++) {
            nextSecretNumber = calculateNextSecretNumber(nextSecretNumber);
        }
        return nextSecretNumber;
    }

    long calculateNextSecretNumber(long secretNumber) {
        long value1 = secretNumber * 64L;
        long step1 = prune(mix(value1, secretNumber));
        long value2 = step1 / 32;
        long step2 = prune(mix(value2, step1));
        long value3 = step2 * 2048;
        return prune(mix(value3, step2));
    }

    private long mix(long value, long secretValue) {
        return value ^ secretValue;
    }

    private long prune(long secretValue) {
        return secretValue % 16777216;
    }


    @Override
    public Integer partTwo(List<Integer> input) {
        var indexes = input.stream().map(this::createBananaChangeIndex).toList();
        var changes = indexes.stream().map(Map::keySet).map(ArrayList::new).flatMap(List::stream).distinct().toList();
        return changes.stream().mapToInt(sequence -> calculateNumberOfBananas(sequence, indexes)).max().orElse(0);
    }

    private int calculateNumberOfBananas(Changes sequence, List<Map<Changes, Integer>> indexes) {
        return indexes.stream().mapToInt(index -> calculateNumberOfBananas(sequence, index)).sum();
    }

    private int calculateNumberOfBananas(Changes sequence, Map<Changes, Integer> index) {
        return index.getOrDefault(sequence, 0);
    }


    Map<Changes, Integer> createBananaChangeIndex(long secretNumber) {
        return createBananaChangeIndex(secretNumber, 2000);
    }

    Map<Changes, Integer> createBananaChangeIndex(long secretNumber, int indexSize) {
        Map<Changes, Integer> changesIndex = new HashMap<>();
        int val1, val2 = Integer.MIN_VALUE, val3 = Integer.MIN_VALUE, val4 = Integer.MIN_VALUE;
        var index = 0;
        var currentNumber = secretNumber;
        while (index++ < indexSize) {
            var newNumber = calculateNextSecretNumber(currentNumber);
            val1 = val2;
            val2 = val3;
            val3 = val4;
            val4 = (int) (newNumber % 10 - currentNumber % 10);
            if (index >= 4) {
                var price = (int) (newNumber % 10);
                var sequence = new Changes(val1, val2, val3, val4);
                if (!changesIndex.containsKey(sequence)) {
                    changesIndex.put(sequence, price);
                }
            }
            currentNumber = newNumber;
        }
        return changesIndex;
    }

    record Changes(long number1, long number2, long number3, long number4) {

    }
}
