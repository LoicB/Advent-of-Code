package aoc.loicb.y2021;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5Test {
    private final static List<Line> SAMPLE_LINES = List.of(
            new Line(new Point(0, 9), new Point(5, 9)),
            new Line(new Point(8, 0), new Point(0, 8)),
            new Line(new Point(9, 4), new Point(3, 4)),
            new Line(new Point(2, 2), new Point(2, 1)),
            new Line(new Point(7, 0), new Point(7, 4)),
            new Line(new Point(6, 4), new Point(2, 0)),
            new Line(new Point(0, 9), new Point(2, 9)),
            new Line(new Point(3, 4), new Point(1, 4)),
            new Line(new Point(0, 0), new Point(8, 8)),
            new Line(new Point(5, 5), new Point(8, 2)));

    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE_LINES, 5)
        );
    }

    private static Stream<Arguments> coveredPointsCaseProvider() {
        return Stream.of(
                Arguments.of(new Line(new Point(1, 1), new Point(1, 3)),
                        List.of(new Point(1, 1), new Point(1, 2), new Point(1, 3))),
                Arguments.of(new Line(new Point(9, 7), new Point(7, 7)),
                        List.of(new Point(7, 7), new Point(8, 7), new Point(9, 7)))
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE_LINES, 12)
        );
    }

    private static Stream<Arguments> coveredPointsPartTwoCaseProvider() {
        return Stream.of(
                Arguments.of(new Line(new Point(1, 1), new Point(1, 3)),
                        List.of(new Point(1, 1), new Point(1, 2), new Point(1, 3))),
                Arguments.of(new Line(new Point(9, 7), new Point(7, 7)),
                        List.of(new Point(7, 7), new Point(8, 7), new Point(9, 7))),
                Arguments.of(new Line(new Point(0, 0), new Point(2, 2)),
                        List.of(new Point(0, 0), new Point(1, 1), new Point(2, 2))),
                Arguments.of(new Line(new Point(2, 0), new Point(0, 2)),
                        List.of(new Point(2, 0), new Point(1, 1), new Point(0, 2)))
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(List<Line> linesOfVents, int expectedOverlaps) {
        Day5 day = new Day5();
        int numberOfOverlaps = day.partOne(linesOfVents);
        assertEquals(expectedOverlaps, numberOfOverlaps);
    }

    @Test
    void initCoveredMap() {
        Day5 day = new Day5();
        int[][] area = day.initCoveredMap(SAMPLE_LINES);
        assertEquals(10, area.length);
        for (int[] line : area) {
            assertEquals(10, line.length);
        }
    }

    @ParameterizedTest
    @MethodSource("coveredPointsCaseProvider")
    void coveredPoints(Line lineOfVents, List<Point> expectedPoints) {
        Day5 day = new Day5();
        List<Point> points = day.coveredPoints(lineOfVents);
        assertEquals(expectedPoints, points);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(List<Line> linesOfVents, int expectedOverlaps) {
        Day5 day = new Day5();
        int numberOfOverlaps = day.partTwo(linesOfVents);
        assertEquals(expectedOverlaps, numberOfOverlaps);
    }

    @ParameterizedTest
    @MethodSource("coveredPointsPartTwoCaseProvider")
    void coveredPointsPartTwo(Line lineOfVents, List<Point> expectedPoints) {
        Day5 day = new Day5();
        List<Point> points = day.coveredPointsPartTwo(lineOfVents);
        assertEquals(expectedPoints, points);
    }
}