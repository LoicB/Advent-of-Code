package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.List;

public class Day2 implements Day<List<Command>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<Command>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public Command transformObject(String rawInput) {
                String[] arr = rawInput.split(" ");
                int units = Integer.parseInt(arr[1]);
                return new Command(arr[0], units);
            }
        });
        de.execute(new Day2());
    }

    @Override
    public Integer partOne(List<Command> commands) {
        int[] finalPosition = calculateFinalPositionPartOne(commands, new int[]{0, 0});
        return finalPosition[0] * finalPosition[1];
    }

    protected int[] calculateFinalPositionPartOne(List<Command> commands, int[] currentPosition) {
        return calculateFinalPosition(commands, new int[]{0, 0}, this::calculateNextPosition);
    }

    private int[] calculateFinalPosition(List<Command> commands, int[] currentPosition, PositionNavigator positionNavigator) {
        int[] finalPosition = currentPosition;
        for (Command command : commands) {
            finalPosition = positionNavigator.calculateNextPosition(command, finalPosition);
        }
        return finalPosition;
    }

    protected int[] calculateNextPosition(Command command, int[] currentPosition) {
        switch (command.getAction()) {
            case "forward" -> {
                return new int[]{currentPosition[0] + command.getUnits(), currentPosition[1]};
            }
            case "up" -> {
                return new int[]{currentPosition[0], currentPosition[1] - command.getUnits()};
            }
            case "down" -> {
                return new int[]{currentPosition[0], currentPosition[1] + command.getUnits()};
            }
        }
        return currentPosition;
    }

    @Override
    public Integer partTwo(List<Command> commands) {
        int[] finalPosition = calculateFinalPositionPartTwo(commands, new int[]{0, 0, 0});
        return finalPosition[0] * finalPosition[1];
    }

    protected int[] calculateFinalPositionPartTwo(List<Command> commands, int[] currentPosition) {
        return calculateFinalPosition(commands, new int[]{0, 0, 0}, this::calculateNextPositionPartTWo);
    }

    protected int[] calculateNextPositionPartTWo(Command command, int[] currentPosition) {
        switch (command.getAction()) {
            case "forward" -> {
                return new int[]{currentPosition[0] + command.getUnits(), currentPosition[1] + currentPosition[2] * command.getUnits(), currentPosition[2]};
            }
            case "up" -> {
                return new int[]{currentPosition[0], currentPosition[1], currentPosition[2] - command.getUnits()};
            }
            case "down" -> {
                return new int[]{currentPosition[0], currentPosition[1], currentPosition[2] + command.getUnits()};
            }
        }
        return currentPosition;
    }

    @FunctionalInterface
    private interface PositionNavigator {
        int[] calculateNextPosition(Command command, int[] currentPosition);
    }
}

record Command(String action, int units) {
    String getAction() {
        return action;
    }

    int getUnits() {
        return units;
    }
}
