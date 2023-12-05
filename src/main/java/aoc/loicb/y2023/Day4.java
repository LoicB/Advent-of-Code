package aoc.loicb.y2023;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4 implements Day<List<String>, Integer> {

    private final Pattern p = Pattern.compile("\\d+");

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
        return input
                .stream()
                .mapToInt(this::calculatePoints)
                .sum();
    }

    int calculatePoints(String input) {
        Set<Integer> commonNumbers = getCommonNumbers(input);
        if (commonNumbers.isEmpty()) return 0;
        return Double.valueOf(Math.pow(2, commonNumbers.size() - 1)).intValue();
    }

    private Set<Integer> getCommonNumbers(String input) {
        Set<Integer> playedNumbers = getPlayedNumbers(input);
        Set<Integer> winningNumbers = getWinningNumbers(input);
        return playedNumbers
                .stream()
                .filter(winningNumbers::contains)
                .collect(Collectors.toSet());
    }

    Set<Integer> getPlayedNumbers(String input) {
        return getNumbers(input.substring(input.indexOf(':') + 1, input.indexOf('|')));
    }

    Set<Integer> getWinningNumbers(String input) {
        return getNumbers(input.substring(input.indexOf('|') + 1));
    }

    private Set<Integer> getNumbers(String playedRepresentation) {
        Matcher m = p.matcher(playedRepresentation);
        Set<Integer> numbers = new HashSet<>();
        while (m.find()) {
            numbers.add(Integer.parseInt(m.group()));
        }
        return numbers;
    }

    @Override
    public Integer partTwo(List<String> input) {
        int[] numberOfCards = new int[input.size()];
        Arrays.fill(numberOfCards, 1);
        for (int i = 0; i < input.size(); i++) {
            int numberOfWinningNumbers = getCommonNumbers(input.get(i)).size();
            for (int j = i; j < i + numberOfWinningNumbers; j++) {
                numberOfCards[j + 1] += numberOfCards[i];
            }
        }
        return Arrays.stream(numberOfCards).sum();
    }


}