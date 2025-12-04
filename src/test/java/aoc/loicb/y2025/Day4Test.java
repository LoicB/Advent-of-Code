package aoc.loicb.y2025;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {
    private final List<String> input = List.of(
            "..@@.@@@@.",
            "@@@.@.@.@@",
            "@@@@@.@.@@",
            "@.@@@@..@.",
            "@@.@@@@.@@",
            ".@@@@@@@.@",
            ".@.@.@.@@@",
            "@.@@@.@@@@",
            ".@@@@@@@@.",
            "@.@.@@@.@."
    );

    private static Stream<Arguments> isRollCaseProvider() {
        return Stream.of(
                Arguments.of(0, 0, false),
                Arguments.of(1, 0, false),
                Arguments.of(2, 0, true),
                Arguments.of(0, 1, true)
        );
    }

    private static Stream<Arguments> isAccessibleByForkliftCaseProvider() {
        return Stream.of(
                Arguments.of(0, 0, false),
                Arguments.of(1, 0, false),
                Arguments.of(2, 0, true),
                Arguments.of(0, 1, true),
                Arguments.of(0, 2, false),
                Arguments.of(1, 1, false)
        );
    }

    @Test
    void partOne() {
        var day = new Day4();
        var accessibleRolls = day.partOne(input);
        assertEquals(13, accessibleRolls);
    }

    @ParameterizedTest
    @MethodSource("isRollCaseProvider")
    void isRoll(int x, int y, boolean expectedRoll) {
        var day = new Day4();
        var accessibleRolls = day.isRoll(input, x, y);
        assertEquals(expectedRoll, accessibleRolls);
    }

    @ParameterizedTest
    @MethodSource("isAccessibleByForkliftCaseProvider")
    void isAccessibleByForklift(int x, int y, boolean expectedRoll) {
        var day = new Day4();
        var accessibleRolls = day.isAccessibleByForklift(input, x, y);
        assertEquals(expectedRoll, accessibleRolls);
    }


    @Test
    void partTwo() {
        var day = new Day4();
        var accessibleRolls = day.partTwo(input);
        assertEquals(43, accessibleRolls);
    }
}