package aoc.loicb.y2023;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5Test {
    private static Stream<Arguments> findSeedLocationCaseProvider() {
        return Stream.of(
                Arguments.of(0, 82),
                Arguments.of(1, 43),
                Arguments.of(2, 86),
                Arguments.of(3, 35)
        );
    }

    private String prepareTestInput() throws IOException {
        return Files.readString(Path.of("./src/test/resources/aoc.loicb.y2023/Day5.txt"));
    }

    @Test
    void partOne() throws IOException {
        var input = prepareTestInput();
        var day = new Day5();
        var totalPoint = day.partOne(input);
        assertEquals(35, totalPoint);
    }

    @Test
    void buildAlmanac() throws IOException {
        var input = prepareTestInput();
        var day = new Day5();
        var almanac = day.buildAlmanac(input);
        assertEquals(List.of(79L, 14L, 55L, 13L), almanac.seeds());
//        assertEquals(81, almanac.maps().get(0).get(79L));

    }

    @Test
    void indexMap() {
        var input = List.of(List.of(50L, 98L, 2L), List.of(52L, 50L, 48L));
        var day = new Day5();
        var map = day.indexMap(input);
        assertEquals(52, map.get(50L));
        assertEquals(53, map.get(51L));
        assertEquals(50, map.get(98L));
        assertEquals(51, map.get(99L));
    }

    @ParameterizedTest
    @MethodSource("findSeedLocationCaseProvider")
    void findSeedLocation(int seedIndex, int expectedPossible) throws IOException {
        var input = prepareTestInput();
        var day = new Day5();
        var almanac = day.buildAlmanac(input);
        var possible = day.findSeedLocation(almanac.seeds().get(seedIndex), almanac.maps());
        assertEquals(expectedPossible, possible);
    }

    @Test
    void partTwo() throws IOException {
        var input = prepareTestInput();
        var day = new Day5();
        var totalPoint = day.partTwo(input);
        assertEquals(46, totalPoint);
    }
}