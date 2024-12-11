package aoc.loicb.y2017;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {


    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(new int[]{1, 1, 2, 2}, 3),
                Arguments.of(new int[]{1, 1, 1, 1}, 4),
                Arguments.of(new int[]{1, 2, 3, 4}, 0),
                Arguments.of(new int[]{9, 1, 2, 1, 2, 1, 2, 9}, 9)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 1, 2}, 6),
                Arguments.of(new int[]{1, 2, 2, 1, 1}, 0),
                Arguments.of(new int[]{1, 2, 3, 4, 2, 5}, 4),
                Arguments.of(new int[]{1, 2, 3, 1, 2, 3}, 12),
                Arguments.of(new int[]{1, 2, 1, 3, 1, 4, 1, 5}, 4)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(int[] input, int expectedSum) {
        var day = new Day1();
        int sum = day.partOne(input);
        assertEquals(expectedSum, sum);

    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(int[] input, int expectedSum) {
        var day = new Day1();
        int sum = day.partTwo(input);
        assertEquals(expectedSum, sum);
    }
}