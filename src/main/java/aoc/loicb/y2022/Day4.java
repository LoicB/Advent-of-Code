package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.List;

public class Day4 implements Day<List<String>, Long> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day4());

    }

    @Override
    public Long partOne(List<String> input) {
        return input
                .stream()
                .filter(this::areElvesOverlapping)
                .count();
    }

    protected boolean areElvesOverlapping(String elvesIDs) {
        String[] elves = elvesIDs.split(",");
        String elf1Ids = elves[0];
        String elf2Ids = elves[1];

        int[] range1 = getRange(elf1Ids);
        int[] range2 = getRange(elf2Ids);

        return range1[0] >= range2[0] && range1[1] <= range2[1]
                || range2[0] >= range1[0] && range2[1] <= range1[1];
    }

    @Override
    public Long partTwo(List<String> input) {
        return input
                .stream()
                .filter(this::areElvesPartiallyOverlapping)
                .count();
    }

    protected boolean areElvesPartiallyOverlapping(String elvesIDs) {
        String[] elves = elvesIDs.split(",");
        String elf1Ids = elves[0];
        String elf2Ids = elves[1];

        int[] range1 = getRange(elf1Ids);
        int[] range2 = getRange(elf2Ids);

        return range1[1] >= range2[0] && range2[1] >= range1[0];
    }

    private int[] getRange(String elfIds) {
        String[] range = elfIds.split("-");
        return new int[]{Integer.parseInt(range[0]), Integer.parseInt(range[1])};
    }
}
