package aoc.loicb.y2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17Test {

    @Test
    void partOne() {
        var jetPattern = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>";
        var day = new Day17();
        long numberOfRocks = day.partOne(jetPattern);
        assertEquals(3068, numberOfRocks);

    }

    @Test
    void partTwo() {
        var jetPattern = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>";
        var day = new Day17();
        long numberOfRocks = day.partTwo(jetPattern);
        assertEquals(1514285714288L, numberOfRocks);
    }
}