package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Day13 implements Day<List<String>, Long> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(rawInput -> List.of(rawInput.split("\n\n")));
        de.execute(new Day13());

    }

    @Override
    public Long partOne(List<String> input) {
        return input
                .stream()
                .map(this::calculateMachineCostToVictory)
                .mapToLong(integer -> integer.orElse(0L))
                .sum();
    }

    Optional<Long> calculateMachineCostToVictory(String input) {
        return calculateMachineCostToVictory(ClawMachine.fromString(input));
    }

    private Optional<Long> calculateMachineCostToVictory(ClawMachine clawMachine) {
        int eliminator1 = clawMachine.buttonB().costY() * clawMachine.buttonA().costX();
        long eliminator2 = clawMachine.buttonB().costY() * clawMachine.prize().x();
        int eliminator3 = clawMachine.buttonB().costX() * clawMachine.buttonA().costY();
        long eliminator4 = clawMachine.buttonB().costX() * clawMachine.prize().y();
        long x = (eliminator2 - eliminator4) / (eliminator1 - eliminator3);
        long y = (clawMachine.prize().x() - clawMachine.buttonA().costX() * x) / clawMachine.buttonB().costX();
        if (isSolutionGood(x, y, clawMachine)) return Optional.of(3 * x + y);
        return Optional.empty();
    }

    private boolean isSolutionGood(long x, long y, ClawMachine clawMachine) {
        return x * clawMachine.buttonA().costX() + y * clawMachine.buttonB().costX() == clawMachine.prize().x()
                && x * clawMachine.buttonA().costY() + y * clawMachine.buttonB().costY() == clawMachine.prize().y();
    }


    @Override
    public Long partTwo(List<String> input) {
        return input
                .stream()
                .map(this::calculateMachineCostToVictoryProperPrice)
                .mapToLong(integer -> integer.orElse(0L))
                .sum();
    }


    Optional<Long> calculateMachineCostToVictoryProperPrice(String input) {
        return calculateMachineCostToVictory(ClawMachine.fromStringPartTwo(input));
    }

    record ClawMachine(Button buttonA, Button buttonB, Prize prize) {
        static ClawMachine fromString(String input) {
            String[] parts = input.split("\n");
            return new ClawMachine(
                    Button.fromString(parts[0]),
                    Button.fromString(parts[1]),
                    Prize.fromString(parts[2])
            );
        }

        static ClawMachine fromStringPartTwo(String input) {
            String[] parts = input.split("\n");
            return new ClawMachine(
                    Button.fromString(parts[0]),
                    Button.fromString(parts[1]),
                    Prize.fromStringPartTWo(parts[2])
            );
        }
    }

    record Prize(long x, long y) {
        static Prize fromString(String input) {
            Scanner in = new Scanner(input).useDelimiter("[^0-9]+");
            return new Prize(in.nextLong(), in.nextLong());
        }

        static Prize fromStringPartTWo(String input) {
            Scanner in = new Scanner(input).useDelimiter("[^0-9]+");
            return new Prize(10000000000000L + in.nextLong(), 10000000000000L + in.nextLong());
        }
    }

    record Button(int costX, int costY) {
        static Button fromString(String input) {
            Scanner in = new Scanner(input).useDelimiter("[^0-9]+");
            return new Button(in.nextInt(), in.nextInt());
        }
    }
}

