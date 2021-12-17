package aoc.loicb.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15Test {

    private final static int[][] SAMPLE1 = new int[][]{
            {1, 1, 6, 3, 7, 5, 1, 7, 4, 2},
            {1, 3, 8, 1, 3, 7, 3, 6, 7, 2},
            {2, 1, 3, 6, 5, 1, 1, 3, 2, 8},
            {3, 6, 9, 4, 9, 3, 1, 5, 6, 9},
            {7, 4, 6, 3, 4, 1, 7, 1, 1, 1},
            {1, 3, 1, 9, 1, 2, 8, 1, 3, 7},
            {1, 3, 5, 9, 9, 1, 2, 4, 2, 1},
            {3, 1, 2, 5, 4, 2, 1, 6, 3, 9},
            {1, 2, 9, 3, 1, 3, 8, 5, 2, 1},
            {2, 3, 1, 1, 9, 4, 4, 5, 8, 1}
    };

    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE1, 40)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE1, 315)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(int[][] map, int expectedRisk) {
        Day15 day = new Day15();
        int risk = day.partOne(map);
        assertEquals(expectedRisk, risk);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(int[][] map, int expectedRisk) {
        Day15 day = new Day15();
        int risk = day.partTwo(map);
        assertEquals(expectedRisk, risk);
    }
}