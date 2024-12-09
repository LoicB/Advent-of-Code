package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9Test {

    private final static List<Integer> INPUT = List.of(2, 3, 3, 3, 1, 3, 3, 1, 2, 1, 4, 1, 4, 1, 3, 1, 4, 0, 2);

    private static Stream<Arguments> buildFilesCaseProvider() {
        return Stream.of(
                Arguments.of(INPUT, "00...111...2...333.44.5555.6666.777.888899"),
                Arguments.of(List.of(1, 2, 3, 4, 5), "0..111....22222")
        );
    }

    private static Stream<Arguments> moveFileBlocksCaseProvider() {
        return Stream.of(
                Arguments.of(INPUT, "0099811188827773336446555566.............."),
                Arguments.of(List.of(1, 2, 3, 4, 5), "022111222......")
        );
    }

    @Test
    void partOne() {
        var day = new Day9();
        var checksum = day.partOne(INPUT);
        assertEquals(1928, checksum);
    }

    @ParameterizedTest
    @MethodSource("buildFilesCaseProvider")
    void buildFiles(List<Integer> input, String expectedFile) {
        var day = new Day9();
        var files = day.buildFiles(input);
        assertEquals(expectedFile, day.printFiles(files));
        var files2 = day.buildFilesBlock(input);
        assertEquals(expectedFile, Arrays.stream(files2).map(Object::toString).collect(Collectors.joining()));
    }

    @ParameterizedTest
    @MethodSource("moveFileBlocksCaseProvider")
    void moveFileBlocks(List<Integer> input, String expectedFile) {
        var day = new Day9();
        var files = day.buildFiles(input);
        day.moveFileBlocks(files);
        assertEquals(expectedFile, day.printFiles(files));
        var files2 = day.buildFilesBlock(input);
        day.moveFileBlocks2(files2);
        assertEquals(expectedFile, Arrays.stream(files2).map(Object::toString).collect(Collectors.joining()));
    }


    @Test
    void partTwo() {
    }
}