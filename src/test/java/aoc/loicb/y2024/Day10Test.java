package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {


    private final static int[][] INPUT_1 = new int[][]
            {{0, 1, 2, 3},
                    {1, 2, 3, 4},
                    {8, 7, 6, 5},
                    {9, 8, 7, 6}};


    private final static int[][] INPUT_2 = new int[][]
            {{8, 9, 0, 1, 0, 1, 2, 3},
                    {7, 8, 1, 2, 1, 8, 7, 4},
                    {8, 7, 4, 3, 0, 9, 6, 5},
                    {9, 6, 5, 4, 9, 8, 7, 4},
                    {4, 5, 6, 7, 8, 9, 0, 3},
                    {3, 2, 0, 1, 9, 0, 1, 2},
                    {0, 1, 3, 2, 9, 8, 0, 1},
                    {1, 0, 4, 5, 6, 7, 3, 2}};


    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(INPUT_1, 1),
                Arguments.of(INPUT_2, 36)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(int[][] map, int expectedScores) {
        var day = new Day10();
        int trailheadsScores = day.partOne(map);
        assertEquals(expectedScores, trailheadsScores);
    }

    @Test
    void partTwo() {
        var day = new Day10();
        int trailheadsScores = day.partTwo(INPUT_2);
        assertEquals(81, trailheadsScores);
    }
}