package aoc.loicb.y2015;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.List;

public class Day8 implements Day<List<String>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day8());
    }

    @Override
    public Integer partOne(List<String> input) {
        return input.stream().mapToInt(String::length).sum()
                - input.stream().mapToInt(this::coundCharacters).sum();
    }

    Integer coundCharacters(String input) {
        return unescape(input).length() - 2;
    }

    private String unescape(String s) {
        return s
                .replace("\\\\", "?")
                .replace("\\\"", "\"")
                .replace("\\[a-z]", "?")
                .replaceAll("\\\\x[0-9a-f]{2}", "?")
                ;
    }

    @Override
    public Integer partTwo(List<String> input) {
        return input.stream().mapToInt(this::escapeCount).sum()
                - input.stream().mapToInt(String::length).sum();
    }

    int escapeCount(String input) {
        return escape(input).length();
    }

    String escape(String input) {
        StringBuilder sb = new StringBuilder();
        sb.append('"');
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '"') sb.append('\\');
            if (input.charAt(i) == '\\') sb.append('\\');
            sb.append(input.charAt(i));
        }
        sb.append('"');
        return sb.toString();
    }

}
