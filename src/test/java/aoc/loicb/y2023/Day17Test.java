package aoc.loicb.y2023;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17Test {

    private final static List<List<Integer>> SAMPLE = List.of(
            List.of(2, 4, 1, 3, 4, 3, 2, 3, 1, 1, 3, 2, 3),
            List.of(3, 2, 1, 5, 4, 5, 3, 5, 3, 5, 6, 2, 3),
            List.of(3, 2, 5, 5, 2, 4, 5, 6, 5, 4, 2, 5, 4),
            List.of(3, 4, 4, 6, 5, 8, 5, 8, 4, 5, 4, 5, 2),
            List.of(4, 5, 4, 6, 6, 5, 7, 8, 6, 7, 5, 3, 6),
            List.of(1, 4, 3, 8, 5, 9, 8, 7, 9, 8, 4, 5, 4),
            List.of(4, 4, 5, 7, 8, 7, 6, 9, 8, 7, 7, 6, 6),
            List.of(3, 6, 3, 7, 8, 7, 7, 9, 7, 9, 6, 5, 3),
            List.of(4, 6, 5, 4, 9, 6, 7, 9, 8, 6, 8, 8, 7),
            List.of(4, 5, 6, 4, 6, 7, 9, 9, 8, 6, 4, 5, 3),
            List.of(1, 2, 2, 4, 6, 8, 6, 8, 6, 5, 5, 6, 3),
            List.of(2, 5, 4, 6, 5, 4, 8, 8, 8, 7, 7, 3, 5),
            List.of(4, 3, 2, 2, 6, 7, 4, 6, 5, 5, 5, 3, 3)
    );
    private final static List<List<Integer>> SAMPLE2 = List.of(
            List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
            List.of(9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 1),
            List.of(9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 1),
            List.of(9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 1),
            List.of(9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 1)
    );

    @Test
    void partOne() {
        var day = new Day17();
        var heatLoss = day.partOne(SAMPLE);
        assertEquals(102L, heatLoss);
    }

    @Test
    void partTwo() {
        var day = new Day17();
        var heatLoss = day.partTwo(SAMPLE);
        assertEquals(94L, heatLoss);
    }

    @Test
    void partTwo2() {
        var day = new Day17();
        var heatLoss = day.partTwo(SAMPLE2);
        assertEquals(71L, heatLoss);
    }
}