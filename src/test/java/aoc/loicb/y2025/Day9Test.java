package aoc.loicb.y2025;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day9Test {

    private final List<Tile> input =
            List.of(
                    new Tile(7, 1),
                    new Tile(11, 1),
                    new Tile(11, 7),
                    new Tile(9, 7),
                    new Tile(9, 5),
                    new Tile(2, 5),
                    new Tile(2, 3),
                    new Tile(7, 3)
            );

    private static Stream<Arguments> calculateRectangleAreaCaseProvider() {
        return Stream.of(
                Arguments.of(new Tile(2, 5), new Tile(9, 7), 24),
                Arguments.of(new Tile(7, 1), new Tile(11, 7), 35),
                Arguments.of(new Tile(7, 3), new Tile(2, 3), 6),
                Arguments.of(new Tile(2, 5), new Tile(11, 1), 50)
        );
    }

    private static Stream<Arguments> generateGreenTilesCaseProvider() {
        return Stream.of(
                Arguments.of(new Tile(7, 1), new Tile(11, 1), Set.of(new Tile(8, 1), new Tile(9, 1), new Tile(10, 1))),
                Arguments.of(new Tile(11, 1), new Tile(11, 5), Set.of(new Tile(11, 2), new Tile(11, 3), new Tile(11, 4))),
                Arguments.of(new Tile(2, 5), new Tile(2, 3), Set.of(new Tile(2, 4)))
        );
    }

    @Test
    void partOne() {
        var day = new Day9();
        var circuitsSize = day.partOne(input);
        assertEquals(50, circuitsSize);
    }

    @ParameterizedTest
    @MethodSource("calculateRectangleAreaCaseProvider")
    void calculateRectangleArea(Tile tile1, Tile tile2, int expectedArea) {
        var day = new Day9();
        var area = day.calculateRectangleArea(tile1, tile2);
        assertEquals(expectedArea, area);
    }

    @Test
    void partTwo() {
        var day = new Day9();
        var circuitsSize = day.partTwo(input);
        assertEquals(24, circuitsSize);
    }

    @Test
    void validateTile() {
        var day = new Day9();
        Set<Tile> tiles = new HashSet<>(input);
        assertTrue(day.validateTile(new Tile(9, 5), new Tile(2, 3), tiles));
    }

    @ParameterizedTest
    @MethodSource("generateGreenTilesCaseProvider")
    void generateGreenTiles(Tile tile1, Tile tile2, Set<Tile> expectedTiles) {
        var day = new Day9();
        var tiles = day.generateGreenTiles(tile1, tile2);
        assertEquals(expectedTiles.size(), tiles.size());
        assertTrue(expectedTiles.containsAll(tiles));
    }
}