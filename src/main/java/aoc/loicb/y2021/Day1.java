package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToIntegerArray;

public class Day1 implements Day<Integer[], Integer> {
    public static void main(String[] args) {
        DayExecutor<Integer[]> de = new DayExecutor<>(new InputToIntegerArray());
        de.execute(new Day1());
    }

    @Override
    public Integer partOne(Integer[] input) {
        int numberOfIncreases = 0;
        for (int i = 0; i < input.length - 1; i++) {
            if (input[i] < input[i + 1]) {
                numberOfIncreases++;
            }
        }
        return numberOfIncreases;
    }

    @Override
    public Integer partTwo(Integer[] input) {
        return partOne(improveMeasurement(input));
    }

    private Integer[] improveMeasurement(Integer[] measurement) {
        Integer[] newMeasurement = new Integer[measurement.length - 2];
        for (int i = 0; i < measurement.length - 2; i++) {
            newMeasurement[i] = measurement[i] + measurement[i + 1] + measurement[i + 2];
        }
        return newMeasurement;
    }
}
