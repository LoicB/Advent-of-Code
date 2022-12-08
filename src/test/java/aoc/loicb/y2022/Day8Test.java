package aoc.loicb.y2022;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {

    private static Stream<Arguments> calculateScoreCaseProvider() {
        return Stream.of(
                Arguments.of(1, 2, 4),
                Arguments.of(3, 2, 8)
        );
    }

    @Test
    void partOne() {
        int[][] input = new int[][]
                {{3, 0, 3, 7, 3},
                        {2, 5, 5, 1, 2},
                        {6, 5, 3, 3, 2},
                        {3, 3, 5, 4, 9},
                        {3, 5, 3, 9, 0}};
        var day = new Day8();
        int sumOfSizes = day.partOne(input);
        assertEquals(21, sumOfSizes);

    }

    @Test
    void partTwo() {
        int[][] input = new int[][]
                {{3, 0, 3, 7, 3},
                        {2, 5, 5, 1, 2},
                        {6, 5, 3, 3, 2},
                        {3, 3, 5, 4, 9},
                        {3, 5, 3, 9, 0}};
        var day = new Day8();
        int sumOfSizes = day.partTwo(input);
        assertEquals(8, sumOfSizes);
    }

    @ParameterizedTest
    @MethodSource("calculateScoreCaseProvider")
    void calculateScore(int x, int y, int expectedScore) {
        int[][] input = new int[][]
                {{3, 0, 3, 7, 3},
                        {2, 5, 5, 1, 2},
                        {6, 5, 3, 3, 2},
                        {3, 3, 5, 4, 9},
                        {3, 5, 3, 9, 0}};
        var day = new Day8();
        int score = day.calculateScore(input, x, y);
        assertEquals(expectedScore, score);
    }
}