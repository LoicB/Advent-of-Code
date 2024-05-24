package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.ArrayList;
import java.util.List;

public class Day18 implements Day<List<String>, Number> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day18());

    }

    @Override
    public Number partOne(List<String> input) {
        var positions = findPositions(buildInstructions(input));
        return shoelaceFormula(positions) + calculatePerimeter(positions) / 2 + 1;
    }

    long shoelaceFormula(List<Position> positions) {
        long sum = 0;
        for (int i = 0; i < positions.size() - 1; i++) {
            Position position1 = positions.get(i);
            Position position2 = positions.get(i + 1);
            sum += ((long) position1.x() * (long) position2.y()) - ((long) position1.y() * (long) position2.x());
        }

        Position position1 = positions.get(0);
        Position position2 = positions.get(positions.size() - 1);
        sum += ((long) position1.x() * (long) position2.y()) - ((long) position1.y() * (long) position2.x());

        return Math.abs(sum) / 2;
    }


    long calculatePerimeter(List<Position> positions) {
        long perimeter = 0;
        for (int i = 0; i < positions.size() - 1; i++) {
            Position currentPosition = positions.get(i);
            Position nextPosition = positions.get(i + 1);
            int xDistance = Math.abs(currentPosition.x() - nextPosition.x());
            int yDistance = Math.abs(currentPosition.y() - nextPosition.y());
            perimeter += xDistance + yDistance;
        }
        return perimeter;
    }

    List<Instruction> buildInstructions(List<String> input) {
        return input
                .stream()
                .map(this::buildInstruction)
                .toList();
    }

    Instruction buildInstruction(String string) {
        String[] arr = string.split(" ");
        return new Instruction(getDirection(arr[0]), Integer.parseInt(arr[1]));
    }

    Direction getDirection(String string) {
        return switch (string) {
            case "D" -> Direction.Down;
            case "L" -> Direction.Left;
            case "U" -> Direction.Up;
            case "R" -> Direction.Right;
            default -> throw new RuntimeException("Invalid direction" + string);
        };
    }

    List<Position> findPositions(List<Instruction> instructions) {
        List<Position> positions = new ArrayList<>();
        int x = 0;
        int y = 0;
        positions.add(new Position(x, y));
        for (Instruction instruction : instructions) {
            if (instruction.direction().equals(Direction.Up)) {
                y += instruction.length();
            } else if (instruction.direction().equals(Direction.Down)) {
                y -= instruction.length();
            } else if (instruction.direction().equals(Direction.Left)) {
                x -= instruction.length();
            } else if (instruction.direction().equals(Direction.Right)) {
                x += instruction.length();
            }
            positions.add(new Position(x, y));
        }
        return positions;

    }

    @Override
    public Number partTwo(List<String> input) {
        var positions = findPositions(buildInstructionsPartTwo(input));
        return shoelaceFormula(positions) + calculatePerimeter(positions) / 2 + 1;
    }

    List<Instruction> buildInstructionsPartTwo(List<String> input) {
        return input.stream().map(this::buildInstructionPartTwo).toList();
    }

    Instruction buildInstructionPartTwo(String string) {
        String[] arr = string.split(" ");
        return new Instruction(getDirection(arr[2].charAt(arr[2].length() - 2)), Integer.parseInt(arr[2].substring(2, arr[2].length() - 2), 16));
    }


    Direction getDirection(char c) {
        return switch (c) {
            case '0' -> Direction.Right;
            case '1' -> Direction.Down;
            case '2' -> Direction.Left;
            case '3' -> Direction.Up;
            default -> throw new RuntimeException("Invalid direction" + c);
        };
    }


    record Position(int x, int y) {
    }

    record Instruction(Direction direction, int length) {
    }

}
