package aoc.loicb.y2015;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {
    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(new Box[]{new Box(2, 3, 4)}, 58),
                Arguments.of(new Box[]{new Box(1, 1, 10)}, 43)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(new Box[]{new Box(2, 3, 4)}, 34),
                Arguments.of(new Box[]{new Box(1, 1, 10)}, 14)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(Box[] input, int expectedSquareFeet) {
        Day2 day = new Day2();
        int squareFeet = day.partOne(input);
        assertEquals(expectedSquareFeet, squareFeet);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(Box[] input, int expectedSquareFeet) {
        Day2 day = new Day2();
        int squareFeet = day.partTwo(input);
        assertEquals(expectedSquareFeet, squareFeet);
    }
}