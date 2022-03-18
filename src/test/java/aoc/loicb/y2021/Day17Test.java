package aoc.loicb.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17Test {

    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(new Area(20, 30, -10, -5), 45)
        );
    }

    private static Stream<Arguments> isInsideCaseProvider() {
        return Stream.of(Arguments.of(new Position(28, -7), new Area(20, 30, -10, -5), true)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(new Area(20, 30, -10, -5), 112)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(Area area, int expectedHighestY) {
        Day17 day = new Day17();
        int highestY = day.partOne(area);
        assertEquals(expectedHighestY, highestY);
    }

    @ParameterizedTest
    @MethodSource("isInsideCaseProvider")
    void isInside(Position position, Area area, boolean expectedResult) {
        Day17 day = new Day17();
        boolean inside = day.isInside(position, area);
        assertEquals(expectedResult, inside);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(Area area, int expectedNumber) {
        Day17 day = new Day17();
        int velocityCount = day.partTwo(area);
        assertEquals(expectedNumber, velocityCount);
    }
}