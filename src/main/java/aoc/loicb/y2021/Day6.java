package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 implements Day<List<Integer>, Number> {

    public static void main(String[] args) {
        DayExecutor<List<Integer>> de = new DayExecutor<>(rawInput -> Stream.of(rawInput.split(",")).map(Integer::parseInt).collect(Collectors.toList()));
        de.execute(new Day6());
    }

    @Override
    public Long partOne(List<Integer> fish) {
        return numberOfFishAfter(fish, 80);
    }

    protected long numberOfFishAfter(List<Integer> fish, int numberOfDays) {
        long[] fishCount = new long[9];
        for (Integer age : fish) {
            fishCount[age]++;
        }
        for (int i = 0; i < numberOfDays; i++) {
            fishCount = nextDay(fishCount);
        }
        return Arrays.stream(fishCount).sum();
    }

    protected long[] nextDay(long[] fish) {
        long newFish = fish[0];
        System.arraycopy(fish, 1, fish, 0, fish.length - 1);
        fish[6] += newFish;
        fish[fish.length - 1] = newFish;
        return fish;
    }

    @Override
    public Long partTwo(List<Integer> fish) {
        return numberOfFishAfter(fish, 256);
    }
}
