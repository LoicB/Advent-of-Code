package aoc.loicb.y2021;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToStringArray;

import java.util.*;
import java.util.function.Function;

public class Day10 implements Day<String[], Number> {
    public static void main(String[] args) {
        DayExecutor<String[]> de = new DayExecutor<>(new InputToStringArray());
        de.execute(new Day10());
    }

    @Override
    public Integer partOne(String[] navigationSubsystem) {
        return Arrays.stream(navigationSubsystem).map(this::foundInvalidCharacter).filter(Optional::isPresent).mapToInt(invalid -> calculateScoreInvalid(invalid.get())).sum();
    }

    private int calculateScoreInvalid(char c) {
        if (c == ')') return 3;
        if (c == ']') return 57;
        if (c == '}') return 1197;
        return 25137;
    }

    protected Optional<Character> foundInvalidCharacter(String line) {
        return evaluateSystemLine(line, Optional::of, characters -> Optional.empty());
    }

    private boolean isChunkOpener(char c) {
        return c == '(' || c == '[' || c == '{' || c == '<';
    }

    private char getChunkOpener(char c) {
        if (c == ')') return '(';
        if (c == ']') return '[';
        if (c == '}') return '{';
        return '<';
    }

    private char getChunkCloser(char c) {
        if (c == '(') return ')';
        if (c == '[') return ']';
        if (c == '{') return '}';
        return '>';
    }

    @Override
    public Long partTwo(String[] navigationSubsystem) {
        List<Long> scores = Arrays.stream(navigationSubsystem)
                .map(this::getRemainingChar)
                .filter(characters -> !characters.isEmpty())
                .map(this::calculateScoreRemaining).sorted().toList();
        return scores.get(scores.size() / 2);
    }

    protected List<Character> getRemainingChar(String line) {
        return evaluateSystemLine(line, character -> new ArrayList<>(), this::findRemainingFromCurrentStack);
    }

    private List<Character> findRemainingFromCurrentStack(LinkedList<Character> stack) {
        List<Character> remainingChar = new ArrayList<>(stack.size());
        while (!stack.isEmpty()) {
            remainingChar.add(getChunkCloser(stack.removeLast()));
        }
        return remainingChar;
    }

    protected long calculateScoreRemaining(List<Character> chars) {
        long score = 0;
        for (char c : chars) {
            score *= 5;
            score += calculateScoreRemaining(c);
        }
        return score;
    }

    private int calculateScoreRemaining(char c) {
        if (c == ')') return 1;
        if (c == ']') return 2;
        if (c == '}') return 3;
        return 4;
    }

    private <T> T evaluateSystemLine(String line, Function<Character, T> invalid, Function<LinkedList<Character>, T> remaining) {
        LinkedList<Character> queue = new LinkedList<>();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (isChunkOpener(c)) {
                queue.add(c);
            } else {
                if (queue.isEmpty() || queue.removeLast() != getChunkOpener(c)) return invalid.apply(c);
            }
        }
        return remaining.apply(queue);
    }

}
