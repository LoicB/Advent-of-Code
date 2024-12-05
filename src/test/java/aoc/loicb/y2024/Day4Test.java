package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {

    private final List<String> input = List.of(
            "MMMSXXMASM",
            "MSAMXMSMSA",
            "AMXSXMAAMM",
            "MSAMASMSMX",
            "XMASAMXAMM",
            "XXAMMXXAMA",
            "SMSMSASXSS",
            "SAXAMASAAA",
            "MAMMMXMMMM",
            "MXMXAXMASX"
    );

    private static Stream<Arguments> countXmasCaseProvider() {
        return Stream.of(
                Arguments.of(0, 0, 0),
                Arguments.of(0, 4, 1),
                Arguments.of(8, 5, 0),
                Arguments.of(9, 9, 2)
        );
    }

    private static Stream<Arguments> isValidCrossCaseProvider() {
        return Stream.of(
                Arguments.of(0, 0, false),
                Arguments.of(5, 7, false),
                Arguments.of(7, 7, true),
                Arguments.of(7, 8, false),
                Arguments.of(7, 9, false),
                Arguments.of(9, 7, false),
                Arguments.of(9, 9, false)
        );
    }

    @Test
    void partOne() {
        var day = new Day4();
        var xmasOccurrences = day.partOne(input);
        assertEquals(18, xmasOccurrences);
    }

    @ParameterizedTest
    @MethodSource("countXmasCaseProvider")
    void countXmas(int x, int y, int expectedValidity) {
        var day = new Day4();
        var valid = day.countXmas(input, x, y);
        assertEquals(expectedValidity, valid);
    }

    @Test
    void partTwo() {
        var day = new Day4();
        var xmasOccurrences = day.partTwo(input);
        assertEquals(9, xmasOccurrences);
    }

    @ParameterizedTest
    @MethodSource("isValidCrossCaseProvider")
    void isValidCross(int x, int y, boolean expectedValidity) {
        var day = new Day4();
        var valid = day.isValidCross(input, x, y);
        assertEquals(expectedValidity, valid);
    }
}