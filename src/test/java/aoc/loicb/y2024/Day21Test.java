package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day21Test {

    private final List<String> input = List.of(
            "029A",
            "980A",
            "179A",
            "456A",
            "379A"
    );

    private static Stream<Arguments> createPathCaseProvider() {
        return Stream.of(
                Arguments.of(new Position(2, 3), new Position(1, 3), "<"),
                Arguments.of(new Position(1, 3), new Position(1, 2), "^"),
                Arguments.of(new Position(1, 2), new Position(2, 0), ">^^"),
                Arguments.of(new Position(2, 0), new Position(2, 3), "vvv")
        );
    }

    private static Stream<Arguments> createPath2CaseProvider() {
        return Stream.of(
                Arguments.of(new Position(2, 0), new Position(1, 1), "<v")
        );
    }

    private static Stream<Arguments> calculateCodeComplexityCaseProvider() {
        return Stream.of(
                Arguments.of("029A", 68 * 29),
                Arguments.of("980A", 60 * 980),
                Arguments.of("179A", 68 * 179),
                Arguments.of("456A", 64 * 456),
                Arguments.of("379A", 64 * 379)
        );
    }

    @Test
    void partOne() {
        var day = new Day21();
        var complexity = day.partOne(input);
        assertEquals(126384, complexity);
    }

    @ParameterizedTest
    @MethodSource("createPathCaseProvider")
    void createPath(Position from, Position to, String expectedPath) {
        var day = new Day21();
        var path = day.createPath(from, to, Day21.Keypad.NumericKeypad());
        assertEquals(expectedPath, path);
    }

    //calculatePathComplexity

    @ParameterizedTest
    @MethodSource("createPath2CaseProvider")
    void createPath2(Position from, Position to, String expectedPath) {
        var day = new Day21();
        var path = day.createPath(from, to, Day21.Keypad.DirectionalKeypad());
        assertEquals(expectedPath, path);
    }

    @ParameterizedTest
    @MethodSource("calculateCodeComplexityCaseProvider")
    void calculateCodeComplexity(String code, int expectedComplexity) {
        var day = new Day21();
        var complexity = day.calculateCodeComplexity(code);
        assertEquals(expectedComplexity, complexity);
    }

    @Test
    void partTwo() {
        var day = new Day21();
        var complexity = day.partTwo(input);
        assertEquals(126384, complexity);
    }
}