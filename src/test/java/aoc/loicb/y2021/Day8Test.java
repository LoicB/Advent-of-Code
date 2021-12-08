package aoc.loicb.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {
    private static Stream<Arguments> partOneCaseProvider() {
        return Stream.of(
                Arguments.of(List.of(new Signal(Collections.emptyList(), List.of("cdfeb", "fcadb", "cdfeb", "cdbaf"))), 0),
                Arguments.of(List.of(new Signal(Collections.emptyList(), List.of("fdgacbe", "cefdb", "cefbgd", "gcbe"))), 2),
                Arguments.of(List.of(new Signal(Collections.emptyList(), List.of("fcgedb", "cgb", "dgebacf", "gc"))), 3),
                Arguments.of(List.of(new Signal(Collections.emptyList(), List.of("cg", "cg", "fdcagb", "cbg"))), 3),
                Arguments.of(List.of(new Signal(Collections.emptyList(), List.of("efabcd", "cedba", "gadfec", "cb"))), 1),
                Arguments.of(List.of(new Signal(Collections.emptyList(), List.of("gecf", "egdcabf", "bgf", "bfgea"))), 3),
                Arguments.of(List.of(new Signal(Collections.emptyList(), List.of("gebdcfa", "ecba", "ca", "fadegcb"))), 4),
                Arguments.of(List.of(new Signal(Collections.emptyList(), List.of("cefg", "dcbef", "fcge", "gbcadfe"))), 3),
                Arguments.of(List.of(new Signal(Collections.emptyList(), List.of("ed", "bcgafe", "cdgba", "cbgef"))), 1),
                Arguments.of(List.of(new Signal(Collections.emptyList(), List.of("gbdfcae", "bgc", "cg", "cgb"))), 4),
                Arguments.of(List.of(new Signal(Collections.emptyList(), List.of("fgae", "cfgab", "fg", "bagce"))), 2)
        );
    }

    private static Stream<Arguments> partTwoCaseProvider() {
        return Stream.of(
                Arguments.of(List.of(new Signal(List.of("acedgfb", "cdfbe", "gcdfa", "fbcad", "dab", "cefabd", "cdfgeb", "eafb", "cagedb", "ab"), List.of("cdfeb", "fcadb", "cdfeb", "cdbaf"))), 5353),
                Arguments.of(List.of(new Signal(List.of("be", "cfbegad", "cbdgef", "fgaecd", "cgeb", "fdcge", "agebfd", "fecdb", "fabcd", "edb"), List.of("fdgacbe", "cefdb", "cefbgd", "gcbe"))), 8394),
                Arguments.of(List.of(new Signal(List.of("edbfga", "begcd", "cbg", "gc", "gcadebf", "fbgde", "acbgfd", "abcde", "gfcbed", "gfec"), List.of("fcgedb", "cgb", "dgebacf", "gc"))), 9781),
                Arguments.of(List.of(new Signal(List.of("fgaebd", "cg", "bdaec", "gdafb", "agbcfd", "gdcbef", "bgcad", "gfac", "gcb", "cdgabef"), List.of("cg", "cg", "fdcagb", "cbg"))), 1197),
                Arguments.of(List.of(new Signal(List.of("fbegcd", "cbd", "adcefb", "dageb", "afcb", "bc", "aefdc", "ecdab", "fgdeca", "fcdbega"), List.of("efabcd", "cedba", "gadfec", "cb"))), 9361),
                Arguments.of(List.of(new Signal(List.of("aecbfdg", "fbg", "gf", "bafeg", "dbefa", "fcge", "gcbea", "fcaegb", "dgceab", "fcbdga"), List.of("gecf", "egdcabf", "bgf", "bfgea"))), 4873),
                Arguments.of(List.of(new Signal(List.of("fgeab", "ca", "afcebg", "bdacfeg", "cfaedg", "gcfdb", "baec", "bfadeg", "bafgc", "acf"), List.of("gebdcfa", "ecba", "ca", "fadegcb"))), 8418),
                Arguments.of(List.of(new Signal(List.of("dbcfg", "fgd", "bdegcaf", "fgec", "aegbdf", "ecdfab", "fbedc", "dacgb", "gdcebf", "gf"), List.of("cefg", "dcbef", "fcge", "gbcadfe"))), 4548),
                Arguments.of(List.of(new Signal(List.of("bdfegc", "cbegaf", "gecbf", "dfcage", "bdacg", "ed", "bedf", "ced", "adcbefg", "gebcd"), List.of("ed", "bcgafe", "cdgba", "cbgef"))), 1625),
                Arguments.of(List.of(new Signal(List.of("egadfb", "cdbfeg", "cegd", "fecab", "cgb", "gbdefca", "cg", "fgcdab", "egfdb", "bfceg"), List.of("gbdfcae", "bgc", "cg", "cgb"))), 8717),
                Arguments.of(List.of(new Signal(List.of("gcafb", "gcf", "dcaebfg", "ecagb", "gf", "abcdeg", "gaef", "cafbge", "fdbac", "fegbdc"), List.of("fgae", "cfgab", "fg", "bagce"))), 4315)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneCaseProvider")
    void partOne(List<Signal> signals, int expectedNumberOfSimpleNumbers) {
        Day8 day = new Day8();
        int simpleNumbers = day.partOne(signals);
        assertEquals(expectedNumberOfSimpleNumbers, simpleNumbers);
    }

    @ParameterizedTest
    @MethodSource("partTwoCaseProvider")
    void partTwo(List<Signal> signals, int expectedNumberOfSimpleNumbers) {
        Day8 day = new Day8();
        int simpleNumbers = day.partTwo(signals);
        assertEquals(expectedNumberOfSimpleNumbers, simpleNumbers);
    }
}