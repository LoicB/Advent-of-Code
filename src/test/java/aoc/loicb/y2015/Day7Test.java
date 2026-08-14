package aoc.loicb.y2015;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {

    private static Stream<Arguments> executeInstructionCaseProvider() {
        return Stream.of(
                Arguments.of("123 -> x", new HashMap<>(), Optional.of(123)),
                Arguments.of("456 -> y", new HashMap<>(), Optional.of(456)),
                Arguments.of("x AND y -> d", new HashMap<>(), Optional.empty()),
                Arguments.of("x AND y -> d", Map.of("x", 123, "y", 456), Optional.of(72)),
                Arguments.of("x OR y -> e", Map.of("x", 123, "y", 456), Optional.of(507)),
                Arguments.of("x LSHIFT 2 -> f", Map.of("x", 123, "y", 456), Optional.of(492)),
                Arguments.of("y RSHIFT 2 -> g", Map.of("x", 123, "y", 456), Optional.of(114)),
                Arguments.of("NOT x -> h", Map.of("x", 123, "y", 456), Optional.of(65412)),
                Arguments.of("NOT y -> i", Map.of("x", 123, "y", 456), Optional.of(65079))
        );
    }

    @Test
    void executeInstructions() {
        var instructions = List.of("123 -> x",
                "456 -> y",
                "x AND y -> d",
                "x OR y -> e",
                "x LSHIFT 2 -> f",
                "y RSHIFT 2 -> g",
                "NOT x -> h",
                "NOT y -> i");
        var day = new Day7();
        var result = day.executeInstructions(instructions);
        System.out.println(result);
        System.out.println(65412 - result.get("h"));
        System.out.println(65079 - result.get("i"));
        assertEquals(72, result.get("d"));
        assertEquals(507, result.get("e"));
        assertEquals(492, result.get("f"));
        assertEquals(114, result.get("g"));
        assertEquals(65412, result.get("h"));
        assertEquals(65079, result.get("i"));
        assertEquals(123, result.get("x"));
        assertEquals(456, result.get("y"));
    }

    @ParameterizedTest
    @MethodSource("executeInstructionCaseProvider")
    void executeInstruction(String instruction, Map<String, Integer> current, Optional<Integer> expected) {
        var day = new Day7();
        var result = day.executeInstruction(instruction, current);
        assertEquals(expected, result);
    }
}