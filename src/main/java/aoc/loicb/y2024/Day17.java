package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day17 implements Day<String, Object> {
    public static void main(String[] args) {
        DayExecutor<String> de = new DayExecutor<>(rawInput -> rawInput);
        de.execute(new Day17());
    }

    @Override
    public String partOne(String input) {
        return executeProgram(createComputer(input)).stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    List<Integer> executeProgram(ThreeBitComputer computer) {
        List<Integer> output = new ArrayList<>();
        int index = 0;
        while (computer.program().size() > index) {
            index = executeProgramInstruction(computer, index, output);
        }
        return output;
    }

    int executeProgramInstruction(ThreeBitComputer computer, int instructionIndex, List<Integer> output) {
        int opcode = computer.program().get(instructionIndex);
        return switch (opcode) {
            case 0 -> advDivision(computer, instructionIndex);
            case 1 -> bxlBitwiseXOR(computer, instructionIndex);
            case 2 -> bstModulo(computer, instructionIndex);
            case 3 -> jump(computer, instructionIndex);
            case 4 -> bxcBitwiseXOR(computer, instructionIndex);
            case 5 -> out(computer, instructionIndex, output);
            case 6 -> bdvDivision(computer, instructionIndex);
            case 7 -> cdvDivision(computer, instructionIndex);
            default -> instructionIndex;
        };
    }

    private int advDivision(ThreeBitComputer computer, int instructionIndex) {
        int numerator = computer.register()[0];
        int denominator = (int) Math.pow(2, getComboOperandValue(computer.program().get(instructionIndex + 1), computer.register()));
        computer.register()[0] = numerator / denominator;
        return instructionIndex + 2;
    }

    private int bxlBitwiseXOR(ThreeBitComputer computer, int instructionIndex) {
        computer.register()[1] = computer.register()[1] ^ computer.program().get(instructionIndex + 1);
        return instructionIndex + 2;
    }

    private int bstModulo(ThreeBitComputer computer, int instructionIndex) {
        int value = getComboOperandValue(computer.program().get(instructionIndex + 1), computer.register());
        computer.register()[1] = value % 8;
        return instructionIndex + 2;
    }

    private int jump(ThreeBitComputer computer, int instructionIndex) {
        if (computer.register()[0] == 0) return instructionIndex + 2;
        return computer.program().get(instructionIndex + 1);
    }

    private int bxcBitwiseXOR(ThreeBitComputer computer, int instructionIndex) {
        computer.register()[1] = (computer.register()[1] ^ computer.register()[2]);
        return instructionIndex + 2;
    }

    private int out(ThreeBitComputer computer, int instructionIndex, List<Integer> output) {
        output.add(getComboOperandValue(computer.program().get(instructionIndex + 1), computer.register()) % 8);
        return instructionIndex + 2;
    }

    private int bdvDivision(ThreeBitComputer computer, int instructionIndex) {
        int numerator = computer.register()[0];
        int denominator = (int) Math.pow(2, getComboOperandValue(computer.program().get(instructionIndex + 1), computer.register()));
        computer.register()[1] = numerator / denominator;
        return instructionIndex + 2;
    }

    private int cdvDivision(ThreeBitComputer computer, int instructionIndex) {
        int numerator = computer.register()[0];
        int denominator = (int) Math.pow(2, getComboOperandValue(computer.program().get(instructionIndex + 1), computer.register()));
        computer.register()[2] = numerator / denominator;
        return instructionIndex + 2;
    }

    private int getComboOperandValue(int comboOperand, int[] register) {
        return switch (comboOperand) {
            case 0, 1, 2, 3 -> comboOperand;
            case 4 -> register[0];
            case 5 -> register[1];
            case 6 -> register[2];
            default -> -1;
        };
    }


    ThreeBitComputer createComputer(String input) {
        String[] lines = input.split("\\r?\\n");
        return new ThreeBitComputer(
                new int[]{getRegisterValue(lines[0]), getRegisterValue(lines[1]), getRegisterValue(lines[2])},
                getProgram(lines[4])
        );
    }

    private int getRegisterValue(String line) {
        return Integer.parseInt(line.substring(12));
    }

    private List<Integer> getProgram(String line) {
        List<Integer> program = new ArrayList<>();
        Scanner in = new Scanner(line).useDelimiter("[^0-9]+");
        while (in.hasNext()) {
            program.add(in.nextInt());
        }
        return program;
    }


    @Override
    public Integer partTwo(String input) {
        return 0;
    }
}

record ThreeBitComputer(int[] register, List<Integer> program) {
}
