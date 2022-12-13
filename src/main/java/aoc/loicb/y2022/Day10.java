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