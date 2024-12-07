package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test {
    private final char[][] input = new char[][]{
            "....#.....".toCharArray(),
            ".........#".toCharArray(),
            "..........".toCharArray(),
            "..#.......".toCharArray(),
            ".......#..".toCharArray(),
            "..........".toCharArray(),
            ".#..^.....".toCharArray(),
            "........#.".toCharArray(),
            "#.........".toCharArray(),
            "......#...".toCharArray(),
    };

    @Test
    void partOne() {
        var day = new Day6();
        int distinctPositions = day.partOne(input);
        assertEquals(41, distinctPositions);
    }

    @Test
    void partTwo() {
        var day = new Day6();
        int distinctPositions = day.partTwo(input);
        assertEquals(6, distinctPositions);
    }
}