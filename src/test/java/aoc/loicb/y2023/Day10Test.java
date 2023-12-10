package aoc.loicb.y2023;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {


    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(new String[]
                        {".....",
                                ".S-7.",
                                ".|.|.",
                                ".L-J.",
                                "....."}, 4),
                Arguments.of(new String[]
                        {"-L|F7",
                                "7S-7|",
                                "L|7||",
                                "-L-J|",
                                "L|-JF"}, 4),
                Arguments.of(new String[]
                        {"..F7.",
                                ".FJ|.",
                                "SJ.L7",
                                "|F--J",
                                "LJ..."}, 8)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(new String[]
                        {"...........",
                                ".S-------7.",
                                ".|F-----7|.",
                                ".||.....||.",
                                ".||.....||.",
                                ".|L-7.F-J|.",
                                ".|..|.|..|.",
                                ".L--J.L--J.",
                                "..........."}, 4),
                Arguments.of(new String[]
                        {"..........",
                                ".S------7.",
                                ".|F----7|.",
                                ".||....||.",
                                ".||....||.",
                                ".|L-7F-J|.",
                                ".|..||..|.",
                                ".L--JL--J.",
                                "..........."}, 4),
                Arguments.of(new String[]
                        {".F----7F7F7F7F-7....",
                                ".|F--7||||||||FJ....",
                                ".||.FJ||||||||L7....",
                                "FJL7L7LJLJ||LJ.L-7..",
                                "L--J.L7...LJS7F-7L7.",
                                "....F-J..F7FJ|L7L7L7",
                                "....L7.F7||L7|.L7L7|",
                                ".....|FJLJ|FJ|F7|.LJ",
                                "....FJL-7.||.||||...",
                                "....L---J.LJ.LJLJ..."}, 8),
                Arguments.of(new String[]
                        {"FF7FSF7F7F7F7F7F---7",
                                "L|LJ||||||||||||F--J",
                                "FL-7LJLJ||||||LJL-77",
                                "F--JF--7||LJLJ7F7FJ-",
                                "L---JF-JLJ.||-FJLJJ7",
                                "|F|F-JF---7F7-L7L|7|",
                                "|FFJF7L7F-JF7|JL---7",
                                "7-L-JL7||F7|L7F-7F7|",
                                "L.L7LFJ|||||FJL7||LJ",
                                "L7JLJL-JLJLJL--JLJ.L"}, 10)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(String[] input, int expectedDistance) {
        var day = new Day10();
        int distance = day.partOne(input);
        assertEquals(expectedDistance, distance);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(String[] input, int expectedNumberOfTiles) {
        var day = new Day10();
        int tiles = day.partTwo(input);
        assertEquals(expectedNumberOfTiles, tiles);
    }
}