package aoc.loicb.y2022;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {

    private static Stream<Arguments> areElvesOverlappingCaseProvider() {
        return Stream.of(
                Arguments.of("2-4,6-8", false),
                Arguments.of("2-3,4-5", false),
                Arguments.of("5-7,7-9", false),
                Arguments.of("2-8,3-7", true),
                Arguments.of("6-6,4-6", true),
                Arguments.of("2-6,4-8", false)
        );
    }

    private static Stream<Arguments> areElvesPartiallyOverlappingCaseProvider() {
        return Stream.of(
                Arguments.of("2-4,6-8", false),
                Arguments.of("2-3,4-5", false),
                Arguments.of("5-7,7-9", true),
                Arguments.of("2-8,3-7", true),
                Arguments.of("6-6,4-6", true),
                Arguments.of("2-6,4-8", true)
        );
    }

    @Test
    void partOne() {
        List<String> input = List.of(
                "2-4,6-8",
                "2-3,4-5",
                "5-7,7-9",
                "2-8,3-7",
                "6-6,4-6",
                "2-6,4-8"
        );
        var day = new Day4();
        var overlaps = day.partOne(input);
        assertEquals(2, overlaps);
    }

    @ParameterizedTest
    @MethodSource("areElvesOverlappingCaseProvider")
    void areElvesOverlapping(String elvesIDs, boolean expectedOverlap) {
        var day = new Day4();
        boolean overlapping = day.areElvesOverlapping(elvesIDs);
        assertEquals(expectedOverlap, overlapping);
    }

    @Test
    void partTwo() {
        List<String> input = List.of(
                "2-4,6-8",
                "2-3,4-5",
                "5-7,7-9",
                "2-8,3-7",
                "6-6,4-6",
                "2-6,4-8"
        );
        var day = new Day4();
        var overlaps = day.partTwo(input);
        assertEquals(4, overlaps);
    }

    @ParameterizedTest
    @MethodSource("areElvesPartiallyOverlappingCaseProvider")
    void areElvesPartiallyOverlapping(String elvesIDs, boolean expectedOverlap) {
        var day = new Day4();
        boolean overlapping = day.areElvesPartiallyOverlapping(elvesIDs);
        assertEquals(expectedOverlap, overlapping);
    }
}