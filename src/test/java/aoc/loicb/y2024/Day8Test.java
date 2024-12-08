package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day8Test {


    private final List<String> input =
            List.of(
                    "............",
                    "........0...",
                    ".....0......",
                    ".......0....",
                    "....0.......",
                    "......A.....",
                    "............",
                    "............",
                    "........A...",
                    ".........A..",
                    "............",
                    "............"
            );
    private final List<String> input2 =
            List.of(
                    "..........",
                    "...#......",
                    "..........",
                    "....a.....",
                    "..........",
                    ".....a....",
                    "..........",
                    "......#...",
                    "..........",
                    ".........."
            );
    private final List<String> input3 =
            List.of(
                    "T.........",
                    "...T......",
                    ".T........",
                    "..........",
                    "..........",
                    "..........",
                    "..........",
                    "..........",
                    "..........",
                    ".........."
            );

    @Test
    void partOne() {
        var day = new Day8();
        var antinode = day.partOne(input);
        assertEquals(14, antinode);
    }

    @Test
    void findAntinodes() {
        var day = new Day8();
        var antinode = day.findAntinodes(
                new Day8.Antenna('a', 4, 3),
                new Day8.Antenna('a', 5, 5));
        assertEquals(List.of(new Day8.Antinode(3, 1), new Day8.Antinode(6, 7)), antinode);
    }

    @Test
    void findAntinodesMultiple() {
        var day = new Day8();
        var antinode = day.findAntinodes(
                input2,
                List.of(
                        new Day8.Antenna('a', 4, 3),
                        new Day8.Antenna('a', 8, 4),
                        new Day8.Antenna('a', 5, 5)));
        var expected = List.of(
                new Day8.Antinode(6, 7),
                new Day8.Antinode(0, 2),
                new Day8.Antinode(2, 6),
                new Day8.Antinode(3, 1)
        );
        assertEquals(expected.size(), antinode.size());
        assertTrue(expected.containsAll(antinode));
        assertTrue(antinode.containsAll(expected));
    }

    @Test
    void partTwo() {
        var day = new Day8();
        var antinode = day.partTwo(input);
        assertEquals(34, antinode);
    }

    @Test
    void findAntinodesAndTs() {
        var day = new Day8();
        var antinode = day.findAntinodesAndTs(
                input3,
                List.of(
                        new Day8.Antenna('T', 0, 0),
                        new Day8.Antenna('T', 3, 1),
                        new Day8.Antenna('T', 1, 2)));
        var expected = List.of(
                new Day8.Antinode(0, 0),
                new Day8.Antinode(1, 2),
                new Day8.Antinode(2, 4),
                new Day8.Antinode(3, 6),
                new Day8.Antinode(4, 8),
                new Day8.Antinode(9, 3),
                new Day8.Antinode(5, 0),
                new Day8.Antinode(6, 2),
                new Day8.Antinode(3, 1)
        );
        assertEquals(expected.size(), antinode.size());
        assertTrue(expected.containsAll(antinode));
        assertTrue(antinode.containsAll(expected));
    }
}