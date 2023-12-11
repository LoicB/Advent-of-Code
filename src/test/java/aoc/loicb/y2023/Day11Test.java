package aoc.loicb.y2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {

    private static Stream<Arguments> expandingUniverseCaseProvider() {
        return Stream.of(
                Arguments.of(10, 1030),
                Arguments.of(100, 8410)
        );
    }

    @Test
    void partOne() {
        var input = List.of("...#......",
                ".......#..",
                "#.........",
                "..........",
                "......#...",
                ".#........",
                ".........#",
                "..........",
                ".......#..",
                "#...#.....");
        var day = new Day11();
        var sumOfShortestPath = day.partOne(input);
        assertEquals(374, sumOfShortestPath);
    }

    @Test
    void countGalaxiesVertically() {
        var input = List.of("...#......",
                ".......#..",
                "#.........",
                "..........",
                "......#...",
                ".#........",
                ".........#",
                "..........",
                ".......#..",
                "#...#.....");
        var day = new Day11();
        var count = day.countGalaxiesVertically(input);
        assertEquals(List.of(2, 1, 0, 1, 1, 0, 1, 2, 0, 1), count);
    }

    @Test
    void countGalaxiesHorizontally() {
        var input = List.of("...#......",
                ".......#..",
                "#.........",
                "..........",
                "......#...",
                ".#........",
                ".........#",
                "..........",
                ".......#..",
                "#...#.....");
        var day = new Day11();
        var count = day.countGalaxiesHorizontally(input);
        assertEquals(List.of(1, 1, 1, 0, 1, 1, 1, 0, 1, 2), count);
    }

    @Test
    void extendUniverse() {
        var input = List.of("...#......",
                ".......#..",
                "#.........",
                "..........",
                "......#...",
                ".#........",
                ".........#",
                "..........",
                ".......#..",
                "#...#.....");
        var day = new Day11();
        var newUniverse = day.expandUniverse(input);
        newUniverse.forEach(System.out::println);
        System.out.println("????? " + newUniverse.size());
        var count1 = day.countGalaxiesVertically(newUniverse);
        assertEquals(List.of(2, 1, 0, 0, 1, 1, 0, 0, 1, 2, 0, 0, 1), count1);
        var count2 = day.countGalaxiesHorizontally(newUniverse);
        assertEquals(List.of(1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 2), count2);
    }

    @ParameterizedTest
    @MethodSource("expandingUniverseCaseProvider")
    void expandingUniverse(int timeLarger, int expectedSum) {
        var input = List.of("...#......",
                ".......#..",
                "#.........",
                "..........",
                "......#...",
                ".#........",
                ".........#",
                "..........",
                ".......#..",
                "#...#.....");
        var day = new Day11();
        var sumOfShortestPath = day.expandingUniverse(input, timeLarger);
        assertEquals(expectedSum, sumOfShortestPath);
    }
}