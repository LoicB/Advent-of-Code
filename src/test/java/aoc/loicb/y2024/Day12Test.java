package aoc.loicb.y2024;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test {

    private final static List<String> SAMPLE_1 =
            List.of(
                    "AAAA",
                    "BBCD",
                    "BBCC",
                    "EEEC"
            );

    private final static List<String> SAMPLE_2 =
            List.of(
                    "OOOOO",
                    "OXOXO",
                    "OOOOO",
                    "OXOXO",
                    "OOOOO"
            );

    private final static List<String> SAMPLE_3 =
            List.of(
                    "RRRRIICCFF",
                    "RRRRIICCCF",
                    "VVRRRCCFFF",
                    "VVRCCCJFFF",
                    "VVVVCJJCFE",
                    "VVIVCCJJEE",
                    "VVIIICJJEE",
                    "MIIIIIJJEE",
                    "MIIISIJEEE",
                    "MMMISSJEEE"
            );
    private final static List<String> SAMPLE_4 =
            List.of(
                    "EEEEE",
                    "EXXXX",
                    "EEEEE",
                    "EXXXX",
                    "EEEEE"
            );
    private final static List<String> SAMPLE_5 =
            List.of(
                    "AAAAAA",
                    "AAABBA",
                    "AAABBA",
                    "ABBAAA",
                    "ABBAAA",
                    "AAAAAA"
            );

    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE_1, 140),
                Arguments.of(SAMPLE_2, 772),
                Arguments.of(SAMPLE_3, 1930)
        );
    }

    private static Stream<Arguments> exploreNeighbourhoodCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE_1, 0, 0, 10, 4),
                Arguments.of(SAMPLE_1, 0, 1, 8, 4),
                Arguments.of(SAMPLE_1, 2, 1, 10, 4),
                Arguments.of(SAMPLE_1, 3, 1, 4, 1),
                Arguments.of(SAMPLE_1, 0, 3, 8, 3)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE_1, 80),
                Arguments.of(SAMPLE_2, 436),
                Arguments.of(SAMPLE_3, 1206),
                Arguments.of(SAMPLE_4, 236),
                Arguments.of(SAMPLE_5, 368)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(List<String> map, int expectedPrice) {
        var day = new Day12();
        int price = day.partOne(map);
        assertEquals(expectedPrice, price);
    }

    @ParameterizedTest
    @MethodSource("exploreNeighbourhoodCaseProvider")
    void exploreNeighbourhood(List<String> map, int x, int y, int expectedFences, int expectedNeighbour) {
        var day = new Day12();
        var price = day.exploreNeighbourhood(map, x, y);
        assertEquals(expectedFences, price.a);
        assertEquals(expectedNeighbour, price.b);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(List<String> map, int expectedPrice) {
        var day = new Day12();
        int price = day.partTwo(map);
        assertEquals(expectedPrice, price);
    }
}