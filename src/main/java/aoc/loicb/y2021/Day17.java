package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;

public class Day17 implements Day<Area, Integer> {

    public static void main(String[] args) {
        DayExecutor<Area> de = new DayExecutor<>(rawInput -> {
            String[] input = rawInput.split("=|\\.\\.|,");
            int xFrom = Integer.parseInt(input[1]);
            int xTo = Integer.parseInt(input[2]);
            int yFrom = Integer.parseInt(input[4]);
            int yTo = Integer.parseInt(input[5]);

            return new Area(xFrom, xTo, yFrom, yTo);
        });
        de.execute(new Day17());


    }


    @Override
    public Integer partOne(Area input) {
        int maxY = 0;
        for (int x = 0; x <= input.xTo(); x++) {
            for (int y = input.yFrom(); y < Math.abs(input.yFrom()); y++) {
                Shot shot = shot(input, x, y);
                if (shot.reachTarget()) maxY = Math.max(maxY, shot.maxY());
            }
        }
        return maxY;
    }

    private Shot shot(Area area, int initialVelocityX, int initialVelocityY) {
        Position position = new Position(0, 0);
        Velocity velocity = new Velocity(initialVelocityX, initialVelocityY);
        int maxY = 0;
        boolean reachedArea = false;
        while (!tooFar(position, area) && !reachedArea) {
            position = calculateNextPosition(position, velocity);
            velocity = calculateNextVelocity(velocity);
            maxY = Math.max(maxY, position.y());
            reachedArea = isInside(position, area);
        }
        return new Shot(reachedArea, maxY);
    }


    private Position calculateNextPosition(Position position, Velocity velocity) {
        return new Position(position.x() + velocity.x(), position.y() + velocity.y());
    }

    private Velocity calculateNextVelocity(Velocity velocity) {
        if (velocity.x() > 0) return new Velocity(velocity.x() - 1, velocity.y() - 1);
        if (velocity.x() < 0) return new Velocity(velocity.x() + 1, velocity.y() - 1);
        return new Velocity(0, velocity.y() - 1);
    }

    protected boolean isInside(Position position, Area targetArea) {
        return position.x() >= targetArea.xFrom()
                && position.x() <= targetArea.xTo()
                && position.y() <= targetArea.yTo()
                && position.y() >= targetArea.yFrom();
    }

    private boolean tooFar(Position position, Area targetArea) {
        return position.y() < targetArea.yFrom();
    }

    @Override
    public Integer partTwo(Area input) {
        int count = 0;
        for (int x = 0; x <= input.xTo(); x++) {
            for (int y = input.yFrom(); y < Math.abs(input.yFrom()); y++) {
                Shot shot = shot(input, x, y);
                if (shot.reachTarget()) count++;
            }
        }
        return count;
    }
}


record Area(int xFrom, int xTo, int yFrom, int yTo) {
}

record Shot(boolean reachTarget, int maxY) {
}

record Position(int x, int y) {
}

record Velocity(int x, int y) {
}



