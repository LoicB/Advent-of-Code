package aoc.loicb.y2024;

import com.github.javaparser.utils.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

    @Test
    void partOne() {
        var input = List.of(new Pair<>(3, 4),
                new Pair<>(4, 3),
                new Pair<>(2, 5),
                new Pair<>(1, 3),
                new Pair<>(3, 9),
                new Pair<>(3, 3));
        Day1 day = new Day1();
        int totalDistance = day.partOne(input);
        assertEquals(11, totalDistance);


    }

    @Test
    void partTwo() {
        var input = List.of(new Pair<>(3, 4),
                new Pair<>(4, 3),
                new Pair<>(2, 5),
                new Pair<>(1, 3),
                new Pair<>(3, 9),
                new Pair<>(3, 3));
        Day1 day = new Day1();
        int similarity = day.partTwo(input);
        assertEquals(31, similarity);
    }
}