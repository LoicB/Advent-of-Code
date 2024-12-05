package aoc.loicb.y2024;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputTransformer;
import com.github.javaparser.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 implements Day<SafetyManual, Integer> {
    public static void main(String[] args) {
        DayExecutor<SafetyManual> de = new DayExecutor<>(new Day5InputTransformer());

        de.execute(new Day5());
    }

    @Override
    public Integer partOne(SafetyManual input) {
        return input.pages()
                .stream()
                .filter(integers -> isUpdateInRightOrder(integers, input.rules()))
                .mapToInt(this::getMiddlePageNumber)
                .sum();
    }

    boolean isUpdateInRightOrder(List<Integer> pages, List<Pair<Integer, Integer>> rules) {
        return rules.stream().allMatch(integerIntegerPair -> isUpdateInRightOrder(pages, integerIntegerPair));
    }

    boolean isUpdateInRightOrder(List<Integer> pages, Pair<Integer, Integer> rule) {
        return pages.indexOf(rule.a) <= pages.indexOf(rule.b) || !pages.contains(rule.b);
    }

    int getMiddlePageNumber(List<Integer> pages) {
        return pages.get(pages.size() / 2);
    }

    @Override
    public Integer partTwo(SafetyManual input) {
        return input.pages()
                .stream()
                .filter(integers -> !isUpdateInRightOrder(integers, input.rules()))
                .map(pages -> fixPageOrdering(pages, input.rules()))
                .mapToInt(this::getMiddlePageNumber)
                .sum();
    }

    List<Integer> fixPageOrdering(List<Integer> pages, List<Pair<Integer, Integer>> rules) {
        var newPages = new ArrayList<>(pages);
        boolean keepGoing = true;
        while (keepGoing) {
            keepGoing = false;
            var breakingRule = rules
                    .stream()
                    .filter(integerIntegerPair -> !isUpdateInRightOrder(newPages, integerIntegerPair))
                    .findFirst();
            if (breakingRule.isPresent()) {
                keepGoing = true;
                var rule = breakingRule.get();
                int rightIndex = newPages.indexOf(rule.a);
                newPages.remove(rule.b);
                newPages.add(rightIndex, rule.b);
            }
        }
        return newPages;
    }

}

record SafetyManual(List<Pair<Integer, Integer>> rules, List<List<Integer>> pages) {
}


class Day5InputTransformer implements InputTransformer<SafetyManual> {
    @Override
    public SafetyManual transform(String rawInput) {
        String[] inputLines = rawInput.split("\\r?\\n");
        int index = 0;
        List<Pair<Integer, Integer>> rules = new ArrayList<>();
        while (!inputLines[index].isEmpty()) {
            String[] ruleStr = inputLines[index].split("\\|");
            rules.add(new Pair<>(Integer.parseInt(ruleStr[0]), Integer.parseInt(ruleStr[1])));
            index++;
        }
        index++;
        List<List<Integer>> pages = new ArrayList<>();
        while (index < inputLines.length) {
            pages.add(Arrays.stream(inputLines[index].split(",")).map(Integer::parseInt).toList());
            index++;
        }
        return new SafetyManual(rules, pages);
    }
}