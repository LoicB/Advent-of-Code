package aoc.loicb.y2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9Test {

    private static Stream<Arguments> findNextRightValueCaseProvider() {
        return Stream.of(
                Arguments.of(List.of(0, 3, 6, 9, 12, 15), 18),
                Arguments.of(List.of(1, 3, 6, 10, 15, 21), 28),
                Arguments.of(List.of(10, 13, 16, 21, 30, 45), 68)
        );
    }

    private static Stream<Arguments> findNextLeftValueCaseProvider() {
        return Stream.of(
                Arguments.of(List.of(0, 3, 6, 9, 12, 15), -3),
                Arguments.of(List.of(1, 3, 6, 10, 15, 21), 0),
                Arguments.of(List.of(10, 13, 16, 21, 30, 45), 5)
        );
    }

    @Test
    void partOne() {
        var input = List.of(List.of(0, 3, 6, 9, 12, 15),
                List.of(1, 3, 6, 10, 15, 21),
                List.of(10, 13, 16, 21, 30, 45));
        var day = new Day9();
        var histories = day.partOne(input);
        assertEquals(114, histories);
    }

    @ParameterizedTest
    @MethodSource("findNextRightValueCaseProvider")
    void findNextRightValue(List<Integer> history, int expectedNextValue) {
        var day = new Day9();
        int nextValue = day.findNextRightValue(history);
        assertEquals(expectedNextValue, nextValue);
    }

    @Test
    void partTwo() {
        var input = List.of(List.of(0, 3, 6, 9, 12, 15),
                List.of(1, 3, 6, 10, 15, 21),
                List.of(10, 13, 16, 21, 30, 45));
        var day = new Day9();
        var histories = day.partTwo(input);
        assertEquals(2, histories);
    }

    @ParameterizedTest
    @MethodSource("findNextLeftValueCaseProvider")
    void findNextLeftValue(List<Integer> history, int expectedNextValue) {
        var day = new Day9();
        int nextValue = day.findNextLeftValue(history);
        assertEquals(expectedNextValue, nextValue);
    }
}