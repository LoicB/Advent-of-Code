package aoc.loicb.y2021;

import aoc.loicb.DayExecutor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {

    @Test
    void partOne() throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2021/Day4.txt"));
        DayExecutor<Bingo> de = new DayExecutor<>(new InputToBingo());
        Bingo bingo = de.transformer().transform(rawInput);
        Day4 day = new Day4();
        int score = day.partOne(bingo);
        assertEquals(4512, score);
    }

    @Test
    void partTwo() throws IOException {
        String rawInput = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2021/Day4.txt"));
        DayExecutor<Bingo> de = new DayExecutor<>(new InputToBingo());
        Bingo bingo = de.transformer().transform(rawInput);
        Day4 day = new Day4();
        int score = day.partTwo(bingo);
        assertEquals(1924, score);
    }
}