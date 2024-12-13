package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Test {

    private final List<String> sample = List.of(
            """
                    Button A: X+94, Y+34
                    Button B: X+22, Y+67
                    Prize: X=8400, Y=5400
                    """
            ,
            """
                    Button A: X+26, Y+66
                    Button B: X+67, Y+21
                    Prize: X=12748, Y=12176
                    """
            ,
            """
                    Button A: X+17, Y+86
                    Button B: X+84, Y+37
                    Prize: X=7870, Y=6450
                    """
            ,
            """
                    Button A: X+69, Y+23
                    Button B: X+27, Y+71
                    Prize: X=18641, Y=10279"""
    );
    private final List<String> sample2 = List.of(
            """
                    Button A: X+94, Y+34
                    Button B: X+22, Y+67
                    Prize: X=10000000008400, Y=10000000005400
                    """,
            """
                    Button A: X+26, Y+66
                    Button B: X+67, Y+21
                    Prize: X=10000000012748, Y=10000000012176
                    """,
            """
                    Button A: X+17, Y+86
                    Button B: X+84, Y+37
                    Prize: X=10000000007870, Y=10000000006450
                    """,
            """
                    Button A: X+69, Y+23
                    Button B: X+27, Y+71
                    Prize: X=10000000018641, Y=10000000010279"""
    );

    private static Stream<Arguments> calculateMachineCostToVictoryCaseProvider() {
        return Stream.of(
                Arguments.of(0, 280),
                Arguments.of(1, -1),
                Arguments.of(2, 200),
                Arguments.of(3, -1)
        );
    }

    @Test
    void partOne() {
        var day = new Day13();
        var tokens = day.partOne(sample);
        assertEquals(480, tokens);
    }

    @ParameterizedTest
    @MethodSource("calculateMachineCostToVictoryCaseProvider")
    void calculateMachineCostToVictory(int sampleIndex, int expectedTokens) {
        var day = new Day13();
        var tokens = day.calculateMachineCostToVictory(sample.get(sampleIndex));
        assertEquals(expectedTokens, tokens.orElse(-1L));
    }

    @Test
    void partTwo() {
        var day = new Day13();
        var tokens = day.partOne(sample2);
        var tokens2 = day.partTwo(sample);
        assertEquals(tokens2, tokens);
    }
}