package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day14Test {

    private static final List<String> sample = List.of(
            "p=0,4 v=3,-3",
            "p=6,3 v=-1,-3",
            "p=10,3 v=-1,2",
            "p=2,0 v=2,-1",
            "p=0,0 v=1,3",
            "p=3,0 v=-2,-2",
            "p=7,6 v=-1,-3",
            "p=3,0 v=-1,-2",
            "p=9,3 v=2,3",
            "p=7,3 v=-1,2",
            "p=2,4 v=2,-3",
            "p=9,5 v=-3,-3"
    );


    @Test
    void calculateSafetyFactory() {
        var day = new Day14();
        var securityFactor = day.calculateSafetyFactory(sample, 11, 7);
        assertEquals(12, securityFactor);
    }
}