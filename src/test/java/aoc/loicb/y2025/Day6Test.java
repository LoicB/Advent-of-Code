package aoc.loicb.y2025;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day6Test {

    private final String[] input = new String[]{
            "123 328  51 64 ",
            " 45 64  387 23 ",
            "  6 98  215 314",
            "*   +   *   +  "
    };

    private static Stream<Arguments> evaluateNumericLineCaseProvider() {
        return Stream.of(
                Arguments.of(0, List.of(123L, 328L, 51L, 64L)),
                Arguments.of(1, List.of(45L, 64L, 387L, 23L)),
                Arguments.of(2, List.of(6L, 98L, 215L, 314L))
        );
    }

    private static Stream<Arguments> solveProblemCaseProvider() {
        return Stream.of(
                Arguments.of(123, 45, 6, '*', 33210),
                Arguments.of(328, 64, 98, '+', 490),
                Arguments.of(51, 387, 215, '*', 4243455),
                Arguments.of(64, 23, 314, '+', 401)
        );
    }

    private static Stream<Arguments> extractNumberCaseProvider() {
        return Stream.of(
                Arguments.of(0, 1),
                Arguments.of(1, 24),
                Arguments.of(2, 356),
                Arguments.of(14, 4)
        );
    }

    @Test
    void partOne() {
        var day = new Day6();
        var freshIDs = day.partOne(input);
        assertEquals(4277556L, freshIDs);
    }

    @ParameterizedTest
    @MethodSource("evaluateNumericLineCaseProvider")
    void evaluateNumericLine(int index, List<Long> expectedNumerics) {
        var day = new Day6();
        var numbers = day.evaluateNumericLine(input[index]);
        assertEquals(expectedNumerics.size(), numbers.size());
        assertTrue(expectedNumerics.containsAll(numbers));
    }

    @Test
    void evaluateSymbols() {
        var day = new Day6();
        var symbols = day.evaluateSymbols(input[3]);
        var expected = List.of('*', '+', '*', '+');
        assertEquals(expected.size(), symbols.size());
        assertTrue(expected.containsAll(symbols));
    }

    @ParameterizedTest
    @MethodSource("solveProblemCaseProvider")
    void solveProblem(int number1, int number2, int number3, char symbol, int expectedSolution) {
        var day = new Day6();
        var solution = day.solveProblem(symbol, number1, number2, number3);
        assertEquals(expectedSolution, solution);
    }

    @Test
    void partTwo() {
        var day = new Day6();
        var freshIDs = day.partTwo(input);
        assertEquals(3263827L, freshIDs);
    }

    @ParameterizedTest
    @MethodSource("extractNumberCaseProvider")
    void extractNumber(int index, int expectedNumber) {
        var day = new Day6();
        var number = day.extractNumber(input, index);
        assertEquals(expectedNumber, number);
    }

    @Test
    void extractSymbol() {
        var day = new Day6();
        assertEquals(Optional.of('*'), day.extractSymbol(input, 0));
        assertEquals(Optional.empty(), day.extractSymbol(input, 1));
        assertEquals(Optional.empty(), day.extractSymbol(input, 2));
        assertEquals(Optional.empty(), day.extractSymbol(input, 3));
        assertEquals(Optional.of('+'), day.extractSymbol(input, 4));
    }
}