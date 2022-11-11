package aoc.loicb.y2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day21Test {

    @Test
    void partOne() {
        int expected = 739785;
        Day21 day = new Day21();
        int product = day.partOne(new int[]{4, 8});
        assertEquals(expected, product);
    }

    @Test
    void partTwo() {
        long expected = 444356092776315L;
        Day21 day = new Day21();
        long product = day.partTwo(new int[]{4, 8});
        assertEquals(expected, product);
    }
}