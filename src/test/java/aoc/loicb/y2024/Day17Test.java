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
    private final String sample2 =
            """
                    Register A: 2024
                    Register B: 0
                    Register C: 0
                                        
                    Program: 0,3,5,4,3,0""";

    private static Stream<Arguments> executeProgramCaseProvider() {
        return Stream.of(
                Arguments.of(new ThreeBitComputer(new int[]{10, 0, 0}, List.of(5, 0, 5, 1, 5, 4)), List.of(0, 1, 2)),
                Arguments.of(new ThreeBitComputer(new int[]{2024, 0, 0}, List.of(0, 1, 5, 4, 3, 0)), List.of(4, 2, 5, 6, 7, 7, 7, 7, 3, 1, 0))
        );
    }

    private static Stream<Arguments> executeProgramInstructionCaseProvider() {
        return Stream.of(
                Arguments.of(
                        new ThreeBitComputer(new int[]{0, 0, 9}, List.of(2, 6)),
                        new int[]{0, 1, 9},
                        new ArrayList<>(),
                        2),
                Arguments.of(
                        new ThreeBitComputer(new int[]{10, 0, 0}, List.of(5, 0)),
                        new int[]{10, 0, 0},
                        List.of(0),
                        2),
                Arguments.of(
                        new ThreeBitComputer(new int[]{10, 0, 0}, List.of(5, 1)),
                        new int[]{10, 0, 0},
                        List.of(1),
                        2),
                Arguments.of(
                        new ThreeBitComputer(new int[]{10, 0, 0}, List.of(5, 4)),
                        new int[]{10, 0, 0},
                        List.of(2),
                        2),
                Arguments.of(
                        new ThreeBitComputer(new int[]{0, 29, 0}, List.of(1, 7)),
                        new int[]{0, 26, 0},
                        new ArrayList<>(),
                        2),
                Arguments.of(
                        new ThreeBitComputer(new int[]{0, 2024, 43690}, List.of(4, 0)),
                        new int[]{0, 44354, 43690},
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

    @Test
    void createComputer() {
        var day = new Day17();
        var computer = day.createComputer(sample);
        assertNotNull(computer);
        assertArrayEquals(new int[]{729, 0, 0}, computer.register());
        assertEquals(List.of(0, 1, 5, 4, 3, 0), computer.program());
    }

    @ParameterizedTest
    @MethodSource("executeProgramCaseProvider")
    void executeProgram(ThreeBitComputer computer, List<Integer> expectedOutput) {
        var day = new Day17();
        var output = day.executeProgram(computer);
        assertEquals(expectedOutput, output);
    }

    @ParameterizedTest
    @MethodSource("executeProgramInstructionCaseProvider")
    void executeProgramInstruction(ThreeBitComputer computer, int[] expectedRegister, List<Integer> expectedOutput, int expectedIndex) {
        var day = new Day17();
        List<Integer> output = new ArrayList<>();
        var index = day.executeProgramInstruction(computer, 0, output);
        assertArrayEquals(expectedRegister, computer.register());
        assertEquals(expectedOutput, output);
        assertEquals(expectedIndex, index);
    }

    @Test
    void partTwo() {
        var day = new Day17();
        var output = day.partTwo(sample2);
        assertEquals(117440, output);
    }
}