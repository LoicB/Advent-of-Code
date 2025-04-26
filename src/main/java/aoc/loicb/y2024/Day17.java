package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import com.github.javaparser.utils.Pair;

import java.util.*;
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

    List<Long> executeProgram(ThreeBitComputer computer) {
        List<Long> output = new ArrayList<>();
        int index = 0;
        while (computer.program().size() > index) {
            index = executeProgramInstruction(computer, index, output);
        }
        return output;
    }

    int executeProgramInstruction(ThreeBitComputer computer, int instructionIndex, List<Long> output) {
        int opcode = computer.program().get(instructionIndex).intValue();
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
        long numerator = computer.register()[0];
        int denominator = (int) Math.pow(2, getComboOperandValue(computer.program().get(instructionIndex + 1).intValue(), computer.register()));
        computer.register()[0] = numerator / denominator;
        return instructionIndex + 2;
    }

    private int bxlBitwiseXOR(ThreeBitComputer computer, int instructionIndex) {
        computer.register()[1] = computer.register()[1] ^ computer.program().get(instructionIndex + 1);
        return instructionIndex + 2;
    }

    private int bstModulo(ThreeBitComputer computer, int instructionIndex) {
        long value = getComboOperandValue(computer.program().get(instructionIndex + 1).intValue(), computer.register());
        computer.register()[1] = value % 8;
        return instructionIndex + 2;
    }

    private int jump(ThreeBitComputer computer, int instructionIndex) {
        if (computer.register()[0] == 0) return instructionIndex + 2;
        return computer.program().get(instructionIndex + 1).intValue();
    }

    private int bxcBitwiseXOR(ThreeBitComputer computer, int instructionIndex) {
        computer.register()[1] = (computer.register()[1] ^ computer.register()[2]);
        return instructionIndex + 2;
    }

    private int out(ThreeBitComputer computer, int instructionIndex, List<Long> output) {
        output.add(getComboOperandValue(computer.program().get(instructionIndex + 1).intValue(), computer.register()) % 8);
        return instructionIndex + 2;
    }

    private int bdvDivision(ThreeBitComputer computer, int instructionIndex) {
        long numerator = computer.register()[0];
        int denominator = (int) Math.pow(2, getComboOperandValue(computer.program().get(instructionIndex + 1).intValue(), computer.register()));
        computer.register()[1] = numerator / denominator;
        return instructionIndex + 2;
    }

    private int cdvDivision(ThreeBitComputer computer, int instructionIndex) {
        long numerator = computer.register()[0];
        int denominator = (int) Math.pow(2, getComboOperandValue(computer.program().get(instructionIndex + 1).intValue(), computer.register()));
        computer.register()[2] = numerator / denominator;
        return instructionIndex + 2;
    }

    private long getComboOperandValue(int comboOperand, long[] register) {
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
                new long[]{getRegisterValue(lines[0]), getRegisterValue(lines[1]), getRegisterValue(lines[2])},
                getProgram(lines[4])
        );
    }

    private int getRegisterValue(String line) {
        return Integer.parseInt(line.substring(12));
    }

    private List<Long> getProgram(String line) {
        List<Long> program = new ArrayList<>();
        Scanner in = new Scanner(line).useDelimiter("[^0-9]+");
        while (in.hasNext()) {
            program.add(in.nextLong());
        }
        return program;
    }


    @Override
    public Long partTwo(String input) {
        return findA(input);
    }

    private long findA(String input) {
        var computer = createComputer(input);
        LinkedList<Long> queue = new LinkedList<>();
        queue.add(0L);
        List<Long> candidates = new ArrayList<>();
        var minimumValue = (long) Math.pow(8, computer.program().size() - 1);
        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (current < minimumValue) {
                var tmpComputer = createComputer(input, current);
                var output = executeProgram(tmpComputer);
                if (isValid(tmpComputer.program(), output)) {
                    var next = findNext(current);
                    queue.addAll(next);
                }
            } else {
                candidates.add(current);
            }
        }
        return candidates
                .stream()
                .sorted()
                .filter(a -> executeProgram(createComputer(input, a)).equals(computer.program()))
                .findFirst()
                .orElseThrow();
    }

    List<Long> findNext(long from) {
        var octalDigits = List.of(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L);
        return octalDigits.stream()
                .map(num -> from * 8 + num)
                .filter(num -> num > from)
                .toList();
    }

    boolean isValid(List<Long> program, List<Long> output) {
        for (int i = 0; i < output.size(); i++) {
            if (!program.get(program.size() - i - 1).equals(output.get(output.size() - i - 1))) {
                return false;
            }
        }
        return true;

    }

    public Pair<ThreeBitComputer, ThreeBitComputer> split(ThreeBitComputer computer) {
        List<Long> first = new ArrayList<>();
        List<Long> second = new ArrayList<>();
        int size = computer.program().size();
        for (int i = 0; i < size / 2; i++) {
            first.add(computer.program().get(i));
        }
        for (int i = size / 2; i < size; i++) {
            second.add(computer.program().get(i));
        }
        return new Pair<>(new ThreeBitComputer(computer.register(), first), new ThreeBitComputer(computer.register(), second));
    }

    ThreeBitComputer createComputer(String input, long registerA) {
        String[] lines = input.split("\\r?\\n");
        return new ThreeBitComputer(
                new long[]{registerA, getRegisterValue(lines[1]), getRegisterValue(lines[2])},
                getProgram(lines[4])
        );
    }
}

record ThreeBitComputer(long[] register, List<Long> program) {
    @Override
    public String toString() {
        return "ThreeBitComputer{" +
                "register=" + Arrays.toString(register) +
                ", program=" + program +
                '}';
    }
}
