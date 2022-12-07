package aoc.loicb.y2022;

import aoc.loicb.Day;
import aoc.loicb.DayExecutor;
import aoc.loicb.InputToObjectList;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Day3 implements Day<List<String>, Integer> {
    public static void main(String[] args) {
        DayExecutor<List<String>> de = new DayExecutor<>(new InputToObjectList<>() {
            @Override
            public String transformObject(String string) {
                return string;
            }
        });
        de.execute(new Day3());

    }

    @Override
    public Integer partOne(List<String> input) {
        return input
                .stream()
                .map(this::getCompartments)
                .map(compartments -> findCommonItem(compartments[0], compartments[1]))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .mapToInt(this::convertToPriority)
                .sum();
    }

    protected String[] getCompartments(String rucksack) {
        return new String[]{rucksack.substring(0, rucksack.length() / 2), rucksack.substring(rucksack.length() / 2)};
    }

    protected Optional<Character> findCommonItem(String firstCompartment, String secondCompartment) {
        Set<Character> firstCompartmentItems = firstCompartment.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
        return secondCompartment.chars().mapToObj(e -> (char) e).filter(firstCompartmentItems::contains).findFirst();
    }

    protected int convertToPriority(char item) {
        return (int) item - (Character.isLowerCase(item) ? 96 : 38);
    }


    @Override
    public Integer partTwo(List<String> input) {
        int sum = 0;
        for (int i = 0; i < input.size(); i += 3) {
            var item = findRucksacksCommonItem(input.get(i), input.get(i + 1), input.get(i + 2));
            if (item.isPresent()) sum += convertToPriority(item.get());
        }
        return sum;
    }

    protected Optional<Character> findRucksacksCommonItem(String firstRucksack, String secondRucksack, String thirdRucksack) {
        Set<Character> firstRucksackItems = firstRucksack.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
        Set<Character> secondRucksackItems = secondRucksack.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
        return thirdRucksack
                .chars()
                .mapToObj(e -> (char) e)
                .filter(firstRucksackItems::contains)
                .filter(secondRucksackItems::contains)
                .findFirst();
    }
}
