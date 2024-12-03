package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {
    private final List<List<Integer>> input = List.of(List.of(7, 6, 4, 2, 1),
            List.of(1, 2, 7, 8, 9),
            List.of(9, 7, 6, 2, 1),
            List.of(1, 3, 2, 4, 5),
            List.of(8, 6, 4, 4, 1),
            List.of(1, 3, 6, 7, 9));

    private static Stream<Arguments> checkReportSafetyCaseProvider() {
        return Stream.of(
                Arguments.of(List.of(7, 6, 4, 2, 1), true),
                Arguments.of(List.of(1, 2, 7, 8, 9), false),
                Arguments.of(List.of(9, 7, 6, 2, 1), false),
                Arguments.of(List.of(1, 3, 2, 4, 5), false),
                Arguments.of(List.of(8, 6, 4, 4, 1), false),
                Arguments.of(List.of(1, 3, 6, 7, 9), true)
        );
    }

    private static Stream<Arguments> checkReportSafetyDampenerCaseProvider() {
        return Stream.of(
                Arguments.of(List.of(7, 6, 4, 2, 1), true),
                Arguments.of(List.of(1, 2, 7, 8, 9), false),
                Arguments.of(List.of(9, 7, 6, 2, 1), false),
                Arguments.of(List.of(1, 3, 2, 4, 5), true),
                Arguments.of(List.of(8, 6, 4, 4, 1), true),
                Arguments.of(List.of(1, 3, 6, 7, 9), true)
        );
    }

    @Test
    void partOne() {
        var day = new Day2();
        var safeReports = day.partOne(input);
        assertEquals(2, safeReports);
    }

    @ParameterizedTest
    @MethodSource("checkReportSafetyCaseProvider")
    void checkReportSafety(List<Integer> levels, boolean expectedSafety) {
        var day = new Day2();
        var safeReports = day.checkReportSafety(levels);
        assertEquals(expectedSafety, safeReports);
    }

    @Test
    void partTwo() {
        var day = new Day2();
        var safeReports = day.partTwo(input);
        assertEquals(4, safeReports);
    }

    @ParameterizedTest
    @MethodSource("checkReportSafetyDampenerCaseProvider")
    void checkReportSafetyDampener(List<Integer> levels, boolean expectedSafety) {
        var day = new Day2();
        var safeReports = day.checkReportSafetyDampener(levels);
        assertEquals(expectedSafety, safeReports);
    }
}