package aoc.loicb.y2025;

import aoc.loicb.y2025.tools.Range;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5Test {
    private final Database input = new Database(
            List.of(new Range(3, 5),
                    new Range(10, 14),
                    new Range(16, 20),
                    new Range(12, 18)),
            List.of(1L,
                    5L,
                    8L,
                    11L,
                    17L,
                    32L)
    );

    private static Stream<Arguments> isInRangesCaseProvider() {
        return Stream.of(
                Arguments.of(0, false),
                Arguments.of(1, true),
                Arguments.of(2, false),
                Arguments.of(3, true),
                Arguments.of(4, true),
                Arguments.of(5, false)
        );
    }

    @Test
    void partOne() {
        var day = new Day5();
        var freshIDs = day.partOne(input);
        assertEquals(3L, freshIDs);
    }

    @ParameterizedTest
    @MethodSource("isInRangesCaseProvider")
    void isInRanges(int idIndex, boolean expectedJIsInRange) {
        var day = new Day5();
        var isInRanges = day.isInRanges(input.IDs().get(idIndex), input.freshIDs());
        assertEquals(expectedJIsInRange, isInRanges);
    }

    @Test
    void partTwo() {
        var day = new Day5();
        var freshIDs = day.partTwo(input);
        assertEquals(14L, freshIDs);
    }
}