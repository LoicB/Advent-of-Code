package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.List;

public class Day2 implements Day<List<String>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day2());

    }

    @Override
    public Integer partOne(List<String> input) {
        return input
                .stream()
                .map(this::replaceInstructionPart1)
                .mapToInt(this::calculateRoundScore)
                .sum();
    }

    private String replaceInstructionPart1(String string) {
        char player;
        switch (string.charAt(2)) {
            case 'Y' -> player = 'B';
            case 'Z' -> player = 'C';
            default -> player = 'A';
        }
        return string.charAt(0) + " " + player;
    }

    protected Integer calculateRoundScore(String round) {
        return calculateShareScore(round) + calculateRoundOutcome(round);
    }

    protected Integer calculateShareScore(String round) {
        return (int) round.charAt(2) - 64;
    }

    protected Integer calculateRoundOutcome(String round) {
        if (hasElfWon(round)) return 0;
        if (hasPlayerWon(round)) return 6;
        return 3;
    }

    private boolean hasElfWon(String round) {
        boolean won;
        switch (round) {
            case "B A", "C B", "A C" -> won = true;
            default -> won = false;
        }
        return won;
    }

    private boolean hasPlayerWon(String round) {
        boolean won;
        switch (round) {
            case "C A", "A B", "B C" -> won = true;
            default -> won = false;
        }
        return won;
    }

    @Override
    public Integer partTwo(List<String> input) {
        return input
                .stream()
                .map(this::replaceInstructionPart2)
                .mapToInt(this::calculateRoundScore)
                .sum();
    }

    private String replaceInstructionPart2(String round) {
        char player;
        switch (round.charAt(2)) {
            case 'Y' -> player = round.charAt(0);
            case 'Z' -> player = shouldWin(round);
            default -> player = shouldLose(round);
        }
        return round.charAt(0) + " " + player;
    }

    private char shouldLose(String string) {
        char loses;
        switch (string.charAt(0)) {
            case 'B' -> loses = 'A';
            case 'C' -> loses = 'B';
            default -> loses = 'C';
        }
        return loses;
    }

    private char shouldWin(String string) {
        char wins;
        switch (string.charAt(0)) {
            case 'B' -> wins = 'C';
            case 'C' -> wins = 'A';
            default -> wins = 'B';
        }
        return wins;
    }
}
