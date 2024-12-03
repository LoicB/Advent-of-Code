package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {

    @Test
    void partOne() {
        var input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
        var day = new Day3();
        int sumInstructions = day.partOne(input);
        assertEquals(161, sumInstructions);
    }

    @Test
    void partTwo() {
        var input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";
        var day = new Day3();
        int sumInstructions = day.partTwo(input);
        assertEquals(48, sumInstructions);
    }

}