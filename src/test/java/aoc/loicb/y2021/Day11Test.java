package aoc.loicb.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {

    private final static int[][] SAMPLE1 = new int[][]{
            {5, 4, 8, 3, 1, 4, 3, 2, 2, 3},
            {2, 7, 4, 5, 8, 5, 4, 7, 1, 1},
            {5, 2, 6, 4, 5, 5, 6, 1, 7, 3},
            {6, 1, 4, 1, 3, 3, 6, 1, 4, 6},
            {6, 3, 5, 7, 3, 8, 5, 4, 7, 8},
            {4, 1, 6, 7, 5, 2, 4, 6, 4, 5},
            {2, 1, 7, 6, 8, 4, 1, 7, 2, 1},
            {6, 8, 8, 2, 8, 8, 1, 1, 3, 4},
            {4, 8, 4, 6, 8, 4, 8, 5, 5, 4},
            {5, 2, 8, 3, 7, 5, 1, 5, 2, 6}
    };

    private final static int[][] SAMPLE2 = new int[][]{
            {1, 1, 1, 1, 1},
            {1, 9, 9, 9, 1},
            {1, 9, 1, 9, 1},
            {1, 9, 9, 9, 1},
            {1, 1, 1, 1, 1}
    };

    private final static int[][] SAMPLE3 = new int[][]{
            {3, 4, 5, 4, 3},
            {4, 0, 0, 0, 4},
            {5, 0, 0, 0, 5},
            {4, 0, 0, 0, 4},
            {3, 4, 5, 4, 3}
    };


    private final static int[][] SAMPLE4 = new int[][]{
            {4, 5, 6, 5, 4},
            {5, 1, 1, 1, 5},
            {6, 1, 1, 1, 6},
            {5, 1, 1, 1, 5},
            {4, 5, 6, 5, 4}
    };

    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE1, 1656)
        );
    }

    private static Stream<Arguments> nextStepCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE2, SAMPLE3, 9),
                Arguments.of(SAMPLE3, SAMPLE4, 0)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE1, 195)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(int[][] energyLevels, int expectedFlashes) {
        Day11 day = new Day11();
        int flashes = day.partOne(energyLevels);
        assertEquals(expectedFlashes, flashes);
    }

    @ParameterizedTest
    @MethodSource("nextStepCaseProvider")
    void nextStep(int[][] energyLevels, int[][] expectedLevels, int expectedCount) {
        Day11 day = new Day11();
        int flashesCount = day.nextStep(energyLevels);
        System.out.println(Arrays.deepToString(energyLevels));
        assertArrayEquals(expectedLevels, energyLevels);
        assertEquals(expectedCount, flashesCount);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(int[][] energyLevels, int expectedFlashes) {
        Day11 day = new Day11();
        int flashes = day.partTwo(energyLevels);
        assertEquals(expectedFlashes, flashes);
    }
}