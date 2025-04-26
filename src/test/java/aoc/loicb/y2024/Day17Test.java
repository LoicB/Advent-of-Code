package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day17Test {

    private final String sample =
            """
                    Register A: 729
                    Register B: 0
                    Register C: 0
                                        
                    Program: 0,1,5,4,3,0""";

    private static Stream<Arguments> executeProgramCaseProvider() {
        return Stream.of(
                Arguments.of(new ThreeBitComputer(new long[]{10L, 0L, 0L}, List.of(5L, 0L, 5L, 1L, 5L, 4L)), List.of(0L, 1L, 2L)),
                Arguments.of(new ThreeBitComputer(new long[]{2024L, 0L, 0L}, List.of(0L, 1L, 5L, 4L, 3L, 0L)), List.of(4L, 2L, 5L, 6L, 7L, 7L, 7L, 7L, 3L, 1L, 0L))
        );
    }

    private static Stream<Arguments> executeProgramInstructionCaseProvider() {
        return Stream.of(
                Arguments.of(
                        new ThreeBitComputer(new long[]{0L, 0L, 9L}, List.of(2L, 6L)),
                        new long[]{0L, 1L, 9L},
                        new ArrayList<>(),
                        2),
                Arguments.of(
                        new ThreeBitComputer(new long[]{10L, 0L, 0L}, List.of(5L, 0L)),
                        new long[]{10L, 0L, 0L},
                        List.of(0L),
                        2),
                Arguments.of(
                        new ThreeBitComputer(new long[]{10L, 0L, 0L}, List.of(5L, 1L)),
                        new long[]{10, 0, 0},
                        List.of(1L),
                        2),
                Arguments.of(
                        new ThreeBitComputer(new long[]{10L, 0L, 0L}, List.of(5L, 4L)),
                        new long[]{10, 0, 0},
                        List.of(2L),
                        2),
                Arguments.of(
                        new ThreeBitComputer(new long[]{0L, 29L, 0L}, List.of(1L, 7L)),
                        new long[]{0, 26, 0},
                        new ArrayList<>(),
                        2),
                Arguments.of(
                        new ThreeBitComputer(new long[]{0L, 2024L, 43690L}, List.of(4L, 0L)),
                        new long[]{0, 44354, 43690},
                        new ArrayList<>(),
                        2)
        );
    }

    @Test
    void partOne() {
        var day = new Day17();
        var output = day.partOne(sample);
        assertEquals("4,6,3,5,6,3,5,2,1,0", output);
    }

    private static Stream<Arguments> isValidCaseProvider() {
        return Stream.of(
                Arguments.of(List.of(0L, 3L, 5L, 4L, 3L, 0L), List.of(0L), true),
                Arguments.of(List.of(0L, 3L, 5L, 4L, 3L, 0L), List.of(3L, 0L), true),
                Arguments.of(List.of(0L, 3L, 5L, 4L, 3L, 0L), List.of(1L, 0L), false)
        );
    }

    private static Stream<Arguments> findNextCaseProvider() {
        return Stream.of(
                Arguments.of(0L, List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L)),
                Arguments.of(3L, List.of(24L, 25L, 26L, 27L, 28L, 29L, 30L, 31L)),
                Arguments.of(24L, List.of(192L, 193L, 194L, 195L, 196L, 197L, 198L, 199L))
        );
    }

    @Test
    void createComputer() {
        var day = new Day17();
        var computer = day.createComputer(sample);
        assertNotNull(computer);
        assertArrayEquals(new long[]{729L, 0L, 0L}, computer.register());
        assertEquals(List.of(0L, 1L, 5L, 4L, 3L, 0L), computer.program());
    }

    @ParameterizedTest
    @MethodSource("executeProgramCaseProvider")
    void executeProgram(ThreeBitComputer computer, List<Long> expectedOutput) {
        var day = new Day17();
        var output = day.executeProgram(computer);
        assertEquals(expectedOutput, output);
    }

    @ParameterizedTest
    @MethodSource("executeProgramInstructionCaseProvider")
    void executeProgramInstruction(ThreeBitComputer computer, long[] expectedRegister, List<Long> expectedOutput, int expectedIndex) {
        var day = new Day17();
        List<Long> output = new ArrayList<>();
        var index = day.executeProgramInstruction(computer, 0, output);
        assertArrayEquals(expectedRegister, computer.register());
        assertEquals(expectedOutput, output);
        assertEquals(expectedIndex, index);
    }

    @ParameterizedTest
    @MethodSource("isValidCaseProvider")
    void isValid(List<Long> program, List<Long> output, boolean expectedValidity) {
        var day = new Day17();
        var validity = day.isValid(program, output);
        assertEquals(expectedValidity, validity);
    }

    @ParameterizedTest
    @MethodSource("findNextCaseProvider")
    void findNext(long from, List<Long> expectedNext) {
        var day = new Day17();
        var next = day.findNext(from);
        assertEquals(expectedNext, next);
    }

    @Test
    void partTwo() {
        var day = new Day17();
        String sample2 = """
                Register A: 2024
                Register B: 0
                Register C: 0
                                    
                Program: 0,3,5,4,3,0""";
        var output = day.partTwo(sample2);
        assertEquals(117440, output);
    }
}