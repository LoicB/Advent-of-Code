package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day18Test {

    private final List<Coordinate> sample = List.of(
            new Coordinate(5, 4),
            new Coordinate(4, 2),
            new Coordinate(4, 5),
            new Coordinate(3, 0),
            new Coordinate(2, 1),
            new Coordinate(6, 3),
            new Coordinate(2, 4),
            new Coordinate(1, 5),
            new Coordinate(0, 6),
            new Coordinate(3, 3),
            new Coordinate(2, 6),
            new Coordinate(5, 1),
            new Coordinate(1, 2),
            new Coordinate(5, 5),
            new Coordinate(2, 5),
            new Coordinate(6, 5),
            new Coordinate(1, 4),
            new Coordinate(0, 4),
            new Coordinate(6, 4),
            new Coordinate(1, 1),
            new Coordinate(6, 1),
            new Coordinate(1, 0),
            new Coordinate(0, 5),
            new Coordinate(1, 6),
            new Coordinate(2, 0)
    );

    @Test
    void findStepToExit() {
        var day = new Day18();
        var map = day.generateMap(sample, 7, 12);
        var steps = day.findStepToExit(map);
        assertEquals(22, steps);
    }


    @Test
    void findBlockingByte() {
        var day = new Day18();
        var blockingByte = day.findBlockingByte(sample, 7, 12);
        assertEquals(new Coordinate(6, 1), blockingByte);
    }
}