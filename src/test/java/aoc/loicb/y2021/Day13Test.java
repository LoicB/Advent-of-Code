package aoc.loicb.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Test {
    private final static TransparentPaper SAMPLE = new TransparentPaper(
            List.of(
                    new Point(6, 10),
                    new Point(0, 14),
                    new Point(9, 10),
                    new Point(0, 3),
                    new Point(10, 4),
                    new Point(4, 11),
                    new Point(6, 0),
                    new Point(6, 12),
                    new Point(4, 1),
                    new Point(0, 13),
                    new Point(10, 12),
                    new Point(3, 4),
                    new Point(3, 0),
                    new Point(8, 4),
                    new Point(1, 10),
                    new Point(2, 14),
                    new Point(8, 10),
                    new Point(9, 0)),
            List.of(new FoldingInstruction('y', 7),
                    new FoldingInstruction('x', 5))
    );

    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE, 17)
        );
    }

    private static Stream<Arguments> generateGridCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE, 11, 15)
        );
    }

    private static Stream<Arguments> foldHorizontallyCaseProvider() {
        return Stream.of(
                Arguments.of(new boolean[][]
                                {{true, false, false},
                                        {false, true, false},
                                        {false, false, false},
                                        {false, true, false},
                                        {false, false, true},
                                        {true, false, false}
                                }, 3,
                        new boolean[][]
                                {{true, false, false},
                                        {false, true, true},
                                        {false, true, false}}
                )
        );
    }

    private static Stream<Arguments> foldVerticallyCaseProvider() {
        return Stream.of(
                Arguments.of(new boolean[][]
                                {{true, false, false, true},
                                        {false, true, false, false},
                                        {false, false, true, true}
                                }, 2,
                        new boolean[][]
                                {{true, false},
                                        {false, true},
                                        {true, true}}
                )
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE, 16)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(TransparentPaper transparentPaper, int expectedNumberOfDots) {
        Day13 day = new Day13();
        int numberOfDots = day.partOne(transparentPaper);
        assertEquals(expectedNumberOfDots, numberOfDots);
    }

    @ParameterizedTest
    @MethodSource("generateGridCaseProvider")
    void generateGrid(TransparentPaper transparentPaper, int width, int height) {
        Day13 day = new Day13();
        boolean[][] grid = day.generateGrid(transparentPaper.dots());
        assertEquals(height, grid.length);
        assertEquals(width, grid[0].length);
    }

    @ParameterizedTest
    @MethodSource("foldHorizontallyCaseProvider")
    void foldHorizontally(boolean[][] grid, int line, boolean[][] expectedFoldedGrid) {
        Day13 day = new Day13();
        boolean[][] foldedGrid = day.foldHorizontally(grid, line);
        assertArrayEquals(expectedFoldedGrid, foldedGrid);
    }

    @ParameterizedTest
    @MethodSource("foldVerticallyCaseProvider")
    void foldVertically(boolean[][] grid, int line, boolean[][] expectedFoldedGrid) {
        Day13 day = new Day13();
        boolean[][] foldedGrid = day.foldVertically(grid, line);
        assertArrayEquals(expectedFoldedGrid, foldedGrid);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(TransparentPaper transparentPaper, int expectedNumberOfDots) {
        Day13 day = new Day13();
        int numberOfDots = day.partTwo(transparentPaper);
        assertEquals(expectedNumberOfDots, numberOfDots);
    }
}