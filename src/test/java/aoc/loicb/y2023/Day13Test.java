package aoc.loicb.y2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Test {
    private final static List<List<String>> SAMPLE =
            List.of(
                    List.of("#.##..##.",
                            "..#.##.#.",
                            "##......#",
                            "##......#",
                            "..#.##.#.",
                            "..##..##.",
                            "#.#.##.#."),
                    List.of("#...##..#",
                            "#....#..#",
                            "..##..###",
                            "#####.##.",
                            "#####.##.",
                            "..##..###",
                            "#....#..#")
            );

    private static Stream<Arguments> calculateReflexionScoreCaseProvider() {
        return Stream.of(
                Arguments.of(0, 5),
                Arguments.of(1, 400)
        );
    }

    private static Stream<Arguments> mirroringColumnIndexCaseProvider() {
        return Stream.of(
                Arguments.of(0, 5),
                Arguments.of(1, 0)
        );
    }

    private static Stream<Arguments> mirroringRowIndexCaseProvider() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(1, 4)
        );
    }

    @Test
    void partOne() {
        var day = new Day13();
        var score = day.partOne(SAMPLE);
        assertEquals(405L, score);
    }

    @ParameterizedTest
    @MethodSource("calculateReflexionScoreCaseProvider")
    void calculateReflexionScore(int sampleIndex, long expectedScore) {
        var day = new Day13();
        var score = day.calculateReflexionScore(SAMPLE.get(sampleIndex));
        assertEquals(expectedScore, score);
    }

    @ParameterizedTest
    @MethodSource("mirroringColumnIndexCaseProvider")
    void mirroringColumnIndex(int sampleIndex, long expectedScore) {
        var day = new Day13();
        var score = day.mirroringColumnIndex(SAMPLE.get(sampleIndex));
        assertEquals(expectedScore, score);
    }

    @ParameterizedTest
    @MethodSource("mirroringRowIndexCaseProvider")
    void mirroringRowIndex(int sampleIndex, long expectedScore) {
        var day = new Day13();
        var score = day.mirroringRowIndex(SAMPLE.get(sampleIndex));
        assertEquals(expectedScore, score);
    }

    @Test
    void mirroringRowIndexTmp() {
        var day = new Day13();
        var input = List.of(
                "....####.",
                ".#.......",
                "....####.",
                "#........",
                "#........",
                "....####.",
                ".#.......",
                ".#..####.",
                "#.###..##"
        );
        var score = day.mirroringRowIndex(input);
        assertEquals(0, score);
    }

    @Test
    void mirroringRowIndexTmp2() {
        var day = new Day13();
        var input = List.of(
                ".##.#.##.#.",
                "#..##..####",
                "#..##..##.#",
                ".##.#.##.#.",
                "....#.##...",
                ".#.###..##.",
                "...#.##..##",
                ".#....#.###",
                ".#....#.###"
        );
        var score = day.mirroringRowIndex(input);
        assertEquals(8, score);
    }

    @Test
    void mirroringRowIndexTmp3() {
        var day = new Day13();
        var input = List.of(
                "##.#.####.#.####.",
                "#..##.##.##..#..#",
                "..###....###..###",
                ".###.####.###..##",
                "..#........#..##.",
                "#..###..###..#.##",
                "#..###..###..#.##",
                "..#........#...#.",
                ".###.####.###..##"
        );
        var score = day.mirroringRowIndex(input);
        assertEquals(0, score);
        var score2 = day.mirroringColumnIndex(input);
        assertEquals(7, score2);
    }

    @Test
    void mirroringRowIndexTmp4() {
        var day = new Day13();
        var input = List.of(
                ".##.##.##....",
                "#.######.##..",
                "##......###..",
                "...####......",
                "...####...###",
                "..#....#..#..",
                ".#.####.#.###",
                "..#....#..###",
                ".##.##.##....",
                "..######..###",
                "#.##..##.####",
                "#.#.##.#.....",
                "##..##..##.##",
                ".#..##..#....",
                "#.##..##.##.."
        );
        var score = day.mirroringRowIndex(input);
        assertEquals(0, score);
        var score2 = day.mirroringColumnIndex(input);
        assertEquals(12, score2);
        var repair = day.repairing(input);
        assertEquals(0, repair);
        var repair2 = day.repairing(day.flipPattern(input));
        assertEquals(0, repair2);
    }

    @Test
    void partTwo() {
        var day = new Day13();
        var score = day.partTwo(SAMPLE);
        assertEquals(400L, score);
    }
}