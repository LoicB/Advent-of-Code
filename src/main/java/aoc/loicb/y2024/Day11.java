package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11 implements Day<List<Long>, Number> {
    private final
    Map<Long, List<Long>> buffer = new HashMap<>();

    public static void main(String[] args) {
        DayExecutor<List<Long>> de = new DayExecutor<>(rawInput -> Stream.of(rawInput.split(" ")).map(Long::parseLong).collect(Collectors.toList()));
        de.execute(new Day11());
    }

    // 175702 too low
    @Override
    public Integer partOne(List<Long> stones) {
        return blink25(stones).size();
//        return blink(25, stones);
    }

    private int blink(int numberOfBlink, List<Long> stones) {
        var size = 0;
        for (long stone : stones) {
            System.out.println(" !!!!!!!!!!!!! " + stone + " !!!!!!!!!!!!! ");
            size += blink(numberOfBlink, stone);
        }
        return size;
    }

    private int blink(int numberOfBlink, Long stone) {
        if (numberOfBlink == 0) return 1;
        var size = 0;
        var newStones = blink(stone);
        for (long newStone : newStones) {
//            System.out.println(i+" / "+ numberOfBlink + " with " + newStones.size());
            size += blink(numberOfBlink - 1, newStone);
        }
        return size;
    }

    private List<Long> blink25(Long stone, Map<Long, List<Long>> buffer25) {
        if (buffer25.containsKey(stone)) return buffer25.get(stone);
        var newStones = blink(stone);
        for (int i = 0; i < 24; i++) {
            System.out.println(i + " / " + 25 + " with " + newStones.size());
            if (newStones.stream().anyMatch(integer -> integer < 0)) {
                System.out.println(newStones);
                System.exit(0);
            }
            newStones = blink(newStones);
        }
        buffer25.put(stone, newStones);
        return newStones;
    }

    private List<Long> blink25(List<Long> stones) {
        var newStones = stones;
        for (int i = 0; i < 25; i++) {
            System.out.println(i + " / " + 25 + " with " + newStones.size());
            if (newStones.stream().anyMatch(integer -> integer < 0)) {
                System.out.println(newStones);
                System.exit(0);
            }
            newStones = blink(newStones);
        }
        return newStones;
    }


    List<Long> blink(List<Long> stones) {
        return stones
                .stream()
                .map(this::blink)
                .flatMap(List::stream)
                .toList();
    }

    List<Long> blink(long stone) {
        if (buffer.containsKey(stone)) return buffer.get(stone);
        if (stone == 0) return List.of(1L);
        int numberOfDigits = numDigits(stone);
        if (numberOfDigits % 2 == 0) {
            int splitLevel = numberOfDigits / 2;
            int split = (int) Math.pow(10, splitLevel);
            long leftStone = stone / split;
            long rightStone = stone % split;
            buffer.put(stone, List.of(leftStone, rightStone));
            return List.of(leftStone, rightStone);
        }
        buffer.put(stone, List.of(stone * 2024));
        return List.of(stone * 2024);
    }

    boolean hasEvenNumberOfDigits(int stone) {
        return numDigits(stone) % 2 == 0;
    }

    int numDigits(long number) {
        if (number == 0) return 0;
        return 1 + (int) (Math.log(number) / Math.log(10));
    }

    @Override
    public Long partTwo(List<Long> stones) {
        Map<Long, List<Long>> buffer25 = new HashMap<>();
        var newStones = blink25(stones);
        long size = 0L;
        for (Long stone : newStones) {
            var subStones = blink25(stone, buffer25);
            for (Long subStone : subStones) {
                size += blink25(subStone, buffer25).size();
            }
        }
        return size;
    }


    private Long quickBlink(Long stone, int blinkCount) {
        if (blinkCount == 75) return 1L;
        if (stone == 0) return quickBlink(1, blinkCount + 1);
        int numberOfDigits = numDigits(stone);
        if (numberOfDigits % 2 == 0) {
            int splitLevel = numberOfDigits / 2;
            int split = (int) Math.pow(10, splitLevel);
            long leftStone = stone / split;
            long rightStone = stone % split;
            return quickBlink(leftStone, blinkCount + 1)
                    + quickBlink(rightStone, blinkCount + 1);
        }
        return quickBlink(stone * 2024, blinkCount + 1);
    }

}
