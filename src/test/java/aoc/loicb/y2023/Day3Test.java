package aoc.loicb.y2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {
    private final char[][] schematic = new char[][]{"467..114..".toCharArray(),
            "...*......".toCharArray(),
            "..35..633.".toCharArray(),
            "......#...".toCharArray(),
            "617*......".toCharArray(),
            ".....+.58.".toCharArray(),
            "..592.....".toCharArray(),
            "......755.".toCharArray(),
            "...$.*....".toCharArray(),
            ".664.598..".toCharArray()};

    private static Stream<Arguments> isPossibleCaseProvider() {
        return Stream.of(
                Arguments.of(0, 0, true),
                Arguments.of(0, 1, true),
                Arguments.of(0, 2, true),
                Arguments.of(0, 5, false),
                Arguments.of(0, 6, false),
                Arguments.of(0, 7, false),
                Arguments.of(9, 1, true),
                Arguments.of(9, 2, true),
                Arguments.of(9, 3, true),
                Arguments.of(9, 5, true),
                Arguments.of(9, 6, true),
                Arguments.of(9, 7, true)
        );
    }

    private static Stream<Arguments> getAdjacentNumbersCaseProvider() {
        return Stream.of(
                Arguments.of(1, 3, List.of(467, 35)),
                Arguments.of(3, 6, List.of(633)),
                Arguments.of(4, 3, List.of(617)),
                Arguments.of(5, 5, List.of(592)),
                Arguments.of(8, 3, List.of(664)),
                Arguments.of(8, 5, List.of(755, 598))
        );
    }

    @Test
    void partOne() {
        var day3 = new Day3();
        var sumOfParts = day3.partOne(schematic);
        assertEquals(4361, sumOfParts);
    }

    @ParameterizedTest
    @MethodSource("isPossibleCaseProvider")
    void isPossible(int x, int y, boolean expectedValidation) {
        var day3 = new Day3();
        var possible = day3.validatePart(schematic, x, y);
        assertEquals(expectedValidation, possible);
    }

    @ParameterizedTest
    @MethodSource("getAdjacentNumbersCaseProvider")
    void getAdjacentNumbers(int x, int y, List<Integer> expectedAdjacentNumbers) {
        var day3 = new Day3();
        var adjacentNumbers = day3.getAdjacentNumbers(schematic, x, y);
        assertEquals(expectedAdjacentNumbers, adjacentNumbers);
    }

    @Test
    void partTwo() {
        var day3 = new Day3();
        var sumOfParts = day3.partTwo(schematic);
        assertEquals(467835, sumOfParts);
    }
}