package aoc.loicb.y2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test {

    @Test
    void partOne() {
        char[][] input = new char[][]{
                "Sabqponm".toCharArray(),
                "abcryxxl".toCharArray(),
                "accszExk".toCharArray(),
                "acctuvwj".toCharArray(),
                "abdefghi".toCharArray()
        };
        var day = new Day12();
        long inspectedItemsProduct = day.partOne(input);
        assertEquals(31, inspectedItemsProduct);
    }

    @Test
    void partTwo() {
        char[][] input = new char[][]{
                "Sabqponm".toCharArray(),
                "abcryxxl".toCharArray(),
                "accszExk".toCharArray(),
                "acctuvwj".toCharArray(),
                "abdefghi".toCharArray()
        };
        var day = new Day12();
        long inspectedItemsProduct = day.partTwo(input);
        assertEquals(29, inspectedItemsProduct);
    }
}