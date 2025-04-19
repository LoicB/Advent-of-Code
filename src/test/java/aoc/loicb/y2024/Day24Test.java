package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day24Test {


    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of("Day24.txt", 4),
                Arguments.of("Day24_2.txt", 2024)
        );
    }

    private static Stream<Arguments> calculateOutputCaseProvider() {
        return Stream.of(
                Arguments.of(Map.of(
                        "z00", false,
                        "z01", false,
                        "z02", true
                ), 4),
                Arguments.of(Map.ofEntries(
                        entry("z00", false),
                        entry("z01", false),
                        entry("z02", false),
                        entry("z03", true),
                        entry("z04", false),
                        entry("z05", true),
                        entry("z06", true),
                        entry("z07", true),
                        entry("z08", true),
                        entry("z09", true),
                        entry("z10", true),
                        entry("z11", false),
                        entry("z12", false)
                ), 2024)
        );
    }

    private MonitoringDevice prepareTestInput(String file) throws IOException {
        var transformer = new Day24InputTransformer();
        return transformer.transform(Files.readString(Path.of("./src/test/resources/aoc.loicb.y2024/" + file)));
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(String file, int expectedOutput) throws IOException {
        var day = new Day24();
        var input = prepareTestInput(file);
        var output = day.partOne(input);
        assertEquals(expectedOutput, output);
    }

    @ParameterizedTest
    @MethodSource("calculateOutputCaseProvider")
    void calculateOutput(Map<String, Boolean> wires, int expectedOutput) {
        var day = new Day24();
        var output = day.calculateOutput(wires);
        assertEquals(expectedOutput, output);
    }

    @Test
    void swapGates() throws IOException {
        var day = new Day24();
        var input = prepareTestInput("Day24_3.txt");
        var gates = day.swapGates(day.swapGates(input.gates(), 1, 2), 0, 5);
        var output = day.partOne(new MonitoringDevice(input.wires(), gates));
        assertEquals(40, output);
    }
}