package aoc.loicb.y2015;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {
    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(">", 2),
                Arguments.of("^>v<", 4),
                Arguments.of("^v^v^v^v^v", 2)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of("^v", 3),
                Arguments.of("^>v<", 3),
                Arguments.of("^v^v^v^v^v", 11)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(String input, int expectedNumberOfHouses) {
        Day3 day = new Day3();
        int houses = day.partOne(input);
        assertEquals(expectedNumberOfHouses, houses);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(String input, int expectedNumberOfHouses) {
        Day3 day = new Day3();
        int squareFeet = day.partTwo(input);
        assertEquals(expectedNumberOfHouses, squareFeet);
    }
}