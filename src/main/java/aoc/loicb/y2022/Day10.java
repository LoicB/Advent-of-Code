package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day10 implements Day<List<String>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day10());

    }


    @Override
    public Integer partOne(List<String> input) {
        List<Integer> values = executeInstructions(input);
        return calculateSignalStrength(values, 20)
                + calculateSignalStrength(values, 60)
                + calculateSignalStrength(values, 100)
                + calculateSignalStrength(values, 140)
                + calculateSignalStrength(values, 180)
                + calculateSignalStrength(values, 220);
    }

    private int calculateSignalStrength(List<Integer> values, int index) {
        return values.get(index - 1) * index;
    }


    private List<Integer> executeInstructions(List<String> input) {
        int index = 0;
        int currentInstructionCycle = 0;
        int value = 1;
        List<Integer> values = new ArrayList<>();
        while (index < input.size()) {
            String instruction = input.get(index);
            values.add(value);
            if (instruction.equals("noop")) {
                index++;
            } else if (currentInstructionCycle == 0) {
                currentInstructionCycle++;
            } else {
                value += Integer.parseInt(instruction.split(" ")[1]);
                currentInstructionCycle = 0;
                index++;
            }
        }
        return values;
    }

    @Override
    public Integer partTwo(List<String> input) {
        char[] screen = new char[240];
        Arrays.fill(screen, '.');
        executeInstructions(input, screen);
        printScreen(screen);
        return null;
    }

    private void printScreen(char[] screen) {
        int index = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 40; j++) {
                System.out.print(screen[index++]);
            }
            System.out.println();
        }
    }


    private void executeInstructions(List<String> input, char[] screen) {
        int cycle = 0;
        int index = 0;
        int currentInstructionCycle = 0;
        int value = 1;
        while (index < input.size()) {
            if (Math.abs((cycle % 40 - value)) <= 1) screen[cycle] = '#';
            cycle++;
            String instruction = input.get(index);
            if (instruction.equals("noop")) {
                index++;
            } else if (currentInstructionCycle == 0) {
                currentInstructionCycle++;
            } else {
                value += Integer.parseInt(instruction.split(" ")[1]);
                currentInstructionCycle = 0;
                index++;
            }
        }
    }
}

//...............###......................

//##..##..##
//        10 13 3
//        11 13 2
//        12 12 0
//        13 12 1
//        14 4 10
//        15 4 11
//        16 17 1
//        17 17 0
//        18 21 3
//        19 21 2
//        20 21 1
//        21 20 1
//        22 20 2
//        23 25 2
//        24 25 1
//        25 24 1
//        26 24 2
//        27 29 2
//        28 29 1
//        29 28 1
//        30 28 2
//        31 33 2
//        32 33 1
//        33 32 1
//        34 32 2
//        35 37 2
//        36 37 1
//        37 36 1
//        38 36 2
//        39 1 38
//        40 1 39