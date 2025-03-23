package aoc.loicb.y2024;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day23Test {


    private List<String> prepareTestInput() throws IOException {
        String[] arr = Files.readString(Path.of("./src/test/resources/aoc.loicb.y2024/Day23.txt")).split("\\r?\\n");
        return Arrays.asList(arr);
    }

    @Test
    void partOne() throws IOException {
        var connections = prepareTestInput();
        var day = new Day23();
        var connectedComputer = day.partOne(connections);
        assertEquals(7, connectedComputer);
    }

    @Test
    void partTwo() throws IOException {
        var connections = prepareTestInput();
        var day = new Day23();
        var connectedComputer = day.partTwo(connections);
        assertEquals("co,de,ka,ta", connectedComputer);
    }
}