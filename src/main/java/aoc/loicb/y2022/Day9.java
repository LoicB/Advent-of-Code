package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day9 implements Day<List<Motion>, Long> {
    public static void main(String[] args) {
        DayExecutor<List<Motion>> de = new DayExecutor<>(new Day9InputTransformer());
        de.execute(new Day9());
    }

    @Override
    public Long partOne(List<Motion> input) {
        Knot head = new Knot();
        Knot tail = new Knot();
        input.forEach(motion -> moveKnots(motion, head, tail));
        return tail.getPositions().stream().distinct().count();
    }

    protected void moveKnots(Motion motion, Knot... knots) {
        IntStream
                .range(0, motion.numberOfSteps())
                .forEach(value -> moveKnotOnce(motion, knots));
    }

    protected void moveKnotOnce(Motion motion, Knot... knots) {
        moveHeadKnot(knots[0], motion.direction());
        moveTail(knots);
    }

    protected void moveHeadKnot(Knot knot, char direction) {
        switch (direction) {
            case 'R' -> knot.right();
            case 'U' -> knot.up();
            case 'L' -> knot.left();
            default -> knot.down();
        }
    }

    protected void moveTail(Knot... knots) {
        for (int i = 1; i < knots.length; i++) {
            moveTail(knots[i - 1], knots[i]);
        }
    }

    protected void moveTail(Knot head, Knot follower) {
        Position newPosition = moveTail(follower.currentPosition(), head.currentPosition());
        follower.move(newPosition);
    }

    protected Position moveTail(Position tail, Position head) {
        if (Math.abs(head.x() - tail.x()) <= 1 && Math.abs(head.y() - tail.y()) <= 1) return tail;
        if (head.y() == tail.y()) return new Position(head.x() + (head.x() < tail.x() ? 1 : -1), head.y());
        if (head.x() == tail.x()) return new Position(tail.x(), head.y() + (head.y() < tail.y() ? 1 : -1));
        int newX = Math.abs(head.x() - tail.x()) == 2 ? head.x() + (head.x() < tail.x() ? 1 : -1) : head.x();
        int newY = Math.abs(head.y() - tail.y()) == 2 ? head.y() + (head.y() < tail.y() ? 1 : -1) : head.y();
        return new Position(newX, newY);
    }

    @Override
    public Long partTwo(List<Motion> input) {

        Knot head = new Knot();
        Knot k1 = new Knot();
        Knot k2 = new Knot();
        Knot k3 = new Knot();
        Knot k4 = new Knot();
        Knot k5 = new Knot();
        Knot k6 = new Knot();
        Knot k7 = new Knot();
        Knot k8 = new Knot();
        Knot tail = new Knot();
        input.forEach(motion -> moveKnots(motion, head, k1, k2, k3, k4, k5, k6, k7, k8, tail));
        return tail.getPositions().stream().distinct().count();
    }
}

record Motion(char direction, int numberOfSteps) {

}

class Knot {
    private final List<Position> positions;

    Knot() {
        this(new Position(0, 0));
    }


    Knot(Position position) {
        this.positions = new ArrayList<>();
        this.positions.add(position);
    }

    public List<Position> getPositions() {
        return positions;
    }

    Position currentPosition() {
        return positions.get(positions.size() - 1);
    }

    void up() {
        move(currentPosition().x(), currentPosition().y() + 1);
    }


    void down() {
        move(currentPosition().x(), currentPosition().y() - 1);
    }

    void right() {
        move(currentPosition().x() + 1, currentPosition().y());
    }

    void left() {
        move(currentPosition().x() - 1, currentPosition().y());
    }

    private void move(int x, int y) {
        positions.add(new Position(x, y));
    }

    void move(Position newPosition) {
        positions.add(newPosition);
    }
}

record Position(int x, int y) {

}

class Day9InputTransformer implements InputTransformer<List<Motion>> {
    @Override
    public List<Motion> transform(String rawInput) {
        String[] inputLines = rawInput.split("\\r?\\n");
        return Arrays.stream(inputLines).map(this::createMotion).collect(Collectors.toList());
    }

    private Motion createMotion(String input) {
        String[] elements = input.split(" ");
        return new Motion(input.charAt(0), Integer.parseInt(elements[1]));
    }
}