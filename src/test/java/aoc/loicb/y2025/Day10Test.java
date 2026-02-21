package aoc.loicb.y2025;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {
    private final List<String> machines =
            List.of(
                    "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}",
                    "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}",
                    "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}"
            );

    private static Stream<Arguments> findFewestButtonPressesCaseProvider() {
        return Stream.of(
                Arguments.of("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7", 2),
                Arguments.of("[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}", 3),
                Arguments.of("[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}", 2)
        );
    }

    private static Stream<Arguments> findFewestButtonPressesJoltageCaseProvider() {
        return Stream.of(
                Arguments.of("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}", 10),
                Arguments.of("[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}", 12),
                Arguments.of("[..##.#...#] (0,3,4,7,9) (0,1,9) (1,2,3,4,5) (0,1,3,7,8) (1,3,4,5,6,7,9) (0,1,2,4,5,6,7,8) (0,1,2,3,5,6,8) (1,2,4,5,8,9) (0,4,5,6,7) (0,2,3,5,8,9) (0,2,6,7,8,9) {87,70,44,58,58,67,44,54,55,54}", 110),
                Arguments.of("[........#] (3,5) (1,2,5,6,7,8) (0,1,5) (1,3,4,8) (1,2,3,4,5,8) (0,1,6) (0,2,3,4,5,8) (2,3,4,5,6,7) (1,2,3,4,5) (1,2,3,4,5,7,8) (0,1,2,4,5,6,7,8) {65,110,97,89,92,122,60,55,80}", 148),
                Arguments.of("[##...##.##] (3,5) (1,3,4,5,9) (5,6,7) (1,2,4,5,6,7,8,9) (0,1,4,5,6,7,8,9) (8,9) (0,2,5,9) (0,4,7,9) (1,3,5,7) (0) (0,1,2) {32,47,21,8,35,53,45,46,32,37}", 67),
                Arguments.of("[#.#######.] (1,8) (0,4,5,7,9) (0,2,3,4,5,6,7,8) (1,3,7) (0,6) (1,5,6) (1,8,9) (2,3,4,5,6,8) (4,6,8) (1,7) (0,3,4,5,7) (3,6,7,8,9) (2,3,5,7) {63,149,19,41,63,59,48,171,55,28}", 226),
                Arguments.of("[##..######] (0,3,4,5,8,9) (1,2,3,4,5,6,8) (0,1,3,4,7,8) (3,4,9) (0,1,2,3,4,7,8,9) (2,5,7,8,9) (1) (4,5,6,7,9) (0,1,2,3,4,6,7,9) (0,4,5,8,9) (0,1,2,4,5,7,8) (1,2,3,4,5,6,9) (0,2,3,5,8,9) {48,72,51,74,94,58,36,60,77,64}", 114),
                Arguments.of("[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}", 11)
        );
    }

    @Test
    void partOne() {
        var day = new Day10();
        var buttonPresses = day.partOne(machines.stream().map(FactoryMachine::fromLine).toList());
        assertEquals(7, buttonPresses);
    }

    @ParameterizedTest
    @MethodSource("findFewestButtonPressesCaseProvider")
    void findFewestButtonPresses(String machine, int expectedPresses) {
        var day = new Day10();
        var factoryMachine = FactoryMachine.fromLine(machine);
        var numberOfPresses = day.findFewestButtonPresses(factoryMachine.lightDiagram(), factoryMachine.buttons());
        assertEquals(expectedPresses, numberOfPresses);
    }

    @Test
    void partTwo() {
        var day = new Day10();
        var buttonPresses = day.partTwo(machines.stream().map(FactoryMachine::fromLine).toList());
        assertEquals(33, buttonPresses);
    }

    @ParameterizedTest
    @MethodSource("findFewestButtonPressesJoltageCaseProvider")
    void findFewestButtonPressesJoltage(String machine, int expectedPresses) {
        var day = new Day10();
        var factoryMachine = FactoryMachine.fromLine(machine);
        var numberOfPresses = day.findFewestButtonPressesJoltage(factoryMachine.joltages(), factoryMachine.buttons());
        assertEquals(expectedPresses, numberOfPresses);
    }
}