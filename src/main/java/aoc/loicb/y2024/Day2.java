package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2 implements Day<List<List<Integer>>, Long> {
    public static void main(String[] args) {
        DayExecutor<List<List<Integer>>> de = new DayExecutor<>(rawInput -> {
            List<List<Integer>> reports = new ArrayList<>();
            for (String levels : rawInput.split("\\r?\\n")) {
                var values = levels.split(" ");
                reports.add(Arrays.stream(values).map(Integer::parseInt).toList());
            }
            return reports;
        });
        de.execute(new Day2());
    }

    @Override
    public Long partOne(List<List<Integer>> input) {
        return input.stream().filter(this::checkReportSafety).count();
    }

    boolean checkReportSafety(List<Integer> levels) {
        boolean increasing = isIncreasing(levels.get(0), levels.get(1));
        int index = 0;
        boolean safe = isProgresSafe(levels.get(index), levels.get(index + 1));
        while (index < levels.size() - 1 && safe) {
            safe = increasing == isIncreasing(levels.get(index), levels.get(index + 1))
                    && isProgresSafe(levels.get(index), levels.get(index + 1));
            index++;
        }
        return safe;
    }

    private boolean isIncreasing(int a, int b) {
        return a < b;
    }

    private boolean isProgresSafe(int a, int b) {
        int diff = Math.abs(a - b);
        return diff > 0 && diff <= 3;
    }

    @Override
    public Long partTwo(List<List<Integer>> input) {
        return input.stream().filter(this::checkReportSafetyDampener).count();
    }

    boolean checkReportSafetyDampener(List<Integer> levels) {
        boolean increasing = isIncreasing(levels.get(0), levels.get(1));
        int index = 0;
        boolean safe = isProgresSafe(levels.get(index), levels.get(index + 1));
        while (index < levels.size() - 1 && safe) {
            safe = increasing == isIncreasing(levels.get(index), levels.get(index + 1))
                    && isProgresSafe(levels.get(index), levels.get(index + 1));
            index++;
        }
        if (safe) return true;
        for (int i = 0; i < levels.size(); i++) {
            var newList = new ArrayList<>(levels);
            newList.remove(i);
            if (checkReportSafety(newList)) return true;
        }
//        var newList = new ArrayList<>(levels);
//        newList.remove(index);
//        if (!checkReportSafety(newList)) {
//            System.out.println("!!!!!!!!!!!!!");
//            System.out.println(levels);
//            System.out.println(newList);
//        }
//        return checkReportSafety(newList);
        return false;
//        boolean increasing = isIncreasing(levels.get(0), levels.get(1));
//        int index = 0;
//        int badLevels = 0;
//        boolean safe = isProgresSafe(levels.get(index), levels.get(index + 1));
//        while (index < levels.size() - 1 && safe && badLevels < 2) {
//            System.out.println(badLevels);
//            safe = increasing == isIncreasing(levels.get(index), levels.get(index + 1))
//                    && isProgresSafe(levels.get(index), levels.get(index + 1));
//            if (increasing != isIncreasing(levels.get(index), levels.get(index + 1))) badLevels++;
//            index ++;
//        }
//        return badLevels < 2;
    }
}
