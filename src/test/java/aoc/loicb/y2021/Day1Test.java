package aoc.loicb.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {
    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(new Integer[]{
                        199,
                        200,
                        208,
                        210,
                        200,
                        207,
                        240,
                        269,
                        260,
                        263
                }, 7),
                Arguments.of(new Integer[]{
                        201,
                        200
                }, 0),
                Arguments.of(new Integer[]{
                        200,
                        201
                }, 1),
                Arguments.of(new Integer[]{
                        200,
                        200
                }, 0)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(new Integer[]{
                        199,
                        200,
                        208,
                        210,
                        200,
                        207,
                        240,
                        269,
                        260,
                        263
                }, 5),
                Arguments.of(new Integer[]{
                        199,
                        200,
                        208,
                        210,
                }, 1),
                Arguments.of(new Integer[]{
                        200,
                        208,
                        210,
                        200,
                }, 0),
                Arguments.of(new Integer[]{
                        208,
                        210,
                        200,
                        207
                }, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(Integer[] input, int expectedFloor) {
        Day1 day = new Day1();
        int numberOfIncreases = day.partOne(input);
        assertEquals(expectedFloor, numberOfIncreases);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(Integer[] input, int expectedFloor) {
        Day1 day = new Day1();
        int numberOfIncreases = day.partTwo(input);
        assertEquals(expectedFloor, numberOfIncreases);
    }
}