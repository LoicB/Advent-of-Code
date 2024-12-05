package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.List;

public class Day4 implements Day<List<String>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day4());

    }

    @Override
    public Integer partOne(List<String> input) {
        int count = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                count += countXmas(input, i, j);
            }
        }
        return count;
    }

    protected int countXmas(List<String> input, int x, int y) {
        if (input.get(x).charAt(y) != 'X') return 0;
        return countXmasLetters(input, x, y, 1, -1, -1)
                + countXmasLetters(input, x, y, 1, -1, 0)
                + countXmasLetters(input, x, y, 1, -1, 1)
                + countXmasLetters(input, x, y, 1, 0, -1)
                + countXmasLetters(input, x, y, 1, 0, 1)
                + countXmasLetters(input, x, y, 1, 1, -1)
                + countXmasLetters(input, x, y, 1, 1, 0)
                + countXmasLetters(input, x, y, 1, 1, 1);
    }

    private int countXmasLetters(List<String> input, int x, int y, int index, int directionX, int directionY) {
        char c = getValueSafely(input, x + index * directionX, y + index * directionY);
        if (index == 1 && c == 'M'
                || index == 2 && c == 'A') return countXmasLetters(input, x, y, index + 1, directionX, directionY);
        if (index == 3 && c == 'S') return 1;
        return 0;
    }

    private char getValueSafely(List<String> input, int x, int y) {
        try {
            return input.get(x).charAt(y);
        } catch (Exception ex) {
            return '.';
        }
    }

    @Override
    public Integer partTwo(List<String> input) {
        int count = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (isValidCross(input, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    protected boolean isValidCross(List<String> input, int x, int y) {
        if (input.get(x).charAt(y) != 'A') return false;
        List<Character> cross = List.of(getValueSafely(input, x - 1, y - 1), getValueSafely(input, x - 1, y + 1), getValueSafely(input, x + 1, y + 1), getValueSafely(input, x + 1, y - 1));
        if (cross.get(0) == cross.get(2) && cross.get(1) == cross.get(3)) return false;
        return cross.stream().filter(character -> character == 'M').count() == 2
                && cross.stream().filter(character -> character == 'S').count() == 2;
    }
}
