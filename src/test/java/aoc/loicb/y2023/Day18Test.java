package aoc.loicb.y2023;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day18Test {

    private static final List<String> SAMPLE = List.of(
            "R 6 (#70c710)",
            "D 5 (#0dc571)",
            "L 2 (#5713f0)",
            "D 2 (#d2c081)",
            "R 2 (#59c680)",
            "D 2 (#411b91)",
            "L 5 (#8ceee2)",
            "U 2 (#caa173)",
            "L 1 (#1b58a2)",
            "U 2 (#caa171)",
            "R 2 (#7807d2)",
            "U 3 (#a77fa3)",
            "L 2 (#015232)",
            "U 2 (#7a21e3)"
    );

    @Test
    void partOne() {
        var day = new Day18();
        var cubicMeters = day.partOne(SAMPLE);
        assertEquals(62L, cubicMeters);
    }

    @Test
    void shoelaceFormula() {
        var day = new Day18();
        var cubicMeters = day.shoelaceFormula(day.findPositions(day.buildInstructions(SAMPLE)));
        assertEquals(42L, cubicMeters);
    }

    @Test
    void calculatePerimeter() {
        var day = new Day18();
        var cubicMeters = day.calculatePerimeter(day.findPositions(day.buildInstructions(SAMPLE)));
        assertEquals(38L, cubicMeters);
    }

    @Test
    void partTwo() {
        var day = new Day18();
        var cubicMeters = day.partTwo(SAMPLE);
        assertEquals(952408144115L, cubicMeters);
    }
}