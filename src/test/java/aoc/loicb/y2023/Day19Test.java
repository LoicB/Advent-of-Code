package aoc.loicb.y2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19Test {
    private static final List<String> SAMPLE = List.of(
            "px{a<2006:qkq,m>2090:A,rfg}",
            "pv{a>1716:R,A}",
            "lnx{m>1548:A,A}",
            "rfg{s<537:gd,x>2440:R,A}",
            "qs{s>3448:A,lnx}",
            "qkq{x<1416:A,crn}",
            "crn{x>2662:A,R}",
            "in{s<1351:px,qqz}",
            "qqz{s>2770:qs,m<1801:hdj,R}",
            "gd{a>3333:R,R}",
            "hdj{m>838:A,pv}",
            "",
            "{x=787,m=2655,a=1222,s=2876}",
            "{x=1679,m=44,a=2067,s=496}",
            "{x=2036,m=264,a=79,s=2244}",
            "{x=2461,m=1339,a=466,s=291}",
            "{x=2127,m=1623,a=2188,s=1013}"
    );

    private static Stream<Arguments> getWorkflowKeyCaseProvider() {
        return Stream.of(
                Arguments.of("px{a<2006:qkq,m>2090:A,rfg}", "px"),
                Arguments.of("pv{a>1716:R,A}", "pv"),
                Arguments.of("lnx{m>1548:A,A}", "lnx"),
                Arguments.of("rfg{s<537:gd,x>2440:R,A}", "rfg"),
                Arguments.of("qs{s>3448:A,lnx}", "qs"),
                Arguments.of("qkq{x<1416:A,crn}", "qkq"),
                Arguments.of("crn{x>2662:A,R}", "crn"),
                Arguments.of("in{s<1351:px,qqz}", "in"),
                Arguments.of("qqz{s>2770:qs,m<1801:hdj,R}", "qqz"),
                Arguments.of("gd{a>3333:R,R}", "gd"),
                Arguments.of("hdj{m>838:A,pv}", "hdj")
        );
    }

    private static Stream<Arguments> getWorkflowOperationsCaseProvider() {
        return Stream.of(
                Arguments.of(new Day19.Part(0, 0, 0, 0), "qkq"),
                Arguments.of(new Day19.Part(0, 0, 2005, 0), "qkq"),
                Arguments.of(new Day19.Part(0, 0, 2006, 0), "rfg"),
                Arguments.of(new Day19.Part(0, 2090, 2006, 0), "rfg"),
                Arguments.of(new Day19.Part(0, 2091, 2006, 0), "A"),
                Arguments.of(new Day19.Part(0, 2091, 2005, 0), "qkq")
        );
    }

    private static Stream<Arguments> recoverCalibrationCaseProvider() {
        return Stream.of(
                Arguments.of("{x=787,m=2655,a=1222,s=2876}", 787, 2655, 1222, 2876),
                Arguments.of("{x=1679,m=44,a=2067,s=496}", 1679, 44, 2067, 496),
                Arguments.of("{x=2036,m=264,a=79,s=2244}", 2036, 264, 79, 2244),
                Arguments.of("{x=2461,m=1339,a=466,s=291}", 2461, 1339, 466, 291),
                Arguments.of("{x==2127,m=1623,a=2188,s=1013}", 2127, 1623, 2188, 1013)
        );
    }

    @Test
    void partOne() {
        var day = new Day19();
        var cubicMeters = day.partOne(SAMPLE);
        assertEquals(19114, cubicMeters);
    }

    @ParameterizedTest
    @MethodSource("getWorkflowKeyCaseProvider")
    void getWorkflowKey(String input, String expectedKey) {
        var day = new Day19();
        var key = day.getWorkflowKey(input);
        assertEquals(expectedKey, key);
    }

    @ParameterizedTest
    @MethodSource("getWorkflowOperationsCaseProvider")
    void getWorkflowOperations(Day19.Part part, String expectedKey) {
        var day = new Day19();
        var function = day.getWorkflowOperations("px{a<2006:qkq,m>2090:A,rfg}");
        assertEquals(expectedKey, function.apply(part));
    }

    @ParameterizedTest
    @MethodSource("recoverCalibrationCaseProvider")
    void recoverCalibration(String input, int expectedX, int expectedM, int expectedA, int expectedS) {
        var day = new Day19();
        var pars = day.extractPart(input);
        assertEquals(expectedX, pars.x());
        assertEquals(expectedM, pars.m());
        assertEquals(expectedA, pars.a());
        assertEquals(expectedS, pars.s());
    }

    @Test
    void partTwo() {
        var day = new Day19();
        var combinations = day.partTwo(SAMPLE);
        assertEquals(167409079868000L, combinations);
    }


    @Test
    void getWorkflowOperationsPartTwo() {
        var day = new Day19();
        var function = day.getWorkflowOperationsPartTwo("px{a<2006:qkq,m>2090:A,rfg}");
        var output = function.apply(Day19.RangePart.startingRange());
        assertEquals(3, output.size());
        assertEquals("qkq", output.get(0).currentKey());
        assertEquals(1, output.get(0).fromA());
        assertEquals(2005, output.get(0).toA());
        assertEquals("A", output.get(1).currentKey());
        assertEquals(2006, output.get(1).fromA());
        assertEquals(4000, output.get(1).toA());
        assertEquals(2091, output.get(1).fromM());
        assertEquals(4000, output.get(1).toM());
        assertEquals("rfg", output.get(2).currentKey());
        assertEquals(2006, output.get(2).fromA());
        assertEquals(4000, output.get(2).toA());
        assertEquals(1, output.get(2).fromM());
        assertEquals(2090, output.get(2).toM());
    }
}