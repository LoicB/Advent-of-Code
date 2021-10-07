package aoc.loicb.y2015;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToStringArray;

import java.util.stream.IntStream;

public class Day5 implements Day<String[], Integer> {

    public static void main(String[] args) {
        DayExecutor<String[]> de = new DayExecutor<>(new InputToStringArray());
        de.execute(new Day5());

    }

    @Override
    public Integer partOne(String[] input) {
        return (int) IntStream.range(0, input.length).filter(index -> isNicePart1(input[index])).count();
    }

    protected boolean isNicePart1(String string) {
        return hasThreeVowels(string) && hasLetterTwice(string) && doesNotContain(string);
    }

    protected boolean hasThreeVowels(String string) {
        return string.chars().filter(this::isVowel).count() >= 3L;
    }

    protected boolean isVowel(int c) {
        return c == 'a' || c == 'e' || c == 'u' || c == 'i' || c == 'o';
    }

    protected boolean hasLetterTwice(String string) {
        for (int i = 1; i < string.length(); i++) {
            if (string.charAt(i - 1) == string.charAt(i)) return true;
        }
        return false;
    }

    protected boolean doesNotContain(String string) {
        return string.indexOf("ab") + string.indexOf("cd") + string.indexOf("pq") + string.indexOf("xy") == -4;
    }


    @Override
    public Integer partTwo(String[] input) {
        return (int) IntStream.range(0, input.length).filter(index -> isNicePart2(input[index])).count();
    }

    protected boolean isNicePart2(String string) {
        return hasPairNoOverlap(string) && hasLetterTwiceWithOneBetween(string);
    }

    protected boolean hasPairNoOverlap(String string) {
        for (int i = 1; i < string.length(); i++) {
            for (int j = i + 2; j < string.length(); j++) {
                if (string.charAt(i - 1) == string.charAt(j - 1) && string.charAt(i) == string.charAt(j)) return true;
            }
        }
        return false;
    }

    protected boolean hasLetterTwiceWithOneBetween(String string) {
        for (int i = 2; i < string.length(); i++) {
            if (string.charAt(i - 2) == string.charAt(i)) return true;
        }
        return false;
    }
}
