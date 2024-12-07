package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {
    private final List<Equation> input = List.of(
            new Equation(190, List.of(10, 19)),
            new Equation(3267, List.of(81, 40, 27)),
            new Equation(83, List.of(17, 5)),
            new Equation(156, List.of(15, 6)),
            new Equation(7290, List.of(6, 8, 6, 15)),
            new Equation(161011, List.of(16, 10, 13)),
            new Equation(192, List.of(17, 8, 14)),
            new Equation(21037, List.of(9, 7, 18, 13)),
            new Equation(292, List.of(11, 6, 16, 20))
    );

    private static Stream<Arguments> isEquationValidCaseProvider() {
        return Stream.of(
                Arguments.of(0, true),
                Arguments.of(1, true),
                Arguments.of(2, false),
                Arguments.of(3, false),
                Arguments.of(4, false),
                Arguments.of(5, false),
                Arguments.of(6, false),
                Arguments.of(7, false),
                Arguments.of(8, true)
        );
    }

    private static Stream<Arguments> calculateSolutionsCaseProvider() {
        return Stream.of(
                Arguments.of(0, List.of(29L, 190L)),
                Arguments.of(1, List.of(148L, 3267L, 3267L, 87480L)),
                Arguments.of(2, List.of(22L, 85L)),
                Arguments.of(3, List.of(21L, 90L))
        );
    }

    private static Stream<Arguments> isEquationValidPartTwoCaseProvider() {
        return Stream.of(
                Arguments.of(0, true),
                Arguments.of(1, true),
                Arguments.of(2, false),
                Arguments.of(3, true),
                Arguments.of(4, true),
                Arguments.of(5, false),
                Arguments.of(6, true),
                Arguments.of(7, false),
                Arguments.of(8, true)
        );
    }

    @Test
    void partOne() {
        var day = new Day7();
        var totalCalibrationResult = day.partOne(input);
        assertEquals(3749, totalCalibrationResult);
    }

    @ParameterizedTest
    @MethodSource("isEquationValidCaseProvider")
    void isEquationValid(int equationIndex, boolean expectedValidity) {
        var day = new Day7();
        boolean validity = day.isEquationValid(input.get(equationIndex));
        assertEquals(expectedValidity, validity);
    }

    @ParameterizedTest
    @MethodSource("calculateSolutionsCaseProvider")
    void calculateSolutions(int equationIndex, List<Long> expectedSolutions) {
        var day = new Day7();
        var solutions = day.calculateSolutions(input.get(equationIndex).values());
        assertEquals(expectedSolutions, solutions);
    }

    @Test
    void partTwo() {
        var day = new Day7();
        var totalCalibrationResult = day.partTwo(input);
        assertEquals(11387, totalCalibrationResult);
    }

    @ParameterizedTest
    @MethodSource("isEquationValidPartTwoCaseProvider")
    void isEquationValidPartTwo(int equationIndex, boolean expectedValidity) {
        var day = new Day7();
        boolean validity = day.isEquationValidPartTwo(input.get(equationIndex));
        assertEquals(expectedValidity, validity);
    }
}