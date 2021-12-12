package aoc.loicb.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test {
    private final static List<Connection> SAMPLE1 = List.of(
            new Connection("start", "A"),
            new Connection("start", "b"),
            new Connection("A", "c"),
            new Connection("A", "b"),
            new Connection("b", "d"),
            new Connection("A", "end"),
            new Connection("b", "end")
    );

    private final static List<Connection> SAMPLE2 = List.of(
            new Connection("dc", "end"),
            new Connection("HN", "start"),
            new Connection("start", "kj"),
            new Connection("dc", "start"),
            new Connection("dc", "HN"),
            new Connection("LN", "dc"),
            new Connection("HN", "end"),
            new Connection("kj", "sa"),
            new Connection("kj", "HN"),
            new Connection("kj", "dc")
    );

    private final static List<Connection> SAMPLE3 = List.of(
            new Connection("fs", "end"),
            new Connection("he", "DX"),
            new Connection("fs", "he"),
            new Connection("start", "DX"),
            new Connection("pj", "DX"),
            new Connection("end", "zg"),
            new Connection("zg", "sl"),
            new Connection("zg", "pj"),
            new Connection("pj", "he"),
            new Connection("RW", "he"),
            new Connection("fs", "DX"),
            new Connection("pj", "RW"),
            new Connection("zg", "RW"),
            new Connection("start", "pj"),
            new Connection("he", "WI"),
            new Connection("zg", "he"),
            new Connection("pj", "fs"),
            new Connection("start", "RW")
    );


    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE1, 10),
                Arguments.of(SAMPLE2, 19),
                Arguments.of(SAMPLE3, 226)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE1, 36),
                Arguments.of(SAMPLE2, 103),
                Arguments.of(SAMPLE3, 3509)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(List<Connection> connections, int expectedNumberOfPaths) {
        Day12 day = new Day12();
        int numberOfPaths = day.partOne(connections);
        assertEquals(expectedNumberOfPaths, numberOfPaths);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(List<Connection> connections, int expectedNumberOfPaths) {
        Day12 day = new Day12();
        int numberOfPaths = day.partTwo(connections);
        assertEquals(expectedNumberOfPaths, numberOfPaths);
    }
}