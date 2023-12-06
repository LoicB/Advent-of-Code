package aoc.loicb.y2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test {

    private static Stream<Arguments> numberOfWaysBeatRecordCaseProvider() {
        return Stream.of(
                Arguments.of(7, 9, 4),
                Arguments.of(15, 40, 8),
                Arguments.of(30, 200, 9),
                Arguments.of(71530, 940200, 71503)
        );
    }

    @Test
    void partOne() {
        var input = "Time:      7  15   30\n" +
                "Distance:  9  40  200";
        var day = new Day6();
        var numberOfWays = day.partOne(input);
        assertEquals(288, numberOfWays);
    }

    @ParameterizedTest
    @MethodSource("numberOfWaysBeatRecordCaseProvider")
    void numberOfWaysBeatRecord(int time, int distance, int expectedNumbers) {
        var day = new Day6();
        var numberOfWays = day.numberOfWaysBeatRecord(time, distance);
        assertEquals(expectedNumbers, numberOfWays);
    }

    @Test
    void partTwo() {
        var input = "Time:      7  15   30\n" +
                "Distance:  9  40  200";
        var day = new Day6();
        var numberOfWays = day.partTwo(input);
        assertEquals(71503, numberOfWays);
    }
}