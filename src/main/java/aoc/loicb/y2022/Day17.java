package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day17 implements Day<String, Number> {
    public static void main(String[] args) {
        DayExecutor<String> de = new DayExecutor<>(rawInput -> rawInput);
        de.execute(new Day17());
    }

    @Override
    public Long partOne(String input) {
        return calculateHeight(input, 2022L);
    }

    private long calculateHeight(String instructions, long numberOfRocks) {
        Chamber chamber = Chamber.starting();
        List<Rock> rocks = new ArrayList<>();
        int instructionIndex = 0;
        int top = 0;
        List<RockEvent> events = new ArrayList<>();
        Optional<Cycle> optionalCycle = Optional.empty();
        while (rocks.size() < numberOfRocks && optionalCycle.isEmpty()) {
            int altitude = top + 3;
            chamber.addLines(altitude);
            Rock rock = createRock(altitude, rocks.size());
            boolean canMoveDown = true;
            while (canMoveDown) {
                moveRock(instructions.charAt(instructionIndex % instructions.length()), rock, chamber);
                canMoveDown = rock.moveDown(chamber.chamber());
                instructionIndex++;
            }
            chamber.applyRock(rock);
            int y = rock.getTop();
            if (rocks.size() > 0) y -= rocks.get(rocks.size() - 1).getTop();
            events.add(new RockEvent(instructionIndex % instructions.length(), rock.id(), rock.parts()[0].x(), y));
            top = Math.max(rock.getTop(), top);
            rocks.add(rock);
            if (rocks.size() % 1000 == 0) optionalCycle = findCycle(events);
        }
        if (optionalCycle.isPresent()) return calculateWithCycle(rocks, optionalCycle.get(), numberOfRocks);
        return rocks.get(rocks.size() - 1).getTop();
    }

    private long calculateWithCycle(List<Rock> rocks, Cycle cycle, long numberOfRocks) {
        long beforeCycle = IntStream
                .range(0, cycle.start())
                .mapToObj(rocks::get)
                .mapToInt(Rock::getTop)
                .max()
                .orElse(0);
        long differenceCycle = IntStream
                .range(cycle.start() + 1, cycle.start() + cycle.length())
                .mapToObj(rocks::get)
                .mapToInt(Rock::getTop)
                .max()
                .orElse(0)
                - beforeCycle;
        long numberOfCycle = ((numberOfRocks - cycle.start()) / cycle.length());
        int afterCycleSize = (int) (numberOfRocks - cycle.start() - (cycle.length() * numberOfCycle));
        long afterCycle = IntStream
                .range(cycle.start() + 1, cycle.start() + afterCycleSize)
                .mapToObj(rocks::get)
                .mapToInt(Rock::getTop)
                .max()
                .orElse(0)
                - beforeCycle;
        return beforeCycle + numberOfCycle * differenceCycle + afterCycle;
    }

    private Optional<Cycle> findCycle(List<RockEvent> events) {
        int tortoise = 0;
        int hare = 0;
        while (hare < events.size() && hare + 2 < events.size()) {
            tortoise++;
            hare += 2;
            if (events.get(hare).equals(events.get(tortoise))) {
                return Optional.of(new Cycle(hare - tortoise, tortoise));
            }
        }
        return Optional.empty();
    }

    private Rock createRock(int altitude, int rockIndex) {
        return switch (rockIndex % 5) {
            case 0 -> Rock.shape1(altitude);
            case 1 -> Rock.shape2(altitude);
            case 2 -> Rock.shape3(altitude);
            case 3 -> Rock.shape4(altitude);
            default -> Rock.shape5(altitude);
        };
    }

    private void moveRock(char instruction, Rock rock, Chamber chamber) {
        if (instruction == '>') rock.moveRight(chamber.chamber());
        if (instruction == '<') rock.moveLeft(chamber.chamber());
    }

    @Override
    public Long partTwo(String input) {
        System.out.println(calculateHeight(input, 1000000000000L));
        return calculateHeight(input, 1000000000000L);
    }
}


class Part {
    private int x;
    private int y;

    Part(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveLeft() {
        this.x--;
    }

    public void moveRight() {
        this.x++;
    }

    public void moveDown() {
        this.y--;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }
}

record Rock(Part[] parts, int id) {
    static Rock shape1(int y) {
        return new Rock(new Part[]{new Part(2, y), new Part(3, y), new Part(4, y), new Part(5, y)}, 1);
    }

    static Rock shape2(int y) {
        return new Rock(new Part[]{new Part(3, y), new Part(2, y + 1), new Part(3, y + 1), new Part(4, y + 1), new Part(3, y + 2)}, 2);
    }

    static Rock shape3(int y) {
        return new Rock(new Part[]{new Part(2, y), new Part(3, y), new Part(4, y), new Part(4, y + 1), new Part(4, y + 2)}, 3);
    }

    static Rock shape4(int y) {
        return new Rock(new Part[]{new Part(2, y), new Part(2, y + 1), new Part(2, y + 2), new Part(2, y + 3)}, 4);
    }

    static Rock shape5(int y) {
        return new Rock(new Part[]{new Part(2, y), new Part(3, y), new Part(2, y + 1), new Part(3, y + 1)}, 5);
    }

    public int getTop() {
        return Arrays.stream(parts).mapToInt(Part::y).max().orElse(0) + 1;
    }

    public void moveLeft(List<Boolean[]> chamber) {
        if (!canMoveLeft(chamber)) return;
        for (Part part : parts) part.moveLeft();
    }

    public void moveRight(List<Boolean[]> chamber) {
        if (!canMoveRight(chamber)) return;
        for (Part part : parts) part.moveRight();
    }

    public boolean moveDown(List<Boolean[]> chamber) {
        if (!canMoveDown(chamber)) return false;
        for (Part part : parts) part.moveDown();
        return true;
    }

    private boolean canMoveLeft(List<Boolean[]> chamber) {
        return Arrays.stream(parts).noneMatch(part -> part.x() == 0 || chamber.get(part.y())[part.x() - 1]);
    }

    private boolean canMoveRight(List<Boolean[]> chamber) {
        return Arrays.stream(parts).noneMatch(part -> part.x() == 6 || chamber.get(part.y())[part.x() + 1]);
    }

    public boolean canMoveDown(List<Boolean[]> chamber) {
        return Arrays.stream(parts).noneMatch(part -> part.y() <= 0 || chamber.get(part.y() - 1)[part.x()]);
    }

}

record Chamber(List<Boolean[]> chamber) {
    static Chamber starting() {
        return new Chamber(IntStream.range(0, 4).mapToObj(operand -> newChamberLevel()).collect(Collectors.toList()));
    }

    private static Boolean[] newChamberLevel() {
        return new Boolean[]{false, false, false, false, false, false, false};
    }

    public void applyRock(Rock rock) {
        Arrays.stream(rock.parts()).forEach(this::applyPart);
    }

    private void applyPart(Part part) {
        chamber.get(part.y())[part.x()] = true;
    }

    public void addLines(int altitude) {
        while (chamber.size() < altitude + 5) chamber.add(newChamberLevel());
    }

}

record RockEvent(int instructionIndex, int rockIndex, int x, int y) {
}

record Cycle(int start, int length) {
}