package aoc.loicb.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9Test {

    private final static int[][] SAMPLE = new int[][]{
            {2, 1, 9, 9, 9, 4, 3, 2, 1, 0},
            {3, 9, 8, 7, 8, 9, 4, 9, 2, 1},
            {9, 8, 5, 6, 7, 8, 9, 8, 9, 2},
            {8, 7, 6, 7, 8, 9, 6, 7, 8, 9},
            {9, 8, 9, 9, 9, 6, 5, 6, 7, 8}
    };

    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE, 15)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE, 1134)
        );
    }

    private static Stream<Arguments> calculateBasinScoreCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE, 0, 1, 3),
                Arguments.of(SAMPLE, 0, 9, 9),
                Arguments.of(SAMPLE, 2, 2, 14),
                Arguments.of(SAMPLE, 4, 6, 9)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(int[][] heightmap, int expectedRisk) {
        Day9 day = new Day9();
        int risk = day.partOne(heightmap);
        assertEquals(expectedRisk, risk);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(int[][] heightmap, int expectedRisk) {
        Day9 day = new Day9();
        int risk = day.partTwo(heightmap);
        assertEquals(expectedRisk, risk);
    }

    @ParameterizedTest
    @MethodSource("calculateBasinScoreCaseProvider")
    void calculateBasinScore(int[][] heightmap, int x, int y, int expectedScore) {
        Day9 day = new Day9();
        int score = day.calculateBasinScore(heightmap, x, y);
        assertEquals(expectedScore, score);
    }
}