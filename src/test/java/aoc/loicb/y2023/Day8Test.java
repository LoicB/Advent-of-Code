package aoc.loicb.y2023;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {
    @Test
    void partOne() {
        var input = List.of("RL",
                "",
                "AAA = (BBB, CCC)",
                "BBB = (DDD, EEE)",
                "CCC = (ZZZ, GGG)",
                "DDD = (DDD, DDD)",
                "EEE = (EEE, EEE)",
                "GGG = (GGG, GGG)",
                "ZZZ = (ZZZ, ZZZ)");
        var day = new Day8();
        var steps = day.partOne(input);
        assertEquals(2L, steps);
    }

    @Test
    void partOneSample2() {
        var input = List.of("LLR",
                "",
                "AAA = (BBB, BBB)",
                "BBB = (AAA, ZZZ)",
                "ZZZ = (ZZZ, ZZZ)");
        var day = new Day8();
        var steps = day.partOne(input);
        assertEquals(6L, steps);
    }

    @Test
    void partTwo() {
        var input = List.of("LR",
                "",
                "11A = (11B, XXX)",
                "11B = (XXX, 11Z)",
                "11Z = (11B, XXX)",
                "22A = (22B, XXX)",
                "22B = (22C, 22C)",
                "22C = (22Z, 22Z)",
                "22Z = (22B, 22B)",
                "XXX = (XXX, XXX)");
        var day = new Day8();
        var steps = day.partTwo(input);
        assertEquals(6L, steps);
    }
}