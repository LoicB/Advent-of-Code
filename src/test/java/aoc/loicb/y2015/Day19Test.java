package aoc.loicb.y2015;

import com.github.javaparser.utils.Pair;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19Test {
    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of("HOH", 4),
                Arguments.of("HOHOHO", 7)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of("HOH", 3),
                Arguments.of("HOHOHO", 6)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(String molecule, int expectedMoleculesCount) {
        var day = new Day19();
        Map<String, List<String>> replacements = new HashMap<>();
        replacements.put("H", List.of("HO", "OH"));
        replacements.put("O", List.of("HH"));
        var input = new Pair<>(replacements,
                molecule
        );
        var distinctMolecules = day.partOne(input);
        assertEquals(expectedMoleculesCount, distinctMolecules);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(String molecule, int expectedNumberOfSteps) {
        var day = new Day19();
        Map<String, List<String>> replacements = new HashMap<>();
        replacements.put("e", List.of("H", "O"));
        replacements.put("H", List.of("HO", "OH"));
        replacements.put("O", List.of("HH"));
        var input = new Pair<>(replacements,
                molecule
        );
        var steps = day.partTwo(input);
        assertEquals(expectedNumberOfSteps, steps);
    }
}