package aoc.loicb.y2023;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day20Test {

    private static final List<String> SAMPLE = List.of("broadcaster -> a, b, c",
            "%a -> b",
            "%b -> c",
            "%c -> inv",
            "&inv -> a");
    private static final List<String> SAMPLE_2 = List.of("broadcaster -> a",
            "%a -> inv, con",
            "&inv -> b",
            "%b -> con",
            "&con -> output");

    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE, 32000000),
                Arguments.of(SAMPLE_2, 11687500)
        );
    }

    private static Stream<Arguments> pressButtonCaseProvider() {
        return Stream.of(
                Arguments.of(SAMPLE, 1, 12),
                Arguments.of(SAMPLE, 1000, 12000),
                Arguments.of(SAMPLE_2, 1, 8),
                Arguments.of(SAMPLE_2, 1000, 7000)
        );
    }

    private static Stream<Arguments> createModuleKeyCaseProvider() {
        return Stream.of(
                Arguments.of("broadcaster -> a", "broadcaster"),
                Arguments.of("%a -> inv, con", "a"),
                Arguments.of("&inv -> b", "inv"),
                Arguments.of("%b -> con", "b"),
                Arguments.of("&con -> output", "con")
        );
    }

    private static Stream<Arguments> createModuleDestinationsCaseProvider() {
        return Stream.of(
                Arguments.of("broadcaster -> a", List.of("a")),
                Arguments.of("%a -> inv, con", List.of("inv", "con")),
                Arguments.of("&inv -> b", List.of("b")),
                Arguments.of("%b -> con", List.of("con")),
                Arguments.of("&con -> output", List.of("output"))
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(List<String> input, int expectedPulses) {
        var day = new Day20();
        var pulses = day.partOne(input);
        assertEquals(expectedPulses, pulses);
    }

    @ParameterizedTest
    @MethodSource("pressButtonCaseProvider")
    void pressButton(List<String> input, int pressCount, int expectedPulsesCount) {
        var day = new Day20();
        var pulses = day.pressButton(pressCount, input);
        assertEquals(expectedPulsesCount, pulses.size());
    }

    @ParameterizedTest
    @MethodSource("createModuleKeyCaseProvider")
    void getWorkflowKey(String input, String expectedKey) {
        var day = new Day20();
        var key = day.createModuleKey(input);
        assertEquals(expectedKey, key);
    }

    @ParameterizedTest
    @MethodSource("createModuleDestinationsCaseProvider")
    void getWorkflowKey(String input, List<String> expectedDestinations) {
        var day = new Day20();
        var destinations = day.createModuleDestinations(input);
        assertEquals(expectedDestinations, destinations);
    }
}