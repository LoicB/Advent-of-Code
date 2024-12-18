package aoc.loicb.y2019;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {

    private final static List<String> INPUT_SAMPLE_1 =
            List.of(
                    ".#..#",
                    ".....",
                    "#####",
                    "....#",
                    "...##"
            );

    private final static List<String> INPUT_SAMPLE_2 =
            List.of(
                    "......#.#.",
                    "#..#.#....",
                    "..#######.",
                    ".#.#.###..",
                    ".#..#.....",
                    "..#....#.#",
                    "#..#....#.",
                    ".##.#..###",
                    "##...#..#.",
                    ".#....####"
            );

    private final static List<String> INPUT_SAMPLE_3 =
            List.of(
                    ".#..##.###...#######",
                    "##.############..##.",
                    ".#.######.########.#",
                    ".###.#######.####.#.",
                    "#####.##.#.##.###.##",
                    "..#####..#.#########",
                    "####################",
                    "#.####....###.#.#.##",
                    "##.#################",
                    "#####.##.###..####..",
                    "..######..##.#######",
                    "####.##.####...##..#",
                    ".#####..#.######.###",
                    "##...#.##########...",
                    "#.##########.#######",
                    ".####.#.###.###.#.##",
                    "....##.##.###..#####",
                    ".#.#.###########.###",
                    "#.#.#.#####.####.###",
                    "###.##.####.##.#..##"
            );
    private final static List<String> INPUT_SAMPLE_4 =
            List.of(
                    ".#....#####...#..",
                    "##...##.#####..##",
                    "##...#...#.#####.",
                    "..#.....X...###..",
                    "..#.#.....#....##"
            );

    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(INPUT_SAMPLE_1, 8),
                Arguments.of(INPUT_SAMPLE_2, 33),
                Arguments.of(INPUT_SAMPLE_3, 210)
        );
    }

    private static Stream<Arguments> findAsteroidCaseProvider() {
        return Stream.of(
                Arguments.of(INPUT_SAMPLE_3, 1, 1112),
                Arguments.of(INPUT_SAMPLE_3, 2, 1201),
                Arguments.of(INPUT_SAMPLE_3, 3, 1202),
                Arguments.of(INPUT_SAMPLE_4, 1, 801),
                Arguments.of(INPUT_SAMPLE_4, 2, 900),
                Arguments.of(INPUT_SAMPLE_4, 3, 901)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(List<String> input, int expectedNumberOfAsteroids) {
        var day = new Day10();
        var numberOfAsteroids = day.partOne(input);
        assertEquals(numberOfAsteroids, expectedNumberOfAsteroids);
    }

    @Test
    void partTwo() {
        var day = new Day10();
        var numberOfAsteroids = day.partTwo(INPUT_SAMPLE_3);
        assertEquals(802, numberOfAsteroids);
    }

    @ParameterizedTest
    @MethodSource("findAsteroidCaseProvider")
    void findAsteroid(List<String> input, int numberOfVaporization, int expectedAsteroid) {
        var day = new Day10();
        var numberOfAsteroids = day.findAsteroid(numberOfVaporization, input);
        assertEquals(expectedAsteroid, numberOfAsteroids);
    }
}