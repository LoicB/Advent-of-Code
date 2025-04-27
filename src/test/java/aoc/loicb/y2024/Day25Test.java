package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day25Test {

    private static final List<String> schema1 = List.of(
            "#####",
            ".####",
            ".####",
            ".####",
            ".#.#.",
            ".#...",
            ".....");
    private static final List<String> schema2 = List.of(
            "#####",
            "##.##",
            ".#.##",
            "...##",
            "...#.",
            "...#.",
            ".....");
    private static final List<String> schema3 = List.of(
            ".....",
            "#....",
            "#....",
            "#...#",
            "#.#.#",
            "#.###",
            "#####");

    private static final List<String> schema4 = List.of(
            ".....",
            ".....",
            "#.#..",
            "###..",
            "###.#",
            "###.#",
            "#####");

    private static final List<String> schema5 = List.of(
            ".....",
            ".....",
            ".....",
            "#....",
            "#.#..",
            "#.#.#",
            "#####");

    private static final List<List<String>> samples = List.of(
            schema1, schema2, schema3, schema4, schema5
    );

    private static Stream<Arguments> isKeyCaseProvider() {
        return Stream.of(
                Arguments.of(schema1, false),
                Arguments.of(schema2, false),
                Arguments.of(schema3, true),
                Arguments.of(schema4, true),
                Arguments.of(schema5, true)
        );
    }

    private static Stream<Arguments> isLockCaseProvider() {
        return Stream.of(
                Arguments.of(schema1, true),
                Arguments.of(schema2, true),
                Arguments.of(schema3, false),
                Arguments.of(schema4, false),
                Arguments.of(schema5, false)
        );
    }

    private static Stream<Arguments> areSchemasFittingCaseProvider() {
        return Stream.of(
                Arguments.of(schema1, schema3, false),
                Arguments.of(schema1, schema4, false),
                Arguments.of(schema1, schema5, true),
                Arguments.of(schema2, schema3, false),
                Arguments.of(schema2, schema4, true),
                Arguments.of(schema2, schema5, true)
        );
    }

    @Test
    void partOne() {
        var day = new Day25();
        var numberOfFit = day.partOne(samples);
        assertEquals(3L, numberOfFit);
    }

    @ParameterizedTest
    @MethodSource("isKeyCaseProvider")
    void isKey(List<String> schema, boolean expectedIsKey) {
        var day = new Day25();
        var isKey = day.isKey(schema);
        assertEquals(expectedIsKey, isKey);
    }

    @ParameterizedTest
    @MethodSource("isLockCaseProvider")
    void isLock(List<String> schema, boolean expectedIsLock) {
        var day = new Day25();
        var isLock = day.isLock(schema);
        assertEquals(expectedIsLock, isLock);
    }

    @ParameterizedTest
    @MethodSource("areSchemasFittingCaseProvider")
    void areSchemasFitting(List<String> schema1, List<String> schema2, boolean expectedSchemasFitting) {
        var day = new Day25();
        var schemasFitting = day.areSchemasFitting(schema1, schema2);
        assertEquals(expectedSchemasFitting, schemasFitting);
    }

    @Test
    void partTwo() {
    }
}