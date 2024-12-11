package aoc.loicb.y2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day16Test {

    private final static List<char[]> SAMPLE =
            Stream.of(
                    ".|...\\....",
                    "|.-.\\.....",
                    ".....|-...",
                    "........|.",
                    "..........",
                    ".........\\",
                    "..../.\\\\..",
                    ".-.-/..|..",
                    ".|....-|.\\",
                    "..//.|...."
            ).map(String::toCharArray).toList();

    private static Stream<Arguments> moveLightCaseProvider() {
        return Stream.of(
                Arguments.of(new Day16.Light(0, 0, Direction.Right), List.of(new Day16.Light(0, 1, Direction.Right))),
                Arguments.of(new Day16.Light(0, 1, Direction.Right), List.of(new Day16.Light(1, 1, Direction.Down))),
                Arguments.of(new Day16.Light(1, 1, Direction.Down), List.of(new Day16.Light(2, 1, Direction.Down))),
                Arguments.of(new Day16.Light(7, 1, Direction.Down), List.of(new Day16.Light(7, 0, Direction.Left), new Day16.Light(7, 2, Direction.Right))),
                Arguments.of(new Day16.Light(7, 3, Direction.Right), List.of(new Day16.Light(7, 4, Direction.Right))),
                Arguments.of(new Day16.Light(7, 4, Direction.Right), List.of(new Day16.Light(6, 4, Direction.Up))),
                Arguments.of(new Day16.Light(6, 4, Direction.Up), List.of(new Day16.Light(6, 5, Direction.Right))),
                Arguments.of(new Day16.Light(6, 7, Direction.Right), List.of(new Day16.Light(7, 7, Direction.Down))),
                Arguments.of(new Day16.Light(1, 4, Direction.Down), List.of(new Day16.Light(1, 5, Direction.Right)))
        );
    }

    @Test
    void partOne() {
        var day = new Day16();
        var numberOfTiles = day.partOne(SAMPLE);
        assertEquals(46, numberOfTiles);
    }

    @ParameterizedTest
    @MethodSource("moveLightCaseProvider")
    void moveLight(Day16.Light startingLight, List<Day16.Light> expectedLights) {
        var day = new Day16();
        var directions = day.moveLight(startingLight, SAMPLE);
        assertEquals(expectedLights, directions);
    }

    @Test
    void partTwo() {
    }
}