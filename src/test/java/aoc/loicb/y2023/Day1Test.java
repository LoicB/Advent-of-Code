package aoc.loicb.y2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

    private static Stream<Arguments> recoverCalibrationCaseProvider() {
        return Stream.of(
                Arguments.of("1abc2", 12),
                Arguments.of("pqr3stu8vwx", 38),
                Arguments.of("a1b2c3d4e5f", 15),
                Arguments.of("treb7uchet", 77)
        );
    }

    private static Stream<Arguments> recoverCalibrationLettersCaseProvider() {
        return Stream.of(
                Arguments.of("two1nine", 29),
                Arguments.of("eightwothree", 83),
                Arguments.of("abcone2threexyz", 13),
                Arguments.of("xtwone3four", 24),
                Arguments.of("4nineeightseven2", 42),
                Arguments.of("zoneight234", 14),
                Arguments.of("7pqrstsixteen", 76)
        );
    }

    @Test
    void partOne() {
        var input = List.of("1abc2",
                "pqr3stu8vwx",
                "a1b2c3d4e5f",
                "treb7uchet");
        Day1 day = new Day1();
        int calibration = day.partOne(input);
        assertEquals(142, calibration);
    }

    @ParameterizedTest
    @MethodSource("recoverCalibrationCaseProvider")
    void recoverCalibration(String input, int expectedCalibration) {
        Day1 day = new Day1();
        int floor = day.recoverCalibration(input);
        assertEquals(expectedCalibration, floor);
    }

    @Test
    void partTwo() {
        var input = List.of("two1nine",
                "eightwothree",
                "abcone2threexyz",
                "xtwone3four",
                "4nineeightseven2",
                "zoneight234",
                "7pqrstsixteen");
        Day1 day = new Day1();
        int calibration = day.partTwo(input);
        assertEquals(281, calibration);
    }

    @ParameterizedTest
    @MethodSource("recoverCalibrationLettersCaseProvider")
    void recoverCalibrationLetters(String input, int expectedCalibration) {
        Day1 day = new Day1();
        int floor = day.recoverCalibrationLetters(input);
        assertEquals(expectedCalibration, floor);
    }
}