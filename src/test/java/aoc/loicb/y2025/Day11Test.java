package aoc.loicb.y2025;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {
    private final Map<String, List<String>> inputPartOne = Day11.transformInput("""
            aaa: you hhh
            you: bbb ccc
            bbb: ddd eee
            ccc: ddd eee fff
            ddd: ggg
            eee: out
            fff: out
            ggg: out
            hhh: ccc fff iii
            iii: out""");
    private final Map<String, List<String>> inputPartTwo = Day11.transformInput("""
            svr: aaa bbb
            aaa: fft
            fft: ccc
            bbb: tty
            tty: ccc
            ccc: ddd eee
            ddd: hub
            hub: fff
            eee: dac
            dac: fff
            fff: ggg hhh
            ggg: out
            hhh: out""");

    @Test
    void partOne() {
        var day = new Day11();
        var paths = day.partOne(inputPartOne);
        assertEquals(5L, paths);
    }

    @Test
    void partTwo() {
        var day = new Day11();
        var paths = day.partTwo(inputPartTwo);
        assertEquals(2L, paths);
    }
}