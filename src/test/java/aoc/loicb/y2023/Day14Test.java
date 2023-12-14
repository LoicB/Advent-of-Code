package aoc.loicb.y2023;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day14Test {
    private final static List<String> SAMPLE = List.of(
            "O....#....",
            "O.OO#....#",
            ".....##...",
            "OO.#O....O",
            ".O.....O#.",
            "O.#..O.#.#",
            "..O..#O..O",
            ".......O..",
            "#....###..",
            "#OO..#....");
    private final static List<String> TILTED_SAMPLE = List.of(
            "OOOO.#.O..",
            "OO..#....#",
            "OO..O##..O",
            "O..#.OO...",
            "........#.",
            "..#....#.#",
            "..O..#.O.O",
            "..O.......",
            "#....###..",
            "#....#....");


    @Test
    void partOne() {
        var day = new Day14();
        var totalLoad = day.partOne(SAMPLE);
        assertEquals(136L, totalLoad);
    }

    @Test
    void tilt() {
        var day = new Day14();
        var tilted = day.tilt(SAMPLE);
        assertEquals(TILTED_SAMPLE, tilted);
    }

    @Test
    void calculateTotalLoad() {
        var day = new Day14();
        var totalLoad = day.calculateTotalLoad(TILTED_SAMPLE);
        assertEquals(136L, totalLoad);
    }

    @Test
    void partTwo() {
        var day = new Day14();
        var totalLoad = day.partTwo(SAMPLE);
        assertEquals(64L, totalLoad);
    }


    @Test
    void tiltCycle() {
        var day = new Day14();
        var tiltedOne = day.tiltCycleOnce(SAMPLE);
        assertEquals(List.of(".....#....",
                "....#...O#",
                "...OO##...",
                ".OO#......",
                ".....OOO#.",
                ".O#...O#.#",
                "....O#....",
                "......OOOO",
                "#...O###..",
                "#..OO#...."), tiltedOne);
        var tiltedTwo = day.tiltCycleOnce(tiltedOne);
        assertEquals(List.of(".....#....",
                "....#...O#",
                ".....##...",
                "..O#......",
                ".....OOO#.",
                ".O#...O#.#",
                "....O#...O",
                ".......OOO",
                "#..OO###..",
                "#.OOO#...O"), tiltedTwo);
        var tiltedThree = day.tiltCycleOnce(tiltedTwo);
        assertEquals(List.of(".....#....",
                "....#...O#",
                ".....##...",
                "..O#......",
                ".....OOO#.",
                ".O#...O#.#",
                "....O#...O",
                ".......OOO",
                "#...O###.O",
                "#.OOO#...O"), tiltedThree);
    }
}